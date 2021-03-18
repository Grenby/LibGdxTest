package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class TestScreen1 implements Screen {

    private PolygonRegion polygonRegion;
    private PolygonSpriteBatch polygonSpriteBatch;
    private SpriteBatch batch;

    @Override
    public void show() {
        Gdx.gl20.glClearColor(0,0,0,1);

        batch=new SpriteBatch();
        Pixmap pixmap=new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        polygonRegion=new PolygonRegion(new TextureRegion(new Texture(pixmap)),new float[]{0,0,100,0,100,100,0,200},new short[]{0,1,2,0,2,3});
        PolygonSprite polygonSprite = new PolygonSprite(polygonRegion);
        polygonSprite.setOrigin(200,200);
        polygonSpriteBatch=new PolygonSpriteBatch();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        polygonSpriteBatch.begin();
        polygonSpriteBatch.draw(polygonRegion,100,100);
        polygonSpriteBatch.end();
        batch.begin();
        batch.end();

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
