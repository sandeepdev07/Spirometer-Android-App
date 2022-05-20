package com.yolohealth.spirometer.ui.activities.login;

import com.yolohealth.spirometer.LungMonitorApp;
import com.yolohealth.spirometer.api.ErrorUtils;
import com.yolohealth.spirometer.api.RestClient;
import com.yolohealth.spirometer.model.loginresponse.LoginParams;
import com.yolohealth.spirometer.model.loginresponse.LoginResponseParams;
import com.yolohealth.spirometer.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractorImpl implements LoginInteractor{
    @Override
    public void login(LoginParams loginParams, LoginViewListener changeListener) {

        Call<LoginResponseParams> loginRes = RestClient.getClient().Login(loginParams);

        loginRes.enqueue(new Callback<LoginResponseParams>() {
            @Override
            public void onResponse(Call<LoginResponseParams> call, Response<LoginResponseParams> response) {

                if (response.isSuccessful()) {
                    LoginResponseParams responseParams = response.body();
                    if (responseParams.getStatus() == 1) {
                        SharedPrefUtils.setLoggedIn(LungMonitorApp.getAppContext(), true);
                        SharedPrefUtils.setToken(LungMonitorApp.getAppContext(),responseParams.getData().getUser().getToken());
                        SharedPrefUtils.setProfileId(LungMonitorApp.getAppContext(), String.valueOf(responseParams
                                .getData().getUser().getId()));

                        changeListener.onSuccess(responseParams.getMessage(), responseParams);
                    } else {
                        changeListener.onError(responseParams.getMessage());
                    }
                } else {
                    changeListener.onError(ErrorUtils.parseError(response));
                }
            }

            @Override
            public void onFailure(Call<LoginResponseParams> call, Throwable t) {

                changeListener.onError("");

            }
        });

    }
}
