package com.example.nathanshumm.decided.viewmodel.login;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    //private LoginResultCallbacks loginResultCallbacks;

    public LoginViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new LoginViewModel();
    }
}
