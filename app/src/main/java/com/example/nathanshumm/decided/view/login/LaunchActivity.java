package com.example.nathanshumm.decided.view.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.Button;


import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.databinding.ActivityLaunchBinding;
import com.example.nathanshumm.decided.viewmodel.launcher.LauncherViewModelFactory;
import com.example.nathanshumm.decided.viewmodel.launcher.LauncherViewModel;

public class LaunchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LauncherViewModel launcherViewModel;
        ActivityLaunchBinding activityLaunchBinding = DataBindingUtil.setContentView(this, R.layout.activity_launch);
        activityLaunchBinding.setViewModel(ViewModelProviders.of(this,
                new LauncherViewModelFactory(this)).get(LauncherViewModel.class));
        window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlack));
    }


    public static Intent newIntent(Context context){
        return new Intent(context, LaunchActivity.class);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.launcherFrame, fragment);
        fragmentTransaction.commit();
    }

/*
    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if(count == 0){
            super.onBackPressed();
        }else{
            getFragmentManager().popBackStack();
        }
    }
    */
}
