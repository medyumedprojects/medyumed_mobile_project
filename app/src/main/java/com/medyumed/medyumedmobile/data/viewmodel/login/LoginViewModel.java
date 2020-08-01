package com.medyumed.medyumedmobile.data.viewmodel.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medyumed.medyumedmobile.App;
import com.medyumed.medyumedmobile.R;
import com.medyumed.medyumedmobile.data.model.login.AuthorizationData;

import com.medyumed.medyumedmobile.data.model.login.ControlState;
import com.medyumed.medyumedmobile.data.model.login.LoginFormState;
import com.medyumed.medyumedmobile.data.model.login.LoginResult;
import com.medyumed.medyumedmobile.data.model.login.ResponseAuthorizationData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<ControlState> controlState = new MutableLiveData<>();

    public LoginViewModel() {
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public MutableLiveData<ControlState> getControlState() {
        return controlState;
    }

    public void login(final String phoneNumber) {

        Call<ResponseAuthorizationData> call = App.getApi()
                .requestPassword(new AuthorizationData(phoneNumber,phoneNumber));
        call.enqueue(new Callback<ResponseAuthorizationData>() {

            @Override
            public void onResponse(@NonNull Call<ResponseAuthorizationData> call,
                                   @NonNull Response<ResponseAuthorizationData> response) {
                if (!response.isSuccessful()) {
                    loginResult.postValue(new LoginResult(R.string.login_failed));
                    return;
                }
                ResponseAuthorizationData infoPackage = response.body();
                loginResult.postValue(new LoginResult(phoneNumber));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseAuthorizationData> call, @NonNull Throwable t) {
                loginResult.postValue(new LoginResult(R.string.login_failed));
            }
        });
    }

    public void setControlState(ControlState state){
        controlState.setValue(state);
    }

    public void loginDataChanged(String phoneNumber) {
        if (!isPhoneNumberValid(phoneNumber)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_phone_number));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder phoneNumber validation check
    private boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }

        String pattern = "(\\d{11})";

        Pattern ptrn = Pattern.compile(pattern);
        Matcher matcher = ptrn.matcher(phoneNumber);

        return matcher.find();
    }
}
