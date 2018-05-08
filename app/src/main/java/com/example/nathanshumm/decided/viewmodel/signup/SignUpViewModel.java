package com.example.nathanshumm.decided.viewmodel.signup;

import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.databinding.library.baseAdapters.BR;
import com.example.nathanshumm.decided.Interface.AuthResultCallbacks;
import com.example.nathanshumm.decided.model.database.User;
import com.example.nathanshumm.decided.view.login.LaunchActivity;
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

    private User user;
    private AuthResultCallbacks authResultCallbacks;

    // Database Reference
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public SignUpViewModel(AuthResultCallbacks authResultCallbacks) {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        firebaseUser = firebaseAuth.getCurrentUser();

        this.user = new User();
        this.authResultCallbacks = authResultCallbacks;
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
            authResultCallbacks.onError("Email field is empty! Please enter a valid email.");
        } else if(TextUtils.isEmpty(password)){
            authResultCallbacks.onError("Password field is empty!");
        }else if(!password.equals(passwordVerify)) {
            authResultCallbacks.onError("Passwords do not match! Please retype password");

        }else{
            // Firebase Authentication
            authResultCallbacks.validate();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                authResultCallbacks.onSuccess("Registration Successful");
                                writeNewUser();

                                view.getContext().startActivity(launcherIntent);
                            } else {
                                Log.e("Registration_Err", task.getException().getMessage());
                                authResultCallbacks.onError(task.getException().getMessage());
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
