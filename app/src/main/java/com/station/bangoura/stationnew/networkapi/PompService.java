package com.station.bangoura.stationnew.networkapi;

import com.station.bangoura.stationnew.models.Pomp;
import com.station.bangoura.stationnew.models.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PompService {
    @GET("pomps/list")
    Call<List<Pomp>> getPomps() ;
    @GET("pomps/{id}")
    Call<List<Pomp>> getPompsByStation(@Path("id") int id) ;
    @GET("pomp/{id}")
    Call<Pomp> getPomp(@Path("id") int id) ;
}
