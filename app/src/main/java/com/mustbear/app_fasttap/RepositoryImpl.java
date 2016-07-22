package com.mustbear.app_fasttap;

import android.content.Context;
import android.util.Log;

import com.mustbear.app_fasttap.data.DataScorer;
import com.mustbear.app_fasttap.data.entities.Score;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class RepositoryImpl implements Repository {

    private static final String NAME_SCORE_FILE = "score_file.txt";

    public RepositoryImpl() {}

    @Override
    public boolean saveStatistics(Context ctx, Score score) {
        if(score.getMaxScore() > DataScorer.getInstance().getMaxScore()) {
            DataScorer.getInstance().setScore(score);
            writeScoreFile(ctx);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Score lookScore() {
        return DataScorer.getInstance().getScore();
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
