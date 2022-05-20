package com.yolohealth.spirometer.ui.activities.medicaltesttype;

import com.yolohealth.spirometer.model.medicalservicesresponse.MedicalServicesResponse;

public interface TestInteractor {

    interface TestViewListener {
        void onSuccess(MedicalServicesResponse medicalServicesResponse, String msg);

        void onError(String errMsg);
    }

    void test(MedicalServicesResponse medicalServicesResponse, TestViewListener listener);
}
