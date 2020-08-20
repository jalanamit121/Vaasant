package com.winbee.vasantvalleyschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefUser {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("refcode")
    @Expose
    private String refcode;
    @SerializedName("class_data")
    @Expose
    private String class_data;
    @SerializedName("registration")
    @Expose
    private String registration;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("registration_id")
    @Expose
    private String registration_id;
    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("MessageOTP")
    @Expose
    private String messageOTP;

    public RefUser(String name, String mobile, String email, String password, String referalcode) {
        this.name = name;
        this.mobile = mobile;
        this.email=email;
        this.password=password;
        this.refcode=referalcode;

    }

    public RefUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
    }

    public String getClass_data() {
        return class_data;
    }

    public void setClass_data(String class_data) {
        this.class_data = class_data;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
    }

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessageOTP() {
        return messageOTP;
    }

    public void setMessageOTP(String messageOTP) {
        this.messageOTP = messageOTP;
    }

}