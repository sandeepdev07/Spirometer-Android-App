package com.yolohealth.lunngmonitor.model.tokenresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yolohealth.lunngmonitor.model.loginresponse.Role;

import java.util.List;

public class CreatedByUser {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("role")
    @Expose
    private Role role;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("centers")
    @Expose
    private List<Object> centers = null;
    @SerializedName("kiosks")
    @Expose
    private Kiosks kiosks;
    @SerializedName("doctor_operating_device_type")
    @Expose
    private Object doctorOperatingDeviceType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Object> getCenters() {
        return centers;
    }

    public void setCenters(List<Object> centers) {
        this.centers = centers;
    }

    public Kiosks getKiosks() {
        return kiosks;
    }

    public void setKiosks(Kiosks kiosks) {
        this.kiosks = kiosks;
    }

    public Object getDoctorOperatingDeviceType() {
        return doctorOperatingDeviceType;
    }

    public void setDoctorOperatingDeviceType(Object doctorOperatingDeviceType) {
        this.doctorOperatingDeviceType = doctorOperatingDeviceType;
    }

}
