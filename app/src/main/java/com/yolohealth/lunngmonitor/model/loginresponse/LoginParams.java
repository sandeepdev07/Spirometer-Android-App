package com.yolohealth.lunngmonitor.model.loginresponse;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class LoginParams implements Parcelable {
    private String email;

    private String password;

    public String getMobile() {
        return email;
    }

    public void setMobile(String phone) {
        this.email = phone;
    }


    public String getOtp() {
        return password;
    }

    public void setOtp(String otp) {
        this.password = otp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.password);
    }

    public LoginParams() {
    }

    protected LoginParams(Parcel in) {
        this.email = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<LoginParams> CREATOR = new Parcelable.Creator<LoginParams>() {
        @Override
        public LoginParams createFromParcel(Parcel source) {
            return new LoginParams(source);
        }

        @Override
        public LoginParams[] newArray(int size) {
            return new LoginParams[size];
        }
    };
}

