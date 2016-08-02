package com.mustbear.app_fasttap.game.ui;

public interface GameActivityView {
    void tap();
    void timeOver();
    void startTimer();
    void updateFields();
    void showNewScoreDialog();
    void showIntersticialAd(double prob);

}
