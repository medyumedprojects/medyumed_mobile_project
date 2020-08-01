package com.medyumed.medyumedmobile.data.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorizationData {
    @SerializedName("phone")
    @Expose
    private String mPhone;
    @SerializedName("fulname")
    @Expose
    private String mFullName;

    public AuthorizationData(){}

    public AuthorizationData(String phone, String fullName){
        mPhone = phone;
        mFullName = fullName;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}
