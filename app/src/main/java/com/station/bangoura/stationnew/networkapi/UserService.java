package com.station.bangoura.stationnew.networkapi;

import com.station.bangoura.stationnew.models.Stock;
import com.station.bangoura.stationnew.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {
    @GET("login/{email}/{password}")
    Call<User> getUser(@Path("email") String email ,@Path("password") String password) ;
}
