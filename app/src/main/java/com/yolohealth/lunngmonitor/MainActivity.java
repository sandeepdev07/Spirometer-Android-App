package com.yolohealth.lunngmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.yolohealth.lunngmonitor.databinding.ActivityMainBinding;
import com.yolohealth.lunngmonitor.ui.activities.BaseActivity;
import com.yolohealth.lunngmonitor.ui.activities.token.TokenActivity;
import com.yolohealth.lunngmonitor.utils.PermissionDialogView;

import java.util.Objects;

import permission.auron.com.permissionhelper.PermissionResult;
import permission.auron.com.permissionhelper.utils.PermissionUtils;

public class MainActivity extends BaseActivity {
    ActivityMainBinding mBinding;

    TextInputEditText fefValue, pefValue, fev1Value, fev6Value, commentValue;
    TextInputLayout fefError, pefError, fev1error, fev6Error, commentError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        //askLocationPermission();

        showInstruction();

        mBinding.btnManual.setOnClickListener(view -> showBottomSheet());


        mBinding.btnStart.setOnClickListener(view -> startTest());

        mBinding.btnSubmit.setOnClickListener(view -> submitTest());


        mBinding.btnRetry.setOnClickListener(view -> {
            showInstruction();
            mBinding.layoutReadingHeight.setVisibility(View.INVISIBLE);
        });

     /*   mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInstruction();
                mBinding.layoutMeasuringHeight.setVisibility(View.INVISIBLE);
            }
        });*/

        setContentView(mBinding.getRoot());

    }


    void askLocationPermission() {

        askCompactPermissions(new String[]{
                PermissionUtils.Manifest_ACCESS_COARSE_LOCATION,
                PermissionUtils.Manifest_ACCESS_FINE_LOCATION,


        }, new PermissionResult() {

            @Override
            public void permissionGranted() {

            }

            @Override
            public void permissionDenied() {
                PermissionDialogView.gotoSettingsDialog(MainActivity.this, PermissionDialogView.LOCATION_PERMISSION);
            }

            @Override
            public void permissionForeverDenied() {
                PermissionDialogView.gotoSettingsDialog(MainActivity.this, PermissionDialogView.LOCATION_PERMISSION);
            }
        });
    }

    void submitTest() {
        Intent i;
        i = new Intent(getApplicationContext(), TokenActivity.class);
        startActivity(i);
        finish();
    }

    void showInstruction() {
        mBinding.layoutSetupSpiro.setVisibility(View.VISIBLE);

        mBinding.layoutSetupSpiro.postDelayed(() -> {
            mBinding.layoutSetupSpiro.setVisibility(View.INVISIBLE);
            mBinding.layoutBtnHeight.setVisibility(View.VISIBLE);
            mBinding.layoutInstruction.setVisibility(View.VISIBLE);
        }, 3000);

    }

    void startTest() {
        mBinding.layoutBtnHeight.setVisibility(View.INVISIBLE);
        mBinding.layoutInstruction.setVisibility(View.INVISIBLE);
        mBinding.layoutMeasuringHeight.setVisibility(View.VISIBLE);

        mBinding.layoutMeasuringHeight.
                postDelayed(() -> {

                    mBinding.layoutMeasuringHeight.setVisibility(View.INVISIBLE);
                    mBinding.layoutReadingHeight.setVisibility(View.VISIBLE);

                }, 3000);
    }


    void showBottomSheet() {

        View view = getLayoutInflater().inflate(R.layout.manual_entry_bottom_sheet_dialog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        ImageView cancel = dialog.findViewById(R.id.btn_cancel);
        Button submit = dialog.findViewById(R.id.btn_proceed);

        fefValue = dialog.findViewById(R.id.et_fef);
        fefError = dialog.findViewById(R.id.til_fef);

        pefValue = dialog.findViewById(R.id.et_pef);
        pefError = dialog.findViewById(R.id.til_pef);

        fev1Value = dialog.findViewById(R.id.et_fev1);
        fev1error = dialog.findViewById(R.id.til_fev1);

        fev6Value = dialog.findViewById(R.id.et_fev6);
        fev6Error = dialog.findViewById(R.id.til_fev6);

        commentValue = dialog.findViewById(R.id.et_comment);
        commentError = dialog.findViewById(R.id.til_comment);

        assert submit != null;
        submit.setOnClickListener(view12 -> {
            if (credentials()) {
                // dec api call
                Intent i;
                i = new Intent(getApplicationContext(), TokenActivity.class);
                startActivity(i);
                finish();
            }
        });

        assert cancel != null;
        cancel.setOnClickListener(view1 -> dialog.dismiss());

        dialog.show();

    }

    private boolean credentials() {

        if (TextUtils.isEmpty(Objects.requireNonNull(fefValue.getText()).toString())) {
            fefError.setError("Required filed");
            return false;
        } else {
            fefError.setError(null);
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(pefValue.getText()).toString())) {
            pefError.setError("Required filed");
            return false;
        } else {
            pefError.setError(null);
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(fev1Value.getText()).toString())) {
            fev1error.setError("Required filed");
            return false;
        } else {
            fev1error.setError(null);
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(fev6Value.getText()).toString())) {
            fev6Error.setError("Required filed");
            return false;
        } else {
            fev6Error.setError(null);
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(commentValue.getText()).toString())) {
            commentError.setError("Required filed");
            return false;
        } else {
            commentError.setError(null);
        }

        return true;
    }

}