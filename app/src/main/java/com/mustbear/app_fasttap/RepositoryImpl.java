package com.mustbear.app_fasttap;

public class RepositoryImpl implements Repository {

    public RepositoryImpl() {}

    @Override
    public void saveStatistics(int score) {
        DataScorer.getInstance().setMaxScore(score);
    }

    @Override
    public int lookScore() {
        return DataScorer.getInstance().getMaxScore();
    }

}
