package com.example.nathanshumm.decided.viewmodel.signup;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nathanshumm.decided.Interface.SignUpResultCallbacks;
import com.example.nathanshumm.decided.view.login.LaunchActivity;
import com.example.nathanshumm.decided.view.login.LoginActivity;
import com.example.nathanshumm.decided.view.login.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpViewModel extends ViewModel {

    public String email;
    public String password, passwordVerify;

    private SignUpResultCallbacks signUpResultCallbacks;

    // Database Reference
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public SignUpViewModel(SignUpResultCallbacks signUpResultCallbacks) {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
        this.signUpResultCallbacks = signUpResultCallbacks;
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
                email = editable.toString();
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
                password = editable.toString();
            }
        };
    }

    public TextWatcher getPasswordVerifyTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                passwordVerify = editable.toString();
            }
        };
    }

    public void onRegisterClicked(final View view){
        final Intent launcherIntent = new Intent(view.getContext(), LaunchActivity.class);

        // Sign Up Error Handling
        if (TextUtils.isEmpty(email)){
            signUpResultCallbacks.onError("Email field is empty! Please enter a valid email.");
        } else if(TextUtils.isEmpty(password)){
            signUpResultCallbacks.onError("Password field is empty!");
        }else if(!password.equals(passwordVerify)) {
            signUpResultCallbacks.onError("Passwords do not match! Please retype password");
            passwordVerify = "";
        }else{
            // Firebase Authentication
            signUpResultCallbacks.onSignUp();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                signUpResultCallbacks.onSuccess("Registration Successful");
                                writeNewUser();

                                view.getContext().startActivity(launcherIntent);
                            } else {
                                Log.e("Registration_Err", task.getException().getMessage());
                                signUpResultCallbacks.onError(task.getException().getMessage());
                            }
                        }
                    });

        }
    }

    // write new user to the database
    public void writeNewUser(){
        String userId;
        String email;
        userId = firebaseUser.getUid();
        email = firebaseUser.getEmail();
        Log.e("email err: ", email);
        databaseReference.child("Users").child(userId).child("Email").setValue(email);
    }

}
