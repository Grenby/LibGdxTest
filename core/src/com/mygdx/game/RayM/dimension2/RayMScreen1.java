package com.mygdx.game.RayM.dimension2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class RayMScreen1 implements Screen {

    private int width= Gdx.graphics.getWidth();
    private int height=Gdx.graphics.getHeight();


    private Vector2 point=new Vector2();
    private View view=new View();
    private ShapeRenderer renderer=new ShapeRenderer();
    private OrthographicCamera camera=new OrthographicCamera(width,height);

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new Input(this));
        view.position.set(width/2f,height/2f);
        view.addFigure(new Circle(new Vector2(width/2f,height/2f),900));
        view.addFigure(new Polygon(new Segment[]{
                new Segment(100,100,200,200),
                new Segment(200,200,100,200),
                new Segment(100,200,100,100)}));
        view.addFigure(new Polygon(new Segment[]{
                new Segment(100,height,150,height),
                new Segment(150,height,150,100),
                new Segment(150,100,100,100),
                new Segment(100,100,100,height)}));

        camera.position.set(view.position.x,view.position.y,0);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        view.render(renderer);
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

    private void toWorldCoordinate(Vector2 v){
        v.y=height/2f-v.y+camera.position.y;
        v.x+=camera.position.x-width/2f;

    }

    void setPoint(float x,float y){
        point.set(x,y);
        toWorldCoordinate(point);
        view.direction.set(point.sub(view.position));
    }

    void move(float x,float y){
        view.position.set(x,y);
        toWorldCoordinate(view.position);
        camera.position.set(view.position.x,view.position.y,0);
        camera.update();
    }

}
