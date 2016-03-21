package com.lumibottle.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by MG-UP on 2016-03-10.
 */
public class AssetLoader {

    /*
        Textures - logical mapping with TextureRegion
     */
    public static Texture backgroundTexture;
    public static Texture starTexture;
    public static Texture sodaTexture;
    public static Texture squirrelTexture;
    public static Texture bacon;
    public static Texture vfxTexture;
    public static Texture enemyTexture;
    public static Texture mustacheTexture;
    /*
        Texture Region
     */
    public static TextureRegion spacebg, star1, star2;
    public static TextureRegion greenBullet, pinkBullet;

    public static TextureRegion sqdown, sqmid, squp;
    public static Animation sqAnimation;
    public static TextureRegion bacon1, bacon2;
    public static Animation baconAnimation;

    public static TextureRegion[] explosion1;
    public static Animation explosionAnim1;

    //TODO: collage texture into one png file
    public static TextureRegion roadroller;

    public static TextureRegion[] mustaches;
    public static Animation mustacheAnim;


    /*
        Particles
     */
    public static ParticleEffect rainbowParticle;
    public static ParticleEffectPool rainbowPool;
	public static ParticleEffect nitroParticle;
	public static ParticleEffectPool nitroPool;

    public static void load() {
        loadParticles();
        loadTextures();
        loadSounds();
    }

    public static void dispose() {
        backgroundTexture.dispose();
        starTexture.dispose();
        sodaTexture.dispose();
        squirrelTexture.dispose();
        bacon.dispose();
        enemyTexture.dispose();
        mustacheTexture.dispose();
        rainbowPool.clear();
		rainbowParticle.dispose();
		nitroPool.clear();
	    nitroParticle.dispose();
    }

    private static void loadTextures() {
        //INIT TEXTURE
        //Game Objects
        squirrelTexture = new Texture(Gdx.files.internal("data/squirrel.png"));
        squirrelTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        bacon = new Texture(Gdx.files.internal("data/bacon.png"));
        bacon.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        enemyTexture = new Texture(Gdx.files.internal("data/enemies.png"));
        mustacheTexture = new Texture(Gdx.files.internal("data/rainbowmusta.png"));


        sodaTexture = new Texture(Gdx.files.internal("data/sodabullet.png"));
        sodaTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Aesthetic Objects/image
        backgroundTexture = new Texture(Gdx.files.internal("data/spacebg.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        starTexture = new Texture(Gdx.files.internal("data/stars.png"));
        starTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        //FX
        vfxTexture = new Texture(Gdx.files.internal("data/limexplosion.png"));
        vfxTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //--------------------------------------------------------------------------------
        //INIT TEXTUREREGION
        //Game Objects
        sqdown = new TextureRegion(squirrelTexture, 0, 0, 20, 20);
        sqmid = new TextureRegion(squirrelTexture, 20, 0, 20, 20);
        squp = new TextureRegion(squirrelTexture, 40, 0, 20, 20);

        TextureRegion[] sqs = {squp, sqmid, sqdown};
        sqAnimation = new Animation(1 / 12f, sqs);
        sqAnimation.setPlayMode(Animation.PlayMode.LOOP);
        bacon1 = new TextureRegion(bacon, 0, 0, 32, 32);
        bacon2 = new TextureRegion(bacon, 32, 0, 32, 32);
        TextureRegion[] bacons = {bacon1, bacon2};
        baconAnimation = new Animation(1 / 8f, bacons);
        baconAnimation.setPlayMode(Animation.PlayMode.LOOP);


        roadroller = new TextureRegion(enemyTexture, 0, 8, 20, 12);

        mustaches = new TextureRegion[5];
        for (int i=0;i<5;i++)
            mustaches[i] = new TextureRegion(mustacheTexture,i*33,0,33,16);
        mustacheAnim = new Animation(1 / 30f, mustaches);
        mustacheAnim.setPlayMode(Animation.PlayMode.NORMAL);

        greenBullet = new TextureRegion(sodaTexture, 0, 0, 16, 16);


        //Aesthetic Objects/image
        spacebg = new TextureRegion(backgroundTexture, 0, 0, 256, 256);
        star1 = new TextureRegion(starTexture, 0, 0, 9, 9);
        star2 = new TextureRegion(starTexture, 9, 0, 9, 9);


        //FX
        explosion1 = new TextureRegion[6];
        for (int i = 0; i < 6; i++)
            explosion1[i] = new TextureRegion(vfxTexture, i * 17, 0, 17, 17);
        explosionAnim1 = new Animation(1 / 12f, explosion1);
        explosionAnim1.setPlayMode(Animation.PlayMode.NORMAL);


    }


    private static void loadParticles(){
        rainbowParticle = new ParticleEffect();
        rainbowParticle.load(Gdx.files.internal("data/rainbow.p"),Gdx.files.internal("data/"));
        rainbowPool = new ParticleEffectPool(rainbowParticle,0,10);

	    nitroParticle = new ParticleEffect();
	    nitroParticle.load(Gdx.files.internal("data/nitro.p"),Gdx.files.internal("data/"));
	    nitroPool = new ParticleEffectPool(nitroParticle,0,10);

    }

    private static void loadSounds() {

    }


}
