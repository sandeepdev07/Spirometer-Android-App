package com.yolohealth.spirometer.ui.activities.spirotest;

public interface SpiroTestView {

    void showProgress();

    void showSuccess(String msg);

    void showError(String errMsg);
}
