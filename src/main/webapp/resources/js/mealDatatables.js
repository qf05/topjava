var ajaxUrl = "ajax/meals/";
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
                "asc"
            ]
        ]
    });
    makeEditable();
});

function filter() {
    f = false;
    var form = $("#filter")
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: form.serialize(),
        success: function (data) {
            datatableApi.clear().rows.add(data).draw();
        }

    });
}
    function cleanfilter(){
        $(filter).find(":input").val("");
    // startTime.clear;
    // startTime = "";

    f = true;
    updateTable();
}