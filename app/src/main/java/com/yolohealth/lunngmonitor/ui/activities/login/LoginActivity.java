package com.yolohealth.lunngmonitor.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.yolohealth.lunngmonitor.databinding.ActivityLoginBinding;
import com.yolohealth.lunngmonitor.ui.activities.token.TokenActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());

        mBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(getApplicationContext(), TokenActivity.class);
                startActivity(i);
                finish();
            }
        });


        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}