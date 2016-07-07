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

	final public static float gameWidth = 240;//fixed width
	final public static float gameHeight = Gdx.graphics.getHeight() / (Gdx.graphics.getWidth()/gameWidth);
	final public static int midPointY =(int) (gameHeight/2);
	/*
    *  set screen size, set mid point,
    *  introduce GameWorld and GameRenderer, pass Gameworld to Renderer
    *  does not interfere with any actual work.
    *  call in inputhandler
    * */

	public GameScreen(){
		runTime = 0;

		Gdx.app.log("GameScreen", "gameHeight="+gameHeight);

		myWorld = new GameWorld();
		myRenderer = new GameRenderer(myWorld, (int) gameHeight);

		Gdx.input.setInputProcessor(new InputHelper(myWorld));
	}


	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		runTime+=delta;

		//state is in GameWorld, because if it is in here.. we have to stop the world.
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
