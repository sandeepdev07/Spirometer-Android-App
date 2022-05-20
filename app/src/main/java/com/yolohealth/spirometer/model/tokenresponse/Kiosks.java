package com.yolohealth.spirometer.model.tokenresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kiosks {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("mac_id")
    @Expose
    private String macId;
    @SerializedName("kiosk_id")
    @Expose
    private Object kioskId;
    @SerializedName("kiosk_logo_url")
    @Expose
    private String kioskLogoUrl;
    @SerializedName("kiosk_prefix")
    @Expose
    private String kioskPrefix;
    @SerializedName("kiosk_type")
    @Expose
    private KioskType kioskType;
    @SerializedName("center")
    @Expose
    private Center center;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public Object getKioskId() {
        return kioskId;
    }

    public void setKioskId(Object kioskId) {
        this.kioskId = kioskId;
    }

    public String getKioskLogoUrl() {
        return kioskLogoUrl;
    }

    public void setKioskLogoUrl(String kioskLogoUrl) {
        this.kioskLogoUrl = kioskLogoUrl;
    }

    public String getKioskPrefix() {
        return kioskPrefix;
    }

    public void setKioskPrefix(String kioskPrefix) {
        this.kioskPrefix = kioskPrefix;
    }

    public KioskType getKioskType() {
        return kioskType;
    }

    public void setKioskType(KioskType kioskType) {
        this.kioskType = kioskType;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }
}
