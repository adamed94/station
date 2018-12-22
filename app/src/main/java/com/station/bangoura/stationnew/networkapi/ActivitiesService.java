package com.station.bangoura.stationnew.networkapi;

import com.station.bangoura.stationnew.models.Activities;
import com.station.bangoura.stationnew.models.CarburanType;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.models.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ActivitiesService {

    @FormUrlEncoded
    @POST("indexage")
    Call<Activities> addActivity(

         @Field("pomp_id") int pomp_id ,

         @Field("index_ess_start") int index_ess_start ,
         @Field("index_ess_end") int index_ess_end ,
         @Field("index_gaz_start") int index_gaz_start ,
         @Field("index_gaz_end") int index_gaz_end,
         @Field("station_id") int station_id
    ) ;

    @GET("indexage/{station_id}")
    Call<List<Activities>> getActivities(@Path("station_id") int station_id) ;



}
