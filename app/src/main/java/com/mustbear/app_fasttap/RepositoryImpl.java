package com.mustbear.app_fasttap;

import android.content.Context;
import android.util.Log;

import com.mustbear.app_fasttap.data.DataScorer;
import com.mustbear.app_fasttap.data.entities.Score;
import com.mustbear.app_fasttap.game.GameActivityPresenterImpl;
import com.mustbear.app_fasttap.game.ui.GameActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class RepositoryImpl implements Repository {

    private static final String NAME_SCORE_FILE = "score_file.txt";

    public RepositoryImpl() {}

    @Override
    public boolean saveStatistics(int score) {
        if(score > DataScorer.getInstance().getMaxScore()) {
            DataScorer.getInstance().setMaxScore(score);
            return true;
        } else {
            DataScorer.getInstance().setMaxScore(score);
            return false;
        }
    }

    @Override
    public void saveNewMaxScore(Context ctx, Score score) {
        DataScorer.getInstance().setScore(score);
        writeScoreFile(ctx);
    }

    @Override
    public Score lookScore() {
        return DataScorer.getInstance().getScore();
    }

    @Override
    public boolean isNewRecord(int currentTaps) {
        if(currentTaps > DataScorer.getInstance().getMaxScore()) {
            return true;
        }
        return false;
    }

    @Override
    public void loadLocalScore(Context ctx) {
        try
        {
            BufferedReader fin = new BufferedReader(new InputStreamReader(ctx.openFileInput(NAME_SCORE_FILE)));

            String texto = fin.readLine();
            String[] score = texto.split(":");
            Score scoreObj = new Score(score[0],Integer.valueOf(score[1]));
            DataScorer.getInstance().setScore(scoreObj);
            fin.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
    }

    @Override
    public boolean maxScoreIsBiggerThanZero() {
        if(DataScorer.getInstance().getMaxScore() > GameActivity.ZERO) {
            return true;
        }
        return false;
    }

    @Override
    public List<Score> loadRankedData() {
        List<Score> ranked = new ArrayList<Score>();
        ranked.add(new Score("Ovomaltina", 81));
        ranked.add(new Score("Juvenal", 78));
        ranked.add(new Score("Romina", 75));
        ranked.add(new Score("Rina", 70));
        ranked.add(new Score("Javi", 69));
        return ranked;
    }

    private void writeScoreFile(Context ctx) {

        try
        {
            OutputStreamWriter fout=
                    new OutputStreamWriter(
                            ctx.openFileOutput(NAME_SCORE_FILE, Context.MODE_PRIVATE));

            Score sc = DataScorer.getInstance().getScore();

            fout.write(sc.getPlayer() + ":" + sc.getMaxScore());

            fout.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
    }



}
