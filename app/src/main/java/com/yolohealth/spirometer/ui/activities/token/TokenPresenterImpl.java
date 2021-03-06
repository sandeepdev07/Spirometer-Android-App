package com.yolohealth.spirometer.ui.activities.token;

import com.yolohealth.spirometer.LungMonitorApp;
import com.yolohealth.spirometer.R;
import com.yolohealth.spirometer.model.tokenresponse.TokenParams;
import com.yolohealth.spirometer.model.tokenresponse.TokenResponse;
import com.yolohealth.spirometer.utils.Common_Utils;

public class TokenPresenterImpl implements TokenPresenter, TokenInteractor.TokenViewListener {

    private TokenView tokenView;
    private TokenInteractor tokenInteractor;

    public TokenPresenterImpl(TokenView tokenView) {
        this.tokenView = tokenView;
        this.tokenInteractor = new TokenInteractorImpl();
    }

    @Override
    public void onSuccess(TokenResponse tokenResponse, String msg) {

        if (tokenView != null) {
            tokenView.showSuccess(tokenResponse, msg);
        }

    }

    @Override
    public void onError(String errMsg) {

        if (tokenView != null) {
            tokenView.showError(errMsg);
        }

    }

    @Override
    public void token(TokenParams tokenParams) {

        if (!Common_Utils.isNetworkAvailable()) {
            Common_Utils.showToast(LungMonitorApp.getAppContext(), LungMonitorApp.getAppContext().getResources().getString(R.string.no_internet));
            return;
        }

        if (tokenView != null) {
            tokenView.showProgress();
        }

        tokenInteractor.token(tokenParams, this);

    }
}
