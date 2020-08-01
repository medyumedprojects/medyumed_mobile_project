package com.medyumed.medyumedmobile.network;

import com.medyumed.medyumedmobile.data.Constants;
import com.medyumed.medyumedmobile.data.model.login.AuthorizationData;
import com.medyumed.medyumedmobile.data.model.login.ResponseAuthorizationData;
import com.medyumed.medyumedmobile.data.model.password.LoginUserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IApiService {
    @POST(Constants.Controllers.REQUEST_PASSWORD)
    Call<ResponseAuthorizationData> requestPassword(@Body AuthorizationData authenticationData);

    @POST(Constants.Controllers.LOGIN)
    Call<String> login(@Body LoginUserData data);
}
