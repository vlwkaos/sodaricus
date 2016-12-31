package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.lumibottle.gameobjects.Bullets.BlockEnemyBullet;
import com.lumibottle.gameobjects.Bullets.Bullet;
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
import com.lumibottle.gameobjects.enemies.WaveHead;
import com.lumibottle.gameobjects.enemies.bosses.BomberBoss;
import com.lumibottle.gameobjects.enemies.bosses.BoxBoss;
import com.lumibottle.gameobjects.enemies.bosses.FirePropulsion;
import com.lumibottle.gameobjects.enemies.bosses.PangBoss;
import com.lumibottle.gameobjects.enemies.bosses.PipeBoss;
import com.lumibottle.gameobjects.enemies.bosses.TimeBomb;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.screen.GameScreen;

/**
 * This class handles the presentation of enemies as the run time passes on.
 */
public class ProgressHandler {

    private boolean pause;

    private int numOfEnemies;
    private float runTime;

    private int stageNumber; //TODO dead flag for bossesw

    private Squirrel mySquirrel;

    //
    private RoadRoller[] roadRollers;
    private Bomb[] bombs;
    private Mustache[] mustaches;
    private LaserCrayon[] laserCrayons;
    private Cowboy[] cowboys;
    private Blackhole[] blackholes;
    private Knife[] knives;
    private Boomerang[] boomerangs;
    private WaveHead[] waveheads;

    private BoxBoss boxBoss;
    private int[] blockspace;
    private int blockspaceCnt;
    private PipeBoss pipeBoss;
    private PangBoss[] pangBosses;
    private BomberBoss bomberBoss;
    private TimeBomb[] timebombs;
    private FirePropulsion[] firePropulsions;

    //Bullet
    private EnemyBullet[] enemyBullets;


    private float hatspeed = 100.0f;

    public ProgressHandler(Squirrel s) {
        stageNumber = 0;
        mySquirrel = s;
        blockspace = new int[5];

        initialize();
    }


    public void update(float delta) {
        runTime += delta;

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
//        updateBoomerangs(delta);
//        updateWaveHeads(delta);
        /*
            Boss Updates
         */
//        updateBoxBoss(delta);
//		updatePipeBoss(delta);
//        updatePangBoss(delta);
        updateBomberBoss(delta);
        updateTimebombs(delta);
        updateFirePropulsions(delta);
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

    private void updateWaveHeads(float delta) {
        for (WaveHead a : waveheads) {
            a.update(delta);
            if (a.isDEAD())
                a.reset(255);
        }
    }

    private void updateBoomerangs(float delta) {
        for (Boomerang a : boomerangs) {
            a.update(delta);
            if (a.isDEAD())
                a.reset(255);
        }
    }

    private void updateKnives(float delta) {
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

        for (Knife a : knives)
            a.collide(mySquirrel);

        for (Boomerang a : boomerangs)
            a.collide(mySquirrel);

        for (Cowboy a : cowboys)
            a.collide(mySquirrel);

        for (WaveHead a : waveheads)
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

        for (TimeBomb a : timebombs)
            a.collide(mySquirrel);
        //nothing for bomber boss, cause you don't want it to get hit by bottles

        for (FirePropulsion a : firePropulsions)
            a.collide(mySquirrel, bomberBoss);
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

                reset when gameover?
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

    private void updateBomberBoss(float delta) {
        bomberBoss.update(delta);
        if (bomberBoss.getShoot()) {
            for (TimeBomb a : timebombs)
                if (a.isDEAD()) {
                    a.reset(bomberBoss.getX(), bomberBoss.getY());
                    break;
                }
            bomberBoss.setShootDone();
        }

    }

    private void updateTimebombs(float delta) {
        for (TimeBomb a : timebombs) {
            a.update(delta);
            if (a.isEXPLODEstate()) {
                int firecnt = 0;
                for (FirePropulsion b : firePropulsions) {
                    if (firecnt < 4) {
                        if (b.isDEAD()) {
                            b.reset(a.getX(), a.getY(), a.getTheta() + firecnt * 90);
                            firecnt++;
                        }
                    } else break;
                }
                //spawn firepropulsion
                a.dead();
            }
        }
    }

    private void updateFirePropulsions(float delta) {
        for (FirePropulsion a : firePropulsions) {
            a.update(delta);
        }
    }

    //TODO restart, initialize
    //called at first
    private void initialize() {
        /*
			Initialize enemy objects
		 */
        roadRollers = new RoadRoller[4];
        for (int i = 0; i < roadRollers.length; i++)
            roadRollers[i] = new RoadRoller();


        bombs = new Bomb[4];
        for (int i = 0; i < bombs.length; i++)
            bombs[i] = new Bomb();


        mustaches = new Mustache[5];
        for (int i = 0; i < mustaches.length; i++)
            mustaches[i] = new Mustache();


        laserCrayons = new LaserCrayon[5];
        for (int i = 0; i < laserCrayons.length; i++)
            laserCrayons[i] = new LaserCrayon();


        cowboys = new Cowboy[3];
        for (int i = 0; i < cowboys.length; i++)
            cowboys[i] = new Cowboy();


        knives = new Knife[1];
        for (int i = 0; i < knives.length; i++)
            knives[i] = new Knife(mySquirrel);


        boomerangs = new Boomerang[1];
        for (int i = 0; i < boomerangs.length; i++)
            boomerangs[i] = new Boomerang();


        waveheads = new WaveHead[6];
        for (int i = 0; i < waveheads.length; i++)
            waveheads[i] = new WaveHead();


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
        for (int i = 0; i < blackholes.length; i++)
            blackholes[i] = new Blackhole();


        // BOSS
        boxBoss = new BoxBoss(mySquirrel);

        pipeBoss = new PipeBoss();

        pangBosses = new PangBoss[8]; // cyclic array가 아니라서 한칸 여분이 필요..
        for (int i = 0; i < pangBosses.length; i++)
            pangBosses[i] = new PangBoss();

        //pangBosses[0].reset(240, GameScreen.gameHeight / 2f - pangBosses[0].getHeight() / 2f, 1);

        bomberBoss = new BomberBoss();

        timebombs = new TimeBomb[5];
        for (int i = 0; i < timebombs.length; i++)
            timebombs[i] = new TimeBomb();


        firePropulsions = new FirePropulsion[20];
        for (int i = 0; i < firePropulsions.length; i++)
            firePropulsions[i] = new FirePropulsion();
    }

    //kill all on gameover
    private void killAll() {
        for (Bullet a : mySquirrel.getBullets())
            if (!a.isDEAD())
                a.dead();

        for (FX f : FXHelper.getInstance().getMyFXs())
            if (f.isTOBEDRAWN())
                f.remove();

        // enemy
        for (RoadRoller a : roadRollers)
            if (!a.isDEAD())
                a.dead();

        for (Bomb a : bombs)
            if (!a.isDEAD())
                a.dead();

        for (Mustache a : mustaches)
            if (!a.isDEAD())
                a.dead();

        for (LaserCrayon a : laserCrayons)
            if (!a.isDEAD())
                a.dead();

        for (Knife a : knives)
            if (!a.isDEAD())
                a.dead();

        for (Boomerang a : boomerangs)
            if (!a.isDEAD())
                a.dead();

        for (Cowboy a : cowboys)
            if (!a.isDEAD())
                a.dead();

        for (WaveHead a : waveheads)
            if (!a.isDEAD())
                a.dead();

        for (EnemyBullet a : enemyBullets)
            if (a != null)
                if (!a.isDEAD())
                    a.dead();

        for (Blackhole a : blackholes)
            if (!a.isDEAD())
                a.dead();

        if (!boxBoss.isDEAD())
            boxBoss.dead();

        if (!pipeBoss.isDEAD())
            pipeBoss.dead();

        for (PangBoss a : pangBosses)
            if (!a.isDEAD())
                a.dead();

        if (!bomberBoss.isDEAD())
            bomberBoss.dead();

        for (TimeBomb a : timebombs)
            if (!a.isDEAD())
                a.dead();

        for (FirePropulsion a : firePropulsions)
            if (!a.isDEAD())
                a.dead();
    }

    public void restart() {
        killAll();

        pause = false;
        runTime = 0.0f;

        blockspaceCnt = 0;
        for (int i = 0; i < blockspace.length; i++)
            blockspace[i] = 0;

        mySquirrel.resetLife();
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

    public BoxBoss getBoxboss() {
        return boxBoss;
    }

    public PipeBoss getPipeBoss() {
        return pipeBoss;
    }

    public PangBoss[] getPangBosses() {
        return pangBosses;
    }

    public BomberBoss getBomberBoss() {
        return bomberBoss;
    }

    public Knife[] getKnives() {
        return knives;
    }

    public Boomerang[] getBoomerangs() {
        return boomerangs;
    }

    public WaveHead[] getWaveheads() {
        return waveheads;
    }

    public TimeBomb[] getTimebombs() {
        return timebombs;
    }

    public FirePropulsion[] getFirePropulsions() {
        return firePropulsions;
    }

    public boolean getPause() {
        return pause;
    }
}
