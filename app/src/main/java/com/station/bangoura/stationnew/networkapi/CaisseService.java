package com.station.bangoura.stationnew.networkapi;

import com.station.bangoura.stationnew.models.Caisse;
import com.station.bangoura.stationnew.models.CaisseSolde;
import com.station.bangoura.stationnew.models.CarburanType;
import com.station.bangoura.stationnew.models.Depense;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CaisseService {


    @GET("caisse/{appro_caisse}/{from_id}")
    Call<String> validAppro(@Path("appro_caisse") int appro_caisse , @Path("from_id") int from_id) ;

    @GET("caisse/{station_id}")
    Call<List<Caisse>> getApproCaisses(@Path("station_id") int station_id) ;

    @GET("depenses/{from_id}/{station_id}")
    Call<List<Depense>> getDepenses(@Path("from_id") int from_id , @Path("station_id") int station_id ) ;
    @GET("solde/caisse/{station_id}")
    Call<CaisseSolde> getSoldeCaisse(@Path("station_id") int station_id ) ;



    @FormUrlEncoded
    @POST("depense")
    Call<Integer> addDepense(@Field("amount") int amount ,  @Field("motif") String motif ,  @Field("from_id") int from_id ,@Field("station_id") int station_id ) ;
}
