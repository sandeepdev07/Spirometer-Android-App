package com.yolohealth.lunngmonitor.ui.activities.passwordreset;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.yolohealth.lunngmonitor.databinding.ActivityResetPasswordBinding;
import com.yolohealth.lunngmonitor.model.forgotpassword.ResetPasswordParams;
import com.yolohealth.lunngmonitor.ui.activities.BaseActivity;
import com.yolohealth.lunngmonitor.ui.activities.login.LoginActivity;
import com.yolohealth.lunngmonitor.ui.activities.spirotest.SpiroTestPresenter;
import com.yolohealth.lunngmonitor.ui.activities.spirotest.SpiroTestPresenterImpl;
import com.yolohealth.lunngmonitor.ui.activities.spirotest.SpiroTestView;
import com.yolohealth.lunngmonitor.utils.Common_Utils;
import com.yolohealth.lunngmonitor.widget.ProgressDialog;

import java.util.Objects;

public class ResetPasswordActivity extends BaseActivity implements SpiroTestView {
    ActivityResetPasswordBinding mBinding;
    private SpiroTestPresenter spiroTestPresenter;
    ResetPasswordParams resetPasswordParams;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityResetPasswordBinding.inflate(getLayoutInflater());

        progressDialog = ProgressDialog.getInstance();

        String email = getIntent().getStringExtra("email");
        System.out.println("email--" + email);

        mBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()) {
                    resetPasswordParams = new ResetPasswordParams();
                    resetPasswordParams.setCode(Objects.requireNonNull(mBinding.etCode.getText()).toString());
                    resetPasswordParams.setPassword(Objects.requireNonNull(mBinding.etPass.getText()).toString());
                    resetPasswordParams.setEmail(email);

                    spiroTestPresenter.resetpassword(resetPasswordParams);

                }

            }
        });


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Reset Password");
        actionBar.setElevation(0);

        spiroTestPresenter = new SpiroTestPresenterImpl(this);

        setContentView(mBinding.getRoot());

    }

    private boolean validation() {

        if (TextUtils.isEmpty(Objects.requireNonNull(mBinding.etCode.getText()).toString())) {
            mBinding.tilCode.setError("Required filed");
            return false;
        } else {
            mBinding.tilCode.setError(null);
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(mBinding.etPass.getText()).toString())) {
            mBinding.tilPass.setError("Required filed");
            return false;
        } else {
            mBinding.tilPass.setError(null);
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(mBinding.etConfirmPass.getText()).toString())) {
            mBinding.tilConfirmPass.setError("Required filed");
            return false;
        } else {
            mBinding.tilConfirmPass.setError(null);
        }

        if (mBinding.etPass.getText().length() < 8) {
            mBinding.tilPass.setError("Password must greater than or equal to 8 character");
            return false;
        }

        if (!mBinding.etPass.getText().toString().equals(mBinding.etConfirmPass.getText().toString())) {
            mBinding.tilConfirmPass.setError("Passwords must match");
            return false;
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                // NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgress() {
        progressDialog.show(ResetPasswordActivity.this);

    }

    @Override
    public void showSuccess(String msg) {

        Intent i;
        i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
        progressDialog.dismiss();
    }

    @Override
    public void showError(String errMsg) {

        progressDialog.dismiss();
        Common_Utils.showToast(getApplicationContext(), errMsg);
    }
}