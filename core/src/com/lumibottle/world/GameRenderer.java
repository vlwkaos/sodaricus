package com.lumibottle.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.lumibottle.gameobjects.Bullet;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.enemies.Blackhole;
import com.lumibottle.gameobjects.enemies.Bomb;
import com.lumibottle.gameobjects.enemies.Cowboy;
import com.lumibottle.gameobjects.enemies.EnemyBullet;
import com.lumibottle.gameobjects.enemies.LaserCrayon;
import com.lumibottle.gameobjects.enemies.Mustache;
import com.lumibottle.gameobjects.ProgressHandler;
import com.lumibottle.gameobjects.enemies.RoadRoller;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.gameobjects.Star;
import com.lumibottle.gameobjects.enemies.bosses.BoxBoss;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.helper.FXHelper;


/**
 * Created by MG-UP on 2016-03-10.
 */
public class GameRenderer {
    //Texture, TextureRegion/Animation
    //Texture need to be disposed
    //


    //libs
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private OrthographicCamera cam;

    // vars
    private int gameHeight;
    private float midPointY;
    private float bgOffset;

    private GameWorld myWorld;

    //GO
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


    private BoxBoss myBoxboss;


    //ASSET
    private TextureRegion splash;
    //
    private TextureRegion squirrelDown;
    private Animation squirrelAnimation;
    private Animation baconAnimation;
    private TextureRegion gb;
	private Animation eyeAnmimation;
	//
    private TextureRegion roadroller;
	private TextureRegion bomb;
	private Animation cowboy;
	private TextureRegion cowboyidle;
    private TextureRegion mustacheIdle;
    private Animation mustacheAnimation;
	private TextureRegion bluecrayon;
	private Animation cowboyhatAnimation;
	private Animation cowboyhatspawnAnimation;
	private TextureRegion hole;

    private Animation boxchargeAnimation;
    private TextureRegion boxgotHit;

	private TextureRegion star1,star2;
    private TextureRegion background;




    public GameRenderer(GameWorld myWorld, int gameHeight){
        Gdx.app.log("GameRenderer", "created");
        this.myWorld = myWorld;
        this.gameHeight = gameHeight;

        bgOffset = (256-gameHeight)/(-2);

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



    public void render(float runTime){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        spriteBatch.begin();
        spriteBatch.draw(background, 0, bgOffset);
        //Gdx.app.log("runTime",runTime+"");
		//rendering order
		drawStars();
	    drawBlackholes();
		drawBacon(runTime);
		/*
		Draw enemies below here
		 */

        drawRoadRollers(runTime);
        drawMustaches(runTime);
		drawBlueCrayons(runTime);
		drawBombs(runTime);
	    drawCowboy(runTime);
		drawEnemyBullets();

        drawBoxBoss();


		//main
	   // spriteBatch.setColor(1.0f,1.0f,1.0f,0.5f); semi transparent
	    drawSquirrel();
	    drawBullets();

	    //fx
	    drawFXs();


        //splash
        drawSplash();

        spriteBatch.end();

        //for debugging
        shapeRenderer.begin();
        shapeRenderer.polygon(mySquirrel.getHitbox().getTransformedVertices());
        for (Mustache m : myMustaches)
            shapeRenderer.polygon(m.getHitbox().getTransformedVertices());

        for (Bullet b : myBullets)
            shapeRenderer.polygon(b.getHitbox().getTransformedVertices());
        shapeRenderer.end();



    }


	/******
		INIT
	 *********/

    private void initGameObjects(){
        mySquirrel = myWorld.getMySquirrel();
		myStars = myWorld.getMyStars();
        myBullets = mySquirrel.getBullets();
        myStage = myWorld.getMyStage();
        myRoadRollers = myStage.getRoadRollers();
        myMustaches= myStage.getMustaches();
	    myLaserCrayons = myStage.getLaserCrayons();
	    myBombs = myStage.getBombs();
	    myEnemyBullets = myStage.getEnemyBullets();
	    myCowboys = myStage.getCowboys();
	    myBlackholes = myStage.getBlackholes();

        myBoxboss = myStage.getBoxboss();
    }
    private void initAsset(){
        splash = AssetHelper.splash;

        squirrelDown = AssetHelper.sqdown;
        squirrelAnimation = AssetHelper.sqAnim;
        baconAnimation = AssetHelper.baconAnim;
		eyeAnmimation = AssetHelper.eyeAnim;

        gb = AssetHelper.greenBullet;


	    //en
        roadroller = AssetHelper.roadroller;
	    bomb = AssetHelper.tanklorry;
        mustacheIdle = AssetHelper.mustaches[4];
        mustacheAnimation = AssetHelper.mustacheAnim;
		cowboy = AssetHelper.cowboythrowAnim;
		cowboyidle = AssetHelper.cowboythrow[0];


		bluecrayon = AssetHelper.bluecrayon;

		cowboyhatAnimation = AssetHelper.cowboyhatsAnim;
	    cowboyhatspawnAnimation = AssetHelper.cowboyhatsspawnAnim;

	    hole = AssetHelper.hole;

        //BOSS
        boxchargeAnimation = AssetHelper.boxchargeAnim;
        boxgotHit = AssetHelper.boxhitFace;
	    //bg
        star1 = AssetHelper.star1;
        star2 = AssetHelper.star2;
        background = AssetHelper.spacebg;

    }

	/*
		Drawing methods
	 */
    private void drawSplash(){
        if (myWorld.isSPLASH()){
            if (myWorld.getRunTime()>1.0f)
                spriteBatch.setColor(1.0f,1.0f,1.0f,1.0f);
            else if (myWorld.getRunTime()<0.0f)
                spriteBatch.setColor(1.0f,1.0f,1.0f,0.0f);
            else
                spriteBatch.setColor(1.0f,1.0f,1.0f,myWorld.getRunTime());

            spriteBatch.draw(splash,0,-(240-gameHeight)/2,240,240);
        }
    }



	private void drawSquirrel(){
		//draw main actor

		if (mySquirrel.IsInvincible()){
			spriteBatch.setColor(1.0f,1.0f,1.0f,0.5f);
		}

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

		spriteBatch.setColor(1.0f,1.0f,1.0f,1.0f);//reset
	}

	private void drawBullets(){
		for (Bullet b:myBullets){
			if (b.isVISIBLE())
				spriteBatch.draw(gb,
						b.getX(), b.getY(),
						b.getWidth() / 2.0f,b.getHeight() / 2.0f,
						b.getWidth(), b.getHeight(),
						1, 1, b.getTheta()+180);

		}
	}

	private void drawStars(){
		for (Star s:myStars){
			if (s.isBigStar()){
				spriteBatch.draw(star1, s.getX(), s.getY());
			} else {
				spriteBatch.draw(star2, s.getX(), s.getY());
			}

		}
	}

	private void drawBacon(float runTime){
		spriteBatch.draw(baconAnimation.getKeyFrame(runTime),
				(mySquirrel.getX()-6), (mySquirrel.getY()-6),
				32 / 2.0f, 32 / 2.0f,
				32, 32,
				1, 1, mySquirrel.getRotation());
	}

    private void drawFXs(){
	    for (FX f: FXHelper.getInstance().getMyFXs()){
		    if (f.isTOBEDRAWN())
		        if (f.getAnimNo()==(short)4)
			        spriteBatch.draw(f.getAnimation().getKeyFrame(f.getRunTime()),f.getX(),f.getY(),128/2f,128/2f,128,128,1.0f,1.0f,mySquirrel.getRotation());
			        else
			    spriteBatch.draw(f.getAnimation().getKeyFrame(f.getRunTime()),f.getX(),f.getY());

	    }
    }



	//Enemies


    private void drawRoadRollers(float runTime){
		for (RoadRoller r: myRoadRollers) {
			if (r.isVISIBLE()) {

				r.getParticle().setPosition(r.getX()+2*r.getWidth()/3f,r.getY()+r.getHeight()/2f);
				r.getParticle().update(Gdx.graphics.getDeltaTime());
				r.getParticle().draw(spriteBatch);
				if (r.getParticle().isComplete())
					r.getParticle().reset();

				spriteBatch.draw(roadroller, r.getX(), r.getY());
				spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime),r.getX(),r.getY());


			}
		}
	}

	private void drawBombs(float runTime){
		for (Bomb b: myBombs) {
			if (b.isVISIBLE()) {

				if (!b.isExploding())
				spriteBatch.draw(bomb, b.getX(), b.getY());

				b.getParticle().setPosition(b.getX()+b.getWidth()/2f,b.getY()+b.getHeight());
				b.getParticle().update(Gdx.graphics.getDeltaTime());
				b.getParticle().draw(spriteBatch);
				if (b.getParticle().isComplete())
					b.getParticle().reset();
				spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime),b.getX(),b.getY()+b.getHeight()/2f);
			}
		}
	}

    private void drawMustaches(float runTime){
        for (Mustache m : myMustaches) {
            if (m.isVISIBLE()) {

                if (m.isDoneMoving())
                spriteBatch.draw(mustacheIdle, m.getX(), m.getY());
                else
                    spriteBatch.draw(mustacheAnimation.getKeyFrame(m.getRunTime()),m.getX(),m.getY());

	            m.getParticle().setPosition(m.getX()+m.getWidth()/2f,m.getY()+m.getHeight()/2f);
	            m.getParticle().update(Gdx.graphics.getDeltaTime());
	            m.getParticle().draw(spriteBatch);
	            if (m.getParticle().isComplete())
		            m.getParticle().reset();

	            spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime),m.getX()+m.getWidth()/3f,m.getY()+m.getHeight()*2/3f);
	            spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime),m.getX()+m.getWidth()/3f+6f,m.getY()+m.getHeight()*2/3f);
            }
        }
    }

	private void drawBlueCrayons(float runTime){
		for (LaserCrayon l: myLaserCrayons) {
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

	private void drawCowboy(float runTime){
		for (Cowboy c: myCowboys){
			if (c.isVISIBLE()) {


				c.getParticle().setPosition(c.getX()+c.getWidth()/2f, c.getY()+8f);
				c.getParticle().update(Gdx.graphics.getDeltaTime());
				c.getParticle().draw(spriteBatch);



			if (c.isPreparing())
				spriteBatch.draw(cowboy.getKeyFrame(c.getRunTime()), c.getX(), c.getY());
				else
				spriteBatch.draw(cowboyidle,c.getX(),c.getY());

				spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime),c.getX()+c.getWidth()/4f,c.getY()+c.getHeight()/2f);
				spriteBatch.draw(eyeAnmimation.getKeyFrame(runTime),c.getX()+c.getWidth()/4f+6f,c.getY()+c.getHeight()/2f);

				if (c.isPreparing())
					spriteBatch.draw(cowboyhatspawnAnimation.getKeyFrame(c.getRunTime()),c.getX(),c.getY()+23f);
			}

			}
	}

	private void  drawEnemyBullets(){
		for (EnemyBullet c : myEnemyBullets)
			if (c.isVISIBLE()) {
				switch( c.getType()){
					case 0: 	spriteBatch.draw(cowboyhatAnimation.getKeyFrame(c.getRunTime()), c.getX(), c.getY()); break;

				}

			}
	}

	private void drawBlackholes(){
		for (Blackhole b : myBlackholes)
			if (b.isVISIBLE()){
				spriteBatch.draw(hole, b.getX(), b.getY(),b.getWidth()/2f,b.getHeight()/2f,b.getWidth(),b.getHeight(),0.5f,0.5f,b.getTheta());
			}
	}


    private void drawBoxBoss(){
        if (myBoxboss.isVISIBLE()){

            if (myBoxboss.gotHit()){
                spriteBatch.draw(boxgotHit, myBoxboss.getX(), myBoxboss.getY(), myBoxboss.getWidth(), myBoxboss.getHeight());
            }else
            if (myBoxboss.isPREPARE()){
                spriteBatch.draw(boxchargeAnimation.getKeyFrame(myBoxboss.getRunTime()/8), myBoxboss.getX(), myBoxboss.getY(), myBoxboss.getWidth(), myBoxboss.getHeight());
            } else {
                spriteBatch.draw(boxchargeAnimation.getKeyFrame(0), myBoxboss.getX(), myBoxboss.getY(), myBoxboss.getWidth(), myBoxboss.getHeight());
            }

        }
    }




	public void dispose(){
		spriteBatch.dispose();
		shapeRenderer.dispose();
	}

}
