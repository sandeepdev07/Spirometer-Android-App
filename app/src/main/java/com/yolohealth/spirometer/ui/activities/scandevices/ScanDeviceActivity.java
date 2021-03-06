package com.yolohealth.spirometer.ui.activities.scandevices;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.telit.terminalio.TIOManager;
import com.telit.terminalio.TIOManagerCallback;
import com.telit.terminalio.TIOPeripheral;
import com.yolohealth.spirometer.R;
import com.yolohealth.spirometer.databinding.ActivityScanDeviceBinding;
import com.yolohealth.spirometer.spirometer.STSwipeTapDetector;
import com.yolohealth.spirometer.ui.activities.BaseActivity;
import com.yolohealth.spirometer.ui.activities.dashboard.MainActivity;
import com.yolohealth.spirometer.utils.Common_Utils;
import com.yolohealth.spirometer.utils.Constants;
import com.yolohealth.spirometer.utils.PermissionDialogView;
import com.yolohealth.spirometer.utils.SharedPrefUtils;

import java.text.MessageFormat;
import java.util.Objects;

import permission.auron.com.permissionhelper.PermissionResult;
import permission.auron.com.permissionhelper.utils.PermissionUtils;
import pub.devrel.easypermissions.EasyPermissions;

public class ScanDeviceActivity extends BaseActivity implements TIOManagerCallback {

    private static final int ENABLE_BT_REQUEST_ID = 1;
    private static final int SCAN_INTERVAL = 8000;
    private static final String TAG = "TioSample";
    private static final int PERMITIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private static final int SERVICE_REQUEST_LOCATION = 2;


    ActivityScanDeviceBinding mBinding;
    private ArrayAdapter<TIOPeripheral> mPeripheralList;
    private final Handler mScanHandler = new Handler();
    private TIOManager mTio;

    // for android version above 10
    @SuppressLint("InlinedApi")
    public static final String[] BLUETOOTH_PERMISSIONS_S = {Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT};

    public boolean checkLocationService(final @NonNull Activity activity) {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            Intent enableLocation = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivityForResult(enableLocation, SERVICE_REQUEST_LOCATION);
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_scan_device);

        mBinding = ActivityScanDeviceBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!EasyPermissions.hasPermissions(this, BLUETOOTH_PERMISSIONS_S)) {
                EasyPermissions.requestPermissions(this, "Provide permission", 22, BLUETOOTH_PERMISSIONS_S);
            }
        }

        askLocationPermission();

        connectViews();

        mTio = TIOManager.getInstance();

        TIOManager.enableTrace(true);

        // displays a dialog requesting user permission to enable Bluetooth.
        if (!mTio.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivityForResult(enableBtIntent, ENABLE_BT_REQUEST_ID);
        }

        // initialize peripherals list view
        initializePeripheralsListView();

        updatePeripheralsListView();

        // initialize clearAllButton
        updateClearAllButton();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Scan Device");
        actionBar.setElevation(0);

    }


    private void askLocationPermission() {

        askCompactPermissions(new String[]{
                PermissionUtils.Manifest_ACCESS_COARSE_LOCATION,
                PermissionUtils.Manifest_ACCESS_FINE_LOCATION,


        }, new PermissionResult() {

            @Override
            public void permissionGranted() {

            }

            @Override
            public void permissionDenied() {
                PermissionDialogView.gotoSettingsDialog(ScanDeviceActivity.this, PermissionDialogView.LOCATION_PERMISSION);
            }

            @Override
            public void permissionForeverDenied() {
                PermissionDialogView.gotoSettingsDialog(ScanDeviceActivity.this, PermissionDialogView.LOCATION_PERMISSION);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.manager, menu);
        // We do not have any settings here
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult " + resultCode);

        switch (requestCode) {
            case ENABLE_BT_REQUEST_ID:
                if (resultCode == Activity.RESULT_CANCELED) {
                    mBinding.scanButton.setEnabled(false);
                    // mScanButton.setEnabled(false);
                    return;
                }
                break;

            case SERVICE_REQUEST_LOCATION:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "GPS enabled now on this device");
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMITIONS_REQUEST_ACCESS_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!checkLocationService(this)) {
                    Log.d(TAG, "GPS disabled on this device");
                } else {
                    this.startTimedScan();
                }
            }
        }
    }

    //******************************************************************************
    // UI event handlers
    //******************************************************************************

    public void onScanButtonPressed(View sender) {
        Log.d(TAG, "onScanButtonPressed");

        if (!Common_Utils.isBluetoothEnabled()) {
            Toast.makeText(getApplicationContext(), Constants.NO_BLUETOOTH_CONNECTION, Toast.LENGTH_LONG).show();
        } else {
            startTimedScan();
        }
    }

    public void onClearAllButtonPressed(View sender) {
        Log.d(TAG, "onClearAllButtonPressed");

        mTio.removeAllPeripherals();
        updatePeripheralsListView();
        updateClearAllButton();
    }

    private void onRemoveButtonPressed(TIOPeripheral peripheral) {
        Log.d(TAG, "onRemoveButtonPressed " + peripheral.toString());

        mTio.removePeripheral(peripheral);
        updatePeripheralsListView();
        updateClearAllButton();
    }

    private void onPeripheralCellPressed(TIOPeripheral peripheral) {
        Log.d(TAG, "onPeripheralCellPressed " + peripheral.toString());

        SharedPrefUtils.setDeviceAddress(getApplicationContext(), Constants.SPIROMETER, peripheral.getAddress());

        Intent intent = new Intent(ScanDeviceActivity.this, MainActivity.class);
        // intent.putExtra(LungMonitorApp.PERIPHERAL_ID_NAME, peripheral.getAddress());
        startActivity(intent);
        finish();
    }


    //******************************************************************************
    // TIOManagerCallback implementation
    //******************************************************************************

    @Override
    public void onPeripheralFound(TIOPeripheral peripheral) {
        Log.d(TAG, "onPeripheralDiscovered " + peripheral.toString());

        // overrule default behaviour: peripheral shall be saved only after having been connected
        peripheral.setShallBeSaved(false);
        mTio.savePeripherals();
        updatePeripheralsListView();
    }

    @Override
    public void onPeripheralUpdate(TIOPeripheral peripheral) {
        Log.d(TAG, "onPeripheralUpdate " + peripheral.toString());

        updatePeripheralsListView();
    }


    //******************************************************************************
    // Internal methods
    //******************************************************************************

    private void connectViews() {
        Log.d(TAG, "connectViews");


        mBinding.scanButton.setOnClickListener(ScanDeviceActivity.this::onScanButtonPressed);

    }

    private void initializePeripheralsListView() {

        // create data adapter for peripherals list view
        mPeripheralList = new ArrayAdapter<TIOPeripheral>(this, 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return createPeripheralCell(position);
            }

            @Override
            public int getCount() {
                return mTio.getPeripherals().length;
            }
        };

        mBinding.peripheralsListView.setAdapter(mPeripheralList);

    }

    private View createPeripheralCell(int position) {

        final TIOPeripheral peripheral = mTio.getPeripherals()[position];

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View peripheralCell = inflater.inflate(R.layout.peripheral_cell, mBinding.peripheralsListView, false);

        TextView mainTitle = (TextView) peripheralCell.findViewById(R.id.mainTitle);
        mainTitle.setText(MessageFormat.format("{0}  {1}", peripheral.getName(), peripheral.getAddress()));

        TextView subTitle = (TextView) peripheralCell.findViewById(R.id.subTitle);
        if (peripheral.getAdvertisement() != null) {
            subTitle.setText(MessageFormat.format("{0} RSSI {1}", peripheral.getAdvertisement().toString(), peripheral.getAdvertisement().getRssi()));
        } else {
            subTitle.setText("");
        }


        Button removeButton = (Button) peripheralCell.findViewById(R.id.removeButton);
        removeButton.setVisibility(View.INVISIBLE);
        removeButton.setOnClickListener(view -> onRemoveButtonPressed(peripheral));

        peripheralCell.setOnTouchListener(createPeripheralCellGestureDetector(peripheral, removeButton));

        return peripheralCell;
    }


    private void startTimedScan() {
        if (SDK_INT > LOLLIPOP) {
            // Check for permissions
            int persmissionCheck = ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);

            if (persmissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMITIONS_REQUEST_ACCESS_COARSE_LOCATION);
                return;
            }

            if (!checkLocationService(this)) {
                Log.d(TAG, "GPS disabled on this device");
                return;
            }
        }

        Log.d(TAG, "startTimedScan");


        mBinding.scanButton.setEnabled(false);
        mBinding.clearAllButton.setEnabled(false);
        mBinding.scanIndicator.setVisibility(View.VISIBLE);

        mScanHandler.postDelayed(() -> {

            try {
                mTio.stopScan();
            } catch (Exception ex) {

            }

            mBinding.scanIndicator.setVisibility(View.INVISIBLE);
            mBinding.scanButton.setEnabled(true);
            updateClearAllButton();
        }, ScanDeviceActivity.SCAN_INTERVAL);

        mTio.startScan(this);
    }

    private void updatePeripheralsListView() {
        // update adapter with currently known peripherals
        mPeripheralList.notifyDataSetChanged();
    }

    private void updateClearAllButton() {
        mBinding.clearAllButton.setEnabled(mTio.getPeripherals().length > 0);
        //.setEnabled(mTio.getPeripherals().length > 0);
    }


    private STSwipeTapDetector createPeripheralCellGestureDetector(final TIOPeripheral peripheral, final Button removeButton) {
        STSwipeTapDetector detector = new STSwipeTapDetector(this) {

            private boolean mRemoveMode;

            @Override
            public boolean onLeftSwipe() {
                if (this.mRemoveMode)
                    return true; // handled

                this.mRemoveMode = true;
                TranslateAnimation animation = new TranslateAnimation(removeButton.getWidth(), 0, 0, 0);
                animation.setDuration(200);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        removeButton.setVisibility(View.VISIBLE);
                        removeButton.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        removeButton.setEnabled(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                removeButton.startAnimation(animation);

                return true; // handled
            }

            @Override
            public boolean onRightSwipe() {
                if (!this.mRemoveMode)
                    return true; // handled

                TranslateAnimation animation = new TranslateAnimation(0, removeButton.getWidth(), 0, 0);
                animation.setDuration(200);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        removeButton.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        removeButton.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                removeButton.startAnimation(animation);
                this.mRemoveMode = false;

                return true;
            }

            @Override
            public boolean onTap() {
                if (this.mRemoveMode)
                    return true; // handled

                ScanDeviceActivity.this.onPeripheralCellPressed(peripheral);

                return true; // handled
            }
        };

        return detector;
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
}