package com.mygdx.projects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.mygdx.projects.mazeGen.MenuMode;
import com.mygdx.projects.perlinNoise.PerlinMode;
import com.mygdx.projects.perlinNoise.PerlinShaderMode;

public class Start extends Game {

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Resource.WIDTH = width;
        Resource.HEIGHT = height;
    }

    @Override
    public void create() {
        Resource.init();
        //Resource.instance().loadDefaultTextures();
        setScreen(new PerlinShaderMode());
    }
}
