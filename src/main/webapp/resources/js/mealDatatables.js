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
    var date = form.find("input[name='" + "dateTime" + "']").val();
    form.find("input[name='" + "dateTime" + "']").val(date.replace(" ","T"));
    return form.serialize();
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
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.replace("T"," ").substring(0, 16);
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
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