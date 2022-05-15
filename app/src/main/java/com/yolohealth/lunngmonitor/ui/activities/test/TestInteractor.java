package com.yolohealth.lunngmonitor.ui.activities.test;

import com.yolohealth.lunngmonitor.model.medicalservicesresponse.MedicalServicesResponse;
import com.yolohealth.lunngmonitor.model.tokenresponse.TokenParams;
import com.yolohealth.lunngmonitor.model.tokenresponse.TokenResponse;
import com.yolohealth.lunngmonitor.ui.activities.token.TokenInteractor;

public interface TestInteractor {

    interface TestViewListener {
        void onSuccess(MedicalServicesResponse medicalServicesResponse, String msg);

        void onError(String errMsg);
    }

    void test(MedicalServicesResponse medicalServicesResponse, TestViewListener listener);
}
