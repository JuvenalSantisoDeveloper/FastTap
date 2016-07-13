package com.mustbear.app_fasttap;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity implements GameActivityView {

    private static final int COUNTDOWN = 60000;
    private static final int SECOND = 1000;


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
    }

    private void initUI() {
        mScoreTextView.setText(String.valueOf(mCurrentScore));
        mMaxScoreTextView.setText(String.valueOf(mPresenter.lookScore()));
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
        mPresenter.saveStatistics(this, mCurrentScore);
        mMaxScoreTextView.setText(String.valueOf(mCurrentScore));
        mCurrentScore = 0;
        mScoreTextView.setText(String.valueOf(mCurrentScore));
    }

    @Override
    public void startTimer() {
        mTimerCountDown.start();
    }
}
