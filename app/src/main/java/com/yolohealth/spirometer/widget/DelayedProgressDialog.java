package com.yolohealth.spirometer.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yolohealth.spirometer.R;

import java.util.Objects;

public class DelayedProgressDialog extends DialogFragment {

    private static final int DELAY_MILLISECOND = 0;
    private static final int MINIMUM_SHOW_DURATION_MILLISECOND = 0;
    private static final int PROGRESS_CONTENT_SIZE_DP = 80;

    private ProgressBar mProgressBar;
    private boolean startedShowing;
    private long mStartMillisecond;
    private long mStopMillisecond;

    private FragmentManager fragmentManager;
    private String tag;
    private Handler showHandler;
    Activity mActivity;

    // default constructor. Needed so rotation doesn't crash
    public DelayedProgressDialog(Activity context) {
        super();
        mActivity = context;
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_progress, null));
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
//        mProgressBar = (GifView) Objects.requireNonNull(getDialog()).findViewById(R.id.progress);
//        mProgressBar.setImageResource(R.drawable.loader);
        mProgressBar = Objects.requireNonNull(getDialog()).findViewById(R.id.progress);


        if (getDialog().getWindow() != null) {
            int px = (int) (PROGRESS_CONTENT_SIZE_DP * getResources().getDisplayMetrics().density);
            getDialog().getWindow().setLayout(px, px);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void show(FragmentManager fm, String tag) {
        if (isAdded())
            return;

        this.fragmentManager = fm;
        this.tag = tag;
        mStartMillisecond = System.currentTimeMillis();
        startedShowing = false;
        mStopMillisecond = Long.MAX_VALUE;

        showHandler = new Handler();
        showHandler.postDelayed(() -> {
            // only show if not already cancelled
            if (mStopMillisecond > System.currentTimeMillis())
                showDialogAfterDelay();
        }, DELAY_MILLISECOND);
    }

    private void showDialogAfterDelay() {
        startedShowing = true;

        DialogFragment dialogFragment = (DialogFragment) fragmentManager.findFragmentByTag(tag);
        if (dialogFragment != null) {
            fragmentManager.beginTransaction().show(dialogFragment).commitAllowingStateLoss();
        } else {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        }
    }

    public void cancel() {
        if (showHandler == null)
            return;

        mStopMillisecond = System.currentTimeMillis();
        showHandler.removeCallbacksAndMessages(null);

        if (startedShowing) {
            if (mProgressBar != null) {
                cancelWhenShowing();
            } else {
                cancelWhenNotShowing();
            }
        } else
            dismiss();
    }

    private void cancelWhenShowing() {
        if (mStopMillisecond < mStartMillisecond + DELAY_MILLISECOND + MINIMUM_SHOW_DURATION_MILLISECOND) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, MINIMUM_SHOW_DURATION_MILLISECOND);
        } else {
            dismiss();
        }
    }

    private void cancelWhenNotShowing() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, DELAY_MILLISECOND);
    }

    @Override
    public void dismiss() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(this);
        ft.commitAllowingStateLoss();
    }
}
