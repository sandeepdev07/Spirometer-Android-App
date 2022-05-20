package com.yolohealth.spirometer.ui.activities.medicaltesttypedevicedata;

import com.yolohealth.spirometer.model.medicalservicesresponse.MedicalServicesResponse;

public interface MedicalTestInteractor {

    interface MedicalTestViewListener {
        void onSuccess(MedicalServicesResponse medicalServicesResponse, String msg);

        void onError(String errMsg);
    }

    void medicaltest(MedicalServicesResponse medicalServicesResponse, MedicalTestViewListener listener);
}
