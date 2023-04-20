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

