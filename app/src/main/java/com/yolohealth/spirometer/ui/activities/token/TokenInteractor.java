package com.yolohealth.spirometer.ui.activities.token;

import com.yolohealth.spirometer.model.tokenresponse.TokenParams;
import com.yolohealth.spirometer.model.tokenresponse.TokenResponse;

public interface TokenInteractor {

    interface TokenViewListener {
        void onSuccess(TokenResponse tokenResponse, String msg);

        void onError(String errMsg);
    }

    void token(TokenParams tokenParams, TokenViewListener listener);
}
