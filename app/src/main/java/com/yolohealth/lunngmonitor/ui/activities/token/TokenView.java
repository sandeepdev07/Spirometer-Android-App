package com.yolohealth.lunngmonitor.ui.activities.token;

import com.yolohealth.lunngmonitor.model.tokenresponse.TokenResponse;

public interface TokenView {

    void showProgress();

    void showSuccess(TokenResponse tokenResponse, String msg);

    void showError(String errMsg);
}
