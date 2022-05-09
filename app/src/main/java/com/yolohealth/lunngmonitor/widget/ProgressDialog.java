package com.yolohealth.lunngmonitor.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.yolohealth.lunngmonitor.R;


public class ProgressDialog {
    private Dialog dialog;
    private static final ProgressDialog ourInstance = new ProgressDialog();

    public static ProgressDialog getInstance() {
        return ourInstance;
    }

    private ProgressDialog() {
    }

    public void show(Context context) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_progress_dialog);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
