/**
 * @Author Kunal Kanere
 */
function pharmacyAction(value){
	if(value == 'approved' || value == 'rejected'){
		//alert("action called");
		$("#loading").css("display","block");
		$("#approveBtn").attr('disabled','disabled');
		$("#rejectBtn").attr('disabled','disabled');
		//Ajax call
		var id = $("#pharmId").val();
		var url = "approveRejectPharmacyRequest.ajax";
		var msg = "pharmId="+id+"&action="+value;

		$.ajax({
			url: url,
			type:'post',
			data:msg,
			dataType:'text',
	        success: function(resp){   
	        	resp = resp.replace(/^\s+|\s+$/g,"");
	        //	alert(resp);
	        	if(resp=="success"){
	        		$("#loading").css("display","none");
	        		alert("Action performed successfully, you will be redirected");
	        		window.location.href = "viewPharmacyRequest.page";
	        	}else{
	        		$("#loading").css("display","none");
	        		$("#approveBtn").removeAttr('disabled');
	        		$("#rejectBtn").removeAttr('disabled');
	        		alert("Could not complete the request");
	        	}
	        }
		});
		
	}else{
		window.location.href = "viewPharmacyRequest.page";
	}
}