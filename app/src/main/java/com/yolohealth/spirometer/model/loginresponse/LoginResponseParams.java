package com.yolohealth.spirometer.model.loginresponse;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseParams implements Parcelable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Creator<LoginResponseParams> CREATOR = new Creator<LoginResponseParams>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LoginResponseParams createFromParcel(android.os.Parcel in) {
            return new LoginResponseParams(in);
        }

        public LoginResponseParams[] newArray(int size) {
            return (new LoginResponseParams[size]);
        }

    }
            ;

    protected LoginResponseParams(android.os.Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public LoginResponseParams() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

}
