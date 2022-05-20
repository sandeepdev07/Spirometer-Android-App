package com.yolohealth.spirometer.ui.fragments;

import androidx.fragment.app.Fragment;

import com.yolohealth.spirometer.R;

import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment {
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
}

