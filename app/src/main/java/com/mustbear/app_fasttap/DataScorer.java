package com.mustbear.app_fasttap;

public class DataScorer {

    private static DataScorer mInstance;

    private int mMaxScore;

    public int getMaxScore() {
        return mMaxScore;
    }

    public void setMaxScore(int maxScore) {
        mMaxScore = maxScore;
    }

    private DataScorer() {
        mMaxScore = 0;
    }

    public static  DataScorer getInstance() {

        if(mInstance == null) {
            mInstance = new DataScorer();
        }

        return mInstance;
    }

}

