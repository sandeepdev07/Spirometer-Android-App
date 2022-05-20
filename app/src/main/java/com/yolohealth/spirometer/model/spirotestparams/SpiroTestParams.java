package com.yolohealth.spirometer.model.spirotestparams;

public class SpiroTestParams {

    String userid;
    String kioskid;
    String medicalservicepk;
    String spiro_fef;
    String spiro_pef;
    String spiro_fev1;
    String spiro_fev6;
    String comment;

    public SpiroTestParams(){

    }

    public SpiroTestParams(String userid, String kioskid, String medicalservicepk, String spiro_fef, String spiro_pef, String spiro_fev1, String spiro_fev6, String comment) {
        this.userid = userid;
        this.kioskid = kioskid;
        this.medicalservicepk = medicalservicepk;
        this.spiro_fef = spiro_fef;
        this.spiro_pef = spiro_pef;
        this.spiro_fev1 = spiro_fev1;
        this.spiro_fev6 = spiro_fev6;
        this.comment = comment;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getKioskid() {
        return kioskid;
    }

    public void setKioskid(String kioskid) {
        this.kioskid = kioskid;
    }

    public String getMedicalservicepk() {
        return medicalservicepk;
    }

    public void setMedicalservicepk(String medicalservicepk) {
        this.medicalservicepk = medicalservicepk;
    }

    public String getSpiro_fef() {
        return spiro_fef;
    }

    public void setSpiro_fef(String spiro_fef) {
        this.spiro_fef = spiro_fef;
    }

    public String getSpiro_pef() {
        return spiro_pef;
    }

    public void setSpiro_pef(String spiro_pef) {
        this.spiro_pef = spiro_pef;
    }

    public String getSpiro_fev1() {
        return spiro_fev1;
    }

    public void setSpiro_fev1(String spiro_fev1) {
        this.spiro_fev1 = spiro_fev1;
    }

    public String getSpiro_fev6() {
        return spiro_fev6;
    }

    public void setSpiro_fev6(String spiro_fev6) {
        this.spiro_fev6 = spiro_fev6;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
