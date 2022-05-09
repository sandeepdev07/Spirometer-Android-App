package com.yolohealth.lunngmonitor.model;

public class MessageEvent {

    public final int type;
    public static final int EVENT_HOME_REFRESH = 0;
    public static final int EVENT_CONSULT_REFRESH = 1;
    public Object data = null;

    public MessageEvent(int type) {
        this.type = type;
    }

    public MessageEvent(int type, Object data) {
        this.type = type;
        this.data = data;
    }
}
