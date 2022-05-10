package com.yolohealth.lunngmonitor.ui.activities.dashboard;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.telit.terminalio.TIOConnection;
import com.telit.terminalio.TIOConnectionCallback;
import com.telit.terminalio.TIOManager;
import com.telit.terminalio.TIOPeripheral;
import com.yolohealth.lunngmonitor.LungMonitorApp;
import com.yolohealth.lunngmonitor.R;
import com.yolohealth.lunngmonitor.databinding.ActivityMainBinding;
import com.yolohealth.lunngmonitor.ui.activities.BaseActivity;
import com.yolohealth.lunngmonitor.ui.activities.token.TokenActivity;

import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends BaseActivity implements TIOConnectionCallback {
    ActivityMainBinding mBinding;

    TextInputEditText fefValue, pefValue, fev1Value, fev6Value, commentValue;
    TextInputLayout fefError, pefError, fev1error, fev6Error, commentError;

    private TIOPeripheral mPeripheral;
    private TIOConnection mConnection;
    private AlertDialog mConnectingDialog;
    private String mErrorMessage;
    private String mText = "";
    private static final int MAX_RECEIVED_TEXT_LENGTH = 512;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        showInstruction();
        connectPeripheral();

        mBinding.btnManual.setOnClickListener(view -> showBottomSheet());

        mBinding.btnStart.setOnClickListener(view -> startTest());

        mBinding.btnConnect.setOnClickListener(view -> connectDevice());

        mBinding.btnCancel.setOnClickListener(view -> {
            showInstruction();

            mBinding.layoutMeasuringHeight.setVisibility(View.INVISIBLE);
            mBinding.layoutMeasuringHeight.setVisibility(View.INVISIBLE);
        });

        mBinding.btnSubmit.setOnClickListener(view -> submitTest());


        mBinding.btnRetry.setOnClickListener(view -> {

            showInstruction();
            mBinding.layoutReadingHeight.setVisibility(View.INVISIBLE);
        });

        setContentView(mBinding.getRoot());

    }


    private void connectPeripheral() {
        // extract peripheral id (address) from intent
        Intent intent = getIntent();
        String address = intent.getStringExtra(LungMonitorApp.PERIPHERAL_ID_NAME);
        System.out.println("address---" + address);

        // retrieve peripheral instance from TIOManager
        mPeripheral = TIOManager.getInstance().findPeripheral(address);

        if (mPeripheral != null) {
            mConnection = mPeripheral.getConnection();

            // register callback
            try {

                // display peripheral properties
                //  mMainTitleView.setText(mPeripheral.getName() + "  " + mPeripheral.getAddress());

              /*  if (mPeripheral.getAdvertisement() == null) {
                    mSubTitleView.setText("");
                } else {
                    mSubTitleView.setText(mPeripheral.getAdvertisement().toString());
                }*/
            } catch (Exception ex) {
                Log.e(TAG, "! Connect to peripheral failed, " + ex.toString());
            }
        }
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
        }, 2000);
    }

    void connectDevice() {
        Log.d(TAG, "onConnectButtonPressed");

        try {
            mConnection = mPeripheral.connect(this);
            showConnectionMessage();
        } catch (Exception ex) {

        }
    }

    void startTest() {
        mBinding.layoutBtnHeight.setVisibility(View.INVISIBLE);
        mBinding.layoutInstruction.setVisibility(View.INVISIBLE);
        mBinding.layoutMeasuringHeight.setVisibility(View.VISIBLE);
        mBinding.layoutMeasuringHeight.setVisibility(View.VISIBLE);

        // blink animation

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200); //You can manage the blinking time with this parameter
        anim.setStartOffset(50);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        mBinding.measuring.startAnimation(anim);
    }


    void showConnectionMessage() {
        final AlertDialog.Builder dialog;
        if (mConnectingDialog == null) {
            // Create dialog
            dialog = new AlertDialog.Builder(this)
                    .setMessage("Connecting Device....")
                    .setNegativeButton(android.R.string.cancel, (dialog1, which) -> {
                        if (mConnection != null) {
                            try {
                                mConnection.cancel();
                                mConnection = null;
                            } catch (Exception ex) {
                                Log.e(TAG, ex.toString());
                            }
                        }
                    });
            mConnectingDialog = dialog.create();

            // Disable click event outside a dialog
            Window window = mConnectingDialog.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        // Show dialog
        mConnectingDialog.show();
    }


    void showErrorAlert(String message) {

        new AlertDialog.Builder(this)
                .setTitle(Html.fromHtml("<font color='#FF7F27'>Error</font>"))
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                })
                .show();
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

    @Override
    public void onConnected(TIOConnection tioConnection) {

        Log.d(TAG, "onConnected");

        if ((mConnectingDialog != null) && mConnectingDialog.isShowing()) {
            mConnectingDialog.dismiss();
        }


        mBinding.btnStart.setVisibility(View.VISIBLE);
        mBinding.btnConnect.setVisibility(View.GONE);

        if (!mPeripheral.shallBeSaved()) {
            // save if connected for the first time
            mPeripheral.setShallBeSaved(true);
            TIOManager.getInstance().savePeripherals();

        }

    }

    @Override
    public void onConnectFailed(TIOConnection tioConnection, String errorMessage) {

        Log.d(TAG, "onConnectFailed " + errorMessage);

        if ((mConnectingDialog != null) && mConnectingDialog.isShowing()) {
            mConnectingDialog.dismiss();
        }
        ;

        mBinding.btnStart.setVisibility(View.GONE);
        mBinding.btnConnect.setVisibility(View.VISIBLE);

        mErrorMessage = errorMessage;
        Runnable runnable = () -> {
            //updateUIState();
            if (mErrorMessage.length() > 0) {
                showErrorAlert("Failed to connect with error message: " + mErrorMessage);
            }
        };
        runOnUiThread(runnable);

    }

    @Override
    public void onDisconnected(TIOConnection tioConnection, String errorMessage) {

        Log.d(TAG, "onDisconnected" + errorMessage);

        if ((mConnectingDialog != null) && mConnectingDialog.isShowing()) {
            mConnectingDialog.dismiss();
        }
        ;

        mBinding.btnStart.setVisibility(View.GONE);
        mBinding.btnConnect.setVisibility(View.VISIBLE);

        // stopRssiListener();
        mErrorMessage = errorMessage;
        Runnable runnable = () -> {
            //updateUIState();
            if (mErrorMessage.length() > 0) {
                showErrorAlert("Disconnected with error message: " + mErrorMessage);
            }
        };
        runOnUiThread(runnable);

    }

    @Override
    public void onDataReceived(TIOConnection tioConnection, byte[] data) {

        Log.d(TAG, "onDataReceived len " + data.length);

        try {
            mText += new String(data);

        } catch (Exception ex) {
        }

        Runnable runnable = () -> {
            //  mBinding.tvReading.append(mText);
            mBinding.tvReading.setText(mText);
            System.out.println("data------>" + mText);
            System.out.print("sandeep*****" + Arrays.toString(data));

            System.out.println("value****" + mText);
            mText = "";

            // limit view's text length to MAX_RECEIVED_TEXT_LENGTH
            if (mBinding.tvReading.length() > MainActivity.MAX_RECEIVED_TEXT_LENGTH + 3) {
                String text = mBinding.tvReading.getText().toString();
                text = "..." + text.substring(text.length() - (MainActivity.MAX_RECEIVED_TEXT_LENGTH + 3));
                mBinding.tvReading.setText(text);
            }
        };

        // view and hide case

        mBinding.layoutBtnHeight.setVisibility(View.INVISIBLE);
        mBinding.layoutInstruction.setVisibility(View.INVISIBLE);
        mBinding.layoutMeasuringHeight.setVisibility(View.VISIBLE);
        mBinding.layoutMeasuringHeight.setVisibility(View.INVISIBLE);
        mBinding.layoutReadingHeight.setVisibility(View.VISIBLE);

        runOnUiThread(runnable);

    }

    @Override
    public void onDataTransmitted(TIOConnection tioConnection, int i, int i1) {


    }

    @Override
    public void onReadRemoteRssi(TIOConnection tioConnection, int i, int i1) {

    }

    @Override
    public void onLocalUARTMtuSizeUpdated(TIOConnection tioConnection, int i) {

    }

    @Override
    public void onRemoteUARTMtuSizeUpdated(TIOConnection tioConnection, int i) {

    }
}