package com.yolohealth.lunngmonitor.ui.activities.token;

import com.yolohealth.lunngmonitor.LungMonitorApp;
import com.yolohealth.lunngmonitor.api.ErrorUtils;
import com.yolohealth.lunngmonitor.api.RestClient;
import com.yolohealth.lunngmonitor.model.tokenresponse.TokenParams;
import com.yolohealth.lunngmonitor.model.tokenresponse.TokenResponse;
import com.yolohealth.lunngmonitor.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenInteractorImpl implements TokenInteractor {
    @Override
    public void token(TokenParams tokenParams, TokenViewListener listener) {

        Call<TokenResponse> tokenResponseCall = RestClient.getClient().token(tokenParams.getToken_no());

        tokenResponseCall.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    if (tokenResponse.getStatus() == 1) {
                        listener.onSuccess(tokenResponse, tokenResponse.getMessage());
                        SharedPrefUtils.setProfileId(LungMonitorApp.getAppContext(), String.valueOf(tokenResponse.getData().getPatients().getId()));
                      //  SharedPrefUtils.setUserId(LungMonitorApp.getAppContext(),tokenResponse.getData().getPatients().getId());
                        SharedPrefUtils.setKioksId(LungMonitorApp.getAppContext(),tokenResponse.getData().getPatients().getCreatedByUser().getKiosks().getId());

                    } else {
                        listener.onError(tokenResponse.getMessage());

                    }
                } else {
                    listener.onError(ErrorUtils.parseError(response));

                }

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                listener.onError("");

            }
        });

    }
}
