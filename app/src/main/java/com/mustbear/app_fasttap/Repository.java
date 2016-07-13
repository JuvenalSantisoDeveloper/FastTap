package com.mustbear.app_fasttap;

import android.content.Context;

public interface Repository {
    void saveStatistics(Context ctx, int score);
    int lookScore();
    void loadLocalScore(Context ctx);
}
