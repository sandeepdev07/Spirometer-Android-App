package com.yolohealth.lunngmonitor.api;

import com.yolohealth.lunngmonitor.model.loginresponse.LoginParams;
import com.yolohealth.lunngmonitor.model.loginresponse.LoginResponseParams;
import com.yolohealth.lunngmonitor.model.medicalservicesresponse.MedicalServicesResponse;
import com.yolohealth.lunngmonitor.model.tokenresponse.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestInterface {

    //login
    @POST("users/email/auth")
    Call<LoginResponseParams> Login(@Body LoginParams loginParams);

    // token

    @GET("patients/token-number")
    Call<TokenResponse> token(@Query("token_no") String token_no);

    // medicalServices

    @GET("medical-services")
    Call<MedicalServicesResponse> testType();
}
