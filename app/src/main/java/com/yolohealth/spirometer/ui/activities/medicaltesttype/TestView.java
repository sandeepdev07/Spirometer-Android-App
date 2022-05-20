package com.yolohealth.spirometer.ui.activities.medicaltesttype;

import com.yolohealth.spirometer.model.medicalservicesresponse.MedicalServicesResponse;

public interface TestView {

    void showProgress();

    void showSuccess(MedicalServicesResponse medicalServicesResponse, String msg);

    void showError(String errMsg);
}
