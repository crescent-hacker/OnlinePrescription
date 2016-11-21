/**
 * Created by stefan on 10/10/2016.
 */
var REG_VAR = {
    REG_WAITING: false
}
$(document).ready(function () {
    //initialize the form
    initializeForm();
    //register button action binding
    $("#register-button").on("click", function () {
        do_register();
    });
    //user type selection
    $('a[name="user-type"]').on("click", function () {
        $('a[name="user-type"]').removeClass('active');
        $(this).addClass('active');
        var type = $(this).attr("id").split("-")[0];
        $("div[name='user-fields']").hide();
        $("#" + type + "-fields").show();
    });
    //register form setup
    $("#register-tips").hide();
    $('#register-button').on('click', do_register);
    $('#register-form').on('keyup', function (event) {
        if (event.which == 13) {
            do_register();
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
        if ($(this).css("border-color") == "rgb(255, 0, 0)")
            $(this).css("border-color", "white");
    });
    $("#i-agree-button").on('click', function () {
        $(this).css("border-color", "white");
    });
});

/**
 * initialize the form
 */
function initializeForm() {
    $('.button-checkbox').each(function () {

        // Settings
        var $widget = $(this),
            $button = $widget.find('button'),
            $checkbox = $widget.find('input:checkbox'),
            color = $button.data('color'),
            settings = {
                on: {
                    icon: 'glyphicon glyphicon-check'
                },
                off: {
                    icon: 'glyphicon glyphicon-unchecked'
                }
            };

        // Event Handlers
        $button.on('click', function () {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
            $checkbox.triggerHandler('change');
            updateDisplay();
        });
        $checkbox.on('change', function () {
            updateDisplay();
        });

        // Actions
        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');

            // Set the button's state
            $button.data('state', (isChecked) ? "on" : "off");
            // Set the button's icon
            $button.find('.state-icon')
                .removeClass()
                .addClass('state-icon ' + settings[$button.data('state')].icon);

            // Update the button's color
            if (isChecked) {
                $button
                    .removeClass('btn-default')
                    .addClass('btn-' + color + ' active');
            }
            else {
                $button
                    .removeClass('btn-' + color + ' active')
                    .addClass('btn-default');
            }
        }

        // Initialization
        function init() {

            updateDisplay();

            // Inject the icon if applicable
            if ($button.find('.state-icon').length == 0) {
                $button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i>');
            }
        }

        init();
    });

}

/**
 * submit register form by user type
 */
function do_register() {
    var type = $("a[name='user-type'].active").attr("id").split("-")[0];
    console.log(type);
    if (type == "patient")
        submitPatientRegister();
    if (type == "doctor")
        submitDoctorRegister();
    if (type == "pharmacy")
        submitPharmacyRegister();
}

/**
 * check patient reg fields before submission
 */
function checkPatientFields() {
    var result_1 = Validation.checkInput($("#username"), function (obj) {
        return obj.val() != "";
    })
    var result_2 = Validation.checkInput($("#password"), function (obj) {
        return obj.val() != "";
    })
    var result_3 = Validation.checkInput($("#password_confirmation"), function (obj) {
        return obj.val() != "" && obj.val() == $("#password").val();
    })
    var result_4 = Validation.checkInput($("#email"), function (obj) {
        return obj.val() != "";
    })
    var result_5 = Validation.checkInput($("#i-agree-button"), function (obj) {
        return $('.button-checkbox').find('input:checkbox').is(':checked');
    })
    var result_6 = Validation.checkInput($("#first_name"), function (obj) {
        return obj.val() != "";
    })
    var result_7 = Validation.checkInput($("#last_name"), function (obj) {
        return obj.val() != "";
    })
    var result_8 = Validation.checkInput($("#medicare_no"), function (obj) {
        return obj.val() != "";
    })
    var result_9 = Validation.checkInput($("#date_of_birth"), function (obj) {
        return obj.val() != "";
    })
    var result_10 = Validation.checkInput($("#phone_no"), function (obj) {
        return obj.val() != "";
    })

    return result_1 & result_2 & result_3 & result_4 & result_5 & result_6 & result_7& result_8& result_9& result_10;
}

/**
 * submit patient reg
 */
function submitPatientRegister() {
    if (!checkPatientFields()) {
        $("#register-form").effect("shake", {times: 3}, 300);
        return;
    }
    //pre-processing
    if (REG_VAR.REG_WAITING) {
        return;
    }
    $('#register-button').attr("disabled", true);
    REG_VAR.REG_WAITING = true;
    $('#waiting-dots').addClass("dotting");    //waiting animation

    //params
    var md5_pwd = md5($("#username").val() + $("#password").val());
    var param = {
        username: $("#username").val(),
        password: md5_pwd,
        first_name: $("#first_name").val(),
        last_name: $("#last_name").val(),
        email: $("#email").val(),
        medicare_no: $("#medicare_no").val(),
        date_of_birth: $("#date_of_birth").val(),
        phone_no: $("#phone_no").val(),
        user_type: "patient"
    };
    //post
    $.ajax({
        type: "post",
        url: "register.ajax",
        data: param,
        dataType: "json",
        success: function (data) {
            if (data.SUCCESS == false) {
                registerAlertTips(1, data.ERRMSG);
            } else {
                //login success
                registerAlertTips(0, "Register successfully. Redirect to login page within 3 seconds.");
                setTimeout(function () {
                    window.location.href = data.redirect_url;
                }, 3000)
            }
            //cancel waiting status
            $('#waiting-dots').removeClass("dotting");
            $('#register-button').attr("disabled", false);
            REG_VAR.REG_WAITING = false;
        },
        error: function (data) {
            $('#waiting-dots').removeClass("dotting");
            $('#register-button').attr("disabled", false);
            REG_VAR.REG_WAITING = false;
            registerAlertTips(2, "Login failed...");
        }
    });
}

/**
 * check doctor reg fields before submission
 */
function checkDoctorFields() {
    var result_1 = Validation.checkInput($("#username"), function (obj) {
        return obj.val() != "";
    })
    var result_2 = Validation.checkInput($("#password"), function (obj) {
        return obj.val() != "";
    })
    var result_3 = Validation.checkInput($("#password_confirmation"), function (obj) {
        return obj.val() != "" && obj.val() == $("#password").val();
    })
    var result_4 = Validation.checkInput($("#d_email"), function (obj) {
        return obj.val() != "";
    })
    var result_5 = Validation.checkInput($("#i-agree-button"), function (obj) {
        return $('.button-checkbox').find('input:checkbox').is(':checked');
    })
    var result_6 = Validation.checkInput($("#d_first_name"), function (obj) {
        return obj.val() != "";
    })
    var result_7 = Validation.checkInput($("#d_last_name"), function (obj) {
        return obj.val() != "";
    })
    var result_8 = Validation.checkInput($("#d_license_no"), function (obj) {
        return obj.val() != "";
    })
    var result_9 = Validation.checkInput($("#d_date_of_birth"), function (obj) {
        return obj.val() != "";
    })
    var result_10 = Validation.checkInput($("#d_phone_no"), function (obj) {
        return obj.val() != "";
    })
    var result_11 = Validation.checkInput($("#d_hospital"), function (obj) {
        return obj.val() != "";
    })
    var result_12 = Validation.checkInput($("#d_department"), function (obj) {
        return obj.val() != "";
    })
    var result_13 = Validation.checkInput($("#d_title"), function (obj) {
        return obj.val() != "";
    })

    return result_1 & result_2 & result_3 & result_4 & result_5 & result_6 & result_7& result_8& result_9& result_10& result_11& result_12& result_13;
}

/**
 * submit doctor reg
 */
function submitDoctorRegister() {
    if (!checkDoctorFields()) {
        $("#register-form").effect("shake", {times: 3}, 300);
        return;
    }
    //pre-processing
    if (REG_VAR.REG_WAITING) {
        return;
    }
    $('#register-button').attr("disabled", true);
    REG_VAR.REG_WAITING = true;
    $('#waiting-dots').addClass("dotting");    //waiting animation

    //params
    var md5_pwd = md5($("#username").val() + $("#password").val());
    var param = {
        username: $("#username").val(),
        password: md5_pwd,
        first_name: $("#d_first_name").val(),
        last_name: $("#d_last_name").val(),
        email: $("#d_email").val(),
        date_of_birth: $("#d_date_of_birth").val(),
        phone_no: $("#d_phone_no").val(),
        license_no: $("#d_license_no").val(),
        hospital: $("#d_hospital").val(),
        department: $("#d_department").val(),
        title: $("#d_title").val(),
        user_type: "doctor"
    };
    //post
    $.ajax({
        type: "post",
        url: "register.ajax",
        data: param,
        dataType: "json",
        success: function (data) {
            if (data.SUCCESS == false) {
                registerAlertTips(1, data.ERRMSG);
            } else {
                //login success
                registerAlertTips(0, "Register successfully. Redirect to login page within 3 seconds.");
                setTimeout(function () {
                    window.location.href = data.redirect_url;
                }, 3000)
            }
            //cancel waiting status
            $('#waiting-dots').removeClass("dotting");
            $('#register-button').attr("disabled", false);
            REG_VAR.REG_WAITING = false;
        },
        error: function (data) {
            $('#waiting-dots').removeClass("dotting");
            $('#register-button').attr("disabled", false);
            REG_VAR.REG_WAITING = false;
            registerAlertTips(2, "Login failed...");
        }
    });
}

/**
 * check pharmacy reg fields before submission
 */
function checkPharmacyFields() {
    var result_1 = Validation.checkInput($("#username"), function (obj) {
        return obj.val() != "";
    })
    var result_2 = Validation.checkInput($("#password"), function (obj) {
        return obj.val() != "";
    })
    var result_3 = Validation.checkInput($("#password_confirmation"), function (obj) {
        return obj.val() != "" && obj.val() == $("#password").val();
    })
    var result_4 = Validation.checkInput($("#p_email"), function (obj) {
        return obj.val() != "";
    })
    var result_5 = Validation.checkInput($("#i-agree-button"), function (obj) {
        return $('.button-checkbox').find('input:checkbox').is(':checked');
    })
    var result_6 = Validation.checkInput($("#p_name"), function (obj) {
        return obj.val() != "";
    })
    var result_7 = Validation.checkInput($("#p_phone_no"), function (obj) {
        return obj.val() != "";
    })
    var result_8 = Validation.checkInput($("#p_street"), function (obj) {
        return obj.val() != "";
    })
    var result_9 = Validation.checkInput($("#p_postcode"), function (obj) {
        return obj.val() != "";
    })
    var result_10 = Validation.checkInput($("#p_suburb"), function (obj) {
        return obj.val() != "";
    })

    return result_1 & result_2 & result_3 & result_4 & result_5 & result_6 & result_7& result_8& result_9& result_10;
}

/**
 * submit pharmacy reg
 */
function submitPharmacyRegister() {
    if (!checkPharmacyFields()) {
        $("#register-form").effect("shake", {times: 3}, 300);
        return;
    }
    //pre-processing
    if (REG_VAR.REG_WAITING) {
        return;
    }
    $('#register-button').attr("disabled", true);
    REG_VAR.REG_WAITING = true;
    $('#waiting-dots').addClass("dotting");    //waiting animation

    //params
    var md5_pwd = md5($("#username").val() + $("#password").val());
    var param = {
        username: $("#username").val(),
        password: md5_pwd,
        name: $("#p_name").val(),
        email: $("#p_email").val(),
        phone_no: $("#p_phone_no").val(),
        street: $("#p_street").val(),
        postcode: $("#p_postcode").val(),
        suburb: $("#p_suburb").val(),
        user_type: "pharmacy"
    };
    //post
    var post = {
        type: "post",
        url: "register.ajax",
        data: param,
        dataType: "json",
        success: function (data) {
            if (data.SUCCESS == false) {
                registerAlertTips(1, data.ERRMSG);
            } else {
                //login success
                registerAlertTips(0, "Register successfully. Redirect to login page within 3 seconds.");
                setTimeout(function () {
                    window.location.href = data.redirect_url;
                }, 3000)
            }
            //cancel waiting status
            $('#waiting-dots').removeClass("dotting");
            $('#register-button').attr("disabled", false);
            REG_VAR.REG_WAITING = false;
        },
        error: function (data) {
            $('#waiting-dots').removeClass("dotting");
            $('#register-button').attr("disabled", false);
            REG_VAR.REG_WAITING = false;
            registerAlertTips(2, "Login failed...");
        }
    };
    getGeometry(post);
}








/**
 * alert if register success or not
 * status: 0-success 1-fail 2-server error
 */
function registerAlertTips(status, msg) {
    var tips = $("#register-tips");

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

/**
 * get longitude and latitude of pharmacy
 */
function getGeometry(post) {
    var street = post.data.street.replace(/ /g, "+");
    var country = "Australia";
    var state = "NSW";
    var api_key = "AIzaSyCzS2PFKgSqc_Uz6R4--UM72xaRjKAcPZU";
    var url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + street + "+" + post.data.suburb + "+" + state + "+" + country + "=" + api_key;
    //post
    $.ajax({
        type: "get",
        url: url,
        dataType: "json",
        success: function (data) {
            var geometry = data.results[0].geometry.location;
            post.data.longitude = geometry.lng;
            post.data.latitude = geometry.lat;
            $.ajax(post);
        },
        error: function (data) {
        }
    });
}