package domainModel.patient;

import domainModel.system.UserType;

import java.util.Map;

/**
 * Author: Difan Chen
 * Date: 30/10/2016
 * Student No: 4901496
 */
public class Patient {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String medicareNo;
    private String dateOfBirth;
    private String phoneNo;
    private UserType userType;

    //ctor
    public Patient(Map<String,Object> map){
        this.userName = (String)map.get("username");
        this.password = (String)map.get("password");
        this.firstName = (String)map.get("first_name");
        this.lastName = (String)map.get("last_name");
        this.email = (String)map.get("email");
        this.medicareNo = (String)map.get("medicare_no");
        this.dateOfBirth = (String)map.get("date_of_birth");
        this.phoneNo = (String)map.get("phone_no");
        this.userType = UserType.getUserTypeByName((String)map.get("user_type"));
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMedicareNo() {
        return medicareNo;
    }

    public void setMedicareNo(String medicareNo) {
        this.medicareNo = medicareNo;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
