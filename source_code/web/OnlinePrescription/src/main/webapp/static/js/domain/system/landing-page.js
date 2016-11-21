$(document).ready(function(){
    genQRCode();
    $('#download-apk-button').popover({
        title:"<span style='font-size:20px'>Android app</span>",
        content:getQRCode,
        html:true,
        trigger:'hover'
    });
});

function genQRCode(){
    var qrcode = new QRCode("qrcodeTable", {
        text: baseURL+"downloadApk.ajax",
        width: 200,
        height: 200,
        colorDark : "#000000",
        colorLight : "#ffffff",
        correctLevel : QRCode.CorrectLevel.H
    });
}

function getQRCode(){
    return $('#qrcodeTable').html();
}