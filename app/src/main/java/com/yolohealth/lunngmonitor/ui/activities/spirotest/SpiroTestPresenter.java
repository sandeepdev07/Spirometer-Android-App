package com.yolohealth.lunngmonitor.ui.activities.spirotest;

import com.yolohealth.lunngmonitor.model.emailverificatoincode.EmailVerificationCode;
import com.yolohealth.lunngmonitor.model.spirotestparams.SpiroTestParams;

public interface SpiroTestPresenter {

    void spirometer(SpiroTestParams spiroTestParams);

    void emailverification(EmailVerificationCode emailVerificationCode);
}
