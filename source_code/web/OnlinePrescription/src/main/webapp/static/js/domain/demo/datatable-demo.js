/**
 * Created by stefan on 5/10/2016.
 */
$(function () {
    //alert("Ready!");
    ajaxDatatable();
});

function ajaxDatatable() {
    var url = "getAllPersonJsonData";

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
            null
        ],
        "sAjaxSource": url,
        "fnServerData": function (sSource, aoData, fnCallback) {
            // Add some extra data to the sender
            aoData.push();
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
