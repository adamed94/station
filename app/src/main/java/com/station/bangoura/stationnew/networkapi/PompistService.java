package com.station.bangoura.stationnew.networkapi;

import com.station.bangoura.stationnew.models.Pompist;
import com.station.bangoura.stationnew.models.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PompistService {
    @GET("pompist/list")
    Call<List<Pompist>> getPompists() ;
    @FormUrlEncoded
    @POST("pompist/ajout")
    Call<Pompist> addPompist(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("station_id") int station_id);
}
