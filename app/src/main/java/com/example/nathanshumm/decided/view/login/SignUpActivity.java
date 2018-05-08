package com.example.nathanshumm.decided.view.login;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nathanshumm.decided.Interface.AuthResultCallbacks;
import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.databinding.ActivitySignUpBinding;
import com.example.nathanshumm.decided.viewmodel.signup.SignUpViewModel;
import com.example.nathanshumm.decided.viewmodel.signup.SignUpViewModelFactory;

public class SignUpActivity extends AppCompatActivity implements AuthResultCallbacks {

    private Toolbar toolbar;
    private Window window;
    private EditText password;
    private EditText passwordVerify;
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpBinding activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        activitySignUpBinding.setViewModel(ViewModelProviders.of(this,
                new SignUpViewModelFactory(this)).get(SignUpViewModel.class));
        setToolbar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    public void validate() {
        Log.e("Flag", "onSignUp()");
        progressDialog = ProgressDialog.show(SignUpActivity.this, "Please wait...", "Processing...", true);
    }

    @Override
    public void onSuccess(String message) {
        progressDialog.dismiss();
        Toast.makeText(SignUpActivity.this , message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(SignUpActivity.this , message, Toast.LENGTH_SHORT).show();
        password = (EditText)findViewById(R.id.et_signup_password);
        passwordVerify = (EditText)findViewById(R.id.et_signup_password_verify);
        password.setText("");
        passwordVerify.setText("");
    }
}
