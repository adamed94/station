package com.station.bangoura.stationnew.networkapi;

import com.station.bangoura.stationnew.models.CmdLivrs;
import com.station.bangoura.stationnew.models.Pompist;
import com.station.bangoura.stationnew.models.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProvisionningService {
    @GET("provisioning/{id}")
    Call<List<CmdLivrs>> getCmdLivrs(@Path("id") int id) ;

    @GET("provisioning/{id}/{stat}/update")
    Call<CmdLivrs> receiptProvisionning(@Path("id") int id ,@Path("stat") int stat) ;


    @FormUrlEncoded
    @POST("provisioning/ajout")
    Call<CmdLivrs> addCmdLivrs(
            @Field("quantity") int quantity,
            @Field("qtyEss") int qtyEss,
            @Field("qtyGaz") int qtyGaz,
            @Field("motif") String motif,
            @Field("station_id") int station_id,
            @Field("user_id") int user_id);
}
