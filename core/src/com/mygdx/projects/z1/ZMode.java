package com.mygdx.projects.z1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ZMode implements Screen {

    static int width = Gdx.graphics.getWidth();
    static  int height = Gdx.graphics.getHeight();
    static float perPixel = 0.1f;
    OrthographicCamera camera = new OrthographicCamera(width,height);
    SpriteBatch batch = new SpriteBatch();
    Stage stage = new Stage();

    @Override
    public void show() {

        stage.setDebugAll(true);
        stage.addActor(new Entity());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.draw();

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
