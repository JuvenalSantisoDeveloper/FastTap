package com.mustbear.app_fasttap.game.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.mustbear.app_fasttap.R;
import com.mustbear.app_fasttap.game.GameSettings;
import com.mustbear.app_fasttap.game.GameSettingsImpl;
import com.mustbear.app_fasttap.game.GameActivityPresenter;
import com.mustbear.app_fasttap.game.GameActivityPresenterImpl;
import com.mustbear.app_fasttap.network.GoogleApiClientHelper;
import com.mustbear.app_fasttap.network.GoogleApiClientHelperImpl;
import com.mustbear.app_fasttap.network.GoogleApiHelper;
import com.mustbear.app_fasttap.network.NetworkConnections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity implements GameActivityView, NetworkConnections {

    private static final String TAG = GameActivity.class.getName();

    private static final int BUG_COUNTDOWN_TWO_SECONDS = 2000;

    private static final int COUNTDOWN = 15000; //60000;
    private static final int SECOND = 1000;
    private static final int FINAL_COUNT_DOWN = 3;

    public static final int ZERO = 0;

    @BindView(R.id.activity_game_tv_time)
    public TextView mTimeTextView;
    @BindView(R.id.activity_game_ib_game)
    public ImageButton mMainImageButton;
    @BindView(R.id.activity_game_tv_score)
    public TextView mScoreTextView;
    @BindView(R.id.activity_game_tv_max_score)
    public TextView mMaxScoreTextView;
    @BindView(R.id.activity_game_tv_score_max_text)
    public TextView mMaxScoreLabelTextView;
    @BindView(R.id.activity_game_tv_score_text)
    public TextView mScoreLabelTextView;
    @BindView(R.id.activity_game_pb_progressBar)
    public ProgressBar mProgressBar;
    @BindView(R.id.activity_game_toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.activity_game_container_game)
    public RelativeLayout mContainer;

    private GameSettings mGameSettings;

    private InterstitialAd mIntersticialAd;

    private GoogleApiClientHelper mClient;

    private GameActivityPresenter mPresenter;

    private int mCurrentScore;
    private CountDownTimer mTimerCountDown;
    private int mSecondsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);


        mClient = new GoogleApiClientHelperImpl();
        mClient.onCreate(this,GoogleApiHelper.getInstance(),GoogleApiHelper.getInstance(), GoogleApiHelper.GoogleApiServices.GAMES);

        GoogleApiHelper.getInstance().setNetworkConnections(this);

        initData();
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
        mClient.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimerCountDown.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_global_stats:
                mGameSettings.seeLeaderBoard();
                return true;
            case R.id.menu_global_achievements:
                mGameSettings.seeAchievements();
                return true;
            case R.id.menu_sign_out:
                mGameSettings.signOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initData() {

        mGameSettings = new GameSettingsImpl(this);

        mPresenter = new GameActivityPresenterImpl(this);

        mCurrentScore = ZERO;
        mSecondsLeft = COUNTDOWN / SECOND;

        mTimerCountDown = new CountDownTimer(COUNTDOWN, SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (Math.round((float) millisUntilFinished / SECOND) != mSecondsLeft) {
                    mSecondsLeft = Math.round((float) millisUntilFinished / SECOND);
                    mTimeTextView.setText(String.valueOf(mSecondsLeft));
                }
                checkTimeIsRunningOut(mSecondsLeft);

                if (millisUntilFinished < BUG_COUNTDOWN_TWO_SECONDS) {
                    Handler h = new Handler();
                    int delay = SECOND; //milliseconds

                    h.postDelayed(new Runnable() {
                        public void run() {
                            mSecondsLeft = 1;
                            mTimeTextView.setText(String.valueOf(mSecondsLeft));
                        }
                    }, delay);
                }
            }

            @Override
            public void onFinish() {
                timeOver();
                updateFields();
            }
        };

        mPresenter.onCreate(this);

        setupIntersticialAd();
    }

    private void initUI() {
        mScoreTextView.setText(String.valueOf(mCurrentScore));
        mMaxScoreTextView.setText(String.valueOf(mPresenter.lookForScore().getMaxScore()));

        setSupportActionBar(mToolbar);

        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mToolbar.setTitleTextColor(Color.WHITE);
    }

    @OnClick(R.id.activity_game_ib_game)
    public void handleTapMainButton() {
        tap();
    }


    @Override
    public void tap() {
        if (mCurrentScore == ZERO) {
            startTimer();
        }
        mCurrentScore++;

        if (maxScoreIsBiggerThanZero() && mPresenter.isNewRecord(mCurrentScore)) {
            mScoreTextView.setTextColor(ContextCompat.getColor(this, R.color.maxScorer));
            mScoreTextView.setAnimation(AnimationUtils.loadAnimation(GameActivity.this, R.anim.shake_animation));
        }
        mScoreTextView.setText(String.valueOf(mCurrentScore));
        calculateProgress();

        mGameSettings.achievementFingerBeginner(mCurrentScore);
        mGameSettings.achievementFireAtYourFinger(mCurrentScore);
        mGameSettings.achievementPrime(mCurrentScore);
    }

    @Override
    public void timeOver() {
        mPresenter.showIntersticialAd(mCurrentScore);
        mPresenter.saveStatistics(this, mCurrentScore);
    }

    @Override
    public void startTimer() {
        mTimerCountDown.start();
        mMaxScoreTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    @Override
    public void updateFields() {
        mTimeTextView.setText(String.valueOf(ZERO));

        mMaxScoreTextView.setText(String.valueOf(mPresenter.lookForScore().getMaxScore()));
        mCurrentScore = ZERO;
        mScoreTextView.setText(String.valueOf(mCurrentScore));

        mTimeTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryText));
        mScoreTextView.setTextColor(ContextCompat.getColor(this, R.color.score));
    }

    @Override
    public void showNewScore() {

        mGameSettings.submitScore(mPresenter.lookForScore().getMaxScore());

        mMaxScoreTextView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in_animation));
        mMaxScoreLabelTextView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in_animation));
        mMaxScoreTextView.setText(String.valueOf(mPresenter.lookForScore().getMaxScore()));
        mMaxScoreTextView.setTextColor(ContextCompat.getColor(this, R.color.maxScorer));

    }

    @Override
    public void showIntersticialAd(double probability) {
        if (randomToShowAd(probability) && mIntersticialAd.isLoaded()) {
            mIntersticialAd.show();
        }
    }

    private boolean randomToShowAd(double probability) {
        if (Math.random() < probability) {
            return true;
        }
        return false;
    }

    private void setupIntersticialAd() {
        mIntersticialAd = new InterstitialAd(this);
        mIntersticialAd.setAdUnitId(getResources().getString(R.string.ad_id));

        mIntersticialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                requestNewIntersticialAd();
            }
        });

        requestNewIntersticialAd();
    }

    private void requestNewIntersticialAd() {
        final String myDevice = "37ABF957437D05CC";
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(myDevice)
                .build();
        mIntersticialAd.loadAd(adRequest);
    }

    private void checkTimeIsRunningOut(double seg) {
        if (seg <= FINAL_COUNT_DOWN) {
            mTimeTextView.setAnimation(AnimationUtils.loadAnimation(GameActivity.this, R.anim.blink_animation));
            mTimeTextView.setTextColor(ContextCompat.getColor(GameActivity.this, R.color.endUpTime));
        }
    }

    private boolean maxScoreIsBiggerThanZero() {
        return mPresenter.maxScoreIsBiggerThanZero();
    }

    private void calculateProgress() {
        int progress = 0;
        if (mPresenter.lookForScore().getMaxScore() != ZERO) {
            progress = (mCurrentScore * 100) / mPresenter.lookForScore().getMaxScore();
        }

        mProgressBar.setProgress(progress);
    }

    @Override
    public void onConnectionFailed() {
        Log.d(TAG, "onConnectionFailed:");
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected:");
        mContainer.setVisibility(View.VISIBLE);

        Log.d(TAG, " --- " + Games.getCurrentAccountName(GoogleApiHelper.getInstance().getGoogleApiClient()) + " " + GoogleApiHelper.getInstance().isConnected());
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.d(TAG, "onConnectionSuspended:");
    }
}
