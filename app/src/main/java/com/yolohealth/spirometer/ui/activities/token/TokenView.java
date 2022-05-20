package com.yolohealth.spirometer.ui.activities.token;

import com.yolohealth.spirometer.model.tokenresponse.TokenResponse;

public interface TokenView {

    void showProgress();

    void showSuccess(TokenResponse tokenResponse, String msg);

    void showError(String errMsg);
}
