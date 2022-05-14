package com.yolohealth.lunngmonitor.model.loginresponse;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User  implements Parcelable
{

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
    private Object kiosks;
    @SerializedName("doctor_operating_device_type")
    @Expose
    private DoctorOperatingDeviceType doctorOperatingDeviceType;
    @SerializedName("token")
    @Expose
    private String token;
    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(android.os.Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
            ;

    protected User(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.role = ((Role) in.readValue((Role.class.getClassLoader())));
        this.status = ((Status) in.readValue((Status.class.getClassLoader())));
        in.readList(this.centers, (java.lang.Object.class.getClassLoader()));
        this.kiosks = ((Object) in.readValue((Object.class.getClassLoader())));
        this.doctorOperatingDeviceType = ((DoctorOperatingDeviceType) in.readValue((DoctorOperatingDeviceType.class.getClassLoader())));
        this.token = ((String) in.readValue((String.class.getClassLoader())));
    }

    public User() {
    }

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

    public Object getKiosks() {
        return kiosks;
    }

    public void setKiosks(Object kiosks) {
        this.kiosks = kiosks;
    }

    public DoctorOperatingDeviceType getDoctorOperatingDeviceType() {
        return doctorOperatingDeviceType;
    }

    public void setDoctorOperatingDeviceType(DoctorOperatingDeviceType doctorOperatingDeviceType) {
        this.doctorOperatingDeviceType = doctorOperatingDeviceType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(email);
        dest.writeValue(name);
        dest.writeValue(mobile);
        dest.writeValue(role);
        dest.writeValue(status);
        dest.writeList(centers);
        dest.writeValue(kiosks);
        dest.writeValue(doctorOperatingDeviceType);
        dest.writeValue(token);
    }

    public int describeContents() {
        return 0;
    }

}
