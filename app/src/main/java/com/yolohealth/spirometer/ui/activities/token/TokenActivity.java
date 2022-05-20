package com.yolohealth.spirometer.ui.activities.token;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.yolohealth.spirometer.databinding.ActivityTokenBinding;
import com.yolohealth.spirometer.model.tokenresponse.TokenParams;
import com.yolohealth.spirometer.model.tokenresponse.TokenResponse;
import com.yolohealth.spirometer.ui.activities.BaseActivity;
import com.yolohealth.spirometer.ui.activities.profile.ProfileActivity;
import com.yolohealth.spirometer.utils.Common_Utils;
import com.yolohealth.spirometer.widget.ProgressDialog;

import java.util.List;
import java.util.Objects;

public class TokenActivity extends BaseActivity implements Validator.ValidationListener, TokenView, View.OnClickListener {
    ActivityTokenBinding mBinding;
    @NotEmpty(message = "Please enter token number")
    TextInputEditText etLoginNumber;
    Validator mValidator;
    private TokenPresenter tokenPresenter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTokenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        progressDialog = ProgressDialog.getInstance();

        etLoginNumber = mBinding.etPhone;
        mBinding.btnLogin.setOnClickListener(this);
        setContentView(mBinding.getRoot());

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

        tokenPresenter = new TokenPresenterImpl(this);

    }

    @Override
    public void onClick(View view) {

        Common_Utils.hideKeyboard(this);
        mBinding.tilLogin.setError(null);
        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {

        String token = Objects.requireNonNull(etLoginNumber.getText()).toString();
        if (Common_Utils.isNotNullOrEmpty(token) && token.length() < 2) {
            mBinding.tilLogin.setError("Please enter token number");
            mBinding.tilLogin.setErrorEnabled(true);
            etLoginNumber.requestFocus();
            return;
        }

        TokenParams tokenParams = new TokenParams();
        tokenParams.setToken_no(token);

        tokenPresenter.token(tokenParams);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Common_Utils.showError(this, errors);

    }

    @Override
    public void showProgress() {

        progressDialog.show(TokenActivity.this);
    }

    @Override
    public void showSuccess(TokenResponse tokenResponse, String msg) {

        progressDialog.dismiss();
        Common_Utils.showToast(this, msg);
        String patientName = tokenResponse.getData().getPatients().getName();
        String patientNo = tokenResponse.getData().getPatients().getMobile();
        String patientSex = tokenResponse.getData().getPatients().getGender().getTitle();
        String patientAge = tokenResponse.getData().getPatients().getDob();
        String patientState = tokenResponse.getData().getPatients().getState().getTitle();
        String patientDistrict = tokenResponse.getData().getPatients().getDistrict().getTitle();
        String patientBarcode = tokenResponse.getData().getPatients().getBarcode();
        String patientType = tokenResponse.getData().getPatients().getBeneficiaryType().getTitle();
        String patientTestDate = tokenResponse.getData().getPatients().getRegisteredOn();

        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("name", patientName);
        i.putExtra("number", patientNo);
        i.putExtra("sex", patientSex);
        i.putExtra("age", patientAge);
        i.putExtra("state", patientState);
        i.putExtra("district", patientDistrict);
        i.putExtra("barcode", patientBarcode);
        i.putExtra("type", patientType);
        i.putExtra("testDate", patientTestDate);
        startActivity(i);
        finish();

    }

    @Override
    public void showError(String errMsg) {
        progressDialog.dismiss();
        Common_Utils.showToast(this, errMsg);

    }
}