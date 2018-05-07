package com.example.nathanshumm.decided.viewmodel.launcher;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class LauncherViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;
    public LauncherViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new LauncherViewModel(context);
    }
}
