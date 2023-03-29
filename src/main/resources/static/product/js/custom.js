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
$(document).ready(function() {
  fragment ();
  
  function fragment () {
    $('#nav').load('/templates/product/fragmentjquery/nav.html');
    $('#footer').load('/templates/product/fragmentjquery/footer.html');
    $('#nav-modal').load('/templates/product/fragmentjquery/modal.html');
  }
});