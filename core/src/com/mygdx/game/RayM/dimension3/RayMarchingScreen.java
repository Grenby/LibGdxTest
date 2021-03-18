package com.mygdx.game.RayM.dimension3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class RayMarchingScreen implements Screen {

    public static final int WIDTH = Gdx.graphics.getWidth();
    public static final int HEIGHT = Gdx.graphics.getHeight();

    private final PerspectiveCamera perspectiveCamera = new PerspectiveCamera(67f,WIDTH,HEIGHT);


    @Override
    public void show() {
        perspectiveCamera.getPickRay(0,0);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
