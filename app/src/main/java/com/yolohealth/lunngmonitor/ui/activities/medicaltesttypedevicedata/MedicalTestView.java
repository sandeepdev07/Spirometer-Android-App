package com.yolohealth.lunngmonitor.ui.activities.medicaltesttypedevicedata;

import com.yolohealth.lunngmonitor.model.medicalservicesresponse.MedicalServicesResponse;

public interface MedicalTestView {

    void showProgress();

    void showMedicalSuccess(MedicalServicesResponse medicalServicesResponse, String msg);

    void showError(String errMsg);
}
