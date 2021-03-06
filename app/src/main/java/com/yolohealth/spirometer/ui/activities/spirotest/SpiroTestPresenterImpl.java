package com.yolohealth.spirometer.ui.activities.spirotest;

import com.yolohealth.spirometer.LungMonitorApp;
import com.yolohealth.spirometer.R;
import com.yolohealth.spirometer.model.forgotpassword.EmailVerificationCode;
import com.yolohealth.spirometer.model.forgotpassword.ResetPasswordParams;
import com.yolohealth.spirometer.model.spirotestparams.SpiroTestParams;
import com.yolohealth.spirometer.utils.Common_Utils;

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

    @Override
    public void resetpassword(ResetPasswordParams resetPasswordParams) {


        if (!Common_Utils.isNetworkAvailable()) {
            Common_Utils.showToast(LungMonitorApp.getAppContext(), LungMonitorApp.getAppContext().getResources().getString(R.string.no_internet));
            return;
        }

        if (spiroTestView != null) {
            spiroTestView.showProgress();
        }
        spiroTestInteractor.resetpassword(resetPasswordParams, this);
    }
}

