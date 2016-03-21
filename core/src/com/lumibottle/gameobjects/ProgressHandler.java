package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;

/**
 * This class handles the presentation of enemies as the run time passes on.
 */
public class ProgressHandler {

    private int numOfEnemies;
    private float runTime;

	private int stageNumber;

	private FX[] myFXs;
    //
	private RoadRoller[] roadRollers;
	private Mustache[] mustaches;

	public ProgressHandler() {
		stageNumber=0;
		roadRollers = new RoadRoller[4];
		for (int i = 0; i < roadRollers.length; i++) {
			roadRollers[i] = new RoadRoller();
			roadRollers[i].reset(240 + i * 50); //later, with runtime.
		}

		mustaches = new Mustache[5];
		for (int i = 0; i < mustaches.length; i++) {
			mustaches[i] = new Mustache();
			mustaches[i].reset(240 + i * 20); //later, with runtime.
		}

		myFXs = new FX[10];
		for (int i=0; i< myFXs.length;i++)
			myFXs[i]= new FX();

	}

	public void update(float delta) {
		runTime += delta;

		for (FX f:myFXs)
				f.update(delta);
		/*
			Movements
		 */
	//	updateRoadRollers(delta);
		updateMustaches(delta);
	}


	/*
		Organize
	 */
	private void updateRoadRollers(float delta) {
		for (RoadRoller r : roadRollers) {
			r.update(delta);
			//TODO how to spawn?
			if (r.isREADY())
				r.reset(250);
		}
	}
		private void updateMustaches(float delta){
		for (Mustache m : mustaches) {
			m.update(delta);

		}

	}







	public void checkCollision(Squirrel mySquirrel){
		for (RoadRoller r: roadRollers)
			r.collide(mySquirrel);

		for (Mustache m : mustaches)
			m.collide(mySquirrel);
	}




	/*
		GETTER SETTER

	 */
	public RoadRoller[] getRoadRollers() {
		return roadRollers;
	}

	public Mustache[] getMustaches() {
		return mustaches;
	}
}
