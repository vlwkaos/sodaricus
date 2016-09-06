package com.lumibottle.gameobjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.lumibottle.gameobjects.Bullets.BlockEnemyBullet;
import com.lumibottle.gameobjects.Bullets.EnemyBullet;
import com.lumibottle.gameobjects.Bullets.HatEnemyBullet;
import com.lumibottle.gameobjects.Bullets.PipeEnemyBullet;
import com.lumibottle.gameobjects.enemies.Blackhole;
import com.lumibottle.gameobjects.enemies.Bomb;
import com.lumibottle.gameobjects.enemies.Boomerang;
import com.lumibottle.gameobjects.enemies.Cowboy;
import com.lumibottle.gameobjects.enemies.Knife;
import com.lumibottle.gameobjects.enemies.LaserCrayon;
import com.lumibottle.gameobjects.enemies.Mustache;
import com.lumibottle.gameobjects.enemies.RoadRoller;
import com.lumibottle.gameobjects.enemies.bosses.BoxBoss;
import com.lumibottle.gameobjects.enemies.bosses.PangBoss;
import com.lumibottle.gameobjects.enemies.bosses.PipeBoss;
import com.lumibottle.screen.GameScreen;

/**
 * This class handles the presentation of enemies as the run time passes on.
 */
public class ProgressHandler {

    private int numOfEnemies;
    private float runTime;

    private int stageNumber; //TODO dead flag for bossesw

    private Squirrel mySquirrel;

    private FX[] myFXs;
    //
    private RoadRoller[] roadRollers;
    private Bomb[] bombs;
    private Mustache[] mustaches;
    private LaserCrayon[] laserCrayons;
    private Cowboy[] cowboys;
    private Blackhole[] blackholes;
    private Knife[] knives;
    private Boomerang[] boomerangs;

    private BoxBoss boxBoss;
    private int[] blockspace;
    private int blockspaceCnt;
    private PipeBoss pipeBoss;
    private PangBoss[] pangBosses;

    //Bullet
    private EnemyBullet[] enemyBullets;


    private float hatspeed = 100f;

    public ProgressHandler(Squirrel s) {
        stageNumber = 0;
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
        for (int i = 0; i < laserCrayons.length; i++) {
            laserCrayons[i] = new LaserCrayon();
            laserCrayons[i].reset(240 + i * 20);
        }


        cowboys = new Cowboy[3];
        for (int i = 0; i < cowboys.length; i++) {
            cowboys[i] = new Cowboy();
            cowboys[i].reset(250);

        }

        knives = new Knife[1];
        for (int i=0; i<knives.length;i++){
            knives[i] = new Knife(mySquirrel);
            knives[i].reset(255);
        }

        boomerangs = new Boomerang[1];
        for (int i=0; i<boomerangs.length;i++){
            boomerangs[i] = new Boomerang();
            boomerangs[i].reset(255);
        }

        Gdx.app.log("ProgressHandler", "Trying to create bullets");
        int j = 0;
        enemyBullets = new EnemyBullet[40];
        for (int i = 0; i < 10; i++) {
            enemyBullets[j] = new HatEnemyBullet();
            j++;
        }
        for (int i = 0; i < 15; i++) {
            enemyBullets[j] = new BlockEnemyBullet(blockspace);
            j++;
        }
        for (int i = 0; i < 10; i++) {
            enemyBullets[j] = new PipeEnemyBullet();
            j++;
        }


        Gdx.app.log("ProgressHandler", "Bullets created");

        blackholes = new Blackhole[3];
        for (int i = 0; i < blackholes.length; i++) {
            blackholes[i] = new Blackhole();
            blackholes[i].reset(240 + i * 20);
        }


        // BOSS
        boxBoss = new BoxBoss(mySquirrel);
        boxBoss.reset();

        pipeBoss = new PipeBoss();
        pipeBoss.reset();

        pangBosses = new PangBoss[8]; // cyclic array가 아니라서 한칸 여분이 필요..
        for (int i = 0; i < pangBosses.length; i++) {
            pangBosses[i] = new PangBoss();
        }
        pangBosses[0].reset(240, GameScreen.gameHeight / 2f - pangBosses[0].getHeight() / 2f, 1);


        myFXs = new FX[10];
        for (int i = 0; i < myFXs.length; i++)
            myFXs[i] = new FX();


    }


    public void update(float delta) {
        runTime += delta;

        for (FX f : myFXs)
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
//        updateKnives(delta);
        updateBoomerangs(delta);

        /*
            Boss Updates
         */
//        updateBoxBoss(delta);
//		updatePipeBoss(delta);
//        updatePangBoss(delta);
//        updateEnemyBullets(delta);
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

    private void updateMustaches(float delta) {
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


    private void updateCowboy(float delta) {
        for (Cowboy c : cowboys) {
            c.update(delta);
            if (c.isShooting())
                for (EnemyBullet h : enemyBullets)
                    if (h instanceof HatEnemyBullet && h.isDEAD()) { // find available bullet from pool
                        float theta = MathUtils.atan2((c.getY() + 23) - mySquirrel.getY(), c.getX() - mySquirrel.getX());
                        h.reset(c.getX() - 6, c.getY() + 23, hatspeed, theta);//sprite type
                        c.doneShooting();
                        break;
                    }

            if (c.isDEAD())
                c.reset(250);
        }
    }

    private void updateEnemyBullets(float delta) {
        for (EnemyBullet c : enemyBullets)
            if (c != null)
                c.update(delta);


    }

    private void updateBlackholes(float delta) {
        for (Blackhole b : blackholes) {
            b.update(delta);
            if (b.isDEAD())
                b.reset(250);
        }
    }

    private void updateBoomerangs(float delta){
        for (Boomerang a : boomerangs){
            a.update(delta);

        }
    }

    private void updateKnives(float delta){
        for (Knife a : knives) {
            a.update(delta);
            if (a.isDEAD())
                a.reset(255);
        }
    }


    public void checkCollision() {
        for (RoadRoller a : roadRollers)
            a.collide(mySquirrel);

        for (Bomb a : bombs)
            a.collide(mySquirrel);

        for (Mustache a : mustaches)
            a.collide(mySquirrel);

        for (LaserCrayon a : laserCrayons)
            a.collide(mySquirrel);

        for (Knife a: knives)
            a.collide(mySquirrel);

        for (Cowboy a : cowboys)
            a.collide(mySquirrel);

        for (EnemyBullet a : enemyBullets)
            if (a != null)
                a.collide(mySquirrel);

        for (Blackhole a : blackholes)
            a.collide(mySquirrel);

        boxBoss.collide(mySquirrel);
        pipeBoss.collide(mySquirrel);

        for (PangBoss a : pangBosses)
            a.collide(mySquirrel);
    }

    /*
        BOSS UPDATES

     */
    private void updateBoxBoss(float delta) {

        blockspaceCnt = 0;
        for (int i : blockspace)
            if (i > 0)
                blockspaceCnt++;
        if (blockspaceCnt == 5) {
            boxBoss.setVul();
            //총알도 삭제
            for (EnemyBullet h : enemyBullets)
                if (h instanceof BlockEnemyBullet)
                    h.dead();
            //space reset
            for (int i = 0; i < blockspace.length; i++)
                blockspace[i] = 0;
            /*
                   It is reset here, or gameover
                   boxboss can only die when vulnerable -> blockspace is already reset, so do not need to reset again.
             */
        }

        boxBoss.update(delta);
        if (boxBoss.isSHOOT()) {
            for (EnemyBullet h : enemyBullets)
                if (h instanceof BlockEnemyBullet && h.isDEAD()) { // find available bullet from pool
                    h.reset(boxBoss.getX(), boxBoss.getY(), 50, 0);//sprite type0
                    boxBoss.doneShooting();
                    break;
                }
        }
    }

    private void updatePipeBoss(float delta) {
        pipeBoss.update(delta);
        if (pipeBoss.isSHOOT()) {
            float ypos = pipeBoss.getY();
            boolean bulletcount = false;
            for (EnemyBullet h : enemyBullets)
                if (h instanceof PipeEnemyBullet && h.isDEAD()) {
                    if (bulletcount) {
                        ((PipeEnemyBullet) h).reset(ypos, true);
                        pipeBoss.doneShooting();
                        break;
                    } else {
                        ((PipeEnemyBullet) h).reset(ypos, false);
                        bulletcount = true;//둘중 먼놈이 ..?
                    }
                }

        }
    }

    private void updatePangBoss(float delta) {
        for (PangBoss a : pangBosses) {
            a.update(delta);

            if (a.isBreeding() && a.getGeneration() < a.max_gen) {
                boolean breed = false;
                for (PangBoss b : pangBosses)
                    if (!b.equals(a)) // 자신은 제외
                        if (breed) {
                            if (b.isDEAD()) {
                                b.reset(a.getPrevX() + a.getWidth() / 2f, a.getPrevY() + a.getHeight() / 2f, a.getGeneration() + 1);
                                a.doneBreeding();
                                break;
                            }
                        } else {
                            if (b.isDEAD()) {
                                b.reset(a.getPrevX() + a.getWidth() / 2f, a.getPrevY() + a.getHeight() / 2f, a.getGeneration() + 1);
                                breed = true;
                            }
                        }


            }
        }
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

    public BoxBoss getBoxboss() {
        return boxBoss;
    }

    public PipeBoss getPipeBoss() {
        return pipeBoss;
    }

    public PangBoss[] getPangBosses() {
        return pangBosses;
    }

    public Knife[] getKnives() {
        return knives;
    }

    public Boomerang[] getBoomerangs() {
        return boomerangs;
    }
}
