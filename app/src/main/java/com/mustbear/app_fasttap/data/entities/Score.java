package com.mustbear.app_fasttap.data.entities;

public class Score {

    private String mPlayer;
    private int mMaxScore;

    public Score(String player, int mMaxScore) {
        this.mPlayer = player;
        this.mMaxScore = mMaxScore;
    }

    public void setPlayer(String player) {
        mPlayer = player;
    }

    public void setMaxScore(int maxScore) {
        mMaxScore = maxScore;
    }

    public String getPlayer() {
        return mPlayer;
    }

    public int getMaxScore() {
        return mMaxScore;
    }
}
