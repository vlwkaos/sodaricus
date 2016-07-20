package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.lumibottle.gameobjects.Bullets.BlockEnemyBullet;
import com.lumibottle.gameobjects.Bullets.EnemyBullet;
import com.lumibottle.gameobjects.Bullets.HatEnemyBullet;
import com.lumibottle.gameobjects.enemies.Blackhole;
import com.lumibottle.gameobjects.enemies.Bomb;
import com.lumibottle.gameobjects.enemies.Cowboy;
import com.lumibottle.gameobjects.enemies.LaserCrayon;
import com.lumibottle.gameobjects.enemies.Mustache;
import com.lumibottle.gameobjects.enemies.RoadRoller;
import com.lumibottle.gameobjects.enemies.bosses.BoxBoss;
import com.lumibottle.gameobjects.enemies.bosses.PipeBoss;

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
	private Blackhole[] blackholes;

    private BoxBoss boxBoss;
    private int[] blockspace;
    private int blockspaceCnt;
    private PipeBoss pipeBoss;

    //Bullet
    private EnemyBullet[] enemyBullets;


	private float hatspeed = 100f;

	public ProgressHandler(Squirrel s) {
		stageNumber=0;
		mySquirrel = s;
        blockspace = new int[5];
        blockspaceCnt = 0;
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
			bombs[i].reset(250 + i * 50); //later, with runtime.
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

        Gdx.app.log("ProgressHandler","Trying to create bullets");
        int j=0;
		enemyBullets = new EnemyBullet[40];
        for (int i=0;i<10;i++){
            enemyBullets[j] = new HatEnemyBullet();
            j++;
        }
        for (int i=0;i<15;i++){
            enemyBullets[j] = new BlockEnemyBullet(blockspace);
            j++;
        }
        Gdx.app.log("ProgressHandler","Bullets created");

		blackholes = new Blackhole[3];
		for (int i=0; i< blackholes.length; i++){
			blackholes[i] = new Blackhole();
			blackholes[i].reset(240+i*20);
		}


        // BOSS
        boxBoss = new BoxBoss(mySquirrel);
        boxBoss.reset();

        pipeBoss = new PipeBoss();
        pipeBoss.reset();


        myFXs = new FX[10];
		for (int i=0; i< myFXs.length;i++)
			myFXs[i]= new FX();


	}



	public void update(float delta) {
		runTime += delta;

		for (FX f:myFXs)
				f.update(delta);
		/*
			Enemy Updates
		 */
//		updateRoadRollers(delta);
//		updateBombs(delta);
//		updateMustaches(delta);
//		updateLaserCrayons(delta);
//		updateCowboy(delta);
//		updateBlackholes(delta);

        /*
            Boss Updates
         */
//        updateBoxBoss(delta);
		updatePipeBoss(delta);
        updateEnemyBullets(delta);
	}


	/*
			Organize
	    //TODO how to spawn?
	*/

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
					if (h instanceof HatEnemyBullet && h.isDEAD()) { // find available bullet from pool
						float theta= MathUtils.atan2((c.getY()+23)-mySquirrel.getY(),c.getX()-mySquirrel.getX());
						h.reset(c.getX()-6,c.getY()+23,hatspeed,theta);//sprite type
						c.doneShooting();
						break;
					}

			if (c.isDEAD())
				c.reset(250);
		}
	}

	private void updateEnemyBullets(float delta){
		for (EnemyBullet c: enemyBullets)
            if (c != null)
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
            if (c!=null)
		    	c.collide(mySquirrel);

		for (Blackhole b : blackholes)
			b.collide(mySquirrel);

        boxBoss.collide(mySquirrel);
        pipeBoss.collide(mySquirrel);
	}


    /*
        BOSS UPDATES

     */
    public void updateBoxBoss(float delta){

        blockspaceCnt=0;
        for (int i: blockspace)
            if (i>0)
                blockspaceCnt++;
        if (blockspaceCnt==5){
            boxBoss.setVul();
            //총알도 삭제
            for (EnemyBullet h: enemyBullets)
                if (h instanceof BlockEnemyBullet)
                    h.dead();
            //space reset
            for (int i=0;i<blockspace.length;i++)
                blockspace[i] = 0;
            /*
                   It is reset here, or gameover
                   boxboss can only die when vulnerable -> blockspace is already reset, so do not need to reset again.
             */
        }

        boxBoss.update(delta);
        if (boxBoss.isSHOOT()){
            for (EnemyBullet h: enemyBullets)
                if (h instanceof BlockEnemyBullet && h.isDEAD()) { // find available bullet from pool
                    h.reset(boxBoss.getX(),boxBoss.getY(),50,0);//sprite type0
                    boxBoss.doneShooting();
                    break;
                }
        }
    }
    public void updatePipeBoss(float delta){
        pipeBoss.update(delta);
    }


    //TODO restart, initialize

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

    public BoxBoss getBoxboss() {return boxBoss;}

    public PipeBoss getPipeBoss(){return pipeBoss;}
}
