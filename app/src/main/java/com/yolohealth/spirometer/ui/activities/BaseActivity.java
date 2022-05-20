package com.yolohealth.spirometer.ui.activities;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yolohealth.spirometer.R;

import java.util.ArrayList;
import java.util.List;

import permission.auron.com.permissionhelper.ActivityManagePermission;

public abstract class BaseActivity extends ActivityManagePermission {
    public List<Integer> getVisibleView() {
        List<Integer> skipIds = new ArrayList<>();
        skipIds.add(R.id.toolbar);

        return skipIds;
    }

   /* public void showProgress(AppCompatActivity activity) {
        ProgressDialogManager.Companion.getInstance().show(activity);
    }

    public void hideProgress() {
        ProgressDialogManager.Companion.getInstance().hide();
    }*/


    protected void showKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getCurrentFocus() == null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                View view = getCurrentFocus();
                inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
