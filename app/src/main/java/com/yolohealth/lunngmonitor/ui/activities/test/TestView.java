package com.yolohealth.lunngmonitor.ui.activities.test;

import com.yolohealth.lunngmonitor.model.medicalservicesresponse.MedicalServicesResponse;

public interface TestView {

    void showProgress();

    void showSuccess(MedicalServicesResponse medicalServicesResponse, String msg);

    void showError(String errMsg);
}
