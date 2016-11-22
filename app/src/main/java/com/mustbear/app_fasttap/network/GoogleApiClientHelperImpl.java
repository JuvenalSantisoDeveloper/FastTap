package com.mustbear.app_fasttap.network;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.mustbear.app_fasttap.game.ui.GameActivity;

public class GoogleApiClientHelperImpl implements GoogleApiClientHelper {

    private static final String TAG = GoogleApiClientHelperImpl.class.getName();

    public GoogleApiClientHelperImpl() {}

    @Override
    public void onCreate(AppCompatActivity ctx, GoogleApiClient.OnConnectionFailedListener listener, GoogleApiClient.ConnectionCallbacks callbacks, GoogleApiHelper.GoogleApiServices op) {

        switch (op) {
            case GAMES:
                createWithGameAPI(ctx, callbacks, listener);
                break;
            case AUTH:
                createWithAuthAPI(ctx, listener);
                break;
            default:
        }

    }

    @Override
    public void onStart() {
        GoogleApiHelper.getInstance().getGoogleApiClient().connect();
    }

    @Override
    public void onStop() {
        GoogleApiHelper.getInstance().getGoogleApiClient().disconnect();
    }

    @Override
    public void createWithGameAPI(AppCompatActivity ctx, GoogleApiClient.ConnectionCallbacks callbacks, GoogleApiClient.OnConnectionFailedListener listener) {

        resetApiGoogle();

        GoogleApiClient googleApi = new GoogleApiClient.Builder(ctx.getApplicationContext())
                .enableAutoManage(ctx /* FragmentActivity */, listener /* OnConnectionFailedListener */)
                .addConnectionCallbacks(callbacks)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .build();


        GoogleApiHelper.getInstance().setGoogleApiClient(googleApi);
    }

    @Override
    public void createWithAuthAPI(AppCompatActivity ctx, GoogleApiClient.OnConnectionFailedListener listener) {

        resetApiGoogle();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        GoogleApiClient googleApi = new GoogleApiClient.Builder(ctx)
                .enableAutoManage(ctx /* FragmentActivity */, listener /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        GoogleApiHelper.getInstance().setGoogleApiClient(googleApi);
    }

    @Override
    public void connect() {
        GoogleApiHelper.getInstance().getGoogleApiClient().connect();
    }

    public void resetApiGoogle() {
        GoogleApiHelper.getInstance().setGoogleApiClient(null);
    }
}
