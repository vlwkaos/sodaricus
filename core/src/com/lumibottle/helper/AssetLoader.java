package com.lumibottle.helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
    public static Texture vfxTexture;
    public static Texture enemyTexture;

    /*
        Texture Region
     */
    public static TextureRegion spacebg, star1, star2, star3;

    public static TextureRegion greenBullet, pinkBullet;
    public static TextureRegion[] gbCrash,pbCrash; // 4 or 3 frames
    public static Animation gbCrashAnim, pbCrashAnim;//animation for bullet hit

    public static TextureRegion[] explosion1;
    public static Animation explosionAnim1;

    public static void load(){
        /*texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, Texture.TextureFilter.Nearest);
*/
    }

    public static void dispose(){
     /*
        texture.dispose();
        dead.dispose();
        flap.dispose();
        coin.dispose();

        font.dispose();
        shadow.dispose();
    */
    }
}
