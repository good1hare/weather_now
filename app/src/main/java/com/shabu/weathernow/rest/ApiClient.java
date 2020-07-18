package com.shabu.weathernow.rest;


import com.shabu.weathernow.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
  public static IApiService getRestClient(){
    return setupRestClient();
  }

  private static IApiService setupRestClient() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(Level.BODY);
    OkHttpClient okHttpClient = new OkHttpClient()
        .newBuilder()
        .addInterceptor(interceptor)
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build();

    return retrofit.create(IApiService.class);
  }
}
