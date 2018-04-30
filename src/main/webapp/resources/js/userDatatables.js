var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

function check(id, enabled) {
    var d = 'tr[id=\'' + id + '\']';
    var s = enabled?"enabled":"disabled";
    $.ajax({
        type: "POST",
        url: ajaxUrl+id,
        data: "enabled="+ enabled,
        success: function () {
            $(d).attr("data-mealExceed", !enabled);
            successNoty("User width id = " + id + " is " + s);
        },
        error: function () {
            $(d).find(":input").prop("checked", !enabled);
        }
    });
}