package com.yolohealth.spirometer.ui.activities.medicaltesttype;

import com.yolohealth.spirometer.LungMonitorApp;
import com.yolohealth.spirometer.R;
import com.yolohealth.spirometer.model.medicalservicesresponse.MedicalServicesResponse;
import com.yolohealth.spirometer.utils.Common_Utils;

public class TestPresenterImpl implements TestPresenter , TestInteractor.TestViewListener {

    private TestView testView;
    private TestInteractor testInteractor;

    public TestPresenterImpl(TestView testView) {
        this.testView = testView;
        this.testInteractor = new TestInteractorImpl();
    }

    @Override
    public void onSuccess(MedicalServicesResponse medicalServicesResponse, String msg) {

        if (testView != null) {
            testView.showSuccess(medicalServicesResponse, msg);
        }

    }

    @Override
    public void onError(String errMsg) {

        if (testView != null) {
            testView.showError(errMsg);
        }
    }

    @Override
    public void test(MedicalServicesResponse medicalServicesResponse) {

        if (!Common_Utils.isNetworkAvailable()) {
            Common_Utils.showToast(LungMonitorApp.getAppContext(), LungMonitorApp.getAppContext().getResources().getString(R.string.no_internet));
            return;
        }

        if (testView != null) {
            testView.showProgress();
        }

        testInteractor.test(medicalServicesResponse,this);

    }
}
