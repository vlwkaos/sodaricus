package com.lumibottle.gameobjects;

import com.badlogic.gdx.math.MathUtils;
import com.lumibottle.gameobjects.enemies.Blackhole;
import com.lumibottle.gameobjects.enemies.Bomb;
import com.lumibottle.gameobjects.enemies.Cowboy;
import com.lumibottle.gameobjects.enemies.EnemyBullet;
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
	private Cowboy[] cowboys;
	private EnemyBullet[] enemyBullets;
	private Blackhole[] blackholes;


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


		cowboys = new Cowboy[3];
		for (int i = 0; i< cowboys.length; i++){
			cowboys[i] = new Cowboy();
			cowboys[i].reset(250);

		}

		enemyBullets = new EnemyBullet[10];
		for (int i = 0; i< enemyBullets.length; i++){
			enemyBullets[i] = new EnemyBullet();
		}

		blackholes = new Blackhole[3];
		for (int i=0; i< blackholes.length; i++){
			blackholes[i] = new Blackhole();
			blackholes[i].reset(240+i*20);
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
		updateMustaches(delta);
//		updateLaserCrayons(delta);
//		updateCowboy(delta);
//		updateEnemyBullets(delta);
//		updateBlackholes(delta);
	}


	/*
			Organize
	    //TODO cowboy는 블럭 단위로?
	   */// how to spawn?

	private void updateRoadRollers(float delta) {
		for (RoadRoller r : roadRollers) {
			r.update(delta);
			if (r.isDEAD())
				r.reset(250);
		}
	}
	private void updateBombs(float delta) {
		for (Bomb b : bombs) {
			b.update(delta);
			if (b.isDEAD())
				b.reset(250);
		}
	}

		private void updateMustaches(float delta){
		for (Mustache m : mustaches) {
			m.update(delta);
			if (m.isDEAD())
				m.reset(250);
		}

	}

	private void updateLaserCrayons(float delta) {
		for (LaserCrayon l : laserCrayons) {
			l.update(delta);

			if (l.isDEAD())
				l.reset(250);
		}
	}


	private void updateCowboy(float delta){
		for (Cowboy c : cowboys){
			c.update(delta);
			if(c.isShooting())
				for (EnemyBullet h: enemyBullets)
					if (h.isDEAD()) {
						float theta= MathUtils.atan2((c.getY()+23)-mySquirrel.getY(),c.getX()-mySquirrel.getX());
						float dx = -hatspeed* MathUtils.cos(theta);
						float dy = -hatspeed* MathUtils.sin(theta);
						h.reset(c.getX()-6,c.getY()+23,dx,dy,0,0);//sprite type
						c.doneShooting();
						break;
					}

			if (c.isDEAD())
				c.reset(250);
		}
	}

	private void updateEnemyBullets(float delta){
		for (EnemyBullet c: enemyBullets)
			c.update(delta);


	}

	private void updateBlackholes(float delta){
		for (Blackhole b: blackholes) {
			b.update(delta);
			if (b.isDEAD())
				b.reset(250);
		}
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

		for (Cowboy c : cowboys)
			c.collide(mySquirrel);

		for (EnemyBullet c : enemyBullets)
			c.collide(mySquirrel);

		for (Blackhole b : blackholes)
			b.collide(mySquirrel);
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

	public EnemyBullet[] getEnemyBullets() {
		return enemyBullets;
	}

	public Cowboy[] getCowboys() {
		return cowboys;
	}

	public Blackhole[] getBlackholes() {
		return blackholes;
	}
}
