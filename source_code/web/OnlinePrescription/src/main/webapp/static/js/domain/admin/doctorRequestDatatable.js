/**
 * Created by: Kunal Kanere
 */

$(function () {
    //alert("Ready!");
    ajaxDatatable();
});

function ajaxDatatable() {
    var url = "getDoctorRequest.ajax";
    var status = $( "#status" ).val();
    $('#table1').dataTable({
        "bJQueryUI": true,
        "bProcessing": true,
        "bServerSide": true,
        "bDestroy": true,
        "bAutoWidth": true,
        //"sScrollX": "940px",
        //"sScrollY": "250px",
        "bScrollCollapse": true,
        "aaSorting": [[1, "asc"]],
        "aoColumns": [
            null,
            null,
            null,
            null,
            null,
            {"bSortable" : false}
        ],
        "sAjaxSource": url,
        "fnServerData": function (sSource, aoData, fnCallback) {
            // Add some extra data to the sender
            aoData.push({"name":"status", "value":status});
            $.ajax({
                "dataType": 'json',
                "type": "POST",
                "url": sSource,
                "data": aoData,
                "success": fnCallback
            });
        }
    });
}

function download(){
	var status=$("#status").val();
	window.location.href = "downloadDoctorReport.ajax?status="+status;
}