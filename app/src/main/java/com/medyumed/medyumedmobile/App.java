package com.medyumed.medyumedmobile;

import android.app.Application;

import com.medyumed.medyumedmobile.data.Constants;
import com.medyumed.medyumedmobile.network.IApiService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static IApiService sService;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Authorization", Constants.API_KEY)
                                .header("Content-Type", "application/json")
                                .method(original.method(),original.body())
                                .build();
                        return chain.proceed(request);
                    }}).build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(defaultHttpClient)
                .build();

        sService = mRetrofit.create(IApiService.class);
    }

    public static IApiService getApi() {
        return sService;
    }
}
