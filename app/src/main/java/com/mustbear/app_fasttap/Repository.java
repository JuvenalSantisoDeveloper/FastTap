package com.mustbear.app_fasttap;

import android.content.Context;

import com.mustbear.app_fasttap.data.entities.Score;

import java.util.List;

public interface Repository {
    //GameActivity
    boolean saveStatistics(Context ctx, int score);
    Score lookScore();
    boolean isNewRecord(int currentTaps);
    void loadLocalScore(Context ctx);
    boolean maxScoreIsBiggerThanZero();
}
