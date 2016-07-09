package com.lumibottle.world;

import com.badlogic.gdx.Gdx;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.ProgressHandler;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.gameobjects.Star;
import com.lumibottle.helper.FXHelper;

/**
 * Created by MG-UP on 2016-03-10.
 * what is on the screen is going to be initialized here
 */
public class GameWorld {

    public enum GameState{
        SPLASH,TITLE,PLAYING,GAMEOVER
    }

    private GameState myGameState;

    //click
    private boolean skipSplash;

    //for calc
    private float runTime;

    private Squirrel mySquirrel;
    private ProgressHandler myStage;

    //
    private Star[] myStars;

    public GameWorld(){
        //init
        runTime = 0.01f;
        skipSplash = false;

        mySquirrel = new Squirrel(20,20);

        myStars = new Star[11];
        for (int i=0;i<myStars.length;i++)
            myStars[i]= new Star();

        myStage = new ProgressHandler(mySquirrel);
        myGameState = GameState.SPLASH;

    }

    public void update(float delta) {
        if (delta > .15f)
            delta = .15f;


        if (myGameState == GameState.SPLASH) {
            if (skipSplash)
                runTime -= delta;
            else if (runTime < 1.0f)
                runTime += delta;

            if (runTime <= -.5f) { // turns dark, skip
                myGameState = GameState.TITLE;
                runTime=0;
            }
        } else {

            if (myGameState == GameState.TITLE) {
                runTime += delta;
            }

        /*
            Game Start
         */
            if (myGameState == GameState.PLAYING) {
                mySquirrel.update(delta);
                myStage.checkCollision();
                myStage.update(delta);
                //gameover


            }

            for (FX f : FXHelper.getInstance().getMyFXs())
                f.update(delta);

            for (Star s : myStars)
                s.update(delta);
        } // not splash
    }

    public void onClick() {
        if (myGameState == GameState.SPLASH) {
            if (runTime > 0.5f)
                skipSplash = true; // for fadeout rendering
        }
        if (myGameState == GameState.TITLE){
            if (runTime >0.5f) {// give some delay to prevent accidental click
                myGameState = GameState.PLAYING;
                Gdx.app.log("GameWorld","game start pressed");
                Gdx.app.log("GameWorld","squirrel pos : ("+mySquirrel.getX()+", "+mySquirrel.getY()+")");
            }
        }


    }

    public Squirrel getMySquirrel() {
            return mySquirrel;
        }


    public Star[] getMyStars(){return myStars;}

    public ProgressHandler getMyStage() {
        return myStage;
    }

    public boolean isSPLASH() {return myGameState == GameState.SPLASH;}

    public boolean isTITLE(){
        return myGameState == GameState.TITLE;
    }


    public boolean isPLAYING(){
        return myGameState == GameState.PLAYING;
    }


    //maybe gameover is not necessary
    public boolean isGAMEOVER(){
        return myGameState == GameState.GAMEOVER;
    }



    public void resetRunTime(){runTime=0;}
    public float getRunTime(){return runTime;}
}
