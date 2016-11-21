$(document).ready(function(){
    getPatientList();
});

function getPatientList(){
    waitingDialog.show();
    var param = {
    };
    $.ajax({
        type: "post",
        url: "viewpatients.ajax",
        data: param,
        dataType: "json",
        success: function (data) {
            showPatientTable(data);
            waitingDialog.hide();

        },
        error: function (data) {
            waitingDialog.hide();
        }
    });
}

function showPatientTable(data){
    var patientTableHtml = "  <thead class='thead-inverse'> <tr> <th>Patient ID</th> <th>Firstname</th> <th>Lastname</th> <th>DOB</th> <th>Medicare#</th> <th>Phone#</th> <th>Status</th> <th>Action</th> </tr></thead>"
    $(data).each(function(idx,obj){
        var rowStr = "<tr>";
        rowStr+="<td>"+obj.patient_id+"</td>";
        rowStr+="<td>"+obj.first_name+"</td>";
        rowStr+="<td>"+obj.last_name+"</td>";
        rowStr+="<td>"+obj.date_of_birth+"</td>";
        rowStr+="<td>"+obj.medicare_no+"</td>";
        rowStr+="<td>"+obj.phone_no+"</td>";
        //translate status
        var statusStr = "";
        if(obj.status == "pending"){
            statusStr+="Pending";
            rowStr+="<td>"+statusStr+"</td>";
            rowStr+="<td><a href='javascript:void(0)' onclick=approvePatient("+obj.patient_id+")>Approve</a> | <a href='javascript:void(0)' onclick=rejectPatient("+obj.patient_id+")>Reject</a></td>";
            rowStr+="</tr>";
            patientTableHtml+=rowStr;
        }
        if(obj.status == "approved"){
            statusStr+="Approved";
            rowStr+="<td>"+statusStr+"</td>";
            rowStr+="<td><a href='javascript:void(0)' onclick=rejectPatient("+obj.patient_id+")>Reject</a></td>";
            rowStr+="</tr>";
            patientTableHtml+=rowStr;
        }

        if(obj.status == "rejected"){
            statusStr+="Rejected";
            rowStr+="<td>"+statusStr+"</td>";
            rowStr+="<td><a href='javascript:void(0)' onclick=approvePatient("+obj.patient_id+")>Approve</a> </td>";
            rowStr+="</tr>";
            patientTableHtml+=rowStr;
        }

    });
    $("#patientTable").html(patientTableHtml);
}


function approvePatient(patientId){
    waitingDialog.show();
    var param = {
        patient_id:patientId
    };
    $.ajax({
        type: "post",
        url: "approvePatient.ajax",
        data: param,
        dataType: "json",
        success: function (data) {
            if(data.SUCCESS){
                getPatientList();
                waitingDialog.hide();
            }
        },
        error: function (data) {
            waitingDialog.hide();
        }
    });}
function rejectPatient(patientId){
    waitingDialog.show();

    var param = {
        patient_id:patientId
    };
    $.ajax({
        type: "post",
        url: "rejectPatient.ajax",
        data: param,
        dataType: "json",
        success: function (data) {
            if(data.SUCCESS){
                getPatientList();
                waitingDialog.hide();
            }
            console.log("Rejected "+patientId+" success: "+data.success)
        },
        error: function (data) {
            waitingDialog.hide();
        }
    });}