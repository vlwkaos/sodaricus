package com.lumibottle.world;

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

    //for calc


    private Squirrel mySquirrel;
    private ProgressHandler myStage;

    //
    private Star[] myStars;

    public GameWorld(){
        mySquirrel = new Squirrel(-255, -255, 20,20);
        myStars = new Star[11];
        for (int i=0;i<myStars.length;i++)
            myStars[i]= new Star();

        myStage = new ProgressHandler(mySquirrel);
        myGameState = GameState.PLAYING;

    }

    public void update(float delta){
        if (delta > .15f)
            delta = .15f;

        if (myGameState == GameState.TITLE){


        }
        /*
            Game Start
         */
        if (myGameState == GameState.PLAYING) {

            if (mySquirrel.isDEAD())
                mySquirrel.updateDead(delta);
            else {
                mySquirrel.update(delta);
                myStage.checkCollision();
            }
            myStage.update(delta);
        }


		for (FX f: FXHelper.getInstance().getMyFXs())
		f.update(delta);

        for (Star s:myStars)
            s.update(delta);
    }

    public Squirrel getMySquirrel() {
        return mySquirrel;
    }

    public Star[] getMyStars(){return myStars;}

    public ProgressHandler getMyStage() {
        return myStage;
    }


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

}
