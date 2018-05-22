package com.example.nathanshumm.decided.viewmodel.main;

import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.example.nathanshumm.decided.view.favorites.FavoritesFragment;
import com.example.nathanshumm.decided.view.filter.FilterFragment;
import com.example.nathanshumm.decided.view.home.HomeFragment;
import com.example.nathanshumm.decided.view.main.MainActivity;

public class MainViewModel extends ViewModel {

    private FavoritesFragment favoritesFragment;
    private FilterFragment filterFragment;
    private HomeFragment homeFragment;

    public MainViewModel() {
        this.favoritesFragment = new FavoritesFragment();
        this.filterFragment = new FilterFragment();
        this.homeFragment = new HomeFragment();
    }

    public FavoritesFragment getFavoritesFragment() {
        return favoritesFragment;
    }

    public FilterFragment getFilterFragment() {
        return filterFragment;
    }

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

}
