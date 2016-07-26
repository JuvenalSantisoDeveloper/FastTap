package com.mustbear.app_fasttap.game.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mustbear.app_fasttap.R;
import com.mustbear.app_fasttap.data.entities.Score;
import com.mustbear.app_fasttap.game.GameActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogGameOver extends DialogFragment implements DialogInterface.OnShowListener {

    public static final String KEY_SCORE = "SCORE";

    @BindView(R.id.dialog_gameover_et_username)
    EditText dialogGameoverEtUsername;
    @BindView(R.id.dialog_gameover_tv_score)
    TextView dialogGameoverTvScore;

    private Context mContext;
    private GameActivityPresenter mPresenter;
    private int mCurrentScore;


    public DialogGameOver() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_gameover_title)
                .setPositiveButton(R.string.dialog_gameover_positive_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.dialog_gameover, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        initUI(getArguments());

        mCurrentScore = getArguments().getInt(KEY_SCORE);

        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialog) {
        final AlertDialog mydialog = (AlertDialog) getDialog();
        if (mydialog != null) {

            Button positiveButton = mydialog.getButton(Dialog.BUTTON_POSITIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.saveNewMaxScore(mContext, new Score(dialogGameoverEtUsername.getText().toString(),mCurrentScore));
                    mPresenter.updateViewFields();
                    dismiss();
                }
            });

        }

    }

    public void initUI(Bundle bundle) {
        dialogGameoverTvScore.setText(bundle.getString(KEY_SCORE));
    }

    public void setPresenter(GameActivityPresenter presenter, Context ctx) {
        mPresenter = presenter;
        mContext = ctx;
    }

}
