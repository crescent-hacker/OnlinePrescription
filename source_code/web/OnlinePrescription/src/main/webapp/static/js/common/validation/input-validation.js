/*
 * component vibrate
 * obj: html element
 * time: time interval
 * wh: range of vibrate
 * fx: animation speed(ms)
 */
var Validation = (function () {
    /********************  private function definition  ********************/


    /********************  public function definition  ********************/
    function checkInput(obj,checkFunc){
        if(!obj instanceof jQuery){
            obj = $(obj);
        }
        var success = checkFunc(obj);
        if(success){
            obj.css("border-color","rgba(238, 238, 238, 0.27)");
            // obj.css("border-color","springgreen");
        }else{
            obj.css("border-color","red");
            obj.css("border-width","1px");
        }
        return success;
    }


    /******************** return model ********************/
    return {
        checkInput:checkInput
    }
})();
