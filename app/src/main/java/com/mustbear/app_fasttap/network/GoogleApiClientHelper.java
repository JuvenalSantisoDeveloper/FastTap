package com.mustbear.app_fasttap.network;

import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;

public interface GoogleApiClientHelper {
    //Lifecycle googleApiClient
    void onCreate(AppCompatActivity ctx, GoogleApiClient.OnConnectionFailedListener listener, GoogleApiClient.ConnectionCallbacks callbacks, GoogleApiHelper.GoogleApiServices op);
    void onStart();
    void onStop();

    //Modes
    void createWithGameAPI(AppCompatActivity ctx, GoogleApiClient.ConnectionCallbacks callbacks, GoogleApiClient.OnConnectionFailedListener listener);
    void createWithAuthAPI(AppCompatActivity ctx, GoogleApiClient.OnConnectionFailedListener listener);

    //Login methods
    void connect();
}
