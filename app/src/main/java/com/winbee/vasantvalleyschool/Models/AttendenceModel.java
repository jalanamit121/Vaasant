package com.winbee.vasantvalleyschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AttendenceModel implements Serializable {

@SerializedName("Success")
@Expose
private Boolean success;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("user_id")
@Expose
private String user_id;

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

public String getUser_id() {
return user_id;
}

public void setUser_id(String user_id) {
this.user_id = user_id;
}

}