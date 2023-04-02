//Function Post AJAX

//1. Post Blog Ajax
function createPost(){
    const URL_REQUEST = "/posts/createpost"

   // Lấy giá trị từ các input
   const editorData = editor.getData();
   const title = $('#title-input').val();
   const category = $('#category-select').val();
   const fileInput = $('#image-input')[0].files[0];
   // Kiểm tra giá trị không được null
   if (title == "" || category == "" || fileInput == undefined || editorData == "") {
     alert("Please fill in all fields!");
     return;
   }
   // Đưa giá trị vào FormData
   var formData = new FormData();
   formData.append('image', fileInput);
   formData.append('title', title);
   formData.append('category', category);
   formData.append('editorData', editorData);
   console.log(formData);
    console.log(title);
   $.ajax({
     url: URL_REQUEST,
     type: 'POST',
     data: formData,
     processData: false,
     contentType: false,
     success: function(data) {
       alert('Post created!');
     },
     error: function(xhr, status, error) {
       alert('Error creating post!');
     }
   });
}

function getDetailPost(id){
  console.log(id);
  $.ajax({
      url: `posts/${id}`,
      type: 'GET',
      success: function(res) {
          console.log(res);
      },
      error: function(xhr){
        console.log("An error occured: " + xhr.status + " " + xhr.statusText);
      }
  });
}

//Function when document is ready
$(document).ready(function() {
  fragment ();

  //Fragment in jquery 
  function fragment () {
    $('#nav').load('/templates/product/fragmentjquery/nav.html');
    $('#footer').load('/templates/product/fragmentjquery/footer.html');
    $('#nav-modal').load('/templates/product/fragmentjquery/modal.html');
  }
});

//Function custom for display 
function changeTitleCreatePost(){
  const titlechange = document.querySelector("#titlechange");
  const titleInput = document.querySelector(".changetileEvent");
  titleInput.addEventListener("input", function(e) {
    let value = e.target.value ;
    titlechange.innerText = value;
    document.title = value;
  });
}
changeTitleCreatePost();