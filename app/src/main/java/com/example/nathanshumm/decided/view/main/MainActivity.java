package com.example.nathanshumm.decided.view.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.databinding.ActivityLoginBinding;
import com.example.nathanshumm.decided.databinding.ActivityMainBinding;
import com.example.nathanshumm.decided.model.api.Place;
import com.example.nathanshumm.decided.view.home.HomeFragment;
import com.example.nathanshumm.decided.viewmodel.login.LoginViewModel;
import com.example.nathanshumm.decided.viewmodel.login.LoginViewModelFactory;
import com.example.nathanshumm.decided.viewmodel.main.CustomViewPager;
import com.example.nathanshumm.decided.viewmodel.main.MainViewModel;
import com.example.nathanshumm.decided.viewmodel.main.ViewPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Window window;
    private BottomNavigationView bottomNavigationView;
    private MainViewModel mainViewModel;
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainViewModel = new MainViewModel();
        //mainViewModel.getHomeFragment().getResponse();
        viewPager = (CustomViewPager)findViewById(R.id.viewPager);
        setViewPager(viewPager);
        setToolbar();
        setNavigationDrawer();
        navigationListener();
    }

    @Override
    protected void onResume() {
        viewPager.setCurrentItem(0);
        super.onResume();
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Choices");

        window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    private void navigationListener(){
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_filter:
                        viewPager.setCurrentItem(0);
                        getSupportActionBar().setTitle("Filter");
                        return true;
                    case R.id.nav_favorites:
                        ArrayList<Place> favoritesList = mainViewModel.getHomeFragment().getFavoritesList();
                        mainViewModel.getFavoritesFragment().loadList(favoritesList);
                        viewPager.setCurrentItem(2);
                        getSupportActionBar().setTitle("Favorites");
                        return true;
                    case R.id.nav_home:
                        mainViewModel.getHomeFragment().getResponse();
                        if(!mainViewModel.getFilterFragment().getCurrentType().equals(
                                mainViewModel.getHomeFragment().getCurrentType()
                        )) {
                            Log.d("reload", "filter type 1: " + mainViewModel.getFilterFragment().getCurrentType()
                                    + "\n home type 1: " + mainViewModel.getHomeFragment().getCurrentType());
                            mainViewModel.getHomeFragment().reload();
                        }else{
                            Log.d("reload", "filter type 2: " + mainViewModel.getFilterFragment().getCurrentType()
                                    + "\n home type 2: " + mainViewModel.getHomeFragment().getCurrentType());
                        }
                        viewPager.setCurrentItem(1);
                        getSupportActionBar().setTitle("Choices");
                        return true;

                    default: return false;
                }
            }
        });
    }


    private void setNavigationDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mainViewModel.getFilterFragment());
        adapter.addFragment(mainViewModel.getHomeFragment());
        adapter.addFragment(mainViewModel.getFavoritesFragment());
        viewPager.setAdapter(adapter);

    }
}
