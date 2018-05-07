package com.example.nathanshumm.decided.Interface;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;

public interface FragmentHelper {

    public FragmentManager getFragmentManager(Context context);

    public void openFragment(Context context, int frameId, Fragment fragment);

    public void addFragment(Context context, int frameId, Fragment fragment);
}
