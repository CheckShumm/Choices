package com.example.nathanshumm.decided.viewmodel.signup;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.nathanshumm.decided.Interface.SignUpResultCallbacks;


public class SignUpViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private SignUpResultCallbacks signUpResultCallbacks;

    public SignUpViewModelFactory(SignUpResultCallbacks signUpResultCallbacks) {
        this.signUpResultCallbacks = signUpResultCallbacks;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new SignUpViewModel(signUpResultCallbacks);
    }
}
