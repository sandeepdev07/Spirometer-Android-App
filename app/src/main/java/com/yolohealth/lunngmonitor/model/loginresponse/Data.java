package com.yolohealth.lunngmonitor.model.loginresponse;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data  implements Parcelable
{

    @SerializedName("user")
    @Expose
    private User user;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(android.os.Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
            ;

    protected Data(android.os.Parcel in) {
        this.user = ((User) in.readValue((User.class.getClassLoader())));
    }

    public Data() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(user);
    }

    public int describeContents() {
        return 0;
    }

}
