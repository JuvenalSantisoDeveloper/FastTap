package com.mustbear.app_fasttap;

import android.content.Context;

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
    public void saveStatistics(Context ctx, int score) {
        mRepository.saveStatistics(ctx, score);
    }

    @Override
    public int lookScore() {
        return mRepository.lookScore();
    }
}
