package com.yolohealth.spirometer.ui.activities.login;

import com.yolohealth.spirometer.model.loginresponse.LoginParams;
import com.yolohealth.spirometer.model.loginresponse.LoginResponseParams;

public interface LoginInteractor {

    interface LoginViewListener {
        void onSuccess(String msg, LoginResponseParams responseParams);

        void onError(String msg);
    }

    void login(LoginParams loginParams, LoginViewListener changeListener);
}
