$(document).ready(function(){
    activeSideBarItem();
});

function activeSideBarItem(){
    $("a[name='sidebar_item']").removeClass("active");
    var page = $("body").attr("page");
    $("#"+page).addClass("active");
}
