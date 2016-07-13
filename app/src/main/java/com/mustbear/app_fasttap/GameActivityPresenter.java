package com.mustbear.app_fasttap;

import android.content.Context;

public interface GameActivityPresenter {
    void onCreate(Context ctx);
    void saveStatistics(Context ctx, int score);
    int lookScore();
}
