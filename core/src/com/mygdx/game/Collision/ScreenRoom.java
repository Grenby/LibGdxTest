package com.mygdx.game.Collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ScreenRoom implements Screen,InputInterface {


    private World world=new World();
    private ShapeRenderer renderer=new ShapeRenderer();
    private ArrayList<Vector2> vectors=new ArrayList<>();
    private ArrayList<Vector2> vectors2=new ArrayList<>();
    private boolean p=false;
    private Vector2 light=new Vector2((float)Gdx.graphics.getWidth()/2,(float)Gdx.graphics.getHeight()/2);

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new Input(this));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.circle(light.x,light.y,5);
        if (vectors!=null){
            int s=vectors.size();
            for (int i = 0; i < s; i++) renderer.line(vectors.get(i),vectors.get((i+1)%s));
        }
        if (vectors2!=null){
            for (Vector2 v:vectors2) {
                renderer.line(light,v);
            }
        }
        renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        if (p){
            for (int i = 0; i <vectors2.size(); i++) {
                Vector2 v1=vectors2.get(i);
                Vector2 v2=vectors2.get((i+1)%vectors2.size());
                renderer.triangle(light.x,light.y,v1.x,v1.y,v2.x,v2.y);
            }
        }
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

    }

    @Override
    public void addPoint(float x, float y, int b) {
        if (b==0) vectors.add(new Vector2(x,y));
        else if (b==1) {
            if (vectors2!=null)vectors2.clear();
            light.set(x, y);
        }
    }

    @Override
    public void press() {
        p=true;
        if (vectors!=null){
            vectors2.clear();
            int s=vectors.size();
            for (int i = 0; i < s; i++) world.addShape(new Section(new float[]{
                    vectors.get(i).x,
                    vectors.get(i).y,
                    vectors.get((i+1)%s).x,
                    vectors.get((i+1)%s).y,
            }));
            Vector2 vv=new Vector2();
            for (Vector2 v:vectors
            ) {
                vectors2.add(world.rayCast1(light.x,light.y,v.x,v.y));
                vv.set(v).sub(light).scl(1000).rotateRad(0.01f).add(light);
                vectors2.add(world.rayCast1(light.x,light.y,vv.x,vv.y));
                vv.set(v).sub(light).scl(1000).rotateRad(-0.01f).add(light);
                vectors2.add(world.rayCast1(light.x,light.y,vv.x,vv.y));
            }
        }

    }

    @Override
    public void move(float x, float y) {
        light.set(x,y);
        if (p){
            press();
        }
    }

}
