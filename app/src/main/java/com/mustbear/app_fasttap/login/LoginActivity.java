package com.mustbear.app_fasttap.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.SignInButton;
import com.mustbear.app_fasttap.R;
import com.mustbear.app_fasttap.game.GameSettings;
import com.mustbear.app_fasttap.game.GameSettingsImpl;
import com.mustbear.app_fasttap.game.ui.GameActivity;
import com.mustbear.app_fasttap.network.GoogleApiClientHelper;
import com.mustbear.app_fasttap.network.GoogleApiClientHelperImpl;
import com.mustbear.app_fasttap.network.GoogleApiHelper;
import com.mustbear.app_fasttap.network.NetworkConnections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements NetworkConnections {

    private static final String TAG = LoginActivity.class.getName();

    @BindView(R.id.sign_in_button)
    public SignInButton mSignInButton;

    private GoogleApiClientHelper mClient;

    private GameSettings mAchievements;

    //Activity Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mClient = new GoogleApiClientHelperImpl();

        mClient.onCreate(this,GoogleApiHelper.getInstance(),GoogleApiHelper.getInstance(), GoogleApiHelper.GoogleApiServices.GAMES);

        GoogleApiHelper.getInstance().setNetworkConnections(this);

        mAchievements = new GameSettingsImpl(this);

        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mClient.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @OnClick(R.id.sign_in_button)
    public void signIn() {
        mClient.connect();
    }


    private void initUI() {
        mSignInButton.setSize(SignInButton.COLOR_AUTO);
    }

    @Override
    public void onConnectionFailed() {
        Log.d(TAG, "onConnectionFailed:");
        mSignInButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mAchievements.achievementWelcome();
        mAchievements.achievementWCTime();

        mSignInButton.setVisibility(View.GONE);

        mClient.onStop();

        Intent intentToGameActivity = new Intent(this,GameActivity.class);
        startActivity(intentToGameActivity);
        finish();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}

