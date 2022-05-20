package com.yolohealth.spirometer.ui.activities.spirotest;

import com.yolohealth.spirometer.model.forgotpassword.EmailVerificationCode;
import com.yolohealth.spirometer.model.forgotpassword.ResetPasswordParams;
import com.yolohealth.spirometer.model.spirotestparams.SpiroTestParams;

public interface SpiroTestPresenter {

    void spirometer(SpiroTestParams spiroTestParams);

    void emailverification(EmailVerificationCode emailVerificationCode);

    void resetpassword(ResetPasswordParams resetPasswordParams);
}
