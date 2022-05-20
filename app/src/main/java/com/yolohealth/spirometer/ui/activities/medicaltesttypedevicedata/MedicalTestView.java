package com.yolohealth.spirometer.ui.activities.medicaltesttypedevicedata;

import com.yolohealth.spirometer.model.medicalservicesresponse.MedicalServicesResponse;

public interface MedicalTestView {

    void showProgress();

    void showMedicalSuccess(MedicalServicesResponse medicalServicesResponse, String msg);

    void showError(String errMsg);
}
