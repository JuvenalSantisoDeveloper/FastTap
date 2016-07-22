package com.mustbear.app_fasttap.game;

import android.content.Context;

import com.mustbear.app_fasttap.Repository;
import com.mustbear.app_fasttap.RepositoryImpl;
import com.mustbear.app_fasttap.data.entities.Score;
import com.mustbear.app_fasttap.game.ui.GameActivityView;

public class GameActivityPresenterImpl implements GameActivityPresenter {

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
    public void saveStatistics(Context ctx, Score score) {
        if(mRepository.saveStatistics(ctx, score)) {
            updateViewFields();
        }

    }

    @Override
    public Score lookForScore() {
        return mRepository.lookScore();
    }

    @Override
    public void updateViewFields() {
        mView.updateFields();
    }
}
