package com.yolohealth.lunngmonitor.api;

import com.yolohealth.lunngmonitor.model.loginresponse.LoginParams;
import com.yolohealth.lunngmonitor.model.loginresponse.LoginResponseParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestInterface {

    @POST("users/email/auth")
    Call<LoginResponseParams> Login(@Body LoginParams loginParams);
}
