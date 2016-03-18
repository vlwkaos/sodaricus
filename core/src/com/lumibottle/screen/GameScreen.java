package com.lumibottle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.lumibottle.helper.InputHelper;
import com.lumibottle.world.GameRenderer;
import com.lumibottle.world.GameWorld;

/**
 * Created by MG-POW on 2016-03-10.
 */
public class GameScreen implements Screen{

	private GameWorld myWorld;
	private GameRenderer myRenderer;
	private float runTime;


	/*
    *  set screen size, set mid point,
    *  introduce GameWorld and GameRenderer, pass Gameworld to Renderer
    *  does not interfere with any actual work.
    *  call in inputhandler
    * */

	public GameScreen(){
		runTime = 0;
		float screenWidth = Gdx.graphics.getWidth();//device pixel
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 240;//fixed width
		float gameHeight = screenHeight / (screenWidth / gameWidth);

		Gdx.app.log("screenwidth", ""+screenWidth);

		Gdx.app.log("screenHeight", ""+screenHeight);

		Gdx.app.log("gameHeight", ""+gameHeight);

		int midPointY = (int) (gameHeight / 2);

		myWorld = new GameWorld(midPointY);
		myRenderer = new GameRenderer(myWorld, (int) gameHeight, midPointY);

		Gdx.input.setInputProcessor(new InputHelper(myWorld));
	}


	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		runTime+=delta;
		myWorld.update(delta);
		myRenderer.render(runTime);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		myRenderer.dispose();
	}
}
