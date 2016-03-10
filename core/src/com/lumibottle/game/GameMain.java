package com.lumibottle.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


public class GameMain extends Game {

	@Override
	public void create () {
		Gdx.app.log("MyGdxGame", "created");
		AssetLoader.load();
		setScreen(new GameScreen());
		//switching screen needs disposal too.
	}


	@Override
	public void dispose(){
		super.dispose();
		AssetLoader.dispose();
	}

}