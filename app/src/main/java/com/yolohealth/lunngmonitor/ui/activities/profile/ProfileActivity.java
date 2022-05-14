package com.yolohealth.lunngmonitor.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.yolohealth.lunngmonitor.R;
import com.yolohealth.lunngmonitor.databinding.ActivityProfileBinding;
import com.yolohealth.lunngmonitor.ui.activities.dashboard.MainActivity;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityProfileBinding.inflate(getLayoutInflater());

        String name = getIntent().getStringExtra("name");
        String number = getIntent().getStringExtra("number");
        String sex = getIntent().getStringExtra("sex");
        String age = getIntent().getStringExtra("age");
        String state = getIntent().getStringExtra("state");
        String district = getIntent().getStringExtra("district");
        String barcode = getIntent().getStringExtra("barcode");
        String type = getIntent().getStringExtra("type");
        String testDate = getIntent().getStringExtra("testDate");
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

        mBinding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                //i = new Intent(getApplicationContext(), ScanDeviceActivity.class);
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.profileToolbar);
        actionBar.setElevation(0);
        setContentView(mBinding.getRoot());
    }
}