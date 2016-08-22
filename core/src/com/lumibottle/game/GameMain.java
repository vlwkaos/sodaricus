package com.lumibottle.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lumibottle.helper.AssetHelper;
import com.lumibottle.screen.GameScreen;


public class GameMain extends Game {

    @Override
    public void create() {
        Gdx.app.log("GameMain", "created");
        AssetHelper.load();


        setScreen(new GameScreen());
        //switching screen needs disposal too.
    }


    @Override
    public void dispose() {
        super.dispose();
        AssetHelper.dispose();
    }

}