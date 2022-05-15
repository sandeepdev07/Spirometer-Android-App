package com.yolohealth.lunngmonitor.ui.activities.dashboard;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

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
import com.yolohealth.lunngmonitor.model.medicalservicesresponse.MedicalServicesResponse;
import com.yolohealth.lunngmonitor.model.medicalservicesresponse.Service;
import com.yolohealth.lunngmonitor.model.spirotestparams.SpiroTestParams;
import com.yolohealth.lunngmonitor.ui.activities.BaseActivity;
import com.yolohealth.lunngmonitor.ui.activities.login.LoginActivity;
import com.yolohealth.lunngmonitor.ui.activities.medicaltesttype.TestPresenter;
import com.yolohealth.lunngmonitor.ui.activities.medicaltesttype.TestPresenterImpl;
import com.yolohealth.lunngmonitor.ui.activities.medicaltesttype.TestView;
import com.yolohealth.lunngmonitor.ui.activities.scandevices.ScanDeviceActivity;
import com.yolohealth.lunngmonitor.ui.activities.spirotest.SpiroTestPresenter;
import com.yolohealth.lunngmonitor.ui.activities.spirotest.SpiroTestPresenterImpl;
import com.yolohealth.lunngmonitor.ui.activities.spirotest.SpiroTestView;
import com.yolohealth.lunngmonitor.ui.activities.token.TokenActivity;
import com.yolohealth.lunngmonitor.utils.Common_Utils;
import com.yolohealth.lunngmonitor.utils.Constants;
import com.yolohealth.lunngmonitor.utils.SharedPrefUtils;
import com.yolohealth.lunngmonitor.widget.AppConstant;
import com.yolohealth.lunngmonitor.widget.ProgressDialog;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

public class MainActivity extends BaseActivity implements TIOConnectionCallback, TestView, SpiroTestView {
    ActivityMainBinding mBinding;

    TextInputEditText fefValue, pefValue, fev1Value, fev6Value, commentValue;
    TextInputLayout fefError, pefError, fev1error, fev6Error, commentError;

    private TIOPeripheral mPeripheral;
    private TIOConnection mConnection;
    private AlertDialog mConnectingDialog;
    private String mErrorMessage;
    private String mText = "";
    private static final int MAX_RECEIVED_TEXT_LENGTH = 512;
    public static String checkStr = "";
    public static String medicalTypeId;
    public static String kioskId;
    public static String userId;

    private TestPresenter testPresenter;
    private SpiroTestPresenter spiroTestPresenter;
    SpiroTestParams spiroTestParams;
    ProgressDialog progressDialog;
    MedicalServicesResponse medicalServicesResponse;
    BottomSheetDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        showInstruction();
        connectPeripheral();

        progressDialog = ProgressDialog.getInstance();

        mBinding.btnManual.setOnClickListener(view -> showBottomSheet());

        mBinding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SharedPrefUtils.getDeviceAddress(getApplicationContext(), Constants.SPIROMETER) != null
                        && SharedPrefUtils.getDeviceAddress(getApplicationContext(), Constants.SPIROMETER).equals("")) {
                    openSetKioskDialog(false);
                }

                if (!Common_Utils.isBluetoothEnabled()) {
                    Toast.makeText(getApplicationContext(), Constants.NO_BLUETOOTH_CONNECTION, Toast.LENGTH_LONG).show();
                } else {
                    connectDevice();
                }

            }
        });


        mBinding.btnCancel.setOnClickListener(view -> {

            //to disconnect device----
            stopRssiListener();

            try {
                mConnection.disconnect();
            } catch (Exception ex) {

            }

            //--------------------------------

            showInstruction();

            mBinding.layoutMeasuringHeight.setVisibility(View.INVISIBLE);
            mBinding.layoutMeasuringHeight.setVisibility(View.INVISIBLE);
        });


        mBinding.btnSubmit.setOnClickListener(
                view -> submitTest()
        );


        mBinding.btnRetry.setOnClickListener(view -> {

            // to disconnect device-----

            stopRssiListener();

            try {
                mConnection.disconnect();
            } catch (Exception ex) {

            }

            // --------------------------------

            // updateUIState();

            showInstruction();
            mBinding.layoutReadingHeight.setVisibility(View.INVISIBLE);
            checkStr = "";
        });


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Dashboard");
        actionBar.setElevation(0);

         kioskId = String.valueOf(SharedPrefUtils.getKioskId(getApplicationContext()));
         userId = SharedPrefUtils.getProfileId(getApplicationContext());
         //userId = String.valueOf(SharedPrefUtils.getUserId(getApplicationContext()));
        System.out.println("kioskId---" + kioskId);
        System.out.println("userId---" + userId);

        testPresenter = new TestPresenterImpl(this);
        spiroTestPresenter = new SpiroTestPresenterImpl(this);

        setContentView(mBinding.getRoot());

    }


    public void openSetKioskDialog(boolean setupHeight) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        String msg = "Please Scan the device to begin";
        String title = "Scan Spirometer Device";

        builder.setMessage(msg).setTitle(title)
                .setCancelable(false)
                .setPositiveButton("SCAN", (dialog, id) -> {
                    Intent i = new Intent(MainActivity.this, ScanDeviceActivity.class);
                    i.putExtra(AppConstant.FROM, AppConstant.NEW_USER);
                    startActivity(i);
                })
                .setNegativeButton("CANCEL", (dialog, id) -> {
                    Toast.makeText(MainActivity.this,
                            "Scan the device to start the test", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                });
        //Creating dialog box
        androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();
    }


    private void stopRssiListener() {
        if (mConnection != null) {

            Log.d(TAG, "stopRssiListener");

            try {
                mConnection.readRemoteRssi(0);
            } catch (Exception ex) {

            }

        }
    }

    private void connectPeripheral() {
        // extract peripheral id (address) from intent

        String spiroMacAddress = SharedPrefUtils.getDeviceAddress(getApplicationContext(), Constants.SPIROMETER);
        System.out.println("address---->" + spiroMacAddress);

        // coming from scan device activity
     /*   Intent intent = getIntent();
        String address = intent.getStringExtra(LungMonitorApp.PERIPHERAL_ID_NAME);
        System.out.println("address---" + address);*/

        // retrieve peripheral instance from TIOManager
        mPeripheral = TIOManager.getInstance().findPeripheral(spiroMacAddress);

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

        // to disconnect device----
        stopRssiListener();

        try {
            mConnection.disconnect();
        } catch (Exception ex) {

        }

        //-----------------------

        Intent i;
        i = new Intent(getApplicationContext(), TokenActivity.class);
        startActivity(i);
        finish();
        checkStr = "";
    }

    void showInstruction() {
        mBinding.layoutSetupSpiro.setVisibility(View.VISIBLE);

        mBinding.layoutSetupSpiro.postDelayed(() -> {
            mBinding.layoutSetupSpiro.setVisibility(View.INVISIBLE);
            mBinding.layoutBtnHeight.setVisibility(View.VISIBLE);
            mBinding.layoutInstruction.setVisibility(View.VISIBLE);
        }, 1000);
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
        checkStr = "";
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
                    .setMessage("Connecting to Vitalograph lung monitor...")
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
        dialog = new BottomSheetDialog(MainActivity.this);

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

                //after validation success go to api
                medicalServicesResponse = new MedicalServicesResponse();
                testPresenter.test(medicalServicesResponse);
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

        startTest(); // show ui when device is connected


        mBinding.btnStart.setVisibility(View.VISIBLE);
        //mBinding.btnConnect.setVisibility(View.GONE);

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

        if (checkStr.length() == 0) {

            Log.d(TAG, "onDataReceived len " + data.length);

            String str = new String(data, StandardCharsets.ISO_8859_1);
            System.out.println("devtest---->" + str);

            float fev1 = Float.parseFloat(str.substring(0, 3)) / 100;
            float fev6 = Float.parseFloat(str.substring(3, 6)) / 100;
            float ratio = Float.parseFloat(str.substring(6, 9)) / 100;
            float fef = Float.parseFloat(str.substring(9, 12)) / 100;

            System.out.println("FEV1------> " + fev1);
            System.out.println("FEV6------> " + fev6);
            System.out.println("FEV1/FEV6------> " + ratio);
            System.out.println("FEF------> " + fef);

            mBinding.tvFev1.setText(MessageFormat.format("FEV1 : {0}", (double) fev1));
            mBinding.tvFev6.setText(MessageFormat.format("FEV6 : {0}", (double) fev6));
            mBinding.tvRatio.setText(MessageFormat.format("FEV1/FEV6 : {0}", (double) ratio));
            mBinding.tvFef.setText(MessageFormat.format("FEF : {0}", (double) fef));

            try {
                mText += new String(data);

            } catch (Exception ex) {
            }

            Runnable runnable = () -> {
                //  mBinding.tvReading.append(mText);
                mBinding.tvReading.setText(mText);

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

        checkStr = new String(data, StandardCharsets.ISO_8859_1);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        menu.findItem(R.id.deviceSetting).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent i;
                i = new Intent(getApplicationContext(), ScanDeviceActivity.class);
                startActivity(i);
                return false;
            }
        });

        menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                logoutAlert(MainActivity.this);
                return false;
            }
        });
        return true;
    }

    private void logoutAlert(MainActivity mainActivity) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mainActivity);
        builder.setTitle(R.string.logout);
        builder.setMessage(R.string.sureLogout);
        builder.setPositiveButton(R.string.log, (dialog, which) -> {
            // clear SharedPref
            SharedPrefUtils.setLoggedIn(LungMonitorApp.getAppContext(), false);
            SharedPrefUtils.setToken(LungMonitorApp.getAppContext(), null);
            SharedPrefUtils.setKioksId(LungMonitorApp.getAppContext(), -1);
            SharedPrefUtils.setProfileId(LungMonitorApp.getAppContext(),null);
          //  SharedPrefUtils.setUserId(LungMonitorApp.getAppContext(), -1);


            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(MainActivity.this, "Logout Successfully",
                    Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();

        }).setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    @Override
    public void showProgress() {

        progressDialog.show(MainActivity.this);
    }

    @Override
    public void showSuccess(String msg) {

        Intent i;
        i = new Intent(getApplicationContext(), TokenActivity.class);
        startActivity(i);
        finish();

        dialog.dismiss();
        progressDialog.dismiss();

    }

    @Override
    public void showSuccess(MedicalServicesResponse medicalServicesResponse, String msg) {


        List<Service> services = medicalServicesResponse.getData().getServices();
        for (int i = 0; i < services.size(); i++) {

            String devCode = services.get(i).getDevCode();

            if (devCode.equals("spirometry")) {
                medicalTypeId = services.get(i).getId().toString();
                // spiroTest
                spiroTestParams();
                System.out.println("id---" + medicalTypeId);
            }
        }
    }

    private void spiroTestParams() {
        System.out.println("datasandeep======");

        spiroTestParams = new SpiroTestParams();
        spiroTestParams.setSpiro_fef(Objects.requireNonNull(fefValue.getText()).toString());
        spiroTestParams.setSpiro_pef(Objects.requireNonNull(pefValue.getText()).toString());
        spiroTestParams.setSpiro_fev1(Objects.requireNonNull(fev1Value.getText()).toString());
        spiroTestParams.setSpiro_fev6(Objects.requireNonNull(fev6Value.getText()).toString());
        spiroTestParams.setComment(Objects.requireNonNull(commentValue.getText()).toString());
        spiroTestParams.setUserid(userId);
        spiroTestParams.setKioskid(kioskId);
        spiroTestParams.setMedicalservicepk(medicalTypeId);

        spiroTestPresenter.spirometer(spiroTestParams);


    }

    @Override
    public void showError(String errMsg) {

        progressDialog.dismiss();
        Common_Utils.showToast(getApplicationContext(), errMsg);
    }

}
