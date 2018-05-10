package com.example.nathanshumm.decided.viewmodel.launcher;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.view.login.LaunchActivity;
import com.example.nathanshumm.decided.view.login.LoginActivity;
import com.example.nathanshumm.decided.view.login.SignUpActivity;
import com.example.nathanshumm.decided.view.main.MainActivity;

public class LauncherViewModel extends ViewModel {

    private Context context;

    public LauncherViewModel(Context context) {
        this.context = context;
    }

    public void onLoginClicked(View view){
        Intent loginIntent = new Intent(view.getContext(), LoginActivity.class);
        view.getContext().startActivity(loginIntent);
    }

    public void onGuestLoginClicked(View view){
        Intent mainIntent = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(mainIntent);
    }

    public void onSignUpClicked(View view){
        Intent signUpIntent = new Intent(view.getContext(),SignUpActivity.class);
        view.getContext().startActivity(signUpIntent);
    }


}
