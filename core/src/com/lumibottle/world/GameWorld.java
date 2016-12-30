package com.lumibottle.world;

import com.badlogic.gdx.Gdx;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.ProgressHandler;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.gameobjects.Star;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.helper.SoundManager;
import com.lumibottle.screen.GameScreen;

/**
 * Created by MG-UP on 2016-03-10.
 * what is on the screen is going to be initialized here
 */
public class GameWorld {

    public enum GameState {
        SPLASH, TITLE, PLAYING, ABOUT,GAMEOVER
    }

    private GameState myGameState;

    //click
    private boolean skipSplash;
    private boolean flash;

    //for drawing static images
    private float runTime;
    private float runTime_2;

    private Squirrel mySquirrel;
    private ProgressHandler myStage;

    //
    private Star[] myStars;

    public GameWorld() {
        //init
        runTime = 0.01f;
        runTime_2 = 0.0f;

        skipSplash = false;
        flash = flash;

        mySquirrel = new Squirrel(20, 20);

        myStars = new Star[11];
        for (int i = 0; i < myStars.length; i++)
            myStars[i] = new Star();

        myStage = new ProgressHandler(mySquirrel);
        myGameState = GameState.SPLASH;

    }

    public void update(float delta) {
        if (delta > .15f)
            delta = .15f;


        /*
            SPLASH
         */
        if (myGameState == GameState.SPLASH) {
            if (skipSplash)
                runTime -= delta;
            else if (runTime < 1.0f)
                runTime += delta;

            if (runTime <= -.5f) { // turns dark, skip
                myGameState = GameState.TITLE;
                runTime = 0;
            }
        } else {
            if (myGameState == GameState.TITLE) {
                runTime += delta;
                runTime_2 += delta;
                if (runTime_2 >0.5f) {
                    flash = !flash;
                    runTime_2 = 0.0f;
                }
            } else if (myGameState == GameState.PLAYING) {
                runTime +=delta;
                mySquirrel.update(delta);
                myStage.update(delta);
                myStage.checkCollision();
                //gameover
            }

            for (FX f : FXHelper.getInstance().getMyFXs())
                f.update(delta);

            for (Star s : myStars)
                s.update(delta);
        } // not splash
    }

    public void onClick(int screenX,int screenY) {
        //click coordinate starts from top-left corner
        //drawing coordinates start from lower-left corner
        Gdx.app.log("GameWorld", "("+ calcGameX(screenX)+", "+ calcGameY(screenY)+")");

        if (myGameState == GameState.SPLASH) {
            if (runTime > 0.5f)
                skipSplash = true; // for fadeout rendering
        } else if (myGameState == GameState.TITLE) {
            if (runTime > 1.0f) {// give some delay to prevent accidental click

                //touch to start
                // else, touch here to see the credit
                if (calcGameX(screenX) < 215.0f && calcGameX(screenX) > 25.0f) {
                    SoundManager.getInstance().play(SoundManager.SELECT);
                    myGameState = GameState.PLAYING;
                    runTime = 0.0f;
                    runTime_2 = 0.0f;
                } else if (calcGameX(screenX) > 215.0f && calcGameY(screenY)> 130.0f){
                    SoundManager.getInstance().play(SoundManager.SELECT);
                    myGameState = GameState.ABOUT;
                }
            }
        } else if (myGameState == GameState.ABOUT){
            myGameState = GameState.TITLE;
            SoundManager.getInstance().play(SoundManager.SELECT);

        } else if (myGameState == GameState.PLAYING){
            if (!mySquirrel.isSPAWNING()) {
                mySquirrel.onClick();
                SoundManager.getInstance().play(SoundManager.JUMP);
            }
        }


    }

    private float calcGameX(int screenX){
        return screenX * GameScreen.gameWidth/Gdx.graphics.getWidth();
    }

    private float calcGameY(int screenY){
        return screenY * GameScreen.gameHeight/Gdx.graphics.getHeight();
    }


    public Squirrel getMySquirrel() {
        return mySquirrel;
    }


    public Star[] getMyStars() {
        return myStars;
    }

    public ProgressHandler getMyStage() {
        return myStage;
    }

    public boolean isSPLASH() {
        return myGameState == GameState.SPLASH;
    }

    public boolean isTITLE() {
        return myGameState == GameState.TITLE;
    }


    public boolean isPLAYING() {
        return myGameState == GameState.PLAYING;
    }

    public boolean isABOUT() {return myGameState == GameState.ABOUT;}

    //maybe gameover is not necessary
    public boolean isGAMEOVER() {
        return myGameState == GameState.GAMEOVER;
    }

    public void resetRunTime() {
        runTime = 0;
    }

    public float getRunTime() {
        return runTime;
    }

    public float getRunTime_2() {
        return runTime_2;
    }

    public boolean getFlash(){return flash;}

}
