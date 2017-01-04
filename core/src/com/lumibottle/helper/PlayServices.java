package com.lumibottle.helper;

/**
 * Created by MG on 2017-01-04.
 */

public interface PlayServices {
    public void signIn();
    public void signOut();
    public void unlockAchievement(int num);
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}
