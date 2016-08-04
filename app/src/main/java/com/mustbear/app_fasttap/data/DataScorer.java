package com.mustbear.app_fasttap.data;

import com.mustbear.app_fasttap.data.entities.Score;

public class DataScorer {

    private static DataScorer mInstance;

    private Score mScore;

    public int getMaxScore() {
        return mScore.getMaxScore();
    }

    public void setMaxScore(int maxScore) {
        mScore.setMaxScore(maxScore);
    }

    private DataScorer() {
        mScore = new Score("Default",0);
    }

    public static  DataScorer getInstance() {

        if(mInstance == null) {
            mInstance = new DataScorer();
        }

        return mInstance;
    }

    public Score getScore() {
        return mScore;
    }

    public void setScore(Score score) {
        mScore = score;
    }
}

