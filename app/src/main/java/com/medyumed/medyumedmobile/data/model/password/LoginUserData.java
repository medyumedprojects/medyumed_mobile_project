package com.medyumed.medyumedmobile.data.model.password;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUserData {

    @SerializedName("login")
    @Expose
    private String phoneNumber;
    @SerializedName("password")
    @Expose
    private String password;

    public LoginUserData(){}

    public LoginUserData(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
