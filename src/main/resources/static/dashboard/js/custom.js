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

