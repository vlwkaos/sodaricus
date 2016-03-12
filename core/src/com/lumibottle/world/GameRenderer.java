package com.lumibottle.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lumibottle.gameobjects.Squirrel;
import com.lumibottle.helper.AssetLoader;

/**
 * Created by MG-UP on 2016-03-10.
 */
public class GameRenderer {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;


    private GameWorld myWorld;
    private OrthographicCamera cam;

    private int gameHeight;

    private float midPointY;
    private float bgOffset;

    //GO
    private Squirrel mySquirrel;

    //ASSET
    private TextureRegion squirrelDown;
    private Animation squirrelAnimation;
    private Animation baconAnimation;


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
        Gdx.gl.glClearColor(100, 100,100, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw with shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.end();

        spriteBatch.begin();
        spriteBatch.draw(background,0,bgOffset);
        Gdx.app.log("runTime",runTime+"");


        spriteBatch.draw(baconAnimation.getKeyFrame(runTime), (mySquirrel.getX()-3),
                (mySquirrel.getY()-3), (mySquirrel.getWidth()+12) / 2.0f,
               (mySquirrel.getHeight()+12) / 2.0f, mySquirrel.getWidth(), mySquirrel.getHeight(),
                1, 1, mySquirrel.getRotation());

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


        spriteBatch.end();

    }

    private void initGameObjects(){
        mySquirrel = myWorld.getMySquirrel();

    }

    private void initAsset(){
        squirrelDown = AssetLoader.sqdown;
        squirrelAnimation = AssetLoader.sqAnimation;
        baconAnimation = AssetLoader.baconAnimation;

        star1 = AssetLoader.star1;
        star2 = AssetLoader.star2;
        background = AssetLoader.spacebg;

    }

}
