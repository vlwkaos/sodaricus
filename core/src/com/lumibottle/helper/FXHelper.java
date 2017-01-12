package com.lumibottle.helper;

import com.badlogic.gdx.audio.Sound;
import com.lumibottle.gameobjects.FX;

/**
 * Created by MG-POW on 2016-03-18.
 */
public class FXHelper {
    private static FXHelper ourInstance = new FXHelper();

    public static FXHelper getInstance() {
        return ourInstance;
    }


    private FX[] myFXs;

    private FXHelper() {
        myFXs = new FX[20];// max number of FXs to be drawn on screen at the moment
        for (int i = 0; i < myFXs.length; i++)
            myFXs[i] = new FX();
    }

    public void newFX(float x, float y, short animNo) {
        for (FX f : myFXs) {
            if (f.isREADY()) {
                switch (animNo) {
                    case FX.QUANTUM_EXPLOSION:
                        SoundManager.getInstance().play(SoundManager.DEAD);
                        break;
                    case FX.SODA_EXPLOSION:
                        SoundManager.getInstance().play(SoundManager.HIT);
                        break;
                    case FX.BOMB_EXPLOSION:
                        SoundManager.getInstance().play(SoundManager.BOMB);
                        break;
                    case FX.LASER_LINE:
                        SoundManager.getInstance().play(SoundManager.LASER_LINE);
                        break;
                    case FX.LASER_SHOT:
                        SoundManager.getInstance().play(SoundManager.LASER_SHOT);
                        break;
                    case FX.WARN:
                        SoundManager.getInstance().play(SoundManager.WARN);
                }

                f.reset(x, y, animNo);
                break;
            }
        }

    }

    public void newFX(float x, float y, float size, short animNo) {
        if (x + size < 0)
            return;

        for (FX f : myFXs) {
            if (f.isREADY()) {
                switch (animNo) {
                    case FX.QUANTUM_EXPLOSION:
                        SoundManager.getInstance().play(SoundManager.DEAD);
                        break;
                    case FX.SODA_EXPLOSION:
                        SoundManager.getInstance().play(SoundManager.HIT);
                        break;
                    case FX.POOF:
                        SoundManager.getInstance().play(SoundManager.KILL);
                        break;
                }

                f.reset(x, y, size, animNo);
                break;
            }
        }

    }

    public FX[] getMyFXs() {
        return myFXs;
    }
}
