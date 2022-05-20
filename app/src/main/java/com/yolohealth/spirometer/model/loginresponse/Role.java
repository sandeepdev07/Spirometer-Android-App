package com.yolohealth.spirometer.model.loginresponse;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Role  implements Parcelable
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
    public final static Creator<Role> CREATOR = new Creator<Role>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Role createFromParcel(android.os.Parcel in) {
            return new Role(in);
        }

        public Role[] newArray(int size) {
            return (new Role[size]);
        }

    }
            ;

    protected Role(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.devCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Role() {
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