package com.yolohealth.spirometer.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.yolohealth.spirometer.R;
import com.yolohealth.spirometer.databinding.ActivityLoginBinding;
import com.yolohealth.spirometer.model.forgotpassword.EmailVerificationCode;
import com.yolohealth.spirometer.model.loginresponse.LoginParams;
import com.yolohealth.spirometer.model.loginresponse.LoginResponseParams;
import com.yolohealth.spirometer.ui.activities.BaseActivity;
import com.yolohealth.spirometer.ui.activities.passwordreset.ResetPasswordActivity;
import com.yolohealth.spirometer.ui.activities.spirotest.SpiroTestPresenter;
import com.yolohealth.spirometer.ui.activities.spirotest.SpiroTestPresenterImpl;
import com.yolohealth.spirometer.ui.activities.spirotest.SpiroTestView;
import com.yolohealth.spirometer.ui.activities.token.TokenActivity;
import com.yolohealth.spirometer.utils.Common_Utils;
import com.yolohealth.spirometer.widget.ProgressDialog;

import java.util.List;
import java.util.Objects;

public class LoginActivity extends BaseActivity implements Validator.ValidationListener, LoginView, SpiroTestView, View.OnClickListener {
    ActivityLoginBinding mBinding;
    @NotEmpty(message = "Please provide valid credentials")
    TextInputEditText etLoginNumber, etPassword;
    TextInputEditText etEmailAddress;
    TextInputLayout tillEmailAddress;
    boolean isEmailValid;
    Validator mValidator;
    private LoginPresenter loginPresenter;
    ProgressDialog progressDialog;

    EmailVerificationCode emailVerificationCodeParams;
    private SpiroTestPresenter spiroTestPresenter;

    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());

        //setSupportActionBar(mBinding.topAppBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        progressDialog = ProgressDialog.getInstance();


        etLoginNumber = mBinding.etPhone;
        etPassword = mBinding.etPass;

        mBinding.btnLogin.setOnClickListener(this);
        mBinding.btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (forgotPassword()) {

                openManualDialog();

                    /*emailVerificationCodeParams = new EmailVerificationCode();
                    emailVerificationCodeParams.setEmail(Objects.requireNonNull(mBinding.etPhone.getText()).toString());

                    spiroTestPresenter.emailverification(emailVerificationCodeParams);*/

                //}
            }
        });
        setContentView(mBinding.getRoot());

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

        loginPresenter = new LoginPresenterImpl(this);
        spiroTestPresenter = new SpiroTestPresenterImpl(this);

    }

    private void openManualDialog() {
        ViewGroup viewGroup = LoginActivity.this.findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.manual_email_varifiaction, viewGroup, false);
        Button btnEnter = dialogView.findViewById(R.id.btn_enter);
        ImageView cancel = dialogView.findViewById(R.id.btn_cancel);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        etEmailAddress = dialogView.findViewById(R.id.et_phone);
        tillEmailAddress = dialogView.findViewById(R.id.til_login);

        if (!mBinding.etPhone.getText().toString().isEmpty()) {
            etEmailAddress.setText(mBinding.etPhone.getText().toString());
        }

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forgotPassword()) {

                    emailVerificationCodeParams = new EmailVerificationCode();
                    emailVerificationCodeParams.setEmail(Objects.requireNonNull(etEmailAddress.getText()).toString());

                    spiroTestPresenter.emailverification(emailVerificationCodeParams);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        alertDialog.show();

    }

    private boolean forgotPassword() {

        if (Objects.requireNonNull(etEmailAddress.getText()).toString().isEmpty()) {
            tillEmailAddress.setError("Please provide email address");
            isEmailValid = false;
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmailAddress.getText().toString()).matches()) {
            tillEmailAddress.setError("Email incorrect");
            isEmailValid = false;
            return false;
        } else {
            isEmailValid = true;
            tillEmailAddress.setErrorEnabled(false);
        }

        return true;
    }


    @Override
    public void onClick(View view) {
        Common_Utils.hideKeyboard(this);
        mBinding.tilLogin.setError(null);
        mBinding.tilPass.setError(null);
        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {

        String email = Objects.requireNonNull(etLoginNumber.getText()).toString();
        String password = Objects.requireNonNull(etPassword.getText()).toString();
        if (Common_Utils.isNotNullOrEmpty(email) && email.length() < 1) {
            mBinding.tilLogin.setError("Please enter email address");
            // mBinding.tilPass.setError("Please enter your password");
            mBinding.tilLogin.setErrorEnabled(true);
            // mBinding.tilPass.setErrorEnabled(true);
            etLoginNumber.requestFocus();
            //  etPassword.requestFocus();
            return;
        }

        if (Common_Utils.isNotNullOrEmpty(password) && password.length() < 6) {
            mBinding.tilPass.setError("Please enter your password");
            mBinding.tilPass.setErrorEnabled(true);
            etPassword.requestFocus();
            return;

        }


        LoginParams loginParams = new LoginParams();

        loginParams.setMobile(email);
        loginParams.setOtp(password);

        loginPresenter.login(loginParams);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Common_Utils.showError(this, errors);

    }


    @Override
    public void showProgress() {
        progressDialog.show(LoginActivity.this);
    }


    //--------------------------email verification success
    @Override
    public void showSuccess(String msg) {

        Intent i;
        i = new Intent(getApplicationContext(), ResetPasswordActivity.class);
        i.putExtra("email", etEmailAddress.getText().toString());
        startActivity(i);
        progressDialog.dismiss();
        alertDialog.dismiss();
    }

    @Override
    public void showError(String errMsg) {
        progressDialog.dismiss();
        Common_Utils.showToast(getApplicationContext(), errMsg);
    }


    //-------------------------------

    @Override
    public void showLoginSuccess(String msg, LoginResponseParams responseParams) {

        progressDialog.dismiss();
        Common_Utils.showToast(this, msg);
        Intent intent = new Intent(this, TokenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showLoginError(String msg) {
        progressDialog.dismiss();
        Common_Utils.showToast(this, msg);
    }
}