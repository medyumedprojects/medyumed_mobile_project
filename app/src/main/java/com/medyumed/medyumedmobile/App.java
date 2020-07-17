package com.medyumed.medyumedmobile;

import android.app.Application;

import com.medyumed.medyumedmobile.data.Constants;
import com.medyumed.medyumedmobile.network.IApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static IApiService sService;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sService = mRetrofit.create(IApiService.class);
    }

    public static IApiService getApi() {
        return sService;
    }
}
