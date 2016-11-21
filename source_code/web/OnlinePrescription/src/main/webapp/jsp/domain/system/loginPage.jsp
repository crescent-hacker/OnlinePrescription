<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Signin page</title>
    <%@include file="../../common/common_header.jsp" %>
    <!-- Custom styles -->
    <link href="static/css/domain/system/login.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Grand+Hotel' rel='stylesheet' type='text/css'>
    <!-- Custom js -->
    <script src="static/vendor/jquery-vibrate/jquery.vibrate.min.js" type="application/javascript"></script>
    <script src="static/js/common/validation/input-validation.js" type="application/javascript"></script>
    <script src="static/js/common/crypto-util/md5.min.js" type="application/javascript"></script>
    <script src="static/vendor/jquery-cookies/jquery.cookie.js" type="application/javascript"></script>
    <script src="static/js/domain/system/login.js" type="application/javascript"></script>
    <script src="static/vendor/jquery-ui/jquery-ui.min.js" type="application/javascript"></script>
</head>

<body>
<%--<%@include file="../../common/navigation.jsp" %>--%>

<div class="main main-bg">
    <video id="video_background" preload="auto" autoplay="autoplay" loop="loop" muted="muted" volume="0">
        <source src="static/img/domain/system/login-bg.webm" type="video/webm">
        Video not supported
    </video>
    <!--    Change the image source '/images/video_bg.jpg')" with your favourite image.     -->

    <div class="cover black" data-color="black"></div>

    <!--   You can change the black color for the filter with those colors: blue, green, red, orange       -->

    <div class="container center-container">
        <h1 class="logo cursive">
            <a href="#" class="big-title">Online Prescription</a>
        </h1>
        <!--  H1 can have 2 designs: "logo" and "logo cursive"           -->

        <div class="content">
            <h4 class="motto"></h4>
            <div class="subscribe">
                <div class="row">
                    <div id="login-form" class="col-md-4 col-md-offset-4 col-sm6-6 col-sm-offset-3">
                        <div class="form-group">
                            <label class="sr-only" for="username">User name</label>
                            <input id="username" type="text" class="input-lg form-control transparent"
                                   placeholder="User name..." value="">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="password">Password</label>
                            <input id="password" type="password" class="input-lg form-control transparent"
                                   placeholder="Password...">
                        </div>
                        <div id="verify_form" class="form-group not_show">
                            <input name="j_code" type="text" id="kaptcha" maxlength="4"
                                   class="input-lg form-control transparent code-input" placeholder="Verify code"/>
                            <img src="code/captcha-image" id="kaptchaImage" class="code-img transparent"/>
                        </div>
                        <div class="remember-me">
                            <a href="javascript:void(0)"><span id="remember-me-box" class="glyphicon glyphicon-unchecked" check=0></span>&nbsp;&nbsp;Remember me</a>
                        </div>
                        <button id="loginbtn" class="btn btn-block btn-ghost btn-lg">Sign In <span
                                id="waiting-dots"></span></button>
                        <div class="forget-pwd-button">
                            <a href="#">Forgot password?</a>
                        </div>
                        <div class="register-button">
                            <a href="register.page"><span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>&nbsp;&nbsp;Create an account</a>
                        </div>
                        <%--<div class="forget-password">--%>
                        <%--</div>--%>
                        <div class="alert fade login-tips" id="login-tips">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
