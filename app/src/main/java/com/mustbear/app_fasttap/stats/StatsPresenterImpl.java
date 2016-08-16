package com.mustbear.app_fasttap.stats;

import com.mustbear.app_fasttap.Repository;
import com.mustbear.app_fasttap.RepositoryImpl;
import com.mustbear.app_fasttap.data.entities.Score;

import java.util.List;

public class StatsPresenterImpl implements StatsPresenter {

    private Repository mRepository;

    public StatsPresenterImpl() {
        mRepository = new RepositoryImpl();
    }

    @Override
    public List<Score> loadData() {
        return mRepository.loadRankedData();
    }
}
