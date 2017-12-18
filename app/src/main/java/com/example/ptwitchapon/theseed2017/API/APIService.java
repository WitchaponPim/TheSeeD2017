package com.example.ptwitchapon.theseed2017.API;

import com.example.ptwitchapon.theseed2017.Model.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


/**
 * Created by ptwitchapon on 18/12/2560.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("login.php")
    Call<List<User>> postUser(@Field("username") String user, @Field("password") String pass);
}
