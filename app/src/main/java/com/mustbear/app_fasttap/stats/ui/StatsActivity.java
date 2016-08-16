package com.mustbear.app_fasttap.stats.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mustbear.app_fasttap.R;
import com.mustbear.app_fasttap.data.entities.Score;
import com.mustbear.app_fasttap.stats.StatsPresenter;
import com.mustbear.app_fasttap.stats.StatsPresenterImpl;
import com.mustbear.app_fasttap.stats.StatsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity {

    @BindView(R.id.activity_game_toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.activity_stats_recycler_view)
    public RecyclerView mRecyclerView;

    private AdapterRankedStats mAdapter;
    private RecyclerView.LayoutManager mManager;

    StatsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        mPresenter = new StatsPresenterImpl();

        initUI();
        setupRecycler();
    }

    private void setupAdapter() {
        mAdapter = new AdapterRankedStats(this, mPresenter.loadData());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupRecycler() {

        setupAdapter();

        mManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        setSupportActionBar(mToolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(StatsUtils.getColoredArrow(this, Color.WHITE));
        }

        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(Color.WHITE);

    }
}
