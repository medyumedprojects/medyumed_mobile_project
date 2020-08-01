package com.medyumed.medyumedmobile.data.model.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
public class LoginFormState {
    @Nullable
    private Integer phoneNumber;

    private boolean isDataValid;

    public LoginFormState(@Nullable Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.isDataValid = false;
    }

    public LoginFormState(boolean isDataValid) {
        this.phoneNumber = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getPhoneNumberError() {
        return phoneNumber;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
