package com.example.nathanshumm.decided.model.database;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.android.databinding.library.baseAdapters.BR;

public class User extends BaseObservable{

    @NonNull
    private String email,password;

    public User() {
    }

    public User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
        notifyPropertyChanged(com.example.nathanshumm.decided.BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public boolean isValidUser(){
        // Firebase AUTH
        return true;
    }
}
