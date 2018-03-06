package com.lumibottle.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.lumibottle.game.GameMain;

import java.util.Locale;

/**
 * Created by MG on 2016-12-31.
 */

public class ScoreHelper {

    private static ScoreHelper ourInstance = new ScoreHelper();
    public static ScoreHelper getInstance() {
        return ourInstance;
    }

    private int score;
    private int best;
    private Preferences preferences;

    protected Preferences getPrefs() {
        if(preferences==null){
            preferences = Gdx.app.getPreferences("prefs");
        }
        return preferences;
    }



    private ScoreHelper(){
        score = 0;
        getPrefs().flush();
        best = preferences.getInteger("best",0);
    }

    public void incrementScore(int add){
        score+=add;
        if (score >= 5000)
            GameMain.playServices.unlockAchievement(4);
    }

    public void resetScore(){
        score = 0;
    }

    public String getScore(){return String.format(Locale.getDefault(),"%06d",score);}

    public int getIntScore() {return score;}

    public String getBest(){return String.format(Locale.getDefault(),"%06d",best);}

    public void saveScore(float runTime){
        //best,
        if (score >(int)(runTime*200)) {
            best = -1;
            return;
        }

        if (score > best) {
            best = score;
            preferences.putInteger("best", score);
            getPrefs().flush();
            GameMain.playServices.submitScore(score);
        }
    }

}
