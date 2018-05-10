package com.example.nathanshumm.decided.viewmodel.login;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.nathanshumm.decided.Interface.AuthResultCallbacks;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private AuthResultCallbacks authResultCallbacks;

    public LoginViewModelFactory(AuthResultCallbacks authResultCallbacks) {
        this.authResultCallbacks = authResultCallbacks;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new LoginViewModel(authResultCallbacks);
    }
}
