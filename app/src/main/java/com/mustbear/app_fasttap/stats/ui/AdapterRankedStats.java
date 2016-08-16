package com.mustbear.app_fasttap.stats.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mustbear.app_fasttap.R;
import com.mustbear.app_fasttap.data.entities.Score;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRankedStats extends RecyclerView.Adapter<AdapterRankedStats.RankedRecordViewHolder> {

    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;

    private Context mContext;
    private List<Score> mRanked;

    public AdapterRankedStats(Context ctx, List<Score> ranked) {
        mContext = ctx; mRanked = ranked;
    }

    @Override
    public RankedRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new RankedRecordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RankedRecordViewHolder holder, int position) {
        Score currentScore = mRanked.get(position);

        holder.itemRecordImage.setImageDrawable(getRankedImage(position));
        holder.itemRecordName.setText(currentScore.getPlayer());
        holder.itemRecordScore.setText(String.valueOf(currentScore.getMaxScore()));
    }

    private Drawable getRankedImage(int position) {
        switch (position) {
            case FIRST: {
                return getDrawableSupportVersion(R.drawable.gold_medal);
            }
            case SECOND: {
                return getDrawableSupportVersion(R.drawable.silver_medal);
            }
            case THIRD: {
                return getDrawableSupportVersion(R.drawable.cupper_medal);
            }
            default: {
                return getDrawableSupportVersion(R.drawable.top_band);
            }

        }
    }

    private Drawable getDrawableSupportVersion(int drawableID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mContext.getResources().getDrawable(drawableID, mContext.getTheme());
        } else {
            return mContext.getDrawable(drawableID);
        }
    }

    @Override
    public int getItemCount() {
        return mRanked.size();
    }

    public static class RankedRecordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_record_image)
        public ImageView itemRecordImage;
        @BindView(R.id.item_record_name)
        public TextView itemRecordName;
        @BindView(R.id.item_record_score)
        public TextView itemRecordScore;
        @BindView(R.id.item_record_linearlayout_container)
        public LinearLayout itemRecordLinearlayoutContainer;
        @BindView(R.id.item_record_card_container)
        public CardView itemRecordCardContainer;

        public RankedRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
