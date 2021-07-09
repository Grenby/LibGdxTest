package com.mygdx.projects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Logger;

public class Resource{

    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();

    private static final String font = "fonts/font.ttf";

    private static Resource res;

    private static final Logger log = new Logger("Resource", Application.LOG_NONE);

    private static boolean initRes = false;

    private final AssetManager assetManager;
    private final Skin uiSkin;

    private Resource(){
        assetManager = new AssetManager();

        AssetManager manager = this.assetManager;
        manager.load("source/uiskin.json", Skin.class,
                new SkinLoader.SkinParameter("source/uiskin.atlas"));
        while (!manager.update(10)) {
            log.info("load...");
        }
        uiSkin = manager.get("source/uiskin.json");

    }

    public void loadFont(){
        AssetManager manager = this.assetManager;
        if (manager.isLoaded(font))
            return;
        //set loader
        FileHandleResolver resolver =new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class,new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class,".ttf",new FreetypeFontLoader(resolver));

        //load font
        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = font;
        mySmallFont.fontParameters.size = 20;
        manager.load(font, BitmapFont.class, mySmallFont);
        while (!manager.update(10)){
            log.info("load fonts...");
        }
        log.info("font loaded");
    }

    public static AssetManager manager(){
        return res.assetManager;
    }

    public static void init(){
        if (!initRes){
            initRes = true;
            res = new Resource();
        }
    }

    public static Resource instance(){
        return res;
    }

    public static BitmapFont getFont(){
        return res.uiSkin.getFont(font);
    }

    public static Skin getUISkin(){
        return res.uiSkin;
    }

}
