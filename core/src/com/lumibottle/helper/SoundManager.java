package com.lumibottle.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by MG-POW on 2016-07-19.
 */

public class SoundManager {

    private static SoundManager ourInstance = new SoundManager();
    public static SoundManager getInstance() {
        return ourInstance;
    }


    final private float MAX_VOL = 0.4f;

    final public static int KILL = 0;
    final public static int HIT = 1;
    final public static int DEAD = 2;
    final public static int SELECT = 3;
    final public static int JUMP = 4;
    final public static int HURT = 5;
    final public static int FIRE = 6;
    final public static int THRO = 7;
    final public static int BOMB = 8;
    final public static int POWU = 9;



    private static Sound hit;
    private static Sound kill;
    private static Sound dead;
    private static Sound select;
    private static Sound jump;
    private static Sound hurt;
    private static Sound fire;
    private static Sound thro;
    private static Sound bomb;
    private static Sound powup;

    private static Music bg;

    private Preferences preferences;

    protected Preferences getPrefs() {
        if(preferences==null){
            preferences = Gdx.app.getPreferences("prefs");
        }
        return preferences;
    }

    private static float mute;

    private SoundManager(){
        getPrefs().flush();
        mute = getPrefs().getFloat("mute", MAX_VOL);
        initSound();

    }

    // play() mute filter

    private void initSound(){
        jump = Gdx.audio.newSound(Gdx.files.internal("data/sound/jump.wav"));
        hurt = Gdx.audio.newSound(Gdx.files.internal("data/sound/hurt.wav"));
        hit = Gdx.audio.newSound(Gdx.files.internal("data/sound/hit.wav"));
        kill = Gdx.audio.newSound(Gdx.files.internal("data/sound/kill.wav"));
        dead = Gdx.audio.newSound(Gdx.files.internal("data/sound/dead.wav"));
        select = Gdx.audio.newSound(Gdx.files.internal("data/sound/select.wav"));

        fire =  Gdx.audio.newSound(Gdx.files.internal("data/sound/fire.wav"));
        thro =  Gdx.audio.newSound(Gdx.files.internal("data/sound/throw.wav"));
        bomb = Gdx.audio.newSound(Gdx.files.internal("data/sound/bomb.wav"));
        powup = Gdx.audio.newSound(Gdx.files.internal("data/sound/powerup.wav"));

        bg = Gdx.audio.newMusic(Gdx.files.internal("data/sound/soda.wav"));
        bg.setLooping(true);
        bg.setVolume(mute);
    }


    void dispose(){
        hit.dispose();
        kill.dispose();
        dead.dispose();
        select.dispose();
        jump.dispose();
        hurt.dispose();

        fire.dispose();
        thro.dispose();
        bomb.dispose();
        powup.dispose();

        bg.dispose();
    }

    public void playBGM(){
        if (!bg.isPlaying())
            bg.play();
    }

    public void toggleBGM(){
        if (bg.isPlaying())
            bg.pause();
        else
            bg.play();
    }

    public void play(int id){
        switch(id){
            case KILL: kill.play(mute); break;
            case HIT: hit.play(mute); break;
            case JUMP: jump.play(mute); break;
            case DEAD: dead.play(mute); break;
            case HURT: hurt.play(mute); break;
            case SELECT: select.play(mute); break;
            case FIRE: fire.play(mute); break;
            case THRO: thro.play(mute); break;
            case BOMB: bomb.play(mute); break;
            case POWU: powup.play(mute); break;
        }

    }

    public void toggleMute(){
        if (mute == MAX_VOL)
            mute = 0.0f;
         else {
            mute = MAX_VOL;
            SoundManager.getInstance().play(SoundManager.SELECT);
        }

        bg.setVolume(mute*2);

        getPrefs().putFloat("mute", mute);
        getPrefs().flush();
    }

    public void setMute(float vol){
        mute = vol;
        bg.setVolume(mute*2);
    }

    public String getMuteState(){
        if (mute == MAX_VOL)
            return "ON";
         else
            return "OFF";


    }

}
