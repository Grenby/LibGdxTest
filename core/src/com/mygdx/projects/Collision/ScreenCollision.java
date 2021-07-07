package com.mygdx.projects.Collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ScreenCollision implements Screen,InputInterface {

    private World world=new World();
    private ShapeRenderer renderer=new ShapeRenderer();
    private ArrayList<Vector2> vectors=new ArrayList<>();
    private Vector2 p1=new Vector2(0,0);
    private Vector2 p2=new Vector2(0,0);
    private boolean point=false;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new Input(this));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.render(renderer);
        if (!p1.isZero()||!p2.isZero()){
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.line(p1,p2);
            renderer.end();
        }
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

    @Override
    public void addPoint(float x, float y, int b) {
        if (b==0) {
            float width = 20;
            float height = 20;
            world.addShape(new Shape(new float[]{x - width / 2, y - height / 2, x + width / 2, y + height / 2}));
        }else if (b==1){
            if (!point) {
                p1.set(x, y);
                p2.set(x,y);
            }else {
                p2.set(x,y);
                world.rayCast(p1.x,p1.y,p2.x,p2.y);
            }
            point=!point;
        }
    }

    @Override
    public void press() {
        for (int i = 0; i <50000 ; i++) {
            final float x= MathUtils.random(0,Gdx.graphics.getWidth());
            final float y=MathUtils.random(0,Gdx.graphics.getHeight());
            world.addShape(new Shape(new float[]{
                    x-5,y-5,x+5,y+5
            }));
        }
        p1.set(0,0);
        long time=System.currentTimeMillis();
        for (Shape s:world.shapes
             ) {
            p2.set(s.getAaBb().getX1(),s.getAaBb().getY1());
            world.rayCast(p1.x,p1.y,p2.x,p2.y);
        }
        System.out.println(System.currentTimeMillis()-time);
    }

    @Override
    public void move(float x, float y) {
        if (point)p2.set(x,y);
    }
}
