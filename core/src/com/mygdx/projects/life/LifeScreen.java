package com.mygdx.projects.life;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.projects.Utils.FPSCounter;
import com.mygdx.projects.Utils.Fonts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LifeScreen implements Screen, InputProcessor {

    private static final Matrix4 MAT4_IDT = new Matrix4().idt();

    private final Set<Integer> keysForUpdate = new HashSet<>(Arrays.asList(
            Input.Keys.W,Input.Keys.D,Input.Keys.S,Input.Keys.A,Input.Keys.UP,Input.Keys.DOWN
    ));

    private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    private final ShapeRenderer renderer = new ShapeRenderer();
    private final SpriteBatch batch = new SpriteBatch();
    private final int SIZE = 4;
    private int step = 0;

    private final Set<Integer> keyPress = new HashSet<>();
    private final Vector2 mousePos = new Vector2();
    private boolean mouseDown = false;
    private int scroll = 0;
    private final Field f = new Field(1000,1000);
    private final Vector2 fieldPos = new Vector2(-f.width*SIZE/2.f,-f.height*SIZE/2.f);

    private final FPSCounter fpsCounter = new FPSCounter(10);
    private final BitmapFont font = Fonts.getFonts();


    private boolean updateField = false;

    private void update(float delta){

        fpsCounter.update(delta);

        final float DELTA_ZOOM = 1.1f * delta;
        final float DELTA_CAMERA = 1000f * delta;
        final float minZoom = 0.1f;
        final float maxZoom = 10;
        if (scroll !=0 ){
            camera.zoom+=scroll*DELTA_ZOOM;
            scroll =0;
        }
        if (keyPress.contains(Input.Keys.DOWN) && camera.zoom < maxZoom) {
            camera.zoom += DELTA_ZOOM;
        }
        if (keyPress.contains(Input.Keys.UP) && camera.zoom > minZoom)
            camera.zoom -= DELTA_ZOOM;

        if (keyPress.contains(Input.Keys.W))
            camera.position.add(0,DELTA_CAMERA,0);
        if (keyPress.contains(Input.Keys.D))
            camera.position.add(DELTA_CAMERA,0,0);
        if (keyPress.contains(Input.Keys.S))
            camera.position.add(0,-DELTA_CAMERA,0);
        if (keyPress.contains(Input.Keys.A))
            camera.position.add(-DELTA_CAMERA,0,0);

        camera.update();


        if (updateField) {
            step ++;
            if (step == 10) {
                step = 0;
                f.update();
            }
        }

    }

    float getCameraWidth(){
        return 2*Math.abs(camera.frustum.planePoints[0].x);
    }

    float getCameraHeight(){
        return 2*Math.abs(camera.frustum.planePoints[0].y);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keysForUpdate.contains(keycode)) {
            keyPress.add(keycode);
            return true;
        }
        switch (keycode){
            case Input.Keys.ESCAPE:
                dispose();
                Gdx.app.exit();
                break;
            case Input.Keys.F:
                updateField = !updateField;
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keysForUpdate.contains(keycode)) {
            keyPress.remove(keycode);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mouseDown = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseDown = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mousePos.set(screenX,Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePos.set(screenX,Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        scroll = amount;
        return true;
    }

    @Override
    public void show() {

        Pixmap pixmap=new Pixmap(16,16,Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        //pixmap.drawCircle(8,8,8);
        pixmap.drawLine(8,0,8,16);
        pixmap.drawLine(0,8,16,8);

        Cursor cursor = Gdx.graphics.newCursor(pixmap,8,8);
        Gdx.graphics.setCursor(cursor);


        MAT4_IDT.set(renderer.getProjectionMatrix());
        Gdx.input.setInputProcessor(this);
        for (int i=0;i<f.height*f.height / 10;i++){
            int x =(int)(Math.random() * f.width);
            int y =(int)(Math.random() * f.height);
            f.set(x,y,1);
        }
//        f.set(50,50,1);
//        f.set(50,49,1);
//        f.set(50,48,1);
//        f.set(49,48,1);
//        f.set(48,49,1);

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);



        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.GRAY);
//        for (float i=0; i <= f.height;i++){
//            renderer.line(fieldPos.x,fieldPos.y + i*SIZE,fieldPos.x+SIZE*f.width,fieldPos.y+i*SIZE);
//        }
//        for (float i=0; i <= f.width;i++){
//            renderer.line(fieldPos.x + i*SIZE,fieldPos.y,fieldPos.y+i*SIZE,fieldPos.x+SIZE*f.height);
//        }
        renderer.line(fieldPos.x,fieldPos.y,fieldPos.x + SIZE* f.width,fieldPos.y);
        renderer.line(fieldPos.x ,fieldPos.y + f.height * SIZE,fieldPos.x + SIZE* f.width,fieldPos.y + f.width * SIZE);

        renderer.line(fieldPos.x+ SIZE * f.width,fieldPos.y,fieldPos.x + SIZE * f.width,fieldPos.y+ f.height * SIZE);
        renderer.line(fieldPos.x,fieldPos.y,fieldPos.x,fieldPos.y+ f.height * SIZE);


        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.RED);
        float w = getCameraWidth()/2;
        float h = getCameraHeight()/2;
        for (int i=0;i<f.width;i++){
            for (int j=0;j<f.height;j++){
                if (f.field[i][j]==1){
                    float x = fieldPos.x + i*SIZE + SIZE/2.f;
                    float y = fieldPos.y + j*SIZE + SIZE/2.f;
                    if (x>=camera.position.x - w && x<= camera.position.x +w && y<=camera.position.y+h && y>= camera.position.y - h)
                        renderer.rect(x,y,SIZE,SIZE);
                }
            }
        }
        renderer.end();


        renderer.setProjectionMatrix(MAT4_IDT);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.circle(mousePos.x,mousePos.y,20);
        renderer.end();

        batch.begin();
        font.draw(batch,Float.toString(fpsCounter.getFps()),0,Gdx.graphics.getHeight());
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
