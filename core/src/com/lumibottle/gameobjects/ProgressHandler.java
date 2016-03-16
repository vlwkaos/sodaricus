package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

/**
 * This class handles the presentation of enemies as the run time passes on.
 */
public class ProgressHandler {

    private int numOfEnemies;
    private float runTime;

    //
	private Squirrel mySquirrel;
    //
	private RoadRoller[] roadRollers;

    //
    private FX[] FXs;



	public ProgressHandler(Squirrel mySquirrel) {
		this.mySquirrel = mySquirrel;
		roadRollers = new RoadRoller[3];
		for (int i = 0; i < roadRollers.length; i++) {
			roadRollers[i] = new RoadRoller();
	//		roadRollers[i].reset(240 + i * 50); later, with runtime.
		}


	}

	public void update(float delta) {
		runTime += delta;

        for (RoadRoller r : roadRollers) {
            if (r.isVISIBLE()) {
                r.update(delta);
                r.collide(mySquirrel);//

            }
        }




	}


	public RoadRoller[] getRoadRollers() {
		return roadRollers;
	}

	public FX[] getFXs() {
		return FXs;
	}
}
