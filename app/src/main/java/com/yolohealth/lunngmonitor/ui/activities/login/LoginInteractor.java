package com.yolohealth.lunngmonitor.ui.activities.login;

import com.yolohealth.lunngmonitor.model.loginresponse.LoginParams;
import com.yolohealth.lunngmonitor.model.loginresponse.LoginResponseParams;

public interface LoginInteractor {

    interface LoginViewListener {
        void onSuccess(String msg, LoginResponseParams responseParams);

        void onError(String msg);
    }

    void login(LoginParams loginParams, LoginViewListener changeListener);
}
