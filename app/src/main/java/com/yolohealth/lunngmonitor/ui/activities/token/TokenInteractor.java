package com.yolohealth.lunngmonitor.ui.activities.token;

import com.yolohealth.lunngmonitor.model.tokenresponse.TokenParams;
import com.yolohealth.lunngmonitor.model.tokenresponse.TokenResponse;

public interface TokenInteractor {

    interface TokenViewListener {
        void onSuccess(TokenResponse tokenResponse, String msg);

        void onError(String errMsg);
    }

    void token(TokenParams tokenParams, TokenViewListener listener);
}
