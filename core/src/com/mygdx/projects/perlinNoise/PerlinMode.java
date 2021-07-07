package com.mygdx.projects.perlinNoise;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class PerlinMode extends InputAdapter implements Screen {

    static final float CELL_SIZE = 30;
    static final int WIDTH = Gdx.graphics.getWidth();
    static final int HEIGHT = Gdx.graphics.getHeight();

    private final ShapeRenderer renderer = new ShapeRenderer();

    Pixmap mep = new Pixmap(WIDTH, HEIGHT, Pixmap.Format.RGBA8888);
    Texture t;
    SpriteBatch batch = new SpriteBatch();

    PerlinNoise noise = new PerlinNoise();

    Color color = new Color();

    Color getColor(float c) {
        color.r = c;
        color.g = c;
        color.b = c;
        color.a = 1;
        return color;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        }
        return true;
    }

    @Override
    public void show() {
        System.out.println(HEIGHT);
        System.out.println(WIDTH);
        Gdx.input.setInputProcessor(this);
        int cellSize = 100;

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                float x = (float) i / cellSize;
                float y = (float) j / cellSize;
                float c = noise.getNoise(x, y, 8, 0.5f);
                mep.setColor(getColor(c));
                mep.drawPixel(i, j);
            }
        }

        t = new Texture(mep);
        mep.dispose();
        System.out.println(1);
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        batch.draw(t, 0, 0);
        batch.end();


        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
//        float x0= WIDTH/2f;
//        float y0= HEIGHT/2f;
//        for (int i=0;i<NUM;i++){
//            renderer.line(x0,y0,x0+100*vectors[i].x,y0+100*vectors[i].y);
//        }
        renderer.end();


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
        t.dispose();
    }

}
