package com.station.bangoura.stationnew.networkapi;

import com.station.bangoura.stationnew.models.Cuve;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CuveService {
    @GET("cuve/list")
    Call<List<Cuve>> getCuves() ;
}
