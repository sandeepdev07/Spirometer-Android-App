package com.yolohealth.spirometer.ui.fragments;

import android.content.Context;

import androidx.annotation.NonNull;

import com.yolohealth.spirometer.ui.activities.dashboard.MainActivity;

public abstract class BaseMainFragment extends BaseFragment {
    protected MainActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }
}
