/**
 * Created by stefan on 31/10/2016.
 */
$(document).ready(function(){
    showPescriptionHistory();
});

/**
 * query history
 */
function showPescriptionHistory(){
    $.ajax({
        type: "get",
        url: "prescriptionHistory.ajax",
        data: {},
        dataType: "json",
        success: function (data) {
            if (data.SUCCESS) {
                $('#historyTable').bootstrapTable({
                    columns: [{
                        field: 'PRESCRIPTION_SEQ',
                        title: 'Seq#'
                    }, {
                        field: 'PRESCRIPTION_DATE',
                        title: 'Date'
                    }, {
                        field: 'FIRST_NAME',
                        title: 'First name'
                    }, {
                        field: 'LAST_NAME',
                        title: 'Last name'
                    }, {
                        field: 'TOTAL_PRICE',
                        title: 'Price'
                    }, {
                        field: 'DESCRIPTION',
                        title: 'Description'
                    }
                    ],
                    data: data.P_HISTORY_LIST
                });
            }else{
                console.log(data.ERRMSG);
            }
            waitingDialog.hide();
        },
        error: function (data) {
            console.log(data.ERRMSG)
            waitingDialog.hide();
        }
    });
}