<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Signin page</title>
    <%@include file="../../common/common_header.jsp" %>
    <!-- Custom styles -->
    <link href="static/css/domain/system/login.css" rel="stylesheet">
    <link href="static/css/domain/system/register.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Grand+Hotel' rel='stylesheet' type='text/css'>
    <!-- Custom js -->
    <script src="static/js/common/validation/input-validation.js" type="application/javascript"></script>
    <script src="static/js/common/crypto-util/md5.min.js" type="application/javascript"></script>
    <script src="static/vendor/jquery-cookies/jquery.cookie.js" type="application/javascript"></script>
    <script src="static/js/domain/system/register.js" type="application/javascript"></script>
    <script src="static/vendor/jquery-ui/jquery-ui.min.js" type="application/javascript"></script>
</head>

<body>
<div class="main main-bg">
    <video id="video_background" preload="auto" autoplay="autoplay" loop="loop" muted="muted" volume="0">
        <source src="static/img/domain/system/login-bg.webm" type="video/webm">
        Video not supported
    </video>
    <!--    Change the image source '/images/video_bg.jpg')" with your favourite image.     -->

    <div class="cover black" data-color="black"></div>
    <!--   You can change the black color for the filter with those colors: blue, green, red, orange       -->
    <div class="content">
        <%--<h1 class="logo cursive">--%>
            <%--Online Prescription--%>
        <%--</h1>--%>
        <div class="container" id="register-form">

            <div class="row">
                <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
                    <form role="form">
                        <div class="alert fade register-tips" id="register-tips"></div>
                        <h1 class="motto">Please Sign Up
                            <%--<small class="subscribe">It's free and always will be.</small>--%>
                        </h1>
                        <hr class="colorgraph">

                        <!-- common fields -->
                        <div class="form-group">
                            <input type="text" name="username" id="username"
                                   class="form-control input-lg transparent"
                                   placeholder="Display User Name" tabindex="1">
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="password" name="password" id="password"
                                           class="form-control input-lg transparent"
                                           placeholder="Password" tabindex="2">
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="password" name="password_confirmation" id="password_confirmation"
                                           class="form-control input-lg transparent" placeholder="Confirm Password"
                                           tabindex="3">
                                </div>
                            </div>
                        </div>
                        <div class="btn-group btn-group-justified">
                            <a href="javascript:void(0)" name="user-type" id="patient-type"
                               class="btn input-lg transparent active">Patient</a>
                            <a href="javascript:void(0)" name="user-type" id="doctor-type"
                               class="btn input-lg transparent">Doctor</a>
                            <a href="javascript:void(0)" name="user-type" id="pharmacy-type"
                               class="btn input-lg transparent">Pharmacy</a>
                        </div>
                        <br>

                        <!-- patient fields -->
                        <div id="patient-fields" name="user-fields">
                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-6">
                                    <div class="form-group">
                                        <input type="text" name="first_name" id="first_name"
                                               class="form-control input-lg transparent"
                                               placeholder="First Name" tabindex="4">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-6">
                                    <div class="form-group">
                                        <input type="text" name="last_name" id="last_name"
                                               class="form-control input-lg transparent"
                                               placeholder="Last Name" tabindex="5">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-7">
                                    <div class="form-group">
                                        <input type="email" name="email" id="email"
                                               class="form-control input-lg transparent"
                                               placeholder="Email Address" tabindex="6">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-5">
                                    <div class="form-group">
                                        <input type="date" name="date_of_birth" id="date_of_birth"
                                               class="form-control input-lg transparent"
                                               placeholder="Date of Birth" tabindex="7">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-4">
                                    <div class="form-group">
                                        <input type="text" name="phone_no" id="phone_no"
                                               class="form-control input-lg transparent"
                                               placeholder="Phone number" maxlength="14" tabindex="9">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-8">
                                    <div class="form-group">
                                        <input type="text" name="medicare_no" id="medicare_no"
                                               class="form-control input-lg transparent"
                                               placeholder="Medicare number" maxlength="18" tabindex="10">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- doctor fields -->
                        <div id="doctor-fields" name="user-fields" style="display: none;">
                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-6">
                                    <div class="form-group">
                                        <input type="text" name="d_first_name" id="d_first_name"
                                               class="form-control input-lg transparent"
                                               placeholder="First Name" tabindex="4">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-6">
                                    <div class="form-group">
                                        <input type="text" name="d_last_name" id="d_last_name"
                                               class="form-control input-lg transparent"
                                               placeholder="Last Name" tabindex="5">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-7">
                                    <div class="form-group">
                                        <input type="email" name="d_email" id="d_email"
                                               class="form-control input-lg transparent"
                                               placeholder="Email Address" tabindex="6">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-5">
                                    <div class="form-group">
                                        <input type="date" name="d_date_of_birth" id="d_date_of_birth"
                                               class="form-control input-lg transparent"
                                               placeholder="Date of Birth" tabindex="7">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-4">
                                    <div class="form-group">
                                        <input type="text" name="d_phone_no" id="d_phone_no"
                                               class="form-control input-lg transparent"
                                               placeholder="Phone number" maxlength="14" tabindex="8">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-8">
                                    <div class="form-group">
                                        <input type="text" name="d_license_no" id="d_license_no"
                                               class="form-control input-lg transparent"
                                               placeholder="License number" maxlength="18" tabindex="9">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 col-sm-7 col-md-5">
                                    <div class="form-group">
                                        <input type="text" name="d_hospital" id="d_hospital"
                                               class="form-control input-lg transparent"
                                               placeholder="Hospital" maxlength="14" tabindex="10">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-3 col-md-4">
                                    <div class="form-group">
                                        <input type="text" name="d_department" id="d_department"
                                               class="form-control input-lg transparent"
                                               placeholder="Department" maxlength="18" tabindex="11">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-2 col-md-3">
                                    <div class="form-group">
                                        <input type="text" name="d_title" id="d_title"
                                               class="form-control input-lg transparent"
                                               placeholder="Title" maxlength="18" tabindex="12">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- patient fields -->
                        <div id="pharmacy-fields" name="user-fields" style="display: none;">
                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-6">
                                    <div class="form-group">
                                        <input type="text" name="p_name" id="p_name"
                                               class="form-control input-lg transparent"
                                               placeholder="Pharmacy name" tabindex="4">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-6">
                                    <div class="form-group">
                                        <input type="text" name="p_postcode" id="p_postcode"
                                               class="form-control input-lg transparent"
                                               placeholder="Postcode" tabindex="5">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-8">
                                    <div class="form-group">
                                        <input type="text" name="p_street" id="p_street"
                                               class="form-control input-lg transparent"
                                               placeholder="Street" tabindex="6">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-4">
                                    <div class="form-group">
                                        <input type="text" name="p_suburb" id="p_suburb"
                                               class="form-control input-lg transparent"
                                               placeholder="Suburb" tabindex="7">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 col-sm-6 col-md-4">
                                    <div class="form-group">
                                        <input type="text" name="phone_no" id="p_phone_no"
                                               class="form-control input-lg transparent"
                                               placeholder="Phone number" maxlength="14" tabindex="9">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6 col-md-8">
                                    <div class="form-group">
                                        <input type="email" name="p_email" id="p_email"
                                               class="form-control input-lg transparent"
                                               placeholder="Email" maxlength="18" tabindex="10">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- user agreement check box -->
                        <div class="row">
                            <div class="col-xs-4 col-sm-3 col-md-3">
					<span class="button-checkbox">
						<button id="i-agree-button" type="button" class="btn transparent" data-color="info"
                                tabindex="20">&nbsp;I Agree</button>
                        <input type="checkbox" name="t_and_c" id="t_and_c" class="hidden" value="1">
					</span>
                            </div>
                            <div class="col-xs-8 col-sm-9 col-md-9">
                                <span class="white-font">By clicking <strong
                                        class="label label-primary">Register</strong> , you agree to the <a
                                        href="#"
                                        data-toggle="modal"
                                        data-target="#t_and_c_m" class="stress">Terms
                                and Conditions</a> set out by this site, including our Cookie Use.</span>
                            </div>
                        </div>

                        <hr class="colorgraph">
                        <div class="row">
                            <div class="col-xs-12 col-md-6">
                                <a id="register-button" href="javascript:void(0)"
                                   class="btn btn-primary btn-block btn-lg"
                                   tabindex="99">Register <span
                                        id="waiting-dots"></span></a>
                            </div>
                            <div class="col-xs-12 col-md-6"><a href="login.page" class="btn btn-success btn-block btn-lg">Sign
                                In</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="t_and_c_m" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Terms & Conditions</h4>
                        </div>
                        <div class="modal-body">
                            <p>This Site allows you to request a refill of a prescription from the same
                                CSCI992Groupb or
                                CSCI992Groupb Pharma Plus pharmacy from which it was originally dispensed and where
                                refills remain (or where, in some provinces, a pharmacist is permitted to authorize
                                refills) and may be refilled by law.</p>
                            <p>The use of the Web Refill tool requires the collection, use, disclosure and
                                transmission
                                of personal information and personal health information across computer systems and
                                networks. In light of the inherent nature of the Internet, we cannot guarantee that
                                this
                                information: (i) will be transmitted securely; (ii) will be error- or defect-free;
                                or
                                (iii) will not be improperly accessed or used by third parties. You are submitting
                                this
                                information over the Internet to us at your own risk.</p>
                            <p>In addition, these Web Refill Terms and Conditions supplement (and in no way limit
                                the
                                applicability of) the Legal [CSCI992Groupb.ca/legal], Privacy
                                [CSCI992Groupb.ca/privacy]
                                and Consent [CSCI992Groupb.ca/consent] Policies that govern your general use of the
                                Site. Any capitalized terms used but not otherwise defined herein have the meaning
                                given
                                to them in the Legal, Privacy and/or Consent Policy as applicable.</p>
                            <p>Your request for a prescription refill will be transmitted to the pharmacy location
                                you
                                have indicated. Once the pharmacy has received the request, it will be handled in
                                the
                                same manner as all refill requests, at all times being subject to the dispensing
                                pharmacist's professional judgment and ethical responsibilities.</p>
                            <p>By submitting the prescription information (and other required information), you
                                consent
                                to our representatives (including a pharmacist or a pharmacy technician or
                                assistant)
                                having access to your personal health information.</p>
                            <p>In order to properly dispense a prescription and for your health and safety, the
                                pharmacy
                                may have to contact (without limitation):

                                the prescribing professional to discuss the refill request and your medication
                                history
                                and medical conditions;
                                your health insurance provider in order to properly process your benefits (if
                                applicable); and/or
                                such other persons or entities as may be required in order to properly process your
                                prescription refill request.</p>
                            <p>In the course of such discussions, your personal information and/or personal health
                                information may be disclosed. Without limiting the Consent and Privacy Policies, you
                                hereby consent to such disclosure and use of your personal information and personal
                                health information.</p>
                            <p>At all times, please consult your CSCI992Groupb or CSCI992Groupb Pharma Plus
                                pharmacist
                                with any questions or concerns.</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="agree-term-button" class="btn btn-primary"
                                    data-dismiss="modal">I
                                Agree
                            </button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
        </div>
    </div>
</div>


</body>
</html>
