package com.station.bangoura.stationnew.networkapi;

import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.models.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StockService {

    @GET("get/stock/{id}")
    Call<List<Stock>> getStock(@Path("id") int id) ;

    @GET("get/barremage/{id}/{caburan_type}/{height}")
    Call<Float> getVolume(@Path("id") int id ,@Path("caburan_type") int caburan_type ,@Path("height") Double height  ) ;
}
