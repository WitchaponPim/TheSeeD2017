package com.example.ptwitchapon.theseed2017.API;

import com.example.ptwitchapon.theseed2017.Model.User;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Retrofit;


/**
 * Created by ptwitchapon on 18/12/2560.
 */

public interface UserCallbackListener {
        public void onResponse(List<User> user, Retrofit retrofit);
        public void onBodyError(ResponseBody responseBodyError);
        public void onBodyErrorIsNull();
        public void onFailure(Throwable t);
}
