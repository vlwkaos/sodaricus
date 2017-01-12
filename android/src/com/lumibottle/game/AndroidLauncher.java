package com.lumibottle.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.lumibottle.helper.PlayServices;

public class AndroidLauncher extends AndroidApplication implements PlayServices {

    private GameHelper gameHelper;
    private AdView mAdView;
    private AdRequest.Builder adRequestBuilder;
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

        //google play game service
        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(false);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {
            }

            @Override
            public void onSignInSucceeded() {
            }
        };

        gameHelper.setup(gameHelperListener);
        //

        //setting main layout
        layout = new RelativeLayout(this);


        //setup gameview
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        View gameView = initializeForView(new GameMain(this), config);

        //add both views
        layout.addView(gameView);

        showAd();
        hideAd();

        Gdx.app.log("AndroidLauncher", "app started--");
        setContentView(layout);


//		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new GameMain(this), config);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (gameHelper != null)
            gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (gameHelper != null)
            gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (gameHelper != null)
            gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume the AdView.
        if (mAdView != null)
            mAdView.resume();
    }

    @Override
    public void onPause() {
        // Pause the AdView.
        if (mAdView != null)
            mAdView.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // Destroy the AdView.
        if (mAdView != null)
            mAdView.destroy();
        super.onDestroy();
    }


    @Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void unlockAchievement(int num) {
        switch (num) {
            case 0:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_what_does_red_soda_taste_like));
                break;
            case 1:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_this_is_not_tetris_));
                break;
            case 2:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_entangled_chaos_));
                break;
            case 3:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_that_is_totally_not_a_smartphone_));
                break;
            case 4:
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_lucky));
                break;
        }

    }

    @Override
    public void submitScore(int highScore) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_high_scores), highScore);
        }
    }

    @Override
    public void showAchievement() {
        if (isSignedIn()) {

        } else {
            signIn();
        }
    }

    @Override
    public void showScore() {
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_high_scores)), requestCode);
        } else {
            signIn();
        }
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }


    @Override
    public void showAd() {
        handler.sendEmptyMessage(SHOW_ADS);
    }

    @Override
    public void hideAd() {
        handler.sendEmptyMessage(HIDE_ADS);
    }


    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_ADS: {
                    _showAd();
                    break;
                }
                case HIDE_ADS: {
                    _hideAd();
                    break;
                }
            }
        }
    };

    private void _showAd() {
        //setup adview
        if (mAdView == null) {
            mAdView = new AdView(this);
            mAdView.setAdSize(AdSize.SMART_BANNER);
            mAdView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        }
        if (adRequestBuilder == null) {
            adRequestBuilder = new AdRequest.Builder();
            adRequestBuilder.addTestDevice("C59742048F1AC7428A402544237A1753");
        }

        RelativeLayout.LayoutParams adParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mAdView.loadAd(adRequestBuilder.build());
        mAdView.setVisibility(View.VISIBLE);
        layout.addView(mAdView, adParams);
    }

    private void _hideAd() {
        if (mAdView != null) {
            mAdView.setVisibility(View.GONE);
            layout.removeView(mAdView);
        }
    }

}
