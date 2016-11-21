package domainModel.system;

/**
 * Author: Difan Chen
 * Date: 17/10/2016
 * Student No: 4901496
 */
public enum UserType {
    DOCTOR,ADMIN,PHARMACY,PATIENT,ROOT,VISITOR;

    public static UserType getUserTypeByName(String userTypeName){
        if("administrator".equalsIgnoreCase(userTypeName)){
            return ADMIN;
        }
        if("root".equalsIgnoreCase(userTypeName)){
            return ROOT;
        }
        if("doctor".equalsIgnoreCase(userTypeName)){
            return DOCTOR;
        }
        if("pharmacist".equalsIgnoreCase(userTypeName)||"pharmacy".equalsIgnoreCase(userTypeName)){
            return PHARMACY;
        }
        if("patient".equalsIgnoreCase(userTypeName)){
            return PATIENT;
        }
        return VISITOR;
    }

    public static String getUserTypeName(UserType userType){
        if("administrator".equals(userType)){
            return "administrator";
        }
        if("root".equals(userType)){
            return "root";
        }
        if("doctor".equals(userType)){
            return "doctor";
        }
        if("pharmacist".equals(userType)){
            return "pharmacist";
        }
        if("patient".equals(userType)){
            return "patient";
        }
        return "visitor";
    }
    
    public static String getRedirectLink(UserType userType){
        if(userType==UserType.ADMIN){
            return "viewDashboard.page";
        }
        if(userType==UserType.DOCTOR){
            return "doctorIndex.page";
        }
        if(userType==UserType.ROOT){
            return "viewDashboard.page";
        }
        if(userType==UserType.PHARMACY){
            return "pharmacyIndex.page";
        }
        return "patientIndex.page";
    }

    public static String getSystemName(UserType userType){
        if(userType==UserType.ADMIN){
            return "Administration Console";
        }
        if(userType==UserType.DOCTOR){
            return "Doctor platform";
        }
        if(userType==UserType.ROOT){
            return "Administration platform";
        }
        if(userType==UserType.PHARMACY){
            return "Pharmacy platform";
        }
        return "Patient platform";
    }
}
