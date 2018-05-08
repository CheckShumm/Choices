package com.example.nathanshumm.decided.Interface;

public interface AuthResultCallbacks {
    void validate();
    void onSuccess(String message);
    void onError(String message);
}
