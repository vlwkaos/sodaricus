package com.lumibottle.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by MG-UP on 2016-03-10.
 */
public class GameRenderer {
    private ShapeRenderer shapeRenderer;


    private GameWorld myWorld;

    private int gameHeight;

    private float midPointY;

    public GameRenderer(GameWorld myWorld, int gameHeight, float midPointY){
        Gdx.app.log("GameRenderer", "created");
        this.myWorld = myWorld;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        shapeRenderer = new ShapeRenderer();
    }

    public void render(float runTime){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw with shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);
        shapeRenderer.end();

    }

}
