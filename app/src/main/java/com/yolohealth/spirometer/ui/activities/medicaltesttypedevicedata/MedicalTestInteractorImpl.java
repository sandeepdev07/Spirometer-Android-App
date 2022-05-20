package com.yolohealth.spirometer.ui.activities.medicaltesttypedevicedata;

import com.yolohealth.spirometer.LungMonitorApp;
import com.yolohealth.spirometer.api.ErrorUtils;
import com.yolohealth.spirometer.api.RestClient;
import com.yolohealth.spirometer.model.medicalservicesresponse.MedicalServicesResponse;
import com.yolohealth.spirometer.utils.SharedPrefUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalTestInteractorImpl implements MedicalTestInteractor{

    @Override
    public void medicaltest(MedicalServicesResponse medicalServicesResponse, MedicalTestViewListener listener) {

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
