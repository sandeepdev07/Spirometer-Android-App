package com.yolohealth.lunngmonitor.model.tokenresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {
    @SerializedName("patients")
    @Expose
    private Patients patients;

    public Patients getPatients() {
        return patients;
    }

    public void setPatients(Patients patients) {
        this.patients = patients;
    }
}
