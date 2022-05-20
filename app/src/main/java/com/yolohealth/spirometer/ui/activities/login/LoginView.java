package com.yolohealth.spirometer.ui.activities.login;

import com.yolohealth.spirometer.model.loginresponse.LoginResponseParams;

public interface LoginView {

    void showProgress();
    void showLoginSuccess(String msg, LoginResponseParams responseParams);
    void showLoginError(String msg);
}
