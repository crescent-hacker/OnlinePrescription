package domainModel.doctor;

import domainModel.system.UserType;

import java.io.Serializable;
import java.util.Map;

public class DoctorBO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int docId;
    private String title;
    private String firstName;
    private String lastName;
    private String dob;
    private String hospital;
    private String department;
    private String registrationDate;
    private String approvalDate;
    private String status;
    private String phone;
    private String registrationNumber;
    private String userName;
    private String password;
    private String email;
    private String license;
    private UserType userType;

    public DoctorBO() {
    }

    public DoctorBO(Map<String, Object> params) {
        this.userName = (String)params.get("username");
        this.password = (String)params.get("password");
        this.firstName = (String)params.get("first_name");
        this.lastName = (String)params.get("last_name");
        this.email = (String)params.get("email");
        this.dob = (String)params.get("date_of_birth");
        this.phone = (String)params.get("phone_no");
        this.license = (String)params.get("license_no");
        this.hospital = (String)params.get("hospital");
        this.department = (String)params.get("department");
        this.title = (String)params.get("title");
        this.userType = UserType.getUserTypeByName((String)params.get("user_type"));
    }


    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
