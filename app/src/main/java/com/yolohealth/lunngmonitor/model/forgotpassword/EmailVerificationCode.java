package com.yolohealth.lunngmonitor.model.forgotpassword;

public class EmailVerificationCode {
    String email;

    public EmailVerificationCode() {

    }

    public EmailVerificationCode(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
