package com.lumibottle.game;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.screen.GameScreen;


public class GameMain extends Game implements ApplicationListener {



    @Override
    public void create() {
        Gdx.app.log("GameMain", "created");
        AssetHelper.load();

        Gdx.input.setCatchBackKey(true);

        setScreen(new GameScreen());
        //switching screen needs disposal too.
    }


    @Override
    public void dispose() {
        super.dispose();
        AssetHelper.dispose();
    }

}