package com.yolohealth.spirometer.api;

import com.yolohealth.spirometer.model.commonresponse.CommonResponse;
import com.yolohealth.spirometer.model.forgotpassword.EmailVerificationCode;
import com.yolohealth.spirometer.model.forgotpassword.ResetPasswordParams;
import com.yolohealth.spirometer.model.loginresponse.LoginParams;
import com.yolohealth.spirometer.model.loginresponse.LoginResponseParams;
import com.yolohealth.spirometer.model.medicalservicesresponse.MedicalServicesResponse;
import com.yolohealth.spirometer.model.spirotestparams.SpiroTestParams;
import com.yolohealth.spirometer.model.spirotestparams.SpiroTestResponse;
import com.yolohealth.spirometer.model.tokenresponse.TokenResponse;
import com.yolohealth.spirometer.widget.AppConstant;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Call<MedicalServicesResponse> testType(@Header(AppConstant.AUTHORIZATION_TAG) String accessToken);

    // SpiroTest

    @POST("medical-reports/test/spirometry")
    Call<SpiroTestResponse> SpiroMeterTest(@Header(AppConstant.AUTHORIZATION_TAG) String accessToken,
                                           @Body SpiroTestParams spiroTestParams);

    // forgot password code verification

    @POST("users/forgot-password")
    Call<CommonResponse> CodeVerification(@Header(AppConstant.AUTHORIZATION_TAG) String accessToken,
                                          @Body EmailVerificationCode verificationCode);

    // reset password

    @POST("users/reset-password")
    Call<CommonResponse> ResetPassword(@Header(AppConstant.AUTHORIZATION_TAG) String accessToken,
                                       @Body ResetPasswordParams resetPasswordParams);
}
