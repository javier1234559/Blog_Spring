//Function Post AJAX

//1. Post Blog Ajax
function createPost() {
  const URL_REQUEST = '/createnewpost';

  // Lấy giá trị từ các input
  const editorData = editor.getData();
  const title = $('#title-input').val();
  const category = $('#category-select').val();
  const fileInput = $('#image-input')[0].files[0];
  // Kiểm tra giá trị không được null
  if (title == '' || category == '' || fileInput == undefined || editorData == '') {
    alert('Please fill in all fields!');
    return;
  }
  // Đưa giá trị vào formData
  const formData = new FormData();
  formData.append('data', fileInput);
  formData.append('title', title);
  formData.append('category', category);
  formData.append('content', editorData);
  console.log(formData);

  $.ajax({
    url: URL_REQUEST,
    type: "POST",
    data: formData,
    contentType: false,
    processData: false,
    success: function (data) {
      alert('Post created!');
      window.location.href = "/";
    },
    error: function (xhr, status, error) {
      alert('Error creating post! ' + error);
    },
  });

}

function getDetailPost(id) {
  console.log(id);
  $.ajax({
    url: `posts/${id}`,
    type: 'GET',
    success: function (res) {
      console.log(res);
    },
    error: function (xhr) {
      console.log('An error occured: ' + xhr.status + ' ' + xhr.statusText);
    },
  });
}





//Function custom for display


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


function handleMenuNavbar(){
  const navbarResponsiveMenuButton = document.querySelector("#navbarResponsiveMenu");
  const navbarResponsive = document.querySelector("#navbarResponsive");
  const navbarIcon = document.querySelector("#navIcon");

  navbarResponsiveMenuButton.addEventListener("click", function() {
    
    if (navbarIcon.style.display === 'none' && navbarResponsive.style.backgroundColor === 'white') {
      navbarIcon.style.display = 'block';
      navbarResponsive.style.backgroundColor  = 'inherit';
      navbarResponsive.style.opacity = '1';
      navbarResponsive.style.marginTop = '0';
      navbarResponsive.style.padding = '0';
      navbarResponsive.style.textAlign = 'left';
    } else {
      navbarIcon.style.display = 'none';
      navbarResponsive.style.backgroundColor  = 'white';
      navbarResponsive.style.opacity = '0.6';
      navbarResponsive.style.marginTop = '1rem';
      navbarResponsive.style.padding = '2rem';
      navbarResponsive.style.textAlign = 'center';
    }
  });
  
}



//Function when document is ready
$(document).ready(function () {

  function fragment() {
    var deferred = $.Deferred();
    var promises = [];
  
    promises.push($('#nav').load('/templates/product/fragmentjquery/nav.html', function() {
    }).promise());
  
    promises.push($('#footer').load('/templates/product/fragmentjquery/footer.html', function() {
    }).promise());
  
    promises.push($('#nav-modal').load('/templates/product/fragmentjquery/modal.html', function() {
    }).promise());
  
    $.when.apply($, promises).then(function() {
      deferred.resolve();
    });
  
    return deferred.promise().then(function() {
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

