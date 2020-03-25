package com.android.thresturent.common.base;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by peter on 19/05/18.
 */

public abstract class BaseFragment extends Fragment {

    protected abstract void initializeViews(View v);

    protected abstract void setListeners();

    protected void replaceFragment(int containerId, Fragment fragment , String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}