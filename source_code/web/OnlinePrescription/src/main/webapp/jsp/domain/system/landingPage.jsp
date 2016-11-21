<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Landing page</title>
    <%@include file="../../common/common_header.jsp" %>
    <!-- Custom styles -->
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"/>
    <link href="//cdnjs.cloudflare.com/ajax/libs/animate.css/3.1.1/animate.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="//code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"/>
    <link href="static/css/domain/system/landing-page.css" rel="stylesheet">
    <!-- Custom js -->
    <%
        String url = request.getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
    %>
    <script>var baseURL = '<%=baseURL%>';</script>
    <script src="static/js/domain/system/qrcode.min.js"></script>
    <script src="static/js/domain/system/landing-page.js"></script>
</head>

<body>
<header id="first">
    <div class="header-content">
        <div class="inner">
            <h1 class="cursive">Online Prescription</h1>
            <h4>Make your life convenient, efficient and cozy</h4>
            <hr>
            <a href="login.page" class="btn btn-primary btn-xl">Start</a> &nbsp; &nbsp; &nbsp;
            <a href="downloadApk.ajax" class="btn btn-primary btn-xl" role="button" data-toggle="popover"  id="download-apk-button">Download App</a>
        </div>
    </div>
    <video autoplay="" loop="" class="fillWidth fadeIn wow collapse in" data-wow-delay="0.5s"
           poster="https://s3-us-west-2.amazonaws.com/coverr/poster/Big-City-Life.jpg" id="video-background">
        <source src="https://s3-us-west-2.amazonaws.com/coverr/mp4/Big-City-Life.mp4" type="video/mp4">
        Your browser does not support the video tag. I suggest you upgrade your browser.
    </video>
</header>
<div id='qrcodeTable' style="display: none"></div>
</body>
</html>
