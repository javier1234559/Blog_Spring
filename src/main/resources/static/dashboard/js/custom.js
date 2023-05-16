//Function Post AJAX
//--------------- Global Data -------------
const SERVER_URL = 'http://localhost:8080';

//------------- COMMON CLASS ------------
class ApiFacade {
  // This class is designed like Facade Pattern , will get (data,url) and return a promise
  constructor() {
    this.baseUrl = SERVER_URL;
  }

  sendRequest(method, url, data) {
    const requestUrl = this.baseUrl + url;

    return new Promise((resolve, reject) => {
      $.ajax({
        url: requestUrl,
        type: method,
        data: data,
        contentType: false,
        processData: false,
        success: function (data) {
          resolve(data);
        },
        error: function (xhr, status, error) {
          reject(error);
        },
      });
    });
  }

  get(url) {
    console.log(url);
    return this.sendRequest('GET', url, null);
  }

  post(url, data) {
    return this.sendRequest('POST', url, data);
  }

  put(url, data) {
    return this.sendRequest('PUT', url, data);
  }

  delete(url) {
    return this.sendRequest('DELETE', url);
  }
}

class FormDataBuilder {
  // This class is designed like a Builder Pattern , will get (inputList) and return FormData or Object Data
  constructor(inputList) {
    this.inputList = inputList;
  }

  *[Symbol.iterator]() {
    for (const input of this.inputList) {
      const element = document.querySelector(`#${input.id}`);
      if (element) {
        if (element.type === 'file') {
          yield [input.id, element.files[0]];
        } else {
          yield [input.id, element.value];
        }
      }
    }
  }

  buildFormData() {
    const formData = new FormData();
    for (const [name, value] of this) {
      if (value === null) alert('Please fill all input');
      formData.append(name, value);
    }
    return formData;
  }

  buildObjectData() {
    const data = {};
    for (const [name, value] of this) {
      data[name] = value;
    }
    return data;
  }
}

function isNullOrEmpty(inputList) {
  for (let i = 0; i < inputList.length; i++) {
    const input = inputList[i];
    const value = document.getElementById(input.id).value;
    
    if (value === null || value.trim() === '') {
      return true; 
    }
  }
  
  return false; 
}


//------------- Handle AJAX ------------

const apiFacade = new ApiFacade();

const handleUpdateAccount = () => {

  const inputList = [
    {
      id: 'iduser',
    },
    {
      id: 'imagefile',
    },
    {
      id: 'name',
    },
    {
      id: 'email',
    },
    {
      id: 'phone',
    },
    {
      id: 'description',
    },
  ];

 


  const iduser = document.getElementById('iduser').value;
  console.log(iduser);
  const formDataBuilder = new FormDataBuilder(inputList);
  const formData = formDataBuilder.buildFormData();

  const roleGroup = document.getElementById('roleGroup');
  const selectedInputrole = roleGroup.querySelector('input[name="role-input"]:checked');
  const role = selectedInputrole ? selectedInputrole.value : 'ROLE_USER';

  const statusGroup = document.getElementById('statusGroup');
  const selectedInputstatus = statusGroup.querySelector('input[name="status-input"]:checked');
  const status = selectedInputstatus ? selectedInputstatus.value : '1';

  formData.append('role', role);
  formData.append('status', status);

  apiFacade
    .put(`/api/users/${iduser}`, formData)
    .then((data) => {
      alert('Update Successfully !');
    })
    .catch((error) => {
      alert('Some error of update user information');
      console.log(error);
    });
};

const handleCreateAccount = () =>{
  const inputList = [
    {
      id: 'pass',
    },
    {
      id: 'imagefile',
    },
    {
      id: 'name',
    },
    {
      id: 'email',
    },
    {
      id: 'phone',
    },
    {
      id: 'description',
    },
  ];

  if(isNullOrEmpty(inputList)){
    alert("Please check fill all input");
    return ;
  }

  const formDataBuilder = new FormDataBuilder(inputList);
  const formData = formDataBuilder.buildFormData();

  const roleGroup = document.getElementById('roleGroup');
  const selectedInputrole = roleGroup.querySelector('input[name="role-input"]:checked');
  const role = selectedInputrole ? selectedInputrole.value : 'ROLE_USER';

  const statusGroup = document.getElementById('statusGroup');
  const selectedInputstatus = statusGroup.querySelector('input[name="status-input"]:checked');
  const status = selectedInputstatus ? selectedInputstatus.value : '1';

  formData.append('role', role);
  formData.append('status', status);

  apiFacade
    .post(`/api/users/createuser`, formData)
    .then((data) => {
      alert('Create new User successfully !');
    })
    .catch((error) => {
      alert('Some error of create user ');
      console.log(error);
    });
};

//--------------------Function custom for display--------------------
const handleDisplayImageUser = () => {
  const avatar = document.getElementById('avatar');
  apiFacade
    .get('/api/users/userImage')
    .then((data) => {
      let imageurl = data;
      let avatarImageUser = SERVER_URL + '/uploaded/' + imageurl;

      avatar.src = avatarImageUser;
      avatarComment.src = avatarImageUser;
    })
    .catch((error) => {
      console.error('Error:', error);
    });
};

function checkFileSize(fileId) {
  const fileInput = document.getElementById(fileId);
  const file = fileInput.files[0];
  const fileSize = file.size / 1024 / 1024; // Convert bytes to MB
  if (fileSize > 2) {
    alert('The selected file is no larger than 2MB.');
    return false;
  }
  return true;
}

function previewImage(imageId, fileId) {
  const image = document.getElementById(imageId);
  const fileInput = document.getElementById(fileId);
  fileInput.addEventListener('change', function () {
    if (!checkFileSize(fileId)) {
      fileInput.value = ''; // Clear the file input to allow the user to select a new file
      return;
    }
    const file = fileInput.files[0];
    const url = URL.createObjectURL(file);
    console.log(url);
    image.style.backgroundImage = `url(${url})`;
  });
}
//------------------Function when document is ready--------------------------------
$(document).ready(function() {
  fragment ();

  //Fragment in jquery 
  function fragment () {
    $('#sidebar').load('/templates/dashboard/fragmentjquery/sidebar.html');
    $('#topbar').load('/templates/dashboard/fragmentjquery/topbar.html');
    $('#footer').load('/templates/dashboard/fragmentjquery/footer.html');
    $('#modal').load('/templates/dashboard/fragmentjquery/modal.html');
  }
});

$(document).ready(function () {

  $('#my-table').DataTable({
    // Add search functionality
    searching: true,
    // Add ordering functionality
    ordering: true,
    // Set the initial order to be based on the first column
    order: [[0, 'asc']],
    // Add pagination functionality
    paging: true,
    // Set the number of rows per page to 10
    pageLength: 10,
  });
});

//------------------------ Function Global Invoke ----------------------
handleDisplayImageUser();
