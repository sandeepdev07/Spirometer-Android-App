package com.yolohealth.spirometer.model.loginresponse;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorOperatingDeviceType  implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("dev_code")
    @Expose
    private String devCode;
    public final static Creator<DoctorOperatingDeviceType> CREATOR = new Creator<DoctorOperatingDeviceType>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DoctorOperatingDeviceType createFromParcel(android.os.Parcel in) {
            return new DoctorOperatingDeviceType(in);
        }

        public DoctorOperatingDeviceType[] newArray(int size) {
            return (new DoctorOperatingDeviceType[size]);
        }

    }
            ;

    protected DoctorOperatingDeviceType(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.devCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DoctorOperatingDeviceType() {
    }

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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(devCode);
    }

    public int describeContents() {
        return 0;
    }

}