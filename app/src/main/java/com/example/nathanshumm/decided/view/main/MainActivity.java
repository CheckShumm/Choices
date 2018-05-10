package com.example.nathanshumm.decided.view.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.databinding.ActivityLoginBinding;
import com.example.nathanshumm.decided.databinding.ActivityMainBinding;
import com.example.nathanshumm.decided.viewmodel.login.LoginViewModel;
import com.example.nathanshumm.decided.viewmodel.login.LoginViewModelFactory;
import com.example.nathanshumm.decided.viewmodel.main.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Window window;
    private BottomNavigationView bottomNavigationView;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainViewModel = new MainViewModel();
        setToolbar();
        setNavigationDrawer();
        navigationListener();
    }

    @Override
    protected void onResume() {
        setFragment(mainViewModel.getHomeFragment());
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
                        setFragment(mainViewModel.getFilterFragment());
                        return true;
                    case R.id.nav_favorites:
                        setFragment(mainViewModel.getFavoritesFragment());
                        return true;
                    case R.id.nav_home:
                        setFragment(mainViewModel.getHomeFragment());
                        return true;

                    default: return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.m_Frame, fragment);
        fragmentTransaction.commit();
    }

    private void setNavigationDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
}
