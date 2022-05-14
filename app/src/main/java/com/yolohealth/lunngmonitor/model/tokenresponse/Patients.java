package com.yolohealth.lunngmonitor.model.tokenresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Patients {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("labourid_no")
    @Expose
    private String labouridNo;
    @SerializedName("mrn_no")
    @Expose
    private String mrnNo;
    @SerializedName("labourid_img")
    @Expose
    private String labouridImg;
    @SerializedName("gender")
    @Expose
    private Gender gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("beneficiary_type")
    @Expose
    private BeneficiaryType beneficiaryType;
    @SerializedName("country")
    @Expose
    private Country country;
    @SerializedName("state")
    @Expose
    private State state;
    @SerializedName("district")
    @Expose
    private District district;
    @SerializedName("created_by_user")
    @Expose
    private CreatedByUser createdByUser;
    @SerializedName("center")
    @Expose
    private Object center;
    @SerializedName("kiosk_labour_reports")
    @Expose
    private List<Object> kioskLabourReports = null;
    @SerializedName("kiosk")
    @Expose
    private Kiosk kiosk;
    @SerializedName("token_number")
    @Expose
    private Integer tokenNumber;
    @SerializedName("registered_on")
    @Expose
    private String registeredOn;
    @SerializedName("barcode")
    @Expose
    private String barcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLabouridNo() {
        return labouridNo;
    }

    public void setLabouridNo(String labouridNo) {
        this.labouridNo = labouridNo;
    }

    public String getMrnNo() {
        return mrnNo;
    }

    public void setMrnNo(String mrnNo) {
        this.mrnNo = mrnNo;
    }

    public String getLabouridImg() {
        return labouridImg;
    }

    public void setLabouridImg(String labouridImg) {
        this.labouridImg = labouridImg;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public BeneficiaryType getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(BeneficiaryType beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public CreatedByUser getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(CreatedByUser createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Object getCenter() {
        return center;
    }

    public void setCenter(Object center) {
        this.center = center;
    }

    public List<Object> getKioskLabourReports() {
        return kioskLabourReports;
    }

    public void setKioskLabourReports(List<Object> kioskLabourReports) {
        this.kioskLabourReports = kioskLabourReports;
    }

    public Kiosk getKiosk() {
        return kiosk;
    }

    public void setKiosk(Kiosk kiosk) {
        this.kiosk = kiosk;
    }

    public Integer getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(Integer tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
