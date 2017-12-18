package com.example.ptwitchapon.theseed2017.API;

import com.example.ptwitchapon.theseed2017.Model.User;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by ptwitchapon on 18/12/2560.
 */

public class NetworkConnectionManager {
    String API = "http://perfectv.hol.es/";
    public NetworkConnectionManager() {

    }
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    APIService con = retrofit.create(APIService.class);
    public void callUser(final UserCallbackListener listener, String user, String pass){

        Call call = con.postUser(user,pass);
        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                List<User> user = response.body();
                if (user == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    //200
                    listener.onResponse( user, retrofit);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                    listener.onFailure(t);
            }

        });

    }
}
