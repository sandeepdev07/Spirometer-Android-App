package com.yolohealth.lunngmonitor.model;

public class SessionExpiredEvent {
    private boolean flag;

    public SessionExpiredEvent(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
}
