package com.mustbear.app_fasttap;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity implements GameActivityView {

    private static final int TIMERCOUNTDOWN = 15000;
    private static final int SECOND = 1000;
    private static final int ZERO = 0;
    private static final int TEN = 10;


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
        mCurrentScore = ZERO;

        mTimerCountDown = new CountDownTimer(TIMERCOUNTDOWN, SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeTextView.setText(String.valueOf(millisUntilFinished / SECOND));
                checkTimeIsRunningOut(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                mTimeTextView.setText(String.valueOf(0));
                timeOver();
            }
        };
    }

    private void checkTimeIsRunningOut(long millisUntilFinished) {
        if(millisUntilFinished/SECOND <= TEN) {
            mTimeTextView.setAnimation(AnimationUtils.loadAnimation(GameActivity.this, R.anim.blink_animation));
            mTimeTextView.setTextColor(ContextCompat.getColor(GameActivity.this, R.color.endUpTime));
        }
    }
    //TODO: comprobar si esta forma es eficiente ~ probablemente no lo sea
    /* Uso de funcion booleana newRecord que se llama para comprobar si tienes el record
     y en función de ello cambiar el color del score y para setear la max score (imagino que esto
     último se hace ahora con el fichero
    */
    private boolean isNewRecord(int currentScore) {
        if(currentScore > Integer.valueOf(mMaxScoreTextView.getText().toString())) {
            return true;
        }
        return false;
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
        if(mCurrentScore == ZERO) {
            startTimer();
        }
        mCurrentScore++;
        mScoreTextView.setText(String.valueOf(mCurrentScore));
        if(Integer.valueOf(mMaxScoreTextView.getText().toString()) > ZERO && isNewRecord(mCurrentScore)) {
            mScoreTextView.setTextColor(ContextCompat.getColor(this, R.color.maxScorer));
            mScoreTextView.setAnimation(AnimationUtils.loadAnimation(GameActivity.this, R.anim.shake_animation));
        }
    }


    //TODO: comprobar si esta forma es eficiente ~ probablemente no lo sea
    /* comentario previo sobre este toDo y el uso de la funcion isNewRecord*/
    @Override
    public void timeOver() {
        mPresenter.saveStatistics(mCurrentScore);
        if(isNewRecord(mCurrentScore)) {
            mMaxScoreTextView.setText(String.valueOf(mCurrentScore));
        }
        mCurrentScore = ZERO;
        mScoreTextView.setText(String.valueOf(mCurrentScore));
        mTimeTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryText));
        mScoreTextView.setTextColor(ContextCompat.getColor(this, R.color.score));
    }

    @Override
    public void startTimer() {
        mTimerCountDown.start();
    }
}
