package com.mygdx.game.TestThread;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class StartScreen implements Screen {

    private Box2DDebugRenderer box2DDebugRenderer;
    private ShapeRenderer renderer;
    private OrthographicCamera camera;
    private MyWorld myWorld;
    private Ray ray;

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);

        box2DDebugRenderer=new Box2DDebugRenderer();
        renderer=new ShapeRenderer();

        camera=new OrthographicCamera(Constant.getCameraW(),Constant.getCameraW());
        camera.position.set(0,0,0);
        camera.update();

        myWorld=new MyWorld(1/60f);
        ray=new Ray();
        ray.cast();
    }

    @Override
    public void render(float delta) {
        myWorld.step(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        box2DDebugRenderer.render(myWorld.getWorld(),camera.combined);
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        ray.draw(renderer);
        renderer.end();
        ray.cast();
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
