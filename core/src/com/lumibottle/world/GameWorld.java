package com.lumibottle.world;

import com.lumibottle.gameobjects.Bullet;
import com.lumibottle.gameobjects.Squirrel;

/**
 * Created by MG-UP on 2016-03-10.
 * what is on the screen is going to be initialized here
 */
public class GameWorld {

    //for calc
    private float midPointY;


    private Squirrel mySquirrel;

    public GameWorld(float midPointY){
    this.midPointY = midPointY;
    mySquirrel = new Squirrel(55, midPointY+5, 20,20);

    }

    public void update(float delta){
        if (delta > .15f) {
            delta = .15f;
        }

        mySquirrel.update(delta);

    }

    public Squirrel getMySquirrel() {
        return mySquirrel;
    }
}
