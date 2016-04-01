package com.lumibottle.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.lumibottle.gameobjects.Bullet;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.enemies.Bomb;
import com.lumibottle.gameobjects.enemies.LaserCrayon;
import com.lumibottle.gameobjects.enemies.Mustache;
import com.lumibottle.gameobjects.ProgressHandler;
import com.lumibottle.gameobjects.enemies.RoadRoller;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.gameobjects.Star;
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


    //ASSET
    private TextureRegion squirrelDown;
    private Animation squirrelAnimation;
    private Animation baconAnimation;
    private TextureRegion gb;
    private TextureRegion roadroller;
	private TextureRegion bomb;
    private TextureRegion mustacheIdle;
    private Animation mustacheAnimation;

    private TextureRegion star1,star2;
    private TextureRegion background;

	private TextureRegion bluecrayon;



    public GameRenderer(GameWorld myWorld, int gameHeight, float midPointY){
        Gdx.app.log("GameRenderer", "created");
        this.myWorld = myWorld;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        bgOffset = (256-gameHeight)/(-2);

        //camera
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 240, gameHeight);

        //renderer
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(cam.combined);


        initGameObjects();
        initAsset();
        }



    public void render(float runTime){
        Gdx.gl.glClearColor(100, 100, 100, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        spriteBatch.begin();
        spriteBatch.draw(background, 0, bgOffset);
        //Gdx.app.log("runTime",runTime+"");
		//rendering order
		drawStars();
		drawBacon(runTime);
		/*
		Draw enemies below here
		 */
        drawRoadRollers();
        drawMustaches();
		drawBlueCrayons();
		drawBombs();


		//main
	    drawSquirrel();
	    drawBullets();

	    //fx
	    drawFXs();

        spriteBatch.end();



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
    }
    private void initAsset(){
        squirrelDown = AssetHelper.sqdown;
        squirrelAnimation = AssetHelper.sqAnimation;
        baconAnimation = AssetHelper.baconAnimation;

        gb = AssetHelper.greenBullet;

        roadroller = AssetHelper.roadroller;
	    bomb = AssetHelper.tanklorry;
        mustacheIdle = AssetHelper.mustaches[4];
        mustacheAnimation = AssetHelper.mustacheAnim;

		bluecrayon = AssetHelper.bluecrayon;

        star1 = AssetHelper.star1;
        star2 = AssetHelper.star2;
        background = AssetHelper.spacebg;

    }

	/*
		Drawing methods
	 */

	private void drawSquirrel(){
		//draw main actor
		if (mySquirrel.isShooting()) {
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
		    spriteBatch.draw(f.getAnimation().getKeyFrame(f.getRunTime()),f.getX(),f.getY());
	    }
    }


    private void drawRoadRollers(){
		for (RoadRoller r: myRoadRollers) {
			if (r.isVISIBLE()) {

				r.getParticle().setPosition(r.getX()+2*r.getWidth()/3f,r.getY()+r.getHeight()/2f);
				r.getParticle().update(Gdx.graphics.getDeltaTime());
				r.getParticle().draw(spriteBatch);
				if (r.getParticle().isComplete())
					r.getParticle().reset();
				spriteBatch.draw(roadroller, r.getX(), r.getY());


			}
		}
	}

	private void drawBombs(){
		for (Bomb b: myBombs) {
			if (b.isVISIBLE()) {

				if (!b.isExploding())
				spriteBatch.draw(bomb, b.getX(), b.getY());
				b.getParticle().setPosition(b.getX()+b.getWidth()/2f,b.getY()+b.getHeight());
				b.getParticle().update(Gdx.graphics.getDeltaTime());
				b.getParticle().draw(spriteBatch);
				if (b.getParticle().isComplete())
					b.getParticle().reset();
			}
		}
	}

    private void drawMustaches(){
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
            }
        }
    }

	private void drawBlueCrayons(){
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

	public void dispose(){
		spriteBatch.dispose();
		shapeRenderer.dispose();
	}

}
