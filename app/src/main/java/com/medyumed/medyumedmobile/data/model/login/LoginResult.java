package com.medyumed.medyumedmobile.data.model.login;

import androidx.annotation.Nullable;


/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult {
    @Nullable
    private String success;
    @Nullable
    private Integer error;

    public LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    public LoginResult(@Nullable String success) {
        this.success = success;
    }

    @Nullable
    public String getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
