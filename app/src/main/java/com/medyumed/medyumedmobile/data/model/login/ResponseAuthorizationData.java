package com.medyumed.medyumedmobile.data.model.login;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAuthorizationData {
    @SerializedName("success")
    @Expose
    private @Nullable
    Boolean success;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    private class Meta{
        @SerializedName("message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @Nullable
    public Boolean getSuccess() {
        return success;
    }

    public Meta getMeta() {
        return meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void setSuccess(@Nullable Boolean success) {
        this.success = success;
    }
}

