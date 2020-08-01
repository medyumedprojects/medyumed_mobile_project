package com.medyumed.medyumedmobile.data.viewmodel.password;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medyumed.medyumedmobile.App;
import com.medyumed.medyumedmobile.data.model.password.LoginUserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PasswordViewModel extends ViewModel {

    private final long startTime = 180 * 1000;
    private final long interval = 1000;

    public MutableLiveData<String> timerLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> loginResult = new MutableLiveData<>();

    private PasswordCountDownTimer timer = new PasswordCountDownTimer(startTime, interval);
    private String phoneNumber;

    public PasswordViewModel()
    {

    }

    public PasswordViewModel(String number){
        phoneNumber = number;
    }

    public LiveData<String> getTimerLiveData() {
        return timerLiveData;
    }

    public LiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public void startTimer(){
        timer.start();
    }

    public void restartTimer(){
        timer.cancel();
        timer.start();
    }

    public void login(String password){

        Call<String> call = App.getApi().login(new LoginUserData(phoneNumber, password));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (!response.isSuccessful()) {
                    loginResult.postValue(false);
                    return;
                }

                loginResult.postValue(true);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                loginResult.postValue(false);
            }
        });
    }


    private class PasswordCountDownTimer extends CountDownTimer {

        public PasswordCountDownTimer(long start, long interval){
            super(start, interval);
        }

        @Override
        public void onTick(long l) {
            long timeElapsed = l ;
            timerLiveData.postValue(String.valueOf(timeElapsed));
        }

        @Override
        public void onFinish() {
            timerLiveData.postValue(null);
        }
    }
}
