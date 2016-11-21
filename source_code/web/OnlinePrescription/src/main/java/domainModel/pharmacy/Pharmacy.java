package domainModel.pharmacy;

import domainModel.system.UserType;

import java.util.Map;

public class Pharmacy {
    private int id;
    private String name;
    private String street;
    private String postcode;
    private String suburb;
    private String phone;
    private String status;
    private String registrationDate;
    private String approvalDate;
    private String registrationNumber;
    private String userName;
    private String password;
    private String email;
    private String longitude;
    private String latitude;
    private UserType userType;

    public Pharmacy() {

    }

    public Pharmacy(Map<String, Object> params) {
        this.userName = (String) params.get("username");
        this.password = (String) params.get("password");
        this.name = (String) params.get("name");
        this.email = (String) params.get("email");
        this.phone = (String) params.get("phone_no");
        this.street = (String) params.get("street");
        this.postcode = (String) params.get("postcode");
        this.suburb = (String) params.get("suburb");
        this.longitude = (String)params.get("longitude");
        this.latitude = (String)params.get("latitude");
        this.userType = UserType.getUserTypeByName((String) params.get("user_type"));
    }


    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
