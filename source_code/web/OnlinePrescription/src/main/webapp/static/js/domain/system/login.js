/**
 * Created by stefan on 10/10/2016.
 */
/**
 * init
 */
var LOGIN_VAR = {
    LOGIN_WAITING: false,
    CK_USER_NAME:"username",
    CK_LOGIN_ERROR:"login_error"
}

$(document).ready(function () {
    //login form setup
    $("#login-tips").hide();
    $('#loginbtn').on('click', do_login);
    $('#login-form').on('keyup', function (event) {
        if (event.which == 13) {
            do_login();
        }
    })
    $('input').on('focus', function () {
        $(this).css("border-color", "white");
    });
    $('input').on('click', function () {
        $(this).css("border-color", "white");
    });
    $('input').on('blur', function () {
        $(this).css("border-color", "rgba(238, 238, 238, 0.27)");
    });
    $('input').on('keyup', function () {
        console.log($(this).css("border-color"));
        if ($(this).css("border-color") == "rgb(255, 0, 0)")
            $(this).css("border-color", "white");
    });

    //binding click function on kaptcha
    $('#kaptchaImage').click(function () {
        changeCode();
    });
    //if login fail happened, user need to pass verify code when they try to login again
    if (!(!$.cookie(LOGIN_VAR.CK_LOGIN_ERROR)) && $.cookie(LOGIN_VAR.CK_LOGIN_ERROR) == 1) {
        $("#verify_form").show();
    }
    //remember me
    $(".remember-me").on("click", function () {
        var rDom = $("#remember-me-box");
        var status = rDom.attr("check");
        if (status == 0) {
            rDom.removeClass("glyphicon-unchecked");
            rDom.addClass("glyphicon-check");
            rDom.attr("check", 1)
        } else {
            rDom.removeClass("glyphicon-check");
            rDom.addClass("glyphicon-unchecked");
            rDom.attr("check", 0)
            $.cookie(LOGIN_VAR.CK_USER_NAME,null);
        }
    });
    //load remembered username
    var username = $.cookie(LOGIN_VAR.CK_USER_NAME);
    if(!(!username)){
        $("#username").val(username);
        var rDom = $("#remember-me-box");
        rDom.removeClass("glyphicon-unchecked");
        rDom.addClass("glyphicon-check");
        rDom.attr("check", 1)
    }
});

/**
 * change kaptcha
 */
function changeCode() {  //refresh
    $('#kaptchaImage').hide().attr('src', 'code/captcha-image?' + Math.floor(Math.random() * 100)).fadeIn();
    event.cancelBubble = true;
}

/**
 * remember the current username
 */
function rememberMe() {
    var rDom = $("#remember-me-box");
    var status = rDom.attr("check");
    if (status == 1) {
        $.cookie(LOGIN_VAR.CK_USER_NAME, $("#username").val());
    }
}
/**
 * login btn press action
 */
function do_login() {
    if (!checkLoginFields()) {
        $(".subscribe").effect("shake",{times:3}, 300 );
        return;
    }

    if (LOGIN_VAR.LOGIN_WAITING) {
        return;
    }
    $('#loginbtn').attr("disabled", true);
    LOGIN_VAR.LOGIN_WAITING = true;

    var param = {
        username: $("#username").val(),
        password: md5($("#username").val() + $("#password").val()),
        code: $("#kaptcha").val()
    };

    //waiting animation
    $('#waiting-dots').addClass("dotting");

    $.ajax({
        type: "post",
        url: "checkLogin.ajax",
        data: param,
        dataType: "json",
        success: function (data) {
            $('#waiting-dots').removeClass("dotting");
            if (data.SUCCESS == false) {
                loginAlertTips(1, data.ERRMSG);
                $("#verify_form").show();
                $.cookie(LOGIN_VAR.CK_LOGIN_ERROR, 1);
                changeCode();
            } else {
                $.cookie(LOGIN_VAR.CK_LOGIN_ERROR, 0);
                rememberMe()
                //login success
                // loginAlertTips(0,"Login succeeded.")
                window.location.href = data.redirect_url;
            }
            $('#loginbtn').attr("disabled", false);
            LOGIN_VAR.LOGIN_WAITING = false;
        },
        error: function (data) {
            $('#loginbtn').attr("disabled", false);
            LOGIN_VAR.LOGIN_WAITING = false;

            $('#waiting-dots').removeClass("dotting");
            loginAlertTips(2, "Login failed...");
            $("#verify_form").show();
            changeCode();
        }
    });
}

/**
 * check login fields before submission
 */
function checkLoginFields() {
    var result_1 = Validation.checkInput($("#username"), function (obj) {
        return obj.val() != "";
    })
    var result_2 = Validation.checkInput($("#password"), function (obj) {
        return obj.val() != "";
    })

    return result_1 & result_2;
}

/**
 * alert if login success or not
 * status: 0-success 1-fail 2-server error
 */
function loginAlertTips(status, msg) {
    var tips = $("#login-tips");

    if (status == 0) {
        tips.removeClass("alert-danger");
        tips.addClass("alert-success");
    }
    else {
        tips.removeClass("alert-success");
        tips.addClass("alert-danger");
    }
    //<button type="button" class="close" data-dismiss="alert">x</button>'
    tips.html(msg);

    tips.alert();
    tips.fadeTo(3000, 500).slideUp(500);
}