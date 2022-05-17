package com.yolohealth.lunngmonitor.ui.activities.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.yolohealth.lunngmonitor.databinding.ActivitySplashBinding;
import com.yolohealth.lunngmonitor.ui.activities.dashboard.MainActivity;
import com.yolohealth.lunngmonitor.ui.activities.login.LoginActivity;
import com.yolohealth.lunngmonitor.ui.activities.token.TokenActivity;
import com.yolohealth.lunngmonitor.utils.SharedPrefUtils;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding mBinding;
    private static final int Splash_timeout = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }, Splash_timeout);
    }

    private void init() {
        splashViewFinish();
    }


    public void splashViewFinish() {

        Intent intent;

        if (!SharedPrefUtils.isLoggedIn(this)) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        } else if (SharedPrefUtils.getToken(this) != null && !SharedPrefUtils.getToken(this).equalsIgnoreCase("")) {
            startApp();
        }

        finish();

    }

    private void startApp() {
        Intent intent = new Intent(this, TokenActivity.class);
        startActivity(intent);
    }
}