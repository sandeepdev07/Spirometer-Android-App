package com.yolohealth.lunngmonitor.ui.activities.spirotest;

import com.yolohealth.lunngmonitor.LungMonitorApp;
import com.yolohealth.lunngmonitor.api.ErrorUtils;
import com.yolohealth.lunngmonitor.api.RestClient;
import com.yolohealth.lunngmonitor.model.commonresponse.CommonResponse;
import com.yolohealth.lunngmonitor.model.forgotpassword.EmailVerificationCode;
import com.yolohealth.lunngmonitor.model.forgotpassword.ResetPasswordParams;
import com.yolohealth.lunngmonitor.model.spirotestparams.SpiroTestParams;
import com.yolohealth.lunngmonitor.model.spirotestparams.SpiroTestResponse;
import com.yolohealth.lunngmonitor.utils.SharedPrefUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpiroTestInteractorImpl implements SpiroTestInteractor {
    @Override
    public void spirometer(SpiroTestParams spiroTestParams, SpiroMeterCallListener listener) {

        String token = SharedPrefUtils.getToken(Objects.requireNonNull(LungMonitorApp.getAppContext()));

        Call<SpiroTestResponse> responseCall = RestClient.getClient()
                .SpiroMeterTest(token, spiroTestParams);

        responseCall.enqueue(new Callback<SpiroTestResponse>() {
            @Override
            public void onResponse(Call<SpiroTestResponse> call, Response<SpiroTestResponse> response) {

                if (response.isSuccessful()) {
                    SpiroTestResponse dataResponse = response.body();
                    assert dataResponse != null;
                    if (dataResponse.getStatus().equals("1")) {
                        listener.onSuccess(dataResponse.getMessage());
                    } else {
                        listener.onError(dataResponse.getMessage());
                    }
                } else {
                    listener.onError(ErrorUtils.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<SpiroTestResponse> call, Throwable t) {

                listener.onError(ErrorUtils.ErrorMessage);
            }
        });
    }

    @Override
    public void emailverification(EmailVerificationCode verificationCode, SpiroMeterCallListener listener) {

        String token = SharedPrefUtils.getToken(Objects.requireNonNull(LungMonitorApp.getAppContext()));

        Call<CommonResponse> verification = RestClient.getClient().CodeVerification(token, verificationCode);

        verification.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    CommonResponse dataResponse = response.body();
                    assert dataResponse != null;
                    if (dataResponse.getStatus() == 1) {
                        listener.onSuccess(dataResponse.getMessage());
                    } else {
                        listener.onError(dataResponse.getMessage());
                    }
                } else {
                    listener.onError(ErrorUtils.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                listener.onError(ErrorUtils.ErrorMessage);

            }
        });

    }

    @Override
    public void resetpassword(ResetPasswordParams resetPasswordParams, SpiroMeterCallListener listener) {

        String token = SharedPrefUtils.getToken(Objects.requireNonNull(LungMonitorApp.getAppContext()));

        Call<CommonResponse> resetPass = RestClient.getClient().ResetPassword(token, resetPasswordParams);

        resetPass.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    CommonResponse dataResponse = response.body();
                    assert dataResponse != null;
                    if (dataResponse.getStatus() == 1) {
                        listener.onSuccess(dataResponse.getMessage());
                    } else {
                        listener.onError(dataResponse.getMessage());
                    }
                } else {
                    listener.onError(ErrorUtils.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                listener.onError(ErrorUtils.ErrorMessage);

            }
        });

    }
}
