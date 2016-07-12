package com.mustbear.app_fasttap;

public class GameActivityPresenterImpl implements GameActivityPresenter {

    private GameActivityView mView;
    private Repository mRepository;

    public GameActivityPresenterImpl(GameActivityView view) {
        mView = view;
        mRepository = new RepositoryImpl();
    }

    @Override
    public void saveStatistics(int score) {
        mRepository.saveStatistics(score);
    }

    @Override
    public int lookScore() {
        return mRepository.lookScore();
    }
}
