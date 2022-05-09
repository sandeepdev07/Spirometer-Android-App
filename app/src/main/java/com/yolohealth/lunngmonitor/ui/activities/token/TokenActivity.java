package com.yolohealth.lunngmonitor.ui.activities.token;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yolohealth.lunngmonitor.MainActivity;
import com.yolohealth.lunngmonitor.R;
import com.yolohealth.lunngmonitor.databinding.ActivityLoginBinding;
import com.yolohealth.lunngmonitor.databinding.ActivityTokenBinding;
import com.yolohealth.lunngmonitor.ui.activities.profile.ProfileActivity;

import java.util.Objects;

public class TokenActivity extends AppCompatActivity {
    ActivityTokenBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTokenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i ;
                i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });



        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

    }
}