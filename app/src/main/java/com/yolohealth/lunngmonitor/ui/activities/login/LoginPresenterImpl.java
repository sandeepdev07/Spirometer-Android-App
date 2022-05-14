package com.yolohealth.lunngmonitor.ui.activities.login;

import com.yolohealth.lunngmonitor.LungMonitorApp;
import com.yolohealth.lunngmonitor.R;
import com.yolohealth.lunngmonitor.model.loginresponse.LoginParams;
import com.yolohealth.lunngmonitor.model.loginresponse.LoginResponseParams;
import com.yolohealth.lunngmonitor.utils.Common_Utils;

public class LoginPresenterImpl implements LoginPresenter,LoginInteractor.LoginViewListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void onSuccess(String msg, LoginResponseParams responseParams) {

        if(loginView!=null){
            loginView.showLoginSuccess(msg,responseParams);
        }

    }

    @Override
    public void onError(String msg) {
        if(loginView!=null){
            loginView.showLoginError(msg);
        }

    }

    @Override
    public void login(LoginParams loginParams) {


        if (!Common_Utils.isNetworkAvailable()) {
            Common_Utils.showToast(LungMonitorApp.getAppContext(),LungMonitorApp.getAppContext().getResources().getString(R.string.no_internet));
            return;
        }


        if(loginView!=null){
            loginView.showProgress();
        }

        loginInteractor.login(loginParams,this);

    }
}
