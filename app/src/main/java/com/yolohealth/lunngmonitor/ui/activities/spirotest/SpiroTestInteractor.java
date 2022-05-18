package com.yolohealth.lunngmonitor.ui.activities.spirotest;

import com.yolohealth.lunngmonitor.model.forgotpassword.EmailVerificationCode;
import com.yolohealth.lunngmonitor.model.forgotpassword.ResetPasswordParams;
import com.yolohealth.lunngmonitor.model.spirotestparams.SpiroTestParams;

public interface SpiroTestInteractor {

    interface SpiroMeterCallListener {
        void onSuccess(String msg);

        void onError(String errMsg);
    }

    void spirometer(SpiroTestParams spiroTestParams, SpiroMeterCallListener listener);

    void  emailverification(EmailVerificationCode verificationCode ,SpiroMeterCallListener listener);

    void resetpassword(ResetPasswordParams resetPasswordParams ,SpiroMeterCallListener listener );

}
