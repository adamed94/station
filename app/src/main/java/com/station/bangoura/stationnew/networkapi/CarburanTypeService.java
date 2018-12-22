package com.station.bangoura.stationnew.networkapi;

import com.station.bangoura.stationnew.models.CarburanType;
import com.station.bangoura.stationnew.models.Cuve;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CarburanTypeService {
    @GET("carburan/list")
    Call<List<CarburanType>> getCarburanType() ;
}
