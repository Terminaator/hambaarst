function searchByName() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("table");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[2];
        if (td) {
            txtValue = td.textContent || td.innerText;
            console.log(txtValue);
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}
jQuery(document).ready(function($) {
    $(".newRow").click(function() {
        $("#dentistId").val($("#" + this.id + "id").text());
        $("#date").val($("#" + this.id + "date").text());
        $("#name").val($("#" + this.id + "name").text()).change();
    });
});

