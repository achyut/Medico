package edu.uta.entities;

import java.util.Date;

/**
 * Abstract class for user which shall be extended by Patients and Doctors
 * Created by achyut on 10/8/15.
 */
public abstract class User {
    private String username;
    private String password;
    private String address;
    private String phone;
    private String fullname;
    private String dateofbirth;
    private String ssn;
    private String gender;
    private String photourl;
    private String email;
    private String usertype;
    private String familydoctorname;
    private String doctor_liscence_number;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getFamilydoctorname() {
        return familydoctorname;
    }

    public void setFamilydoctorname(String familydoctorname) {
        this.familydoctorname = familydoctorname;
    }

    public String getDoctor_liscence_number() {
        return doctor_liscence_number;
    }

    public void setDoctor_liscence_number(String doctor_liscence_number) {
        this.doctor_liscence_number = doctor_liscence_number;
    }
}
