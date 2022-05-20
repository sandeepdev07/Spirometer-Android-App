package com.yolohealth.spirometer.model.tokenresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Center__1 {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("center_name")
    @Expose
    private String centerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }
}
