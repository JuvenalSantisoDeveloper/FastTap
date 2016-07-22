package com.mustbear.app_fasttap.game;

import android.content.Context;

import com.mustbear.app_fasttap.data.entities.Score;

public interface GameActivityPresenter {
    void onCreate(Context ctx);
    void saveStatistics(Context ctx, Score score);
    Score lookForScore();
    void updateViewFields();
}
