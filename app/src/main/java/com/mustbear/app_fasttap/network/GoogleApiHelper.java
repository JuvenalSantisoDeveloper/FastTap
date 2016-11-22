package com.mustbear.app_fasttap.network;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mustbear.app_fasttap.game.ui.GameActivity;

public class GoogleApiHelper implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{

    public enum GoogleApiServices {
        GAMES, AUTH
    }

    private static GoogleApiHelper ourInstance = new GoogleApiHelper();

    public static GoogleApiHelper getInstance() {
        return ourInstance;
    }

    private GoogleApiHelper() {}

    private GoogleApiClient mGoogleApiClient;
    private NetworkConnections mNetworkConnections;

    public void setNetworkConnections(NetworkConnections networkConnections) {
        mNetworkConnections = networkConnections;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
    }

    public boolean isConnected() {
        return mGoogleApiClient.isConnected();
    }

    //Google Api Client
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mNetworkConnections.onConnectionFailed();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mNetworkConnections.onConnected(bundle);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mNetworkConnections.onConnectionSuspended(i);
    }
}
