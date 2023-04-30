//Function Post AJAX

//------------- COMMON CLASS ------------
class ApiFacade { // This class is designed like Facade Pattern , will get (data,url) and return a promise 
  constructor() {
    this.baseUrl = 'http://localhost:8080';
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
    return this.sendRequest('GET', url);
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

class FormDataBuilder { // This class is designed like a Builder Pattern , will get (inputList) and return FormData or Object Data 
  constructor(inputList) {
    this.inputList = inputList;
  }

  *[Symbol.iterator]() {
    for (const input of this.inputList) {
      const element = document.querySelector(`#${input.id}`);
      if (element) {
        if (element.type === 'file') {
          console.log(element[0]);
          console.log(element.files);
          console.log(element.files[0]);
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

//------------- Handle AJAX ------------
const apiFacade = new ApiFacade();

const  handleWritePost = () => {

  //Declare Data , note ; id must be the same with object id
  const inputList = [
    { id: 'title' },
    { id: 'category' },
    { id: 'data' },
  ];
  const editorData = editor.getData();
  
  //Use Class Builder to loop all value and get FormData
  const formDataBuilder = new FormDataBuilder(inputList);
  const formData = formDataBuilder.buildFormData();
  formData.append('content', editorData);

  // const objectData = formDataBuilder.buildObjectData();

  //User apiFacade to post data to Server
  apiFacade
    .post('/createnewpost', formData)
    .then((data) => {
      alert('Post created!');
      window.location.href = '/';
    })
    .catch((error) => {
      alert('Error creating post! ' + error);
    });
};

const handleSearch = (inputId, selectId) => {

  const keyword = document.querySelector(`#${inputId}`).value;
  const category = document.querySelector(`#${selectId}`).value;
  apiFacade
  .get('/api/posts')
  .then((data) => {
    alert("get thanh cong !") ;
    console.log(data);
    console.log(data.filter(post => post.title.includes(keyword)));
    console.log(data.filter(post => post.title.includes(keyword) && post.category === category));
  })
  .catch((error) => {
    alert('Error creating post! ' + error);
  });
}
// apiFacade.get('/users')
//   .then((data) => {
//   })
//   .catch((error) => {
//   });



//--------------------Function custom for display--------------------

function changeTitleCreatePost() {
  const titlechange = document.querySelector('#titlechange');
  const titleInput = document.querySelector('.changetileEvent');
  titleInput.addEventListener('input', function (e) {
    let value = e.target.value;
    titlechange.innerText = value;
    document.title = value;
  });
}

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

function handleMenuNavbar() {
  const navbarResponsiveMenuButton = document.querySelector('#navbarResponsiveMenu');
  const navbarResponsive = document.querySelector('#navbarResponsive');
  const navbarIcon = document.querySelector('#navIcon');

  navbarResponsiveMenuButton.addEventListener('click', function () {
    if (navbarIcon.style.display === 'none' && navbarResponsive.style.backgroundColor === 'white') {
      navbarIcon.style.display = 'block';
      navbarResponsive.style.backgroundColor = 'inherit';
      navbarResponsive.style.opacity = '1';
      navbarResponsive.style.marginTop = '0';
      navbarResponsive.style.padding = '0';
      navbarResponsive.style.textAlign = 'left';
    } else {
      navbarIcon.style.display = 'none';
      navbarResponsive.style.backgroundColor = 'white';
      navbarResponsive.style.opacity = '0.6';
      navbarResponsive.style.marginTop = '1rem';
      navbarResponsive.style.padding = '2rem';
      navbarResponsive.style.textAlign = 'center';
    }
  });
}

//------------------Function when document is ready--------------------------------
$(document).ready(function () {
  function fragment() {
    var deferred = $.Deferred();
    var promises = [];

    promises.push(
      $('#nav')
        .load('/templates/product/fragmentjquery/nav.html', function () {})
        .promise()
    );

    promises.push(
      $('#footer')
        .load('/templates/product/fragmentjquery/footer.html', function () {})
        .promise()
    );

    promises.push(
      $('#nav-modal')
        .load('/templates/product/fragmentjquery/modal.html', function () {})
        .promise()
    );

    $.when.apply($, promises).then(function () {
      deferred.resolve();
    });

    return deferred.promise().then(function () {
      handleMenuNavbar();
    });
  }

  fragment();

  $('#example').DataTable({
    // Add search functionality
    searching: true,
    // Add ordering functionality
    ordering: true,
    // Set the initial order to be based on the first column
    order: [[0, 'asc']],
    // Add pagination functionality
    paging: true,
    // Set the number of rows per page to 10
    info: false,
  });
});
