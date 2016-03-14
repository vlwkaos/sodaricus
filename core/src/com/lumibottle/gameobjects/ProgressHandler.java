package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

/**
 * This class handles the presentation of enemies as the run time passes on.
 */
public class ProgressHandler {

    private Squirrel mySquirrel;

    private int numOfEnemies;

    private float runTime;

    private RoadRoller[] roadRollers;

    public ProgressHandler(Squirrel mySquirrel){
        this.mySquirrel = mySquirrel;


        roadRollers= new RoadRoller[3];
        for (int i=0;i<roadRollers.length;i++){
            roadRollers[i]= new RoadRoller(240,0,-50);
        }
    }

    public void update(float delta){
        for (RoadRoller r : roadRollers)
            r.update(delta,mySquirrel);

    }


    public RoadRoller[] getRoadRollers() {
        return roadRollers;
    }
}
