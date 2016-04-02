package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.lumibottle.gameobjects.enemies.Bomb;
import com.lumibottle.gameobjects.enemies.CowboyHat;
import com.lumibottle.gameobjects.enemies.CowboySausage;
import com.lumibottle.gameobjects.enemies.LaserCrayon;
import com.lumibottle.gameobjects.enemies.Mustache;
import com.lumibottle.gameobjects.enemies.RoadRoller;

/**
 * This class handles the presentation of enemies as the run time passes on.
 */
public class ProgressHandler {

    private int numOfEnemies;
    private float runTime;

	private int stageNumber;

	private Squirrel mySquirrel;

	private FX[] myFXs;
    //
	private RoadRoller[] roadRollers;
	private Bomb[] bombs;
	private Mustache[] mustaches;
	private LaserCrayon[] laserCrayons;
	private CowboySausage[] cowboySausages;
	private CowboyHat[] cowboyHats;
	private float hatspeed = 100f;
	private boolean squirrelHit;

	public ProgressHandler(Squirrel s) {
		stageNumber=0;

		mySquirrel = s;
		/*
			Initialize enemy objects
		 */
		roadRollers = new RoadRoller[4];
		for (int i = 0; i < roadRollers.length; i++) {
			roadRollers[i] = new RoadRoller();
			roadRollers[i].reset(240 + i * 50); //later, with runtime.
		}

		bombs = new Bomb[4];
		for (int i = 0; i < bombs.length; i++) {
			bombs[i] = new Bomb();
			bombs[i].reset(240 + i * 50); //later, with runtime.
		}


		mustaches = new Mustache[5];
		for (int i = 0; i < mustaches.length; i++) {
			mustaches[i] = new Mustache();
			mustaches[i].reset(240 + i * 20); //later, with runtime.
		}

		laserCrayons = new LaserCrayon[5];
		for (int i=0;i<laserCrayons.length;i++){
			laserCrayons[i] = new LaserCrayon();
			laserCrayons[i].reset(240 + i * 20);
		}


		cowboySausages = new CowboySausage[5];
		for (int i=0; i< cowboySausages.length;i++){
			cowboySausages[i] = new CowboySausage();
			cowboySausages[i].reset(250);

		}

		cowboyHats = new CowboyHat[10];
		for (int i=0;i<cowboyHats.length;i++){
			cowboyHats[i] = new CowboyHat();
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
//		updateRoadRollers(delta);
//		updateBombs(delta);
//		updateMustaches(delta);
//		updateLaserCrayons(delta);
		updateCowboySausages(delta);
		updateCowboyHats(delta);

	}


	/*
		Organize
	 *///TODO how to spawn?
	private void updateRoadRollers(float delta) {
		for (RoadRoller r : roadRollers) {
			r.update(delta);
			if (r.isREADY())
				r.reset(250);
		}
	}
	private void updateBombs(float delta) {
		for (Bomb b : bombs) {
			b.update(delta);
			if (b.isREADY())
				b.reset(250);
		}
	}

		private void updateMustaches(float delta){
		for (Mustache m : mustaches) {
			m.update(delta);
			if (m.isREADY())
				m.reset(250);
		}

	}

	private void updateLaserCrayons(float delta) {
		for (LaserCrayon l : laserCrayons) {
			l.update(delta);

			if (l.isREADY())
				l.reset(250);
		}
	}


	private void updateCowboySausages(float delta){
		for (CowboySausage c : cowboySausages){
			c.update(delta);
			if(c.isShooting())
				for (CowboyHat h: cowboyHats)
					if (h.isREADY()) {
						float theta= MathUtils.atan2(c.getY()-mySquirrel.getY(),c.getX()-mySquirrel.getX());
						float dx = -hatspeed* MathUtils.cos(theta);
						float dy = -hatspeed* MathUtils.sin(theta);
						h.reset(c.getX(),c.getY(),dx,dy,0);
						c.doneShooting();
						break;
					}

			if (c.isREADY())
				c.reset(250);
		}
	}

	private void updateCowboyHats(float delta){
		for (CowboyHat c: cowboyHats)
			c.update(delta);
	}


	public void checkCollision(){
		for (RoadRoller r: roadRollers)
			r.collide(mySquirrel);

		for (Bomb b: bombs)
			b.collide(mySquirrel);

		for (Mustache m : mustaches)
			m.collide(mySquirrel);

		for (LaserCrayon l : laserCrayons)
			l.collide(mySquirrel);

		for (CowboySausage c : cowboySausages)
			c.collide(mySquirrel);

		for (CowboyHat c :cowboyHats)
			c.collide(mySquirrel);
	}




	/*
		GETTER SETTER

	 */
	public RoadRoller[] getRoadRollers() {
		return roadRollers;
	}

	public Bomb[] getBombs() {
		return bombs;
	}

	public Mustache[] getMustaches() {
		return mustaches;
	}
	public LaserCrayon[] getLaserCrayons() {
		return laserCrayons;
	}

	public CowboyHat[] getCowboyHats() {
		return cowboyHats;
	}

	public CowboySausage[] getCowboySausages() {
		return cowboySausages;
	}
}
