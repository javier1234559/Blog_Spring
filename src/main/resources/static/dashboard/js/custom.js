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
    const element = document.getElementById(input.id);
    if(element === null){
      console.log(input);
      return false;
    } 

    console.log(element.type);
    if (element.type === 'file') {
      const file = element.files[0];
      if (file === undefined) {
        return true;
      }
    } else {
      const value = element.value;
      if (value === null || value.trim() === '') {
        return true;
      }
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

const handleCreateAccount = () => {
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

  if (isNullOrEmpty(inputList)) {
    alert('Please check fill all input');
    return;
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

const handleDeleteTopPost = (id) => {
  if (id === null || id === 0) return;
  console.log(id);
  apiFacade
    .delete(`/api/topposts/${id}`)
    .then((data) => {
      alert("Delete Successfully !")
      console.log(data);
      handleDisplayTopPostList();
    })
    .catch((error) => {
      alert('Some error of delete Top Post ');
      console.log(error);
    });
};

const handleDisplayTopPostList = () => {
  
  apiFacade
    .get(`/api/topposts`)
    .then((data) => {
      let listData = data;
      console.log(data);
      const container = document.querySelector('#topPostAjaxContainer');
      container.innerHTML = '';
      listData.forEach((post) => {
        const html = `
          <div class="d-flex category-setting-item">
              <div class="category-setting-item-image">
                  <img src="${SERVER_URL}/uploaded/${post.imageurl}" alt="Top Post" />
              </div>
              <div class="category-setting-item-content" style="overflow:hidden;">
                <p class="category-setting-item-content-heading">${post.title}</p>
                <p class="category-setting-ttem-content-content">
                  <div class="htmlparse" style="display:contents">${post.content}</div>
                </p>
              </div>
              <div style="cursor: pointer;" class="pointer p-4" onclick="handleDeleteTopPost(${post.idpost})">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-lg" viewBox="0 0 16 16">
                  <path d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8 2.146 2.854Z" />
                </svg>
              </div>
            </div>
        `;
        container.innerHTML += html;
      });

      handleParseHTMLTopPost();
    })
    .catch((error) => {
      alert('Error:', error);
    });
};

const handleAddTopPost = (event, idpost) => {
  event.preventDefault();

  const formData = new FormData();
  formData.append('idpost', idpost);

  apiFacade
    .post(`/api/topposts/addTopPost`, formData)
    .then((data) => {
      alert('Done');
      handleDisplayTopPostList();
      console.log(data);
    })
    .catch((error) => {
      alert('Some error of add Top Post ');
      console.log(error);
    });
};

const handlePublishBanner = (event) => {
  event.preventDefault();

  const inputList = [
    {
      id: 'imagefile',
    },
    {
      id: 'title',
    },
    {
      id: 'subtitle',
    },
    {
      id: 'category',
    }
  ];

  if (isNullOrEmpty(inputList)) {
    alert('Please check fill all input');
    return;
  }

  const formDataBuilder = new FormDataBuilder(inputList);
  const formData = formDataBuilder.buildFormData();

  console.log(formData);

  apiFacade
    .post(`/api/banners/uploadbanner`, formData)
    .then((data) => {

      alert('Publish Banner successfully !');
    })
    .catch((error) => {
      alert('Some error of create user ');
      console.log(error);
    });

};

const handlePublishDisplayBanner = () => {

  let checkcategory = document.getElementById("category").value;
  const category = checkcategory || 'HomeBanner';

  apiFacade
    .get(`/api/banners/${category}`)
    .then((data) => {
      console.log(data);
      document.getElementById('title').value = data.title;
      document.getElementById('subtitle').value = data.subtitle;
      document.getElementById('category').value = data.category;
      document.getElementById('imageBanner').src = `${SERVER_URL}/uploaded/banners/${data.category}`;

    })
    .catch((error) => {
      console.log('Error:', error);
      document.getElementById('title').value = '';
      document.getElementById('subtitle').value = '';
      document.getElementById('imageBanner').src = "https://cdn.vectorstock.com/i/preview-1x/65/30/default-image-icon-missing-picture-page-vector-40546530.jpg";

    });
};



//--------------------Function custom for display--------------------
function handleParseHTMLTopPost() {
  const htmlElements = document.querySelectorAll('.htmlparse');

  for (let i = 0; i < htmlElements.length; i++) {
    const htmlString = htmlElements[i].innerHTML;
    const $html = $(htmlString);
    const textContent = $html.text();
    const formattedText = `${textContent.slice(0, 15)}...`;

    // create a new element to replace the old one
    const newElement = document.createElement('p');
    newElement.className = 'category-setting-ttem-content-content';
    newElement.textContent = formattedText;

    // insert the new element after the old one, then remove the old one
    htmlElements[i].parentNode.insertBefore(newElement, htmlElements[i].nextSibling);
    htmlElements[i].parentNode.removeChild(htmlElements[i]);
  }
}

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
    image.src = url;
  });
}

function hideSpinner() {
  var spinnerElement = document.getElementById("spinner");
  spinnerElement.classList.add("hide");
  setTimeout(function() {
      spinnerElement.style.display = "none";
  }, 1000);
}

//------------------Function when document is ready--------------------------------
$(document).ready(function () {
  fragment();

  hideSpinner();
  //Fragment in jquery
  function fragment() {
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
handleParseHTMLTopPost();
