var ajaxUrl = "ajax/profile/meals/";
var datatableApi;


$('#dateTime').datetimepicker({
    format: 'Y-m-d H:i'
});

$('#startDate, #endDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d'
});

$('#startTime, #endTime').datetimepicker({
    datepicker: false,
    format: 'H:i'
});

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

function updates(){
    if (form.find("input[name='" + "calories" + "']").val().length<1) {
        form.find("input[name='" + "calories" + "']").val(0)
    }
    var date = form.find("input[name='" + "dateTime" + "']").val();
    form.find("input[name='" + "dateTime" + "']").val(setT(date));
    return form.serialize();
}

function delT(date){
    return date.replace("T"," ");
}

function setT(date){
    return date.replace(" ","T");
}

function delTInFofm(){
    var date = $('#editRow').find("input[name='" + "dateTime" + "']").val();
    $('#editRow').find("input[name='" + "dateTime" + "']").val(delT(date).substring(0,16));
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": delT
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "Edit",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExceed", data.exceed);
        },
        "initComplete": makeEditable
    });
});