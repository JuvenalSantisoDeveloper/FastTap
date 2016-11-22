package com.mustbear.app_fasttap.game;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.games.Games;
import com.mustbear.app_fasttap.R;
import com.mustbear.app_fasttap.network.GoogleApiHelper;

public class GameSettingsImpl implements GameSettings {

    private static final String TAG = GameSettingsImpl.class.getName();

    private static final int UNLOCK_FINGER_BEGINER = 10;
    private static final int UNLOCK_FIRE_AT_YOUR_FINGER = 100;
    private static final int UNLOCK_PRIME = 17;

    private static final int REQUEST_ACHIEVEMENTS = 9001;
    private static final int REQUEST_LEADERBOARD = 9002;

    private Context mContext;
    private AppCompatActivity mActivity;

    public GameSettingsImpl(AppCompatActivity activity) {
        mContext = activity;
        mActivity = activity;
    }

    @Override
    public void achievementFingerBeginner(int i) {
        if(i > UNLOCK_FINGER_BEGINER) {
            Games.Achievements.unlock(GoogleApiHelper.getInstance().getGoogleApiClient(), mContext.getString(R.string.achievement_finger_beginner));
        }
    }

    @Override
    public void achievementFireAtYourFinger(int i) {
        if(i > UNLOCK_FIRE_AT_YOUR_FINGER) {
            Games.Achievements.unlock(GoogleApiHelper.getInstance().getGoogleApiClient(), mContext.getString(R.string.achievement_fire_at_your_finger));
        }
    }

    @Override
    public void achievementWelcome() {
        Games.Achievements.increment(GoogleApiHelper.getInstance().getGoogleApiClient(), mContext.getString(R.string.achievement_welcome),1);
    }

    @Override
    public void achievementWCTime() {
        Games.Achievements.increment(GoogleApiHelper.getInstance().getGoogleApiClient(), mContext.getString(R.string.achievement_wc_time),1);
    }

    @Override
    public void achievementPrime(int i) {
        if(i > UNLOCK_PRIME) {
            Games.Achievements.unlock(GoogleApiHelper.getInstance().getGoogleApiClient(), mContext.getString(R.string.achievement_prime));
        }
    }

    @Override
    public void submitScore(int score) {
        Games.Leaderboards.submitScore(GoogleApiHelper.getInstance().getGoogleApiClient(), mContext.getString(R.string.leaderboard_ranked), score);
    }

    @Override
    public void seeAchievements() {
        if (GoogleApiHelper.getInstance().isConnected())
            mActivity.startActivityForResult(Games.Achievements.getAchievementsIntent(GoogleApiHelper.getInstance().getGoogleApiClient()), REQUEST_ACHIEVEMENTS);
        else Log.e(TAG,"Please sign in to view achievements");

    }

    @Override
    public void seeLeaderBoard() {
        mActivity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(GoogleApiHelper.getInstance().getGoogleApiClient(),
                mContext.getString(R.string.leaderboard_ranked)), REQUEST_LEADERBOARD);
    }

    @Override
    public void signOut() {
        Games.signOut(GoogleApiHelper.getInstance().getGoogleApiClient());
    }
}
