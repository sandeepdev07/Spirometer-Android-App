package com.yolohealth.lunngmonitor.ui.activities.spirotest;

import com.yolohealth.lunngmonitor.LungMonitorApp;
import com.yolohealth.lunngmonitor.api.ErrorUtils;
import com.yolohealth.lunngmonitor.api.RestClient;
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
}
