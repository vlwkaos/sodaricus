package com.lumibottle.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.lumibottle.gameobjects.Bullet;
import com.lumibottle.gameobjects.FX;
import com.lumibottle.gameobjects.Mustache;
import com.lumibottle.gameobjects.ProgressHandler;
import com.lumibottle.gameobjects.RoadRoller;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.gameobjects.Star;
import com.lumibottle.helper.AssetLoader;
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
    private Mustache[] myMustaches;
    private FX[] myFXs, enemFXs;


    //ASSET
    private TextureRegion squirrelDown;
    private Animation squirrelAnimation;
    private Animation baconAnimation;
    private TextureRegion gb;
    private TextureRegion roadroller;
    private TextureRegion mustacheIdle;
    private Animation mustacheAnimation;

    private TextureRegion star1,star2;
    private TextureRegion background;


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

        //draw with shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.end();

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




        //draw main actor
        if (mySquirrel.isShooting()) {
            spriteBatch.draw(squirrelAnimation.getKeyFrame(runTime), mySquirrel.getX(),
                    mySquirrel.getY(), mySquirrel.getWidth() / 2.0f,
                    mySquirrel.getHeight() / 2.0f, mySquirrel.getWidth(), mySquirrel.getHeight(),
                    1, 1, mySquirrel.getRotation());
        } else {
            spriteBatch.draw(squirrelDown, mySquirrel.getX(),
                    mySquirrel.getY(), mySquirrel.getWidth() / 2.0f,
                    mySquirrel.getHeight() / 2.0f, mySquirrel.getWidth(), mySquirrel.getHeight(),
                    1, 1, mySquirrel.getRotation());
        }

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
    }

    private void initAsset(){
        squirrelDown = AssetLoader.sqdown;
        squirrelAnimation = AssetLoader.sqAnimation;
        baconAnimation = AssetLoader.baconAnimation;

        gb = AssetLoader.greenBullet;

        roadroller = AssetLoader.roadroller;
        mustacheIdle = AssetLoader.mustaches[4];
        mustacheAnimation = AssetLoader.mustacheAnim;

        star1 = AssetLoader.star1;
        star2 = AssetLoader.star2;
        background = AssetLoader.spacebg;

    }

	/*
		Drawing methods
	 */
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

	private void drawBullets(){
		for (Bullet b:myBullets){
            if (b.isVISIBLE())
			spriteBatch.draw(gb,
					b.getX(), b.getY(),
					b.getWidth() / 2.0f,b.getHeight() / 2.0f,
					b.getWidth(), b.getHeight(),
					1, 1, b.getTheta()+90);

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

	public void dispose(){
		spriteBatch.dispose();
		shapeRenderer.dispose();
	}

}
