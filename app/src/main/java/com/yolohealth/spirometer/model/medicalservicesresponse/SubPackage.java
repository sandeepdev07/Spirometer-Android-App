package com.yolohealth.spirometer.model.medicalservicesresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubPackage {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("dev_code")
    @Expose
    private String devCode;
    @SerializedName("label")
    @Expose
    private String label;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
