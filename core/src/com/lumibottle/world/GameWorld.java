package com.lumibottle.world;

import com.lumibottle.gameobjects.Bullet;
import com.lumibottle.gameobjects.ProgressHandler;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.gameobjects.Star;

/**
 * Created by MG-UP on 2016-03-10.
 * what is on the screen is going to be initialized here
 */
public class GameWorld {

    //for calc
    private float midPointY;


    private Squirrel mySquirrel;
    private ProgressHandler myStage;

    //
    private Star[] myStars;

    public GameWorld(float midPointY){
    this.midPointY = midPointY;
    mySquirrel = new Squirrel(55, midPointY+5, 20,20);
    myStars = new Star[14];
        for (int i=0;i<myStars.length;i++)
            myStars[i]= new Star();
    myStage = new ProgressHandler(mySquirrel);

    }

    public void update(float delta){
        if (delta > .15f) {
            delta = .15f;
        }

        myStage.update(delta);
        mySquirrel.update(delta);
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
}
