package com.lumibottle.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.lumibottle.gameobjects.Bullets.Bullet;
import com.lumibottle.gameobjects.Bullets.EnemyBullet;
import com.lumibottle.gameobjects.Bullets.PipeEnemyBullet;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.Item;
import com.lumibottle.gameobjects.ProgressHandler;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.gameobjects.Star;
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
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;
import com.lumibottle.helper.ScoreHelper;
import com.lumibottle.helper.SoundManager;


/**
 * Created by MG-UP on 2016-03-10.
 */
public class GameRenderer {
    //Texture, TextureRegion/Animation
    //Texture need to be disposed

    //libs
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private OrthographicCamera cam;

    // vars
    private int gameHeight;
    private float bgOffset;

    private GameWorld myWorld;

    //GO
    private Item[] myItems;

    private Squirrel mySquirrel;
    private Bullet[] myBullets;
    private Star[] myStars;
    private ProgressHandler myStage;
    private RoadRoller[] myRoadRollers;
    private Bomb[] myBombs;
    private Mustache[] myMustaches;
    private LaserCrayon[] myLaserCrayons;
    private EnemyBullet[] myEnemyBullets;
    private Cowboy[] myCowboys;
    private Blackhole[] myBlackholes;
    private Knife[] myKnives;
    private Boomerang[] myBoomerangs;
    private WaveHead[] myWaveheads;

    private BoxBoss myBoxboss;
    private PipeBoss myPipeboss;
    private PangBoss[] myPangbosses;
    private BomberBoss myBomberboss;
    private TimeBomb[] myTimebombs;
    private FirePropulsion[] myFirepropulsions;

    //****************************************************
    //ASSET
    //****************************************************
    private TextureRegion splash;
    private TextureRegion title;
    private TextureRegion titletext;
    private TextureRegion whiteflash;

    private TextureRegion life;
    private TextureRegion oneUP;
    private TextureRegion POW;
    //
    private TextureRegion squirrelDown;
    private TextureRegion squirrelStand;
    private Animation squirrelAnimation;
    private Animation baconAnimation;
    private TextureRegion gb;
    private Animation eyeAnmimation;
    //
    private TextureRegion bomb;
    private TextureRegion roadroller;
    private Animation cowboy;
    private TextureRegion cowboyidle;
    private TextureRegion mustacheIdle;
    private Animation mustacheAnimation;
    private TextureRegion bluecrayon;
    private Animation cowboyhatAnimation;
    private Animation cowboyhatspawnAnimation;
    private TextureRegion hole;
    private TextureRegion knife;
    private TextureRegion boomerang;
    private TextureRegion wavehead;

    private Animation boxchargeAnimation;
    private TextureRegion boxgotHit;
    private TextureRegion boxvulface;
    private TextureRegion blockbullet;

    private Animation bomberbossAnimation;
    private TextureRegion bomberbosshit;

    private Animation pipebossAnimation;
    private Animation forceshieldAnimation;
    private Animation redsodapillarAnimation;
    private Animation redsodapillarbotAnimation;


    private Animation pangbossAnimation;
    private Animation timebombAnimation;

    private TextureRegion star1, star2;
    private TextureRegion background;

    private BitmapFont font;


    public GameRenderer(GameWorld myWorld, int gameHeight) {
        Gdx.app.log("GameRenderer", "created");
        this.myWorld = myWorld;
        this.gameHeight = gameHeight;


        bgOffset = (256 - gameHeight) / (-2);

        //camera
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 240, gameHeight);

        //renderer
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(cam.combined);
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(cam.combined);


        initGameObjects();
        initAsset();
    }


    public void render(float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        spriteBatch.begin();


        //Gdx.app.log("runTime",runTime+"");
        //rendering order
        if (!myWorld.isSPLASH()) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            spriteBatch.draw(background, 0, bgOffset);
            drawStars();
        }
        if (myWorld.isSPLASH()) {
            //splash
            drawSplash();
        } else if (myWorld.isTITLE()) {
            drawTitle(runTime);
        } else if (myWorld.isABOUT()) {
            drawAbout();
        } else {
            //draw for playing status


            drawBlackholes();
            drawBacon(runTime);
        /*
        Draw enemies below here
		 */
            drawRoadRollers(runTime);
            drawMustaches(runTime);
            drawBlueCrayons();
            drawBombs(runTime);
            drawCowboy(runTime);
            drawBoomerangs();
            drawKnives();
            drawWaveHeads();
            drawEnemyBullets();

            drawBoxBoss();
            drawPipeBoss();
            drawPangBoss(runTime);
            drawBomberBoss();
            drawTimebombs(runTime);

            //main
            // spriteBatch.setColor(1.0f,1.0f,1.0f,0.5f); semi transparent
            drawSquirrel();
            drawBullets();

            drawItems();

            //fx
            drawFXs();
            drawFirePropulsion();

            if (myWorld.isPLAYING())
                drawStart();
            else if (myWorld.isGAMEOVER())
                drawGameOver();

            if (myWorld.isPaused())
                drawPause();

        }

//        font.getData().setScale(0.1f);
//        font.draw(spriteBatch, "FPS:" + Gdx.graphics.getFramesPerSecond(), 0, 10);
        spriteBatch.end();
//		drawDebugMode();

    }


    /******
     * INIT
     *********/

    private void initGameObjects() {

        mySquirrel = myWorld.getMySquirrel();
        myStars = myWorld.getMyStars();
        myBullets = mySquirrel.getBullets();
        myStage = myWorld.getMyStage();
        myRoadRollers = myStage.getRoadRollers();
        myMustaches = myStage.getMustaches();
        myLaserCrayons = myStage.getLaserCrayons();
        myBombs = myStage.getBombs();
        myEnemyBullets = myStage.getEnemyBullets();
        myCowboys = myStage.getCowboys();
        myBlackholes = myStage.getBlackholes();
        myKnives = myStage.getKnives();
        myBoomerangs = myStage.getBoomerangs();
        myWaveheads = myStage.getWaveheads();

        myItems = myStage.getItems();


        myBoxboss = myStage.getBoxboss();
        myPipeboss = myStage.getPipeBoss();
        myPangbosses = myStage.getPangBosses();
        myBomberboss = myStage.getBomberBoss();
        myTimebombs = myStage.getTimebombs();
        myFirepropulsions = myStage.getFirePropulsions();

    }

    private void initAsset() {
        splash = AssetHelper.splash;
        title = AssetHelper.title;
        titletext = AssetHelper.titletext;
        whiteflash = AssetHelper.whiteflash;

        squirrelDown = AssetHelper.sqdown;
        squirrelStand = AssetHelper.sqstand;
        squirrelAnimation = AssetHelper.sqAnim;
        baconAnimation = AssetHelper.baconAnim;
        eyeAnmimation = AssetHelper.eyeAnim;

        gb = AssetHelper.greenBullet;

        life = AssetHelper.life;
        oneUP = AssetHelper.oneUP;
        POW = AssetHelper.POW;
        //en
        bomb = AssetHelper.roadroller;
        roadroller = AssetHelper.tanklorry;
        mustacheIdle = AssetHelper.mustaches[4];
        mustacheAnimation = AssetHelper.mustacheAnim;
        cowboy = AssetHelper.cowboythrowAnim;
        cowboyidle = AssetHelper.cowboythrow[0];

        knife = AssetHelper.knife;
        boomerang = AssetHelper.boomerang;
        wavehead = AssetHelper.wavehead;

        bluecrayon = AssetHelper.bluecrayon;

        cowboyhatAnimation = AssetHelper.cowboyhatsAnim;
        cowboyhatspawnAnimation = AssetHelper.cowboyhatsspawnAnim;

        hole = AssetHelper.hole;

        //BOSS
        boxchargeAnimation = AssetHelper.boxchargeAnim;
        boxgotHit = AssetHelper.boxhitFace;
        boxvulface = AssetHelper.boxvulFace;
        blockbullet = AssetHelper.blockbullet;

        pipebossAnimation = AssetHelper.pipeBossAnim;
        forceshieldAnimation = AssetHelper.forceshieldAnim;
        redsodapillarAnimation = AssetHelper.redsodapillarAnim;
        redsodapillarbotAnimation = AssetHelper.redsodapillarbotAnim;

        pangbossAnimation = AssetHelper.pangBossAnim;
        bomberbossAnimation = AssetHelper.bomberbossthrowAnim;
        bomberbosshit = AssetHelper.bomberbosshit;
        timebombAnimation = AssetHelper.timebombAnim;

        //bg
        star1 = AssetHelper.star1;
        star2 = AssetHelper.star2;
        background = AssetHelper.spacebg;


        font = AssetHelper.font;

    }

    /*
        Drawing methods
        check if object is VISIBLE for culling
     */

    /********************************************************
     * Game State Drawing
     ********************************************************/

    private void drawSplash() {
        if (myWorld.getRunTime() > 1.0f)
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        else if (myWorld.getRunTime() < 0.0f)
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 0.0f);
        else
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, myWorld.getRunTime());

        spriteBatch.draw(splash, 0, -(240 - gameHeight) / 2, 240, 240);
    }


    private void drawTitle(float runTime) {
        //squirrel
        spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        spriteBatch.draw(title, 0, (-(240 - gameHeight) / 2)+ 2*MathUtils.sin(runTime*2), 240, 240);
        //title text
        spriteBatch.draw(titletext, 0, -(240 - gameHeight) / 2, 240, 240);

        //credit button
        String str = "ABOUT";
        font.getData().setScale(0.16f);
        font.draw(spriteBatch, str, (240 - str.length() * 5.2f), 5.0f);

        //sound option
        str = "SOUND " + SoundManager.getInstance().getMuteState();
        font.getData().setScale(0.16f);
        font.draw(spriteBatch, str, (240 - str.length() * 5.2f), gameHeight - 2.2f);

        //touch to start
        if (myWorld.getFlash()) {
            font.getData().setScale(0.2f);
            font.draw(spriteBatch, "TOUCH TO START", (240 - 90) / 2, gameHeight * 0.15f);
        }

        //transition flash
        if (1.0f - myWorld.getRunTime() > 0) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f - myWorld.getRunTime());
            spriteBatch.draw(whiteflash, 0, -(240 - gameHeight) / 2, 240, 240);
        }


    }

    private void drawStart() {
        //while playing

        if (1.0f - myWorld.getRunTime() >= 0) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f - myWorld.getRunTime());
            spriteBatch.draw(whiteflash, 0, -(240 - gameHeight) / 2, 240, 240);
        }

        if (3.0f - myWorld.getRunTime() > 0) {
            font.getData().setScale(0.16f);
            font.draw(spriteBatch, "BEST " + ScoreHelper.getInstance().getBest(), 110 - (ScoreHelper.getInstance().getBest().length() * 5.2f) / 2, gameHeight - 2.2f);
        }

        //score
        font.getData().setScale(0.16f);
        font.draw(spriteBatch, ScoreHelper.getInstance().getScore(), 0, gameHeight - 2.2f);

        //life
        spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        spriteBatch.draw(life, 0, 0, 8, 9);
        if (mySquirrel.getLife() <0)
            font.draw(spriteBatch, "x0", 9, 5.0f);
        else
            font.draw(spriteBatch, "x"+mySquirrel.getLife(), 9, 5.0f);

        //boss
        if (myStage.getBossApproachingTimer() < 2.0f)
            font.draw(spriteBatch, "BOSS APPROACHING!!!", 120-19*5.1f/2, gameHeight/2);


    }


    //TODO a
    private void drawAbout(){
        String text = "SODARICUS v1.2.3";
        font.getData().setScale(0.16f);
        font.draw(spriteBatch, text, 120 - (text.length()*5.1f)/2, gameHeight*0.85f);

        font.setColor(1.0f,1.0f,0.3f,1.0f);
        text = "Developed By";
        font.draw(spriteBatch, text, 120 - (text.length()*5.1f)/2, gameHeight*0.65f);

        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        text = "INSTANTPOOD";
        font.draw(spriteBatch, text, 120 - (text.length()*5.1f)/2, gameHeight*0.58f);

        font.setColor(1.0f,1.0f,0.3f,1.0f);
        text = "Special Thanks To";
        font.draw(spriteBatch, text, 120 - (text.length()*5.1f)/2, gameHeight*0.45f);
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        text = "Superdowonman";
        font.draw(spriteBatch, text, 120 - (text.length()*5.1f)/2, gameHeight*0.38f);
        text = "Blue Eyed Kang Mayoungjohn Masochist Storm of Hyper Spacetime Man";
        font.draw(spriteBatch, text, 120 - (text.length()*5.1f)/2, gameHeight*0.30f);

    }

    private void drawGameOver() {
        if (myWorld.getRunTime_2() > 2.0f){
            font.getData().setScale(0.16f);

            font.draw(spriteBatch, "Score "+ScoreHelper.getInstance().getScore(), 120-(12 * 5.2f)/2, gameHeight*0.7f);
            font.draw(spriteBatch, "BEST " + ScoreHelper.getInstance().getBest(), 120 - (11 * 5.2f) / 2, gameHeight*0.6f);

            font.getData().setScale(0.2f);
            font.draw(spriteBatch, "GAME OVER", (120 - 9*3.0f), gameHeight*0.8f);

            String text = "To Title";
            font.getData().setScale(0.16f);
            font.draw(spriteBatch, text, 60 - (text.length()*5.1f)/2, gameHeight*0.30f);

            text = "High Scores";
            font.getData().setScale(0.16f);
            font.draw(spriteBatch, text, 120 - (text.length()*5.1f)/2, gameHeight*0.30f);

            text = "Retry";
            font.getData().setScale(0.16f);
            font.draw(spriteBatch, text, 180 - (text.length()*5.1f)/2, gameHeight*0.30f);
        }

    }

    private void drawPause(){
        String str = "PAUSED";
        font.getData().setScale(0.2f);
        font.draw(spriteBatch, str, (120 - 6.0f*str.length()/2), gameHeight/2);

        str = "To Title";
        font.draw(spriteBatch, str, (240 - 7.0f*str.length()), gameHeight-2.2f);

        font.getData().setScale(0.16f);
        font.draw(spriteBatch, "BEST " + ScoreHelper.getInstance().getBest(), 110 - (ScoreHelper.getInstance().getBest().length() * 5.2f) / 2, gameHeight - 2.2f);
    }


    /********************************************************
     * Game Object Drawing
     ********************************************************/

    private void drawSquirrel() {
        //draw main actor

        if (mySquirrel.IsTransparent()) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 0.0f);
        } else {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        if (mySquirrel.isRamseyThunder()) {
            spriteBatch.draw(squirrelStand, mySquirrel.getX(),
                    mySquirrel.getY(), mySquirrel.getWidth() / 2.0f,
                    mySquirrel.getHeight() / 2.0f, mySquirrel.getWidth(), mySquirrel.getHeight(),
                    1, 1, mySquirrel.getRotation());
        } else
        if (mySquirrel.isSHOOTING()) {
            spriteBatch.draw(squirrelAnimation.getKeyFrame(mySquirrel.getAnimRunTime()), mySquirrel.getX(),
                    mySquirrel.getY(), mySquirrel.getWidth() / 2.0f,
                    mySquirrel.getHeight() / 2.0f, mySquirrel.getWidth(), mySquirrel.getHeight(),
                    1, 1, mySquirrel.getRotation());

        } else {
            spriteBatch.draw(squirrelDown, mySquirrel.getX(),
                    mySquirrel.getY(), mySquirrel.getWidth() / 2.0f,
                    mySquirrel.getHeight() / 2.0f, mySquirrel.getWidth(), mySquirrel.getHeight(),
                    1, 1, mySquirrel.getRotation());


        }
//
//		mySquirrel.getSodaburst().setPosition(mySquirrel.getX()+mySquirrel.getWidth()/2f,mySquirrel.getY()+mySquirrel.getHeight()/2f);
//		mySquirrel.getSodaburst().update(Gdx.graphics.getDeltaTime());
//		mySquirrel.getSodaburst().draw(spriteBatch);
        spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void drawBullets() {
        for (Bullet b : myBullets) {
            if (b.isVISIBLE())
            if (b.getThunderMode()){
                b.getParticle().setPosition(b.getX() + b.getWidth() / 2.0f,b.getY() + b.getHeight() / 2.0f);
                b.getParticle().update(Gdx.graphics.getDeltaTime());
                b.getParticle().draw(spriteBatch);
            } else {
                spriteBatch.draw(gb,
                        b.getX(), b.getY(),
                        b.getWidth() / 2.0f, b.getHeight() / 2.0f,
                        b.getWidth(), b.getHeight(),
                        1, 1, b.getTheta() + 180);
            }
        }
    }

    private void drawStars() {
        spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        for (Star s : myStars) {
            if (s.isBigStar()) {
                spriteBatch.draw(star1, s.getX(), s.getY());
            } else {
                spriteBatch.draw(star2, s.getX(), s.getY());
            }

        }
    }

    private void drawBacon(float runTime) {

        if (mySquirrel.IsTransparent()) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 0.0f);
        } else {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }


        spriteBatch.draw(baconAnimation.getKeyFrame(runTime),
                (mySquirrel.getX() - 6), (mySquirrel.getY() - 6),
                32 / 2.0f, 32 / 2.0f,
                32, 32,
                1, 1, mySquirrel.getRotation());

        spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void drawFXs() {
        for (FX f : FXHelper.getInstance().getMyFXs()) {
            if (f.isTOBEDRAWN())
                switch (f.getAnimNo()) {
                    case 4:
                        spriteBatch.draw(f.getAnimation().getKeyFrame(f.getRunTime()), f.getX(), f.getY(), 128 / 2f, 128 / 2f, 128, 128, 1.0f, 1.0f, mySquirrel.getRotation());
                        break;
                    case 5:
                        spriteBatch.draw(f.getAnimation().getKeyFrame(f.getRunTime()), f.getX(), f.getY(), f.getSize(), f.getSize());
                        break;
                    default:
                        spriteBatch.draw(f.getAnimation().getKeyFrame(f.getRunTime()), f.getX(), f.getY());
                }
        }
    }

    private void drawItems(){
        for (Item a : myItems){
            if (a.isVISIBLE()){
                switch (a.getType()){
                    case 0 : spriteBatch.draw(oneUP, a.getX(),a.getY(),a.getWidth(),a.getHeight()); break;
                    case 1 : spriteBatch.draw(POW, a.getX(),a.getY(),a.getWidth(),a.getHeight()); break;
                }
            }
        }

    }

    //Enemies
    private void drawWaveHeads() {
        for (WaveHead a : myWaveheads) {
            if (a.isVISIBLE()) {

                a.getParticle().setPosition(a.getX() + a.getWidth() / 2.0f, a.getY() + a.getHeight() / 2.0f);
                a.getParticle().update(Gdx.graphics.getDeltaTime());
                a.getParticle().draw(spriteBatch);

                spriteBatch.draw(wavehead, a.getX(), a.getY());

            }
        }
    }

    private void drawBoomerangs() {
        for (Boomerang a : myBoomerangs)
            if (a.isVISIBLE()) {
                spriteBatch.draw(boomerang, a.getX(), a.getY(), a.getWidth() / 2.0f, a.getHeight() / 2.0f, a.getWidth(), a.getHeight(), 1.0f, 1.0f, a.getTheta());
            }
    }

    private void drawKnives() {
        for (Knife a : myKnives)
            if (a.isVISIBLE()) {
                spriteBatch.draw(knife, a.getX(), a.getY(), a.getWidth() / 2.0f, a.getHeight() / 2.0f, a.getWidth(), a.getHeight(), 1.0f, 1.0f, a.getTheta());
            }
    }


    private void drawRoadRollers(float runTime) {
        for (RoadRoller r : myRoadRollers) {
            if (r.isVISIBLE()) {
                r.getParticle().setPosition(r.getX() + r.getWidth() / 2f, r.getY() + r.getHeight());
                r.getParticle().update(Gdx.graphics.getDeltaTime());
                r.getParticle().draw(spriteBatch);
                if (r.getParticle().isComplete())
                    r.getParticle().reset();

                spriteBatch.draw(roadroller, r.getX(), r.getY());
                spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime), r.getX(), r.getY() + r.getHeight() / 2f);

            }
        }
    }

    private void drawBombs(float runTime) {
        for (Bomb b : myBombs) {
            if (b.isVISIBLE()) {

                if (!b.isExploding()) {

                    b.getParticle().setPosition(b.getX() + 2 * b.getWidth() / 3f, b.getY() + b.getHeight() / 2f);
                    b.getParticle().update(Gdx.graphics.getDeltaTime());
                    b.getParticle().draw(spriteBatch);
                    if (b.getParticle().isComplete())
                        b.getParticle().reset();

                    spriteBatch.draw(bomb, b.getX(), b.getY());
                    spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime), b.getX(), b.getY());

                }

            }
        }
    }

    private void drawMustaches(float runTime) {
        for (Mustache m : myMustaches) {
            if (m.isVISIBLE()) {

                if (m.isDoneMoving())
                    spriteBatch.draw(mustacheIdle, m.getX(), m.getY());
                else
                    spriteBatch.draw(mustacheAnimation.getKeyFrame(m.getRunTime()), m.getX(), m.getY());

                m.getParticle().setPosition(m.getX() + m.getWidth() / 2f, m.getY() + m.getHeight() / 2f);
                m.getParticle().update(Gdx.graphics.getDeltaTime());
                m.getParticle().draw(spriteBatch);
                if (m.getParticle().isComplete())
                    m.getParticle().reset();

                spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime), m.getX() + m.getWidth() / 3f, m.getY() + m.getHeight() * 2 / 3f);
                spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime), m.getX() + m.getWidth() / 3f + 6f, m.getY() + m.getHeight() * 2 / 3f);
            }
        }
    }

    private void drawBlueCrayons() {
        for (LaserCrayon l : myLaserCrayons) {
            if (l.isVISIBLE()) {

                if (l.isREADYTOSHOOT()) {
                    l.getParticle().setPosition(l.getX(), l.getY());
                    l.getParticle().update(Gdx.graphics.getDeltaTime());
                    l.getParticle().draw(spriteBatch);
                }

                spriteBatch.draw(bluecrayon, l.getX(), l.getY());


            }
        }
    }

    private void drawCowboy(float runTime) {
        for (Cowboy c : myCowboys) {
            if (c.isVISIBLE()) {


                c.getParticle().setPosition(c.getX() + c.getWidth() / 2f, c.getY() + 8f);
                c.getParticle().update(Gdx.graphics.getDeltaTime());
                c.getParticle().draw(spriteBatch);


                if (c.isPreparing())
                    spriteBatch.draw(cowboy.getKeyFrame(c.getRunTime()), c.getX(), c.getY());
                else
                    spriteBatch.draw(cowboyidle, c.getX(), c.getY());

                spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime), c.getX() + c.getWidth() / 4f, c.getY() + c.getHeight() / 2f);
                spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime), c.getX() + c.getWidth() / 4f + 6f, c.getY() + c.getHeight() / 2f);

                if (c.isPreparing())
                    spriteBatch.draw(cowboyhatspawnAnimation.getKeyFrame(c.getRunTime()), c.getX(), c.getY() + 23f);
            }

        }
    }

    private void drawEnemyBullets() {
        for (EnemyBullet c : myEnemyBullets)
            if (c != null && c.isVISIBLE()) {
                String s = c.getClass().getSimpleName();
                if (s.equals("HatEnemyBullet")) {
                    spriteBatch.draw(cowboyhatAnimation.getKeyFrame(c.getRunTime()), c.getX(), c.getY());
                }
                if (s.equals("BlockEnemyBullet")) {
                    spriteBatch.draw(blockbullet, c.getX(), c.getY(), myBoxboss.getWidth(), myBoxboss.getWidth());
                }
                if (s.equals("PipeEnemyBullet")) {
                    if (((PipeEnemyBullet) c).isUp()) {
                        spriteBatch.draw(redsodapillarAnimation.getKeyFrame(c.getRunTime()), c.getX(), c.getY());
                        spriteBatch.draw(redsodapillarbotAnimation.getKeyFrame(c.getRunTime()), c.getX() - 4, c.getY() - 3);
                    } else {
                        spriteBatch.draw(redsodapillarAnimation.getKeyFrame(c.getRunTime()), c.getX(), c.getY(), c.getWidth() / 2, c.getHeight() / 2, c.getWidth(), c.getHeight(), 1.0f, 1.0f, 180);
                        spriteBatch.draw(redsodapillarbotAnimation.getKeyFrame(c.getRunTime()), c.getX() - 5, c.getY() + c.getHeight() - 13, 33 / 2.0f, 16 / 2.0f, 33, 16, 1.0f, 1.0f, 180);
                    }
                }
            }
    }

    private void drawBlackholes() {
        for (Blackhole b : myBlackholes)
            if (b.isVISIBLE()) {
                spriteBatch.draw(hole, b.getX(), b.getY(), b.getWidth() / 2f, b.getHeight() / 2f, b.getWidth(), b.getHeight(), 0.5f, 0.5f, b.getTheta());
            }
    }


    private void drawBoxBoss() {
        if (myBoxboss.isVISIBLE()) {

            if (myBoxboss.gotHit()) {
                spriteBatch.draw(boxgotHit, myBoxboss.getX(), myBoxboss.getY(), myBoxboss.getWidth(), myBoxboss.getHeight());
            } else if (myBoxboss.isIDLE()) {
                spriteBatch.draw(boxchargeAnimation.getKeyFrame(myBoxboss.getRunTime() / 8), myBoxboss.getX(), myBoxboss.getY(), myBoxboss.getWidth(), myBoxboss.getHeight());
            } else if (myBoxboss.isVULNERABLE()) {
                spriteBatch.draw(boxvulface, myBoxboss.getX(), myBoxboss.getY(), myBoxboss.getWidth(), myBoxboss.getHeight());
            } else
                spriteBatch.draw(boxchargeAnimation.getKeyFrame(7), myBoxboss.getX(), myBoxboss.getY(), myBoxboss.getWidth(), myBoxboss.getHeight());
        }
    }


    private void drawPipeBoss() {
        if (myPipeboss.isVISIBLE()) {

            if (myPipeboss.isIDLE())
                spriteBatch.draw(pipebossAnimation.getKeyFrame(0), myPipeboss.getX(), myPipeboss.getY());
            else {
                spriteBatch.draw(pipebossAnimation.getKeyFrame(myPipeboss.getShootRunTime()), myPipeboss.getX(), myPipeboss.getY());
            }

            if (myPipeboss.gotHit()) {
                spriteBatch.draw(forceshieldAnimation.getKeyFrame(myPipeboss.getHitAnimRunTime()), myPipeboss.getX(), myPipeboss.getY());
            }
        }
    }


    private void drawPangBoss(float runTime) {
        for (PangBoss a : myPangbosses)
            if (a.isVISIBLE())
                spriteBatch.draw(pangbossAnimation.getKeyFrame(runTime), a.getX(), a.getY(), a.getWidth() / 2f, a.getHeight() / 2f, a.getWidth(), a.getHeight(), 1.0f, 1.0f, a.getAestheticTheta());
    }

    private void drawBomberBoss() {
        if (myBomberboss.isVISIBLE()) {

            myBomberboss.getParticle().setPosition(myBomberboss.getX() + myBomberboss.getWidth(), myBomberboss.getY() + 6.0f);
            myBomberboss.getParticle().update(Gdx.graphics.getDeltaTime());
            myBomberboss.getParticle().draw(spriteBatch);

            if (myBomberboss.getInvtime() < 0.5f) {
                spriteBatch.draw(bomberbosshit, myBomberboss.getX(), myBomberboss.getY());
            } else {
                if (myBomberboss.getPrepared()) {
                    spriteBatch.draw(bomberbossAnimation.getKeyFrame(myBomberboss.getShootDelay()), myBomberboss.getX(), myBomberboss.getY());
                } else {
                    spriteBatch.draw(bomberbossAnimation.getKeyFrame(0), myBomberboss.getX(), myBomberboss.getY());
                }
            }
        }
    }

    private void drawTimebombs(float runTime) {
        for (TimeBomb a : myTimebombs) {
            if (a.isVISIBLE())
                if (a.isTICKstate())
                    spriteBatch.draw(timebombAnimation.getKeyFrame(runTime), a.getX(), a.getY(), a.getWidth() / 2f, a.getHeight() / 2f, a.getWidth(), a.getHeight(), 1.0f, 1.0f, a.getTheta());
                else
                    spriteBatch.draw(timebombAnimation.getKeyFrame(0), a.getX(), a.getY(), a.getWidth() / 2f, a.getHeight() / 2f, a.getWidth(), a.getHeight(), 1.0f, 1.0f, a.getTheta());
            //add faster flickering?
        }
    }

    private void drawFirePropulsion() {
        for (FirePropulsion a : myFirepropulsions) {
            if (a.isVISIBLE()) {
                a.getParticle().setPosition(a.getX() + a.getWidth() / 2.0f, a.getY() + a.getHeight() / 2.0f);
                a.getParticle().update(Gdx.graphics.getDeltaTime());
                a.getParticle().draw(spriteBatch);
            }
        }
    }

    private void drawDebugMode() {
        //for debugging
        shapeRenderer.begin();
        shapeRenderer.polygon(mySquirrel.getHitbox().getTransformedVertices());


        for (RoadRoller b : myRoadRollers)
            if (b.isVISIBLE())
                shapeRenderer.polygon(b.getHitbox().getTransformedVertices());

        for (Mustache b : myMustaches)
            if (b.isVISIBLE())
                shapeRenderer.polygon(b.getHitbox().getTransformedVertices());
        for (Bomb b : myBombs)
            if (b.isVISIBLE())
                shapeRenderer.polygon(b.getHitbox().getTransformedVertices());
        for (Bullet b : myBullets)
            if (b.isVISIBLE())
                shapeRenderer.polygon(b.getHitbox().getTransformedVertices());
        for (EnemyBullet b : myEnemyBullets)
            if (b != null)
                shapeRenderer.polygon((b.getHitbox().getTransformedVertices()));
        for (Knife b : myKnives)
            if (b.isVISIBLE())
                shapeRenderer.polygon((b.getHitbox().getTransformedVertices()));
        for (PangBoss b : myPangbosses)
            if (b.isVISIBLE())
                shapeRenderer.polygon((b.getHitbox().getTransformedVertices()));
        for (FirePropulsion b : myFirepropulsions)
            if (b.isVISIBLE())
                shapeRenderer.polygon((b.getHitbox().getTransformedVertices()));

        for (LaserCrayon b : myLaserCrayons)
            if (b.isVISIBLE())
                shapeRenderer.polygon(b.getHitbox().getTransformedVertices());

        for (Blackhole b : myBlackholes)
            if (b.isVISIBLE())
                shapeRenderer.polygon(b.getHitbox().getTransformedVertices());

        shapeRenderer.end();
    }

    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }

}
