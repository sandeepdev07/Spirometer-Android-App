package com.yolohealth.lunngmonitor.ui.activities.medicaltesttypedevicedata;

import com.yolohealth.lunngmonitor.model.medicalservicesresponse.MedicalServicesResponse;
import com.yolohealth.lunngmonitor.ui.activities.medicaltesttype.TestInteractor;

public interface MedicalTestInteractor {

    interface MedicalTestViewListener {
        void onSuccess(MedicalServicesResponse medicalServicesResponse, String msg);

        void onError(String errMsg);
    }

    void medicaltest(MedicalServicesResponse medicalServicesResponse, MedicalTestViewListener listener);
}
