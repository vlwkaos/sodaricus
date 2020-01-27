package com.lumibottle.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

    private RelativeLayout layout;

    private final static int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set what initilize does
        //these need to come before.. for compatibility
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        //
        super.onCreate(savedInstanceState);

        //setting main layout
        layout = new RelativeLayout(this);


        //setup gameview
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        View gameView = initializeForView(new GameMain(), config);

        //add both views
        layout.addView(gameView);

        Gdx.app.log("AndroidLauncher", "app started--");
        setContentView(layout);


//		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new GameMain(this), config);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        // Pause the AdView.

        super.onPause();
    }

    @Override
    public void onDestroy() {
        // Destroy the AdView.

        super.onDestroy();
    }


}
