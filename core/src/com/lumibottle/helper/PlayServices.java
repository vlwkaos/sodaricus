package com.lumibottle.helper;

/**
 * Created by MG on 2017-01-04.
 */

public interface PlayServices {
    void signIn();
    void signOut();
    void unlockAchievement(int num);
    void submitScore(int highScore);
    void showAchievement();
    void showScore();
    boolean isSignedIn();
}
