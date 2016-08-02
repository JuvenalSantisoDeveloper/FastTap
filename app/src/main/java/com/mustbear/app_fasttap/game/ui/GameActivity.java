package com.mustbear.app_fasttap.game.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mustbear.app_fasttap.R;
import com.mustbear.app_fasttap.data.entities.Score;
import com.mustbear.app_fasttap.game.GameActivityPresenter;
import com.mustbear.app_fasttap.game.GameActivityPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity implements GameActivityView {

    private static final int COUNTDOWN = 5000; //60000;
    private static final int SECOND = 1000;

    private InterstitialAd mIntersticialAd;


    @BindView(R.id.activity_game_tv_time)
    public TextView mTimeTextView;
    @BindView(R.id.activity_game_ib_game)
    public ImageButton mMainImageButton;
    @BindView(R.id.activity_game_tv_score)
    public TextView mScoreTextView;
    @BindView(R.id.activity_game_tv_max_score)
    public TextView mMaxScoreTextView;

    private GameActivityPresenter mPresenter;

    private int mCurrentScore;
    private Score mPlayerMaxScore;
    private CountDownTimer mTimerCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        initData();
        initUI();
    }

    private void initData() {
        mPresenter = new GameActivityPresenterImpl(this);

        mCurrentScore = 0;

        mTimerCountDown = new CountDownTimer(COUNTDOWN, SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeTextView.setText(String.valueOf(millisUntilFinished/SECOND));
            }

            @Override
            public void onFinish() {
                mTimeTextView.setText(String.valueOf(0));
                timeOver();
            }
        };

        mPresenter.onCreate(this);

        mPlayerMaxScore = mPresenter.lookForScore();

        setupIntersticialAd();
    }

    private void initUI() {
        mScoreTextView.setText(String.valueOf(mCurrentScore));
        mMaxScoreTextView.setText(mPlayerMaxScore.getPlayer() + " " + mPlayerMaxScore.getMaxScore());
    }

    @OnClick(R.id.activity_game_ib_game)
    public void handleTapMainButton() {
        tap();
    }


    @Override
    public void tap() {
        if(mCurrentScore == 0) {
            startTimer();
        }
        mCurrentScore++;
        mScoreTextView.setText(String.valueOf(mCurrentScore));
    }

    @Override
    public void timeOver() {
        mPresenter.showIntersticialAd(mCurrentScore);
        mPresenter.saveStatistics(mCurrentScore);
       /* Intent i = new Intent(this,AdActivity.class);
        startActivity(i);*/
    }

    @Override
    public void startTimer() {
        mTimerCountDown.start();
    }

    @Override
    public void updateFields() {
        mPlayerMaxScore = mPresenter.lookForScore();
        mMaxScoreTextView.setText(mPlayerMaxScore.getPlayer() + " " + mPlayerMaxScore.getMaxScore());
        mCurrentScore = 0;
        mScoreTextView.setText(String.valueOf(mCurrentScore));
    }

    @Override
    public void showNewScoreDialog() {
        DialogGameOver dialog = new DialogGameOver();
        Bundle bundle = new Bundle();
        bundle.putInt(DialogGameOver.KEY_SCORE, mCurrentScore);
        dialog.setArguments(bundle);
        dialog.setPresenter(mPresenter,this);
        dialog.show(getSupportFragmentManager(),"");
    }

    @Override
    public void showIntersticialAd(double probability) {
        if(randomToShowAd(probability) && mIntersticialAd.isLoaded()) {
            mIntersticialAd.show();
        }
    }

    private boolean randomToShowAd(double probability) {
        if ( Math.random() < probability ) {
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
                .addTestDevice(myDevice)
                .build();
        mIntersticialAd.loadAd(adRequest);
    }

}
