//Function Post AJAX
//--------------- Global Data -------------
let posts = null;
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

//------------- Handle AJAX ------------
const apiFacade = new ApiFacade();

const handleWritePost = () => {
  //Declare Data , note ; id must be the same with object id
  const inputList = [{ id: 'title' }, { id: 'category' }, { id: 'data' }];
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

const handleSearch = () => {
  apiFacade
    .get('/api/posts')
    .then((data) => {
      posts = data;
      console.log(data);
      handlefilterPostsSearch();
    })
    .catch((error) => {
      console.error('Error:', error);
    });
};

const handlefilterPostsSearch = () => {
  const keyword = document.querySelector('#keywordSearch').value;
  const category = document.querySelector('#categorySelected').value;

  const filteredPosts = posts.filter((post) => {
    const keywordMatch = post.title.includes(keyword);
    const categoryMatch = category === 'All' ? true : post.category === category;
    console.log(post.category);
    console.log(category);

    return keywordMatch && categoryMatch;
  });
  console.log('Filtered posts:', filteredPosts);

  const container = document.querySelector('#searchResults');
  container.innerHTML = '';
  filteredPosts.forEach((post) => {
    const html = `
      <a href="/posts/${post.idpost}">
      <div class="d-flex justify-content-center">
        <div class="main-container-searchItems">
          <img class="main-image" id="image-searchItems" src="${SERVER_URL}/uploaded/${post.imageurl}"/>
          <div class="main-content">
            <h2 class="main-title" id="title-searchItems">${post.title}</h2>
            <div class="tags-container">
              <div class="tag" id="category-searchItems">${post.category}</div>
            </div>
          </div>
        </div>
      </div>
    </a>
    `;

    container.innerHTML += html;
  });
};

const handleRegister = (event) => {
  event.preventDefault();
  const inputList = [{ id: 'name' }, { id: 'email' }, { id: 'pass' }];

  const formDataBuilder = new FormDataBuilder(inputList);
  const formData = formDataBuilder.buildFormData();
  console.log(formData);
  apiFacade
    .post('/api/users/register', formData)
    .then((data) => {
      alert('Register successfully !');
      window.location.href = '/login';
    })
    .catch((error) => {
      alert('Error  register ! ' + error);
    });
};

const handleDisplayComment = (idpost) => {
  let listData = null; // get all comment here

  $(document).ready(function () {
    // let idpost  = document.getElementById("#idpost").value;
    apiFacade
      .get(`/api/post/comments/${idpost}`)
      .then((data) => {
        listData = data;
        console.log(data);
        const container = document.querySelector('#commentAjaxContainer');
        container.innerHTML = '';
        listData.reverse();
        listData.forEach((comment) => {
          const html = `
          <div class="mb-3">
            <div class="d-flex post-comment-input-custom mt-5">
                <img src="${SERVER_URL}/uploaded/${comment.imageurl}" class="comment-avatar-custom" id="imageurl" alt="" />
                <div class="ms-4 detail-post-commented-custom">
                    <p class="m-0 fs-4 font-inter comment-title-custom" id="name">${comment.name}</p>
                    <div>
                        <small style="display:none" >${comment.date}</small>
                    </div>
                    <div>
                        <small class="comment-content-custom">${comment.content}</small>
                    </div>
                </div>
              </div>
          </div>`;

          container.innerHTML += html;
        });
      })
      .catch((error) => {
        alert('Error:', error);
      });
  });
};

const handleCreateComment = (event) => {
  event.preventDefault();

  const inputList = [{ id: 'idpost' }, { id: 'content' }];

  const formDataBuilder = new FormDataBuilder(inputList);
  const formData = formDataBuilder.buildFormData();
  console.log(formData);

  apiFacade
    .post('/api/post/comments', formData)
    .then((data) => {
      handleDisplayComment(formData.get('idpost'));

      const clearValue = document.getElementById('content'); //clear value of comment input
      clearValue.value = '';
    })
    .catch((error) => {
      alert('Some error of comment');
      console.log(error);
    });
};

const handleUpdateInfoUserSetting = (event) => {
  event.preventDefault();

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
  console.log(formData.get('imagefile'));

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

const handleUpdatePost = (id) => {
  //Declare Data , note ; id must be the same with object id
  const inputList = [{ id: 'title' }, { id: 'category' }, { id: 'imagefile' }];
  const editorData = editor.getData();

  //Use Class Builder to loop all value and get FormData
  const formDataBuilder = new FormDataBuilder(inputList);
  const formData = formDataBuilder.buildFormData();
  formData.append('content', editorData);

  //User apiFacade to post data to Server
  console.log(formData);
  apiFacade
    .put(`/api/posts/${id}`, formData)
    .then((data) => {
      alert('Post saved!');
      window.location.href = '/updatepost';
    })
    .catch((error) => {
      alert('Error occur when saving post! ' + error);
    });
};

const handleDeletePost = () => {
  const id = document.getElementById('idpost').value;

  console.log(id);
  apiFacade
    .delete(`/api/posts/${id}`)
    .then((data) => {
      alert('Post deleted!');
      window.location.href = '/updatepost';
    })
    .catch((error) => {
      alert('Error occur when saving post! ' + error);
    });
};

const handleDisplayImageUser = () => {
  const avatar = document.getElementById('avatar');
  const avatarComment = document.getElementById('avatarComment');
  apiFacade
  .get('/api/users/userImage')
  .then((data) => {
    let imageurl = data;
    let avatarImageUser = SERVER_URL + '/uploaded/' + imageurl;

    avatar.src = avatarImageUser ;
    avatarComment.src = avatarImageUser;
  })
  .catch((error) => {
    console.error('Error:', error);
  });
};

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

function handleParseHTMLPost() {
  const htmlElements = document.querySelectorAll('.htmlparsePost');

  for (let i = 0; i < htmlElements.length; i++) {
    const htmlString = htmlElements[i].innerHTML;
    const $html = $(htmlString);
    const textContent = $html.text();
    const formattedText = `${textContent.slice(0, 15)}...`;

    // create a new element to replace the old one
    const newElement = document.createElement('p');
    newElement.className = 'card-text-custom text-secondary-emphasis';
    newElement.textContent = formattedText;

    // insert the new element after the old one, then remove the old one
    htmlElements[i].parentNode.insertBefore(newElement, htmlElements[i].nextSibling);
    htmlElements[i].parentNode.removeChild(htmlElements[i]);
  }
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
//------------------------ Function Global Invoke ----------------------
handleParseHTMLPost();
handleParseHTMLTopPost();
handleDisplayImageUser();

// handleLogin();
// handleRegister();
