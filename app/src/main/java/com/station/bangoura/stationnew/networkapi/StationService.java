package com.station.bangoura.stationnew.networkapi;

import com.station.bangoura.stationnew.models.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StationService {

    @GET("station/list")
    Call<List<Station>> getStations() ;
    @GET("stations/{id}")
    Call<List<Station>> getStationById(@Path("id") int id) ;
    @FormUrlEncoded
    @POST("station/create")
    Call<Station> addStation(
            @Field("name") String name,
            @Field("city") String city);
}
