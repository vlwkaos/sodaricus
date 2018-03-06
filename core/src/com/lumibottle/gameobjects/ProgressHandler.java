package com.lumibottle.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.lumibottle.game.GameMain;
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
import com.lumibottle.helper.ScoreHelper;
import com.lumibottle.helper.SoundManager;
import com.lumibottle.screen.GameScreen;

/**
 * This class handles the presentation of enemies as the run time passes on.
 */
public class ProgressHandler {

    private boolean pause;

    //LEVEL VARIABLES
    private float bossApproachingTimer; // for whole session time
    private int enemyCount; // number of enemies at a given time 5?
    private int numEnemyTypes; // number of enemy types that can be chosen from. 0~4
    private float spawnTimer; // spawn enemy every x seconds. add score for each spawn

    private int hazardCount;
    private float hazardFrequency;
    private int numHazardTypes;
    private float hazardTimer;

    private float bossFrequency;
    private int bossNum;
    private int bossStage;

    private int itemFrequency;
    private float itemTimer;

    private int progress; // spawn boss every x seconds.
    //TODO hazard?

    private Squirrel mySquirrel;

    private Item[] items;

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


    final private float hatspeed = 100.0f;

    public ProgressHandler(Squirrel s) {
        mySquirrel = s;
        blockspace = new int[5];

        initialize();
    }


    public void update(float delta) {


        /*
            test

        numHazardTypes = 1;
        hazardCount = 1;
  */
        //spawnItem(0);
        //보스전이 아니면
        updateEnemies(delta);

        if (noBossAlive()) {
            if (bossStage != -1) { // after a boss fight
                itemFrequency+=1000;
                GameMain.playServices.unlockAchievement(bossStage);
                bossStage = -1;
                ScoreHelper.getInstance().incrementScore(1000);
                spawnItem(0); // spawn life
            }

            if (!mySquirrel.isSPAWNING()) {
                spawnTimer += delta;
                hazardTimer += delta;
                bossFrequency += delta;
                itemTimer += delta;
            }

            spawnEnemy();
            spawnHazard();
            spawnBoss();
            spawnItem();
        } else {
            if (bossApproachingTimer <2.1f)
                bossApproachingTimer+=delta;
        }


    }

    /*************************************************
     * Enemy Spawning
     * progress = 2 sec
     ***********************************************/
    private void progressCheck(){
        boolean changed = false;

        if (progress == 5) { // 10
            changed = true;
            numEnemyTypes++; // 1
        }else if (progress == 10) {
            changed = true;
            numEnemyTypes++; // 2
        }else if (progress == 15) {
            changed = true;
            numEnemyTypes++; // 3
        }else if (progress == 20) {
            changed = true;
            hazardFrequency-=0.5f;// 5.5 -> 5.0
        } else if (progress == 25){
            changed = true;
            numEnemyTypes++; // 4 max
        } else if (progress == 30){ // 60 보스 두마리 째?
            hazardFrequency-=0.5f; // 5.0 -> 4.5
            changed = true;
        } else if (progress == 35){
            changed = true;
            hazardFrequency-=0.5f;// 4.5 -> 4.0
        } else if (progress == 40){
            changed = true;
            hazardFrequency-=0.5f;// 4.0 -> 3.5
        } else if (progress == 45){
            changed = true;
            hazardFrequency-=0.5f;// 3.5 -> 3.0
        } else if (progress == 50){
            changed = true;
            hazardFrequency-=0.5f;// 3.0 -> 2.5
        } else if (progress == 55){
            changed = true;
            hazardFrequency-=0.1f;// 2.5 -> 2.4
        } else if (progress == 60){
            changed = true;
            hazardFrequency-=0.1f;// 2.4 -> 2.3
        } else if (progress == 65){
            changed = true;
            hazardFrequency-=0.1f;// 2.3 -> 2.2
        }



        if (changed)
            SoundManager.getInstance().play(SoundManager.POWUP);

    }


    private void spawnEnemy() {
        //stage
        float freq = 2.5f;
        enemyCount = 3;
        if (mySquirrel.isRamseyThunder()) {
            freq = 1.25f;
            enemyCount = 5;
        }
        if (spawnTimer > freq) {
            ScoreHelper.getInstance().incrementScore(10);
            for (int i = 0; i < enemyCount; i++) {
                int type = MathUtils.random(numEnemyTypes);
                switch (type) {
                    case 0:
                        spawnRoadRoller();
                        break;
                    case 1:
                        spawnBomb();
                        break;
                    case 2:
                        spawnWaveHead();
                        break;
                    case 3:
                        spawnMustaches();
                        break;
                    case 4:
                        spawnCowboy();
                        break;
                }
            }
            spawnTimer = 0.0f;
            progress++;
            progressCheck();
        }
    }

    private void spawnHazard() {
        if (numHazardTypes == 0)
            return;

        if (hazardTimer > MathUtils.random(hazardFrequency-0.4f, hazardFrequency)) {
            ScoreHelper.getInstance().incrementScore(50);
            for (int i = 0; i < hazardCount; i++) {
                int type = MathUtils.random(numHazardTypes);
                switch (type) {
                    case 1:
                        spawnKnife();
                        break;
                    case 2:
                        spawnBoomerang();
                        break;
                    case 3:
                        spawnLaserCrayon();
                        break;
                    case 4:
                        spawnBlackhole();
                        break;
                }
            }

            hazardTimer = 0.0f;
        }//endif
    }

    private void spawnBoss(){

        if (bossFrequency > 35.0f){
            int type = MathUtils.random(bossNum);
            switch (type){
                case 0: spawnPipeBoss(); break;
                case 1: spawnBoxboss(); break;
                case 2: spawnPangBoss(); break;
                case 3: spawnBomberBoss(); break;
            }
            bossFrequency = 0.0f;
            bossApproachingTimer = 0.0f;
            bossStage = type;
        }

    }

    //every 500
    private void spawnItem(){
        if (ScoreHelper.getInstance().getIntScore() > itemFrequency){
            itemFrequency+=MathUtils.random(800,950);
            spawnItem(1);
        }
        if (itemTimer > 22.0f){
            itemFrequency+=500;
            spawnItem(1);
            itemTimer = 0.0f;
        }
    }

    private boolean noBossAlive() {
        boolean pangisDEAD = true;
        for (PangBoss a : pangBosses)
            if (!a.isDEAD()) {
                pangisDEAD = false;
                break;
            }
        return pipeBoss.isDEAD() && boxBoss.isDEAD() && bomberBoss.isDEAD() && pangisDEAD;
    }


    private void updateEnemies(float delta) {
        /*
            Enemy Updates
		 */
        updateRoadRollers(delta);
        updateBombs(delta);
        updateMustaches(delta);
        updateCowboy(delta);
        updateWaveHeads(delta);

        updateLaserCrayons(delta);
        updateBlackholes(delta);
        updateKnives(delta);
        updateBoomerangs(delta);

        /*
            Item update
         */
        updateItem(delta);

        /*
            Boss Updates
         */
        updateBoxBoss(delta);
        updatePipeBoss(delta);
        updatePangBoss(delta);
        updateBomberBoss(delta);
        updateTimebombs(delta);
        updateFirePropulsions(delta);
        updateEnemyBullets(delta);
    }
    /*
            Organize
	*/

    private void updateRoadRollers(float delta) {
        for (RoadRoller r : roadRollers)
            r.update(delta);
    }

    private void spawnRoadRoller() {
        for (RoadRoller a : roadRollers)
            if (a.isDEAD()) {
                a.reset(250 + MathUtils.random(25));
                break;
            }
    }

    private void updateBombs(float delta) {
        for (Bomb b : bombs)
            b.update(delta);
    }

    private void spawnBomb() {
        for (Bomb a : bombs)
            if (a.isDEAD()) {
                a.reset(250 + MathUtils.random(25));
                break;
            }
    }

    private void updateMustaches(float delta) {
        for (Mustache m : mustaches)
            m.update(delta);
    }

    private void spawnMustaches() {
        for (Mustache a : mustaches)
            if (a.isDEAD()) {
                a.reset(250 + MathUtils.random(25));
                break;
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
        }
    }

    private void spawnCowboy() {
        for (Cowboy a : cowboys)
            if (a.isDEAD()) {
                a.reset(250 + MathUtils.random(25));
                break;
            }
    }


    private void updateWaveHeads(float delta) {
        for (WaveHead a : waveheads)
            a.update(delta);
    }

    private void spawnWaveHead() {
        for (WaveHead a : waveheads)
            if (a.isDEAD()) {
                a.reset(250 + MathUtils.random(25));
                break;
            }
    }

    /********************************************
     * Hazards
     *******************************************/
    private void updateLaserCrayons(float delta) {
        for (LaserCrayon l : laserCrayons)
            l.update(delta);
    }

    private void spawnLaserCrayon() {
        for (LaserCrayon a : laserCrayons)
            if (a.isDEAD()) {
                a.reset(250 + MathUtils.random(25));
                break;
            }
    }

    private void updateBlackholes(float delta) {
        for (Blackhole b : blackholes)
            b.update(delta);
    }

    private void spawnBlackhole() {
        for (Blackhole a : blackholes)
            if (a.isDEAD()) {
                a.reset(250 + MathUtils.random(25));
                break;
            }
    }

    private void updateBoomerangs(float delta) {
        for (Boomerang a : boomerangs)
            a.update(delta);
    }

    private void spawnBoomerang() {
        for (Boomerang a : boomerangs)
            if (a.isDEAD()) {
                a.reset(290 + MathUtils.random(25));
                break;
            }
    }

    private void updateKnives(float delta) {
        for (Knife a : knives)
            a.update(delta);
    }

    private void spawnKnife() {
        for (Knife a : knives)
            if (a.isDEAD()) {
                a.reset(290 + MathUtils.random(25));
                break;
            }
    }

    /******************************************
     Item
     ******************************************/
    private void updateItem(float delta){
        for (Item a : items)
            a.update(delta);
    }

    private void spawnItem(int type){
        for (Item a : items)
            if (a.isDEAD()){
                a.reset(250+MathUtils.random(25),type);
                break;
            }

    }

    /*************************************************
     * BOSS UPDATES
     *************************************************/
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

    private void spawnBoxboss() {
        blockspaceCnt = 0;
        for (int i = 0; i < blockspace.length; i++)
            blockspace[i] = 0;
        boxBoss.reset();
    }

    private void updatePipeBoss(float delta) {
        pipeBoss.update(delta);
        if (pipeBoss.isSHOOT()) {
            float ypos = GameScreen.gameHeight/2 + MathUtils.random(-GameScreen.gameHeight/3, GameScreen.gameHeight/3);
            Gdx.app.log("ypos",ypos+"");
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

    private void spawnPipeBoss() {
        pipeBoss.reset();
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
    private void spawnPangBoss(){
        pangBosses[0].reset(260, GameScreen.gameHeight / 2f - pangBosses[0].getHeight() / 2f, 1);

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
    private void spawnBomberBoss(){
        bomberBoss.reset();
    }




    /******************************************
     * Enemy Bullets
     ****************************************/
    private void updateEnemyBullets(float delta) {
        for (EnemyBullet c : enemyBullets)
            if (c != null)
                c.update(delta);
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
        for (FirePropulsion a : firePropulsions)
            a.update(delta);
    }



    /******************************************
        Collision
     ******************************************/

    public void checkCollision() {
        for (Item a : items)
            a.collide(mySquirrel);

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

    /******************************************
        Initialize
     ******************************************/
    //TODO restart, initialize
    //called at first
    private void initialize() {
        items = new Item[3];
        for (int i=0;i<items.length;i++)
            items[i] = new Item();

        /*
			Initialize enemy objects
		 */
        roadRollers = new RoadRoller[10];
        for (int i = 0; i < roadRollers.length; i++)
            roadRollers[i] = new RoadRoller();


        bombs = new Bomb[10];
        for (int i = 0; i < bombs.length; i++)
            bombs[i] = new Bomb();


        mustaches = new Mustache[5];
        for (int i = 0; i < mustaches.length; i++)
            mustaches[i] = new Mustache();

        waveheads = new WaveHead[10];
        for (int i = 0; i < waveheads.length; i++)
            waveheads[i] = new WaveHead();


        cowboys = new Cowboy[3];
        for (int i = 0; i < cowboys.length; i++)
            cowboys[i] = new Cowboy();


        knives = new Knife[5];
        for (int i = 0; i < knives.length; i++)
            knives[i] = new Knife(mySquirrel);

        laserCrayons = new LaserCrayon[5];
        for (int i = 0; i < laserCrayons.length; i++)
            laserCrayons[i] = new LaserCrayon();


        boomerangs = new Boomerang[5];
        for (int i = 0; i < boomerangs.length; i++)
            boomerangs[i] = new Boomerang();


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

        blackholes = new Blackhole[1];
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

        timebombs = new TimeBomb[6];
        for (int i = 0; i < timebombs.length; i++)
            timebombs[i] = new TimeBomb();


        firePropulsions = new FirePropulsion[20];
        for (int i = 0; i < firePropulsions.length; i++)
            firePropulsions[i] = new FirePropulsion();
    }

    //kill all on gameover
    private void killAll() {

        for (Item a : items)
            if (!a.isDEAD())
                a.silentDead();
        // enemy
        for (RoadRoller a : roadRollers)
            if (!a.isDEAD())
                a.silentDead();

        for (Bomb a : bombs)
            if (!a.isDEAD())
                a.silentDead();

        for (Mustache a : mustaches)
            if (!a.isDEAD())
                a.silentDead();

        for (LaserCrayon a : laserCrayons)
            if (!a.isDEAD())
                a.silentDead();

        for (Knife a : knives)
            if (!a.isDEAD())
                a.silentDead();

        for (Boomerang a : boomerangs)
            if (!a.isDEAD())
                a.silentDead();

        for (Cowboy a : cowboys)
            if (!a.isDEAD())
                a.silentDead();

        for (WaveHead a : waveheads)
            if (!a.isDEAD())
                a.silentDead();

        for (EnemyBullet a : enemyBullets)
            if (a != null)
                if (!a.isDEAD())
                    a.silentDead();

        for (Blackhole a : blackholes)
            if (!a.isDEAD())
                a.silentDead();

        if (!boxBoss.isDEAD())
            boxBoss.silentDead();

        if (!pipeBoss.isDEAD())
            pipeBoss.silentDead();

        for (PangBoss a : pangBosses) {
            a.doneBreeding();
            if (!a.isDEAD())
                a.silentDead();
        }


        if (!bomberBoss.isDEAD())
            bomberBoss.silentDead();

        for (TimeBomb a : timebombs)
            if (!a.isDEAD())
                a.silentDead();

        for (FirePropulsion a : firePropulsions)
            if (!a.isDEAD())
                a.silentDead();

        for (Bullet a : mySquirrel.getBullets())
            if (!a.isDEAD())
                a.silentDead();

        for (FX f : FXHelper.getInstance().getMyFXs())
            f.remove();

        mySquirrel.realDead();
    }



    public void restart() {

        killAll();



        pause = false;

        enemyCount = 3; // number of enemies at a given time 5?
        numEnemyTypes = 0; // number of enemy types that can be chosen from. 0~4
        spawnTimer = 0.0f; // spawn enemy every x seconds. add score for each spawn
        hazardTimer = 0.0f; //actual frequency
        hazardFrequency = 5.5f;
        hazardCount = 1; // 1,2
        numHazardTypes = 4; //0 ~4

        bossApproachingTimer = 3.0f;
        bossFrequency = 0.0f;
        bossNum = 3;
        bossStage = -1;

        itemFrequency = 200;
        itemTimer=0.0f;

        progress = 0;

        mySquirrel.resetLife();
        ScoreHelper.getInstance().resetScore();
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

    public void togglePause(){pause = !pause;}

    public Item[] getItems() {
        return items;
    }

    public float getBossApproachingTimer(){return bossApproachingTimer;}

    public void addItemFrequency(int add){itemFrequency+=add;}

}
