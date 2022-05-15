package com.yolohealth.lunngmonitor.ui.activities.test;

import com.yolohealth.lunngmonitor.LungMonitorApp;
import com.yolohealth.lunngmonitor.api.ErrorUtils;
import com.yolohealth.lunngmonitor.api.RestClient;
import com.yolohealth.lunngmonitor.model.medicalservicesresponse.MedicalServicesResponse;
import com.yolohealth.lunngmonitor.model.tokenresponse.TokenParams;
import com.yolohealth.lunngmonitor.model.tokenresponse.TokenResponse;
import com.yolohealth.lunngmonitor.utils.SharedPrefUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestInteractorImpl implements TestInteractor {
    @Override
    public void test(MedicalServicesResponse medicalServicesResponse, TestViewListener listener) {

        String token = SharedPrefUtils.getToken(Objects.requireNonNull(LungMonitorApp.getAppContext()));

        Call<MedicalServicesResponse> apiResponse = RestClient.getClient().testType(token);

        apiResponse.enqueue(new Callback<MedicalServicesResponse>() {
            @Override
            public void onResponse(Call<MedicalServicesResponse> call, Response<MedicalServicesResponse> response) {

                if (response.isSuccessful()) {

                    MedicalServicesResponse testTypes = response.body();
                    if (testTypes.getStatus() == 1) {
                        listener.onSuccess(testTypes, testTypes.getMessage());


                    } else {
                        listener.onError(testTypes.getMessage());

                    }
                } else {
                    listener.onError(ErrorUtils.parseError(response));

                }
            }

            @Override
            public void onFailure(Call<MedicalServicesResponse> call, Throwable t) {

                listener.onError("");
            }
        });
    }
}
