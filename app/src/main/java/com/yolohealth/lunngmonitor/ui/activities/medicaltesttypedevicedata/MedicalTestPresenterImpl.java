package com.yolohealth.lunngmonitor.ui.activities.medicaltesttypedevicedata;

import com.yolohealth.lunngmonitor.LungMonitorApp;
import com.yolohealth.lunngmonitor.R;
import com.yolohealth.lunngmonitor.model.medicalservicesresponse.MedicalServicesResponse;
import com.yolohealth.lunngmonitor.utils.Common_Utils;

public class MedicalTestPresenterImpl implements MedicalTestPresenter, MedicalTestInteractor.MedicalTestViewListener {

    private MedicalTestView testView;
    private MedicalTestInteractor testInteractor;

    public MedicalTestPresenterImpl(MedicalTestView testView) {
        this.testView = testView;
        this.testInteractor = new MedicalTestInteractorImpl();
    }

    @Override
    public void onSuccess(MedicalServicesResponse medicalServicesResponse, String msg) {

        if (testView != null) {
            testView.showMedicalSuccess(medicalServicesResponse, msg);
        }
    }

    @Override
    public void onError(String errMsg) {

        if (testView != null) {
            testView.showError(errMsg);
        }
    }

    @Override
    public void medicaltest(MedicalServicesResponse medicalServicesResponse) {

        if (!Common_Utils.isNetworkAvailable()) {
            Common_Utils.showToast(LungMonitorApp.getAppContext(), LungMonitorApp.getAppContext().getResources().getString(R.string.no_internet));
            return;
        }

        if (testView != null) {
            testView.showProgress();
        }

        testInteractor.medicaltest(medicalServicesResponse,this);

    }
}

