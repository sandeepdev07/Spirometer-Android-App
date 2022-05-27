package com.yolohealth.spirometer.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.yolohealth.spirometer.R;
import com.yolohealth.spirometer.databinding.ActivityProfileBinding;
import com.yolohealth.spirometer.ui.activities.dashboard.MainActivity;
import com.yolohealth.spirometer.widget.ProgressDialog;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding mBinding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProfileBinding.inflate(getLayoutInflater());

        progressDialog = ProgressDialog.getInstance();

        String name = getIntent().getStringExtra("name");
        String number = getIntent().getStringExtra("number");
        String sex = getIntent().getStringExtra("sex");
        String age = getIntent().getStringExtra("age");
        String state = getIntent().getStringExtra("state");
        String district = getIntent().getStringExtra("district");
        String barcode = getIntent().getStringExtra("barcode");
        String type = getIntent().getStringExtra("type");
        String testDate = getIntent().getStringExtra("testDate");
        String labourId = getIntent().getStringExtra("labourId");
        System.out.println("PatientName---->" + type);

        mBinding.etPatientName.setText(name);
        mBinding.etNumber.setText(number);
        mBinding.etGender.setText(sex);
        mBinding.etAge.setText(age);
        mBinding.etState.setText(state);
        mBinding.etDistrict.setText(district);
        mBinding.etBarcode.setText(barcode);
        mBinding.etType.setText(type);
        mBinding.etDate.setText(testDate);
        mBinding.etLabour.setText(labourId);

        mBinding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show(ProfileActivity.this);

                // add delay
                mBinding.btnProceed.postDelayed(() -> {
                    Intent i;
                    //i = new Intent(getApplicationContext(), ScanDeviceActivity.class);
                    i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                    progressDialog.dismiss();

                }, 1000);

            }
        });


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.profileToolbar);
        actionBar.setElevation(0);
        setContentView(mBinding.getRoot());
    }
}