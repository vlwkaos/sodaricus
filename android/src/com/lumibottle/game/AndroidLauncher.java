package com.lumibottle.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.lumibottle.helper.PlayServices;

public class AndroidLauncher extends AndroidApplication implements PlayServices {

	private GameHelper gameHelper;
	private final static int requestCode = 1;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed(){ }

			@Override
			public void onSignInSucceeded(){ }
		};

		gameHelper.setup(gameHelperListener);


		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new GameMain(this), config);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void signIn()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void unlockAchievement(int num)
	{
		switch (num){
			case 0:		Games.Achievements.unlock(gameHelper.getApiClient(),
					getString(R.string.achievement_what_does_red_soda_taste_like)); break;
			case 1: 	Games.Achievements.unlock(gameHelper.getApiClient(),
					getString(R.string.achievement_this_is_not_tetris_)); break;
			case 2: 	Games.Achievements.unlock(gameHelper.getApiClient(),
					getString(R.string.achievement_entangled_chaos_)); break;
			case 3: 	Games.Achievements.unlock(gameHelper.getApiClient(),
					getString(R.string.achievement_that_is_totally_not_a_smartphone_)); break;
			case 4: 	Games.Achievements.unlock(gameHelper.getApiClient(),
					getString(R.string.achievement_lucky)); break;
		}

	}

	@Override
	public void submitScore(int highScore)
	{
		if (isSignedIn())
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_high_scores), highScore);
		}
	}

	@Override
	public void showAchievement()
	{
		if (isSignedIn())
		{

		}
		else
		{
			signIn();
		}
	}

	@Override
	public void showScore()
	{
		if (isSignedIn())
		{
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
					getString(R.string.leaderboard_high_scores)), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn()
	{
		return gameHelper.isSignedIn();
	}
}
