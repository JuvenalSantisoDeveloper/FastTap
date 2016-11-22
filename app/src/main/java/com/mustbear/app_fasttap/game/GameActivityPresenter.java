package com.mustbear.app_fasttap.game;

import android.content.Context;

import com.mustbear.app_fasttap.data.entities.Score;

public interface GameActivityPresenter {
    void onCreate(Context ctx);
    void saveStatistics(Context context, int score);
    Score lookForScore();
    void showIntersticialAd(int currentRecord);
    boolean isNewRecord(int currentTaps);
    boolean maxScoreIsBiggerThanZero();
}
