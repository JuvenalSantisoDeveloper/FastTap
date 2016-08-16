package com.mustbear.app_fasttap.stats;


import com.mustbear.app_fasttap.data.entities.Score;

import java.util.List;

public interface StatsPresenter {
    List<Score> loadData();
}
