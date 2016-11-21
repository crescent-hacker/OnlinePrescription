package exception;

public enum OPErrorCode {
	
	NULL_OBJ("OP001","Object null"),
	ERROR_ADD_USER("OP002","Add user failed."),
	LOGIN_VERIFY_FAILURE("OP003","Login verification failed, please check your user name or password again."),
	CAPTCHA_VERIFY_FAILURE("OP004","Verify code verification failed, please type in again or change another code."),
	UNKNOWN_ERROR("OP999","System is busy, please try later....");
	
	
    private String value;
    private String desc;

    private OPErrorCode(String value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    @Override
    public String toString() {
    	return this.desc;
    }
}
