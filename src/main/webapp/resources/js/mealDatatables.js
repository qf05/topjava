var ajaxUrl = "ajax/meals/";
var updateUrl = "ajax/meals/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#mealdatatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
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
                "desc"
            ]
        ]
    });
    makeEditable();
});

function filter() {
    updateUrl = "ajax/meals/filter/";
    mealsFilter = $("#filter").serialize();
    updateTable();
}

function cleanfilter() {
    $(filter).find(":input").val("");
    mealsFilter = "";
    updateUrl = "ajax/meals/";
    updateTable();
}