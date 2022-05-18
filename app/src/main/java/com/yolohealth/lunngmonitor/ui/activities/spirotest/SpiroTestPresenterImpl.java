package com.yolohealth.lunngmonitor.ui.activities.spirotest;

import com.yolohealth.lunngmonitor.LungMonitorApp;
import com.yolohealth.lunngmonitor.R;
import com.yolohealth.lunngmonitor.model.emailverificatoincode.EmailVerificationCode;
import com.yolohealth.lunngmonitor.model.spirotestparams.SpiroTestParams;
import com.yolohealth.lunngmonitor.utils.Common_Utils;

public class SpiroTestPresenterImpl implements SpiroTestPresenter, SpiroTestInteractor.SpiroMeterCallListener {

    private SpiroTestView spiroTestView;
    private SpiroTestInteractor spiroTestInteractor;

    public SpiroTestPresenterImpl(SpiroTestView spiroTestView) {
        this.spiroTestView = spiroTestView;
        this.spiroTestInteractor = new SpiroTestInteractorImpl();
    }

    @Override
    public void onSuccess(String msg) {

        if (spiroTestView != null) {
            spiroTestView.showSuccess(msg);
        }
    }

    @Override
    public void onError(String errMsg) {

        if (spiroTestView != null) {
            spiroTestView.showError(errMsg);
        }
    }

    @Override
    public void spirometer(SpiroTestParams spiroTestParams) {

        if (!Common_Utils.isNetworkAvailable()) {
            Common_Utils.showToast(LungMonitorApp.getAppContext(), LungMonitorApp.getAppContext().getResources().getString(R.string.no_internet));
            return;
        }

        if (spiroTestView != null) {
            spiroTestView.showProgress();
        }
        spiroTestInteractor.spirometer(spiroTestParams, this);

    }

    @Override
    public void emailverification(EmailVerificationCode emailVerificationCode) {

        if (!Common_Utils.isNetworkAvailable()) {
            Common_Utils.showToast(LungMonitorApp.getAppContext(), LungMonitorApp.getAppContext().getResources().getString(R.string.no_internet));
            return;
        }

        if (spiroTestView != null) {
            spiroTestView.showProgress();
        }
        spiroTestInteractor.emailverification(emailVerificationCode, this);

    }
}

