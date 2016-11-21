/**
 * global variables for prescribe page
 */
var PRESCRIBE_VAR = {
    DRUG_LIST: [],
    PATIENT_LIST: []
}
/**
 * Created by stefan
 * Note: The function in the ready() method will only be executed till html has been loaded completely,
 *       You may need this segment of code in each jsp page.
 */
$(document).ready(function () {
    //get drugs and fill into the input text
    getDrugs();
    //get patients and fill into the input text
    getPatients();
    //binding medicine add buton
    addButtonBind();
    //submit binding
    $("#prescribe-button").on('click',submitPrescription);
});

/**
 * get patient
 */
function getPatients() {
    waitingDialog.show();
    $.ajax({
        type: "get",
        url: "getPatients.ajax",
        data: {},
        dataType: "json",
        success: function (data) {
            if (data.SUCCESS) {
                PRESCRIBE_VAR.PATIENT_LIST = data.PATIENT_LIST;
                var html = genPatientListHtml(data.PATIENT_LIST);
                var patient_select = $("select[name='patient']");
                patient_select.selectpicker('destroy');
                patient_select.html(html);
                patient_select.addClass('selectpicker');
                patient_select.selectpicker('render');
            }
            waitingDialog.hide();
        },
        error: function (data) {
            console.log(data.ERRMSG);
            waitingDialog.hide();
        }
    });
}


/**
 * get drugs list
 */
function getDrugs() {
    waitingDialog.show();
    var param = {};
    $.ajax({
        type: "get",
        url: "getDrugs.ajax",
        data: param,
        dataType: "json",
        success: function (data) {
            if (data.SUCCESS) {
                PRESCRIBE_VAR.DRUG_LIST = data.DRUG_LIST;
                var html = genDrugListHtml(data.DRUG_LIST);
                var drug_select = $("select[name='drug-name']");
                drug_select.selectpicker('destroy');
                drug_select.html(html);
                drug_select.addClass('selectpicker');
                drug_select.selectpicker('render');
                //after type in amount, compute total price
                totalPriceBinding()
            }
            waitingDialog.hide();
        },
        error: function (data) {
            console.log(data.ERRMSG);
            waitingDialog.hide();
        }
    });
}

/**
 * offline refresh drug list
 */
function getDrugsOffline(obj) {
    var html = genDrugListHtml(PRESCRIBE_VAR.DRUG_LIST);
    obj.html(html);
    obj.addClass('selectpicker');
    obj.selectpicker('render');
}


/**
 *  total price binding
 */
function totalPriceBinding() {
    function bind(){
        var totalPrice = computeTotalPrice();
        if(!totalPrice)
            return;
        var tpStr = ("" + totalPrice);
        tpStr = tpStr.substr(0, tpStr.indexOf(".") + 3);
        $("#total-price").html(tpStr);
    }
    $("input[name=dose]").on('keyup', function () {
        bind();
    });
    $("select[name=drug-name]").on("change",function(){
        bind();
    });
    bind();
}

/**
 * generate drug list html
 */
function genDrugListHtml(drug_list) {
    var html = "";
    $.each(drug_list, function (idx, elem) {
        var options = "<option value='" + elem.id + "'>" + elem.name + "</option>";
        html += options;
    })
    return html;
}
/**
 * generate patient list html
 */
function genPatientListHtml(patient_list) {
    var html = "";
    $.each(patient_list, function (idx, elem) {
        var options = "<option value='" + elem.patient_id + "'>" + elem.first_name + " " + elem.last_name + "</option>";
        html += options;
    })
    return html;
}

/**
 * compute total price
 */
function computeTotalPrice() {
    var totalPrice = 0;
    var selectedDrugs = $("select[name=drug-name]");
    var selectedDoses = $("input[name=dose]");
    $.each(selectedDrugs, function (idx, drugOption) {
        var drugId = drugOption.value;
        var price = getDrugPriceById(drugId);
        var dose = selectedDoses[idx].value;
        totalPrice += price * dose;
    });
    return totalPrice;
}

/**
 * get drug price by given drug id
 */
function getDrugPriceById(id) {
    for (var i = 0; i < PRESCRIBE_VAR.DRUG_LIST.length; i++) {
        if (PRESCRIBE_VAR.DRUG_LIST[i].id == id)
            return PRESCRIBE_VAR.DRUG_LIST[i].price;
    }
}

/**
 * binding add drugs button
 */
function addButtonBind() {
    var html = '<div class="voca row"><div class="col-md-3"><select name="drug-name" class="selectpicker" data-live-search="true" data-width="auto"></select></div><div class="col-md-5"><input class="form-control" placeholder="Drug description" name="drug-desc" type="text" value=""></div><div class="col-md-2"><input class="form-control" placeholder="1" name="dose" type="number" min="1" value="1"></div><button type="button" class="btn btn-success btn-add"><span class="glyphicon glyphicon-plus-sign"aria-hidden="true"></span> Add more</button></div>';
    $(document).on('click', '.btn-add', function (e) {
        e.preventDefault();

        var controlForm = $('.controls form:first');
        // currentEntry = $(this).parents('.voca:first');
        var newEntry = $(html).appendTo(controlForm);
        // $(".selectpicker").selectpicker('refresh');
        getDrugsOffline(newEntry.find('.selectpicker'));
        newEntry.find('input').val('');
        controlForm.find('.btn-add:not(:last)')
            .removeClass('btn-default').addClass('btn-danger')
            .removeClass('btn-add').addClass('btn-remove')
            .html('<span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Remove   ');
        newEntry.find("input[name=dose]").val(1);
        //total price bind
        totalPriceBinding();
    }).on('click', '.btn-remove', function (e) {
        $(this).parents('.voca:first').remove();
        totalPriceBinding();
        e.preventDefault();
        return false;
    });
}

/**
 * submit prescription
 */
function submitPrescription(){
    waitingDialog.show();
    //organize params
    var params = {
        patient_id:$("select[name=patient]").val(),
        drugs_id:extractSelectValue($("select[name=drug-name]")),
        drugs_desc:extractSelectValue($("input[name=drug-desc]")),
        drugs_dose:extractSelectValue($("input[name=dose]")),
        total_price:$("#total-price").html(),
        description:$("#prescription-desc").val(),
        repeat:$("#repeat").val(),
        duration:$("#duration").val()
    };
    //post request
    $.ajax({
        type: "post",
        url: "submitPrescription.ajax",
        data: params,
        dataType: "json",
        success: function (data) {
            if (data.SUCCESS) {
                prescribeAlertTips(0, "Prescribe successfully. Redirect to patient history page within 3 seconds.");
                setTimeout(function () {
                    window.location.href = data.redirect_url;
                }, 3000)
            }else{
                prescribeAlertTips(1, data.ERRMSG);
            }
            waitingDialog.hide();
        },
        error: function (data) {
            prescribeAlertTips(2, data.ERRMSG);
            waitingDialog.hide();
        }
    });
}

/**
 * extract values as String from html elements
 */
function extractSelectValue(elems){
    var str = "";
    $.each(elems,function(idx,elem){
        str+=elem.value+"~";
    });
    str.substr(0,str.length-2);
    return str;
}

/**
 * alert if prescribe success or not
 * status: 0-success 1-fail 2-server error
 */
function prescribeAlertTips(status, msg) {
    var tips = $("#prescribe-tips");

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