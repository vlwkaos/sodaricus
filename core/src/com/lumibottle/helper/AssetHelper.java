package com.lumibottle.helper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created by MG-UP on 2016-03-10.
 */
public class AssetHelper {

    /*
        Textures - logical mapping with TextureRegion
     */
    public static Texture backgroundTexture;
    public static Texture starTexture;
    public static Texture sodaTexture;
    public static Texture squirrelTexture;
    public static Texture bacon;
    public static Texture limeexplosionTexture;
	public static Texture bombexplosionTexture;
    public static Texture enemyTexture;
    public static Texture mustacheTexture;
    public static Texture bluecrayonTexture;
	public static Texture redlaserTexture;
	public static Texture cowboyhatTexture;
	public static Texture cowboythrowTexture;
	public static Texture eyeTexture;
	public static Texture holeTexture;
	public static Texture deadTexture;
	//16

    /*
        Texture Region
     */
    public static TextureRegion spacebg, star1, star2;
    public static TextureRegion greenBullet, pinkBullet;
	public static TextureRegion[] eyes;
	public static Animation eyeAnim;


    public static TextureRegion sqdown, sqmid, squp;
    public static Animation sqAnim;
    public static TextureRegion bacon1, bacon2;
    public static Animation baconAnim;


	//Enemies
    //TODO: collage texture into one png file
    public static TextureRegion roadroller;
	public static TextureRegion tanklorry;

    public static TextureRegion[] mustaches;
    public static Animation mustacheAnim;

	public static TextureRegion bluecrayon;

	public static TextureRegion[] cowboyhats;
	public static Animation cowboyhatsAnim;
	public static TextureRegion[] cowboyhatsspawn;
	public static Animation cowboyhatsspawnAnim;

	public static TextureRegion[] cowboythrow;
	public static Animation cowboythrowAnim;

	public static TextureRegion hole;

	//FX
	public static TextureRegion[] explosion1;
	public static Animation explosionAnim1;

	public static TextureRegion[] explosion2;
	public static Animation explosionAnim2;

	public static TextureRegion[] redlaser;
	public static Animation redlaserinit;
	public static Animation redlaserAnim;

	public static TextureRegion[] deadplosion;
    public static Animation deadAnim;



	/*
        Particles
     */
    public static ParticleEffect rainbowParticle;
    public static ParticleEffectPool rainbowPool;
	public static ParticleEffect nitroParticle;
	public static ParticleEffectPool nitroPool;
	public static ParticleEffect energyParticle;
	public static ParticleEffectPool energyPool;
	public static ParticleEffect popcornParticle;
	public static ParticleEffectPool popcornPool;
	public static ParticleEffect sodaburstParticle;
	public static ParticleEffectPool sodaburstPool;
	public static ParticleEffect nitro2Particle;
	public static ParticleEffectPool nitro2Pool;


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
	    limeexplosionTexture.dispose();
	    bombexplosionTexture.dispose();
	    bluecrayonTexture.dispose();
	    redlaserTexture.dispose();
	    cowboyhatTexture.dispose();
	    eyeTexture.dispose();
	    holeTexture.dispose();
	    deadTexture.dispose();
	    cowboythrowTexture.dispose();
	    //16

//particle
	    rainbowPool.clear();
	    rainbowParticle.dispose();
	    nitroPool.clear();
	    nitroParticle.dispose();
        energyPool.clear();
	    energyParticle.dispose();
	    popcornPool.clear();
	    popcornParticle.dispose();
	    sodaburstPool.clear();
	    sodaburstParticle.dispose();
	    nitro2Pool.clear();
	    nitro2Particle.dispose();
    }

    private static void loadTextures() {
        //INIT TEXTURE
        //Game Objects
        squirrelTexture = new Texture(Gdx.files.internal("data/squirrel.png"));
        squirrelTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        bacon = new Texture(Gdx.files.internal("data/bacon.png"));
        bacon.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

	    eyeTexture = new Texture(Gdx.files.internal("data/eyes.png"));
	    eyeTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

	    //ENemy
        enemyTexture = new Texture(Gdx.files.internal("data/enemies.png"));
	    enemyTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        mustacheTexture = new Texture(Gdx.files.internal("data/rainbowmusta.png"));
	    mustacheTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
	    bluecrayonTexture = new Texture(Gdx.files.internal("data/bluepastel.png"));
	    bluecrayonTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


	    cowboythrowTexture = new Texture(Gdx.files.internal("data/cowboymotion.png"));
	    cowboythrowTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		holeTexture = new Texture(Gdx.files.internal("data/hole.png"));
	    holeTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        sodaTexture = new Texture(Gdx.files.internal("data/sodabullet.png"));
        sodaTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

	    cowboyhatTexture = new Texture(Gdx.files.internal("data/cowboyhat.png"));
		cowboyhatTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        //Aesthetic Objects/image
        backgroundTexture = new Texture(Gdx.files.internal("data/spacebg.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        starTexture = new Texture(Gdx.files.internal("data/stars.png"));
        starTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        //FX
        limeexplosionTexture = new Texture(Gdx.files.internal("data/gfx/limexplosion.png"));
        limeexplosionTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

	    redlaserTexture = new Texture(Gdx.files.internal("data/gfx/redlaser.png"));
	    redlaserTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

	    bombexplosionTexture = new Texture(Gdx.files.internal("data/gfx/bombexplosion.png"));
	    bombexplosionTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

	    deadTexture = new Texture(Gdx.files.internal("data/gfx/dead.png"));
	    deadTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //--------------------------------------------------------------------------------
        //INIT TEXTUREREGION
        //Game Objects
	    //Squirrel
        sqdown = new TextureRegion(squirrelTexture, 0, 0, 20, 20);
        sqmid = new TextureRegion(squirrelTexture, 20, 0, 20, 20);
        squp = new TextureRegion(squirrelTexture, 40, 0, 20, 20);
	    TextureRegion[] sqs = {squp, sqmid, sqdown};
	    sqAnim = new Animation(1 / 16f, sqs);
	    sqAnim.setPlayMode(Animation.PlayMode.NORMAL);

	    bacon1 = new TextureRegion(bacon, 0, 0, 32, 32);
	    bacon2 = new TextureRegion(bacon, 32, 0, 32, 32);
	    TextureRegion[] bacons = {bacon1, bacon2};
	    baconAnim = new Animation(1 / 8f, bacons);
	    baconAnim.setPlayMode(Animation.PlayMode.LOOP);

	    //For enemies
	    eyes = new TextureRegion[4];
	    for (int i=0;i<4;i++)
		    eyes[i] = new TextureRegion(eyeTexture,i*6,0,6,6);
	    eyeAnim = new Animation(1/15f, eyes);
	    eyeAnim.setPlayMode(Animation.PlayMode.LOOP);

        roadroller = new TextureRegion(enemyTexture, 0, 0, 20, 12);
		tanklorry = new TextureRegion(enemyTexture,21,0,32,14);

        mustaches = new TextureRegion[5];
        for (int i=0;i<5;i++)
            mustaches[i] = new TextureRegion(mustacheTexture,i*33,0,33,16);
        mustacheAnim = new Animation(1 / 30f, mustaches);
        mustacheAnim.setPlayMode(Animation.PlayMode.NORMAL);

	    bluecrayon = new TextureRegion(bluecrayonTexture);

	    cowboyhats = new TextureRegion[4];
	    for (int i=0;i<4;i++)
		    cowboyhats[i] = new TextureRegion(cowboyhatTexture,i*32,0,32,16);
	    cowboyhatsAnim = new Animation(1/30f, cowboyhats);
	    cowboyhatsAnim.setPlayMode(Animation.PlayMode.LOOP);

	    cowboyhatsspawn = new TextureRegion[4];
	    for (int i=0;i<4;i++)
		    cowboyhatsspawn[i] = new TextureRegion(cowboyhatTexture,(i+4)*32,0,32,16);
	    cowboyhatsspawnAnim = new Animation(1/30f,cowboyhatsspawn);
	    cowboyhatsspawnAnim.setPlayMode(Animation.PlayMode.NORMAL);


	    cowboythrow = new TextureRegion[4];
	    for (int i=0;i<4;i++)
		    cowboythrow[i] = new TextureRegion(cowboythrowTexture,i*32,0,32,48);
	    cowboythrowAnim = new Animation(1/30f, cowboythrow);
	    cowboythrowAnim.setPlayMode(Animation.PlayMode.NORMAL);


	    hole = new TextureRegion(holeTexture,0,0,32,32);

	    //
        greenBullet = new TextureRegion(sodaTexture, 0, 0, 16, 16);

        //Aesthetic Objects/image
        spacebg = new TextureRegion(backgroundTexture, 0, 0, 256, 256);
        star1 = new TextureRegion(starTexture, 0, 0, 9, 9);
        star2 = new TextureRegion(starTexture, 9, 0, 9, 9);


        //FX
        explosion1 = new TextureRegion[6];
        for (int i = 0; i < 6; i++)
            explosion1[i] = new TextureRegion(limeexplosionTexture, i * 17, 0, 17, 17);
        explosionAnim1 = new Animation(1 / 30f, explosion1);
        explosionAnim1.setPlayMode(Animation.PlayMode.NORMAL);

	    explosion2 = new TextureRegion[10];
	    for (int i = 0; i < 10; i++)
		    explosion2[i] = new TextureRegion(bombexplosionTexture, i * 40, 0, 40, 40);
	    explosionAnim2 = new Animation(1/30f, explosion2);
	    explosionAnim2.setPlayMode(Animation.PlayMode.NORMAL);

	    redlaser = new TextureRegion[7];
	    for (int i=0; i<7;i++) {
		    redlaser[i] = new TextureRegion(redlaserTexture, 0, i*30, 240, 30);
	        redlaser[i].flip(true,false);
	    }
	    redlaserinit = new Animation(1f,redlaser[0]);
	    redlaserinit.setPlayMode(Animation.PlayMode.NORMAL);
		redlaserAnim = new Animation(1/30f,redlaser);
        redlaserAnim.setPlayMode(Animation.PlayMode.NORMAL);


	    deadplosion = new TextureRegion[60];
	    for (int i=0;i<6;i++)
		    for (int j=0;j<10;j++)
		    deadplosion[i*10+j] = new TextureRegion(deadTexture,j*128,i*128,128,128);
	    deadAnim = new Animation(1/30f,deadplosion);
	    deadAnim.setPlayMode(Animation.PlayMode.NORMAL);



    }


    private static void loadParticles(){
        rainbowParticle = new ParticleEffect();
        rainbowParticle.load(Gdx.files.internal("data/particles/rainbow.p"),Gdx.files.internal("data/particles/"));
        rainbowPool = new ParticleEffectPool(rainbowParticle,0,10);

	    nitroParticle = new ParticleEffect();
	    nitroParticle.load(Gdx.files.internal("data/particles/nitro.p"),Gdx.files.internal("data/particles/"));
	    nitroPool = new ParticleEffectPool(nitroParticle,0,10);

	    nitro2Particle = new ParticleEffect();
	    nitro2Particle.load(Gdx.files.internal("data/particles/nitro2.p"),Gdx.files.internal("data/particles/"));
	    nitro2Pool = new ParticleEffectPool(nitro2Particle,0,10);



	    energyParticle = new ParticleEffect();
	    energyParticle.load(Gdx.files.internal("data/particles/energy.p"),Gdx.files.internal("data/particles/"));
	    energyPool = new ParticleEffectPool(energyParticle,0,10);


	    popcornParticle = new ParticleEffect();
	    popcornParticle.load(Gdx.files.internal("data/particles/popcorn.p"),Gdx.files.internal("data/particles/"));
	    popcornPool = new ParticleEffectPool(popcornParticle,0,10);

	    sodaburstParticle = new ParticleEffect();
	    sodaburstParticle.load(Gdx.files.internal("data/particles/sodaburst.p"),Gdx.files.internal("data/"));
		sodaburstPool = new ParticleEffectPool(sodaburstParticle,0,3);



    }

    private static void loadSounds() {

    }


}
