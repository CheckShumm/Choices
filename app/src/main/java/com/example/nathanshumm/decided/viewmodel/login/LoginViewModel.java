package com.example.nathanshumm.decided.viewmodel.login;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.nathanshumm.decided.Interface.AuthResultCallbacks;
import com.example.nathanshumm.decided.R;
import com.example.nathanshumm.decided.model.database.User;
import com.example.nathanshumm.decided.view.login.LaunchActivity;
import com.example.nathanshumm.decided.view.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginViewModel extends ViewModel{

    // Database Reference
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private User users;

    private AuthResultCallbacks authResultCallbacks;
    // add resultCallBack
    // initialize resultCallBack

    public LoginViewModel(AuthResultCallbacks authResultCallbacks) {
        this.authResultCallbacks = authResultCallbacks;
        this.users = new User();
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    public TextWatcher getEmailTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                users.setEmail(editable.toString());
            }
        };
    }

    public TextWatcher getPasswordTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                users.setPassword(editable.toString());
            }
        };
    }

    public void onLoginClicked(final View view) {
        final Intent mainIntent = new Intent(view.getContext(), MainActivity.class);

        // Sign Up Error Handling
        if (TextUtils.isEmpty(users.getEmail())){
            authResultCallbacks.onError("Email field is empty! Please enter a valid email.");
        } else if(TextUtils.isEmpty(users.getEmail())){
            authResultCallbacks.onError("Password field is empty!");
        }else{
            // Firebase Authentication
            authResultCallbacks.validate();
            firebaseAuth.signInWithEmailAndPassword(users.getEmail(), users.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                authResultCallbacks.onSuccess("Login Successful");

                                view.getContext().startActivity(mainIntent);
                            } else {
                                Log.e("Registration_Err", task.getException().getMessage());
                                authResultCallbacks.onError(task.getException().getMessage());
                            }
                        }
                    });

        }
    }


}
