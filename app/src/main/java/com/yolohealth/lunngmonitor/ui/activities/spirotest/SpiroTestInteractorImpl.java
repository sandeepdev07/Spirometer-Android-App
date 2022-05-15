package com.yolohealth.lunngmonitor.ui.activities.spirotest;

import com.yolohealth.lunngmonitor.api.ErrorUtils;
import com.yolohealth.lunngmonitor.api.RestClient;
import com.yolohealth.lunngmonitor.model.commonresponse.CommonResponse;
import com.yolohealth.lunngmonitor.model.spirotestparams.SpiroTestParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpiroTestInteractorImpl implements SpiroTestInteractor{
    @Override
    public void spirometer(SpiroTestParams spiroTestParams, SpiroMeterCallListener listener) {

        Call<CommonResponse> responseCall = RestClient.getClient()
                                           .SpiroMeterTest(spiroTestParams);

        responseCall.enqueue(new Callback<CommonResponse>() {
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
