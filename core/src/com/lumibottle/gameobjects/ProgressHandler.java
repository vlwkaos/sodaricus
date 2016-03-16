package com.lumibottle.gameobjects;

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
		FXs = new FX[5];
		for (int i=0; i<FXs.length;i++)
			FXs[i] = new FX();


		roadRollers = new RoadRoller[3];
		for (int i = 0; i < roadRollers.length; i++) {
			roadRollers[i] = new RoadRoller();
			roadRollers[i].reset(240 + i * 50); //later, with runtime.
		}


	}

	public void update(float delta) {
		runTime += delta;

		for (FX f: FXs)
		f.update(delta);


		updateRoadRollers(delta);





	}


	/*
		Organize
	 */
	private void updateRoadRollers(float delta){
		for (RoadRoller r : roadRollers) {
			if (r.isVISIBLE()) {
				r.update(delta);
				r.collide(mySquirrel);//
				if (r.isEffectReady())
					for (FX f : FXs)
						if (f.isREADY()) {// FXs ready is different, it means it is ready to be set to something else.
						//	f.reset(r.getX(), r.getY(), (short) 0);
						r.ready();
						}

			} else if (r.isREADY()) {
				r.reset(250);
			}
		}
	}




	/*
		GETTER SETTER

	 */
	public RoadRoller[] getRoadRollers() {
		return roadRollers;
	}

	public FX[] getFXs() {
		return FXs;
	}




}
