package com.yolohealth.spirometer.model.tokenresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Center {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("center_name")
    @Expose
    private String centerName;
    @SerializedName("country")
    @Expose
    private Country__1 country;
    @SerializedName("state")
    @Expose
    private State__1 state;
    @SerializedName("district")
    @Expose
    private District__1 district;

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

    public Country__1 getCountry() {
        return country;
    }

    public void setCountry(Country__1 country) {
        this.country = country;
    }

    public State__1 getState() {
        return state;
    }

    public void setState(State__1 state) {
        this.state = state;
    }

    public District__1 getDistrict() {
        return district;
    }

    public void setDistrict(District__1 district) {
        this.district = district;
    }

}
