var ajaxUrl = "ajax/meals/";
var updateUrl = ajaxUrl;
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
    updateUrl = ajaxUrl + "filter/";

    updateTable();
}

function cleanfilter() {
    $(filter).find(":input").val("");
    updateUrl = ajaxUrl;
    updateTable();
}

function updateTable() {
    updateUrl===("ajax/meals/filter/")?$.post(updateUrl, $("#filter").serialize(), function (data){
        datatableApi.clear().rows.add(data).draw()
    }):
        updates(updateUrl);
}