package com.yolohealth.lunngmonitor.ui.activities.login;

import com.yolohealth.lunngmonitor.model.loginresponse.LoginResponseParams;

public interface LoginView {

    void showProgress();
    void showLoginSuccess(String msg, LoginResponseParams responseParams);
    void showLoginError(String msg);
}
