package com.yolohealth.lunngmonitor.ui.activities.spirotest;

import com.yolohealth.lunngmonitor.model.spirotestparams.SpiroTestParams;

public interface SpiroTestInteractor {

    interface SpiroMeterCallListener {
        void onSuccess(String msg);

        void onError(String errMsg);
    }

    void spirometer(SpiroTestParams spiroTestParams, SpiroMeterCallListener listener);
}
