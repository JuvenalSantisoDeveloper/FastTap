package com.mustbear.app_fasttap.game;

import android.content.Context;

import com.mustbear.app_fasttap.Repository;
import com.mustbear.app_fasttap.RepositoryImpl;
import com.mustbear.app_fasttap.data.entities.Score;
import com.mustbear.app_fasttap.game.ui.GameActivityView;

public class GameActivityPresenterImpl implements GameActivityPresenter {

    private double NEAR_RECORD_PERCENTAGE = 0.45;
    private double NORMAL_PERCENTAGE = 0.25;


    private GameActivityView mView;
    private Repository mRepository;

    public GameActivityPresenterImpl(GameActivityView view) {
        mView = view;
        mRepository = new RepositoryImpl();
    }

    @Override
    public void onCreate(Context ctx) {
        mRepository.loadLocalScore(ctx);
    }

    @Override
    public void saveStatistics(Context context, int score) {
        if(mRepository.saveStatistics(context, score)) {
            mView.showNewScore();
        }
    }

    @Override
    public Score lookForScore() {
        return mRepository.lookScore();
    }

    @Override
    public void showIntersticialAd(int currentRecord) {
        if(mRepository.lookScore().getMaxScore() < currentRecord) {
            mView.showIntersticialAd(NEAR_RECORD_PERCENTAGE);
        } else {
            mView.showIntersticialAd(NORMAL_PERCENTAGE);
        }
    }

    @Override
    public boolean isNewRecord(int currentTaps) {
        return mRepository.isNewRecord(currentTaps);
    }

    @Override
    public boolean maxScoreIsBiggerThanZero() {
        return mRepository.maxScoreIsBiggerThanZero();
    }
}
