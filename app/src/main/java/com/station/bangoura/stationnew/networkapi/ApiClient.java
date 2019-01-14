package com.station.bangoura.stationnew.networkapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private int READ_TIMEOUT = 10;
    private int CONNECT_TIMEOUT = 20;

    // public static final String BASE_URL = "http://techplaceguinea.com/api/" ;
     public static final String BASE_URL = "http://192.168.1.8/App/public/api/" ;
    //public static final String BASE_URL = "http://192.168.43.70:80/api/public/" ;
    private static Retrofit mRetrofit ;
    public static Retrofit getRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create() ;

        if (mRetrofit == null) {
            mRetrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return mRetrofit;
    }
}