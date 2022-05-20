package com.yolohealth.spirometer.model.tokenresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kiosk {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kiosk_id")
    @Expose
    private Integer kioskId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("kiosk_prefix")
    @Expose
    private String kioskPrefix;
    @SerializedName("center")
    @Expose
    private Center__1 center;
    @SerializedName("kiosk_type")
    @Expose
    private KioskType__1 kioskType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKioskId() {
        return kioskId;
    }

    public void setKioskId(Integer kioskId) {
        this.kioskId = kioskId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKioskPrefix() {
        return kioskPrefix;
    }

    public void setKioskPrefix(String kioskPrefix) {
        this.kioskPrefix = kioskPrefix;
    }

    public Center__1 getCenter() {
        return center;
    }

    public void setCenter(Center__1 center) {
        this.center = center;
    }

    public KioskType__1 getKioskType() {
        return kioskType;
    }

    public void setKioskType(KioskType__1 kioskType) {
        this.kioskType = kioskType;
    }
}
