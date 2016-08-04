package com.mustbear.app_fasttap;

import android.content.Context;

import com.mustbear.app_fasttap.data.entities.Score;

public interface Repository {
    boolean saveStatistics(int score);
    void saveNewMaxScore(Context ctx, Score score);
    Score lookScore();
    boolean isNewRecord(int currentTaps);
    void loadLocalScore(Context ctx);
    boolean maxScoreIsBiggerThanZero();
}
