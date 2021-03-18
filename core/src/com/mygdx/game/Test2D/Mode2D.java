package com.mygdx.game.Test2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Mode2D implements Screen, InputProcessor {

    private final int cellSize=20;

    public int
            width = Gdx.graphics.getWidth(),
            height = Gdx.graphics.getHeight();

    OrthographicCamera camera = new OrthographicCamera(width,height);
    ArrayList<Wall> walls=new ArrayList<>();

    Vector2
            first=new Vector2(-1,-1),
            second = new Vector2(-1,-1),
            tmp = new Vector2();

    ShapeRenderer renderer=new ShapeRenderer();

    private void createWall(){
        walls.add(new Wall(new float[]{420.0f,440.0f,0.0f,420.0f,820.0f,0.0f,440.0f,820.0f,0.0f,440.0f,440.0f,0.0f}));
        walls.add(new Wall(new float[]{460.0f,420.0f,0.0f,460.0f,440.0f,0.0f,500.0f,440.0f,0.0f,500.0f,420.0f,0.0f}));
        walls.add(new Wall(new float[]{540.0f,420.0f,0.0f,540.0f,440.0f,0.0f,580.0f,440.0f,0.0f,580.0f,420.0f,0.0f}));
        walls.add(new Wall(new float[]{620.0f,420.0f,0.0f,620.0f,440.0f,0.0f,660.0f,440.0f,0.0f,660.0f,420.0f,0.0f}));
        walls.add(new Wall(new float[]{380.0f,760.0f,0.0f,380.0f,780.0f,0.0f,760.0f,780.0f,0.0f,760.0f,760.0f,0.0f}));
        walls.add(new Wall(new float[]{760.0f,700.0f,0.0f,760.0f,740.0f,0.0f,780.0f,740.0f,0.0f,780.0f,700.0f,0.0f}));
        walls.add(new Wall(new float[]{760.0f,620.0f,0.0f,760.0f,660.0f,0.0f,780.0f,660.0f,0.0f,780.0f,620.0f,0.0f}));
        walls.add(new Wall(new float[]{760.0f,540.0f,0.0f,760.0f,580.0f,0.0f,780.0f,580.0f,0.0f,780.0f,540.0f,0.0f}));
        walls.add(new Wall(new float[]{700.0f,420.0f,0.0f,700.0f,440.0f,0.0f,760.0f,440.0f,0.0f,760.0f,420.0f,0.0f}));
        walls.add(new Wall(new float[]{760.0f,440.0f,0.0f,760.0f,500.0f,0.0f,780.0f,500.0f,0.0f,780.0f,440.0f,0.0f}));
        walls.add(new Wall(new float[]{460.0f,720.0f,0.0f,460.0f,740.0f,0.0f,480.0f,740.0f,0.0f,480.0f,720.0f,0.0f}));
        walls.add(new Wall(new float[]{480.0f,680.0f,0.0f,480.0f,720.0f,0.0f,520.0f,720.0f,0.0f,520.0f,680.0f,0.0f}));
        walls.add(new Wall(new float[]{520.0f,660.0f,0.0f,520.0f,680.0f,0.0f,540.0f,680.0f,0.0f,540.0f,660.0f,0.0f}));
        walls.add(new Wall(new float[]{540.0f,600.0f,0.0f,540.0f,660.0f,0.0f,600.0f,660.0f,0.0f,600.0f,600.0f,0.0f}));
        walls.add(new Wall(new float[]{660.0f,500.0f,0.0f,660.0f,540.0f,0.0f,700.0f,540.0f,0.0f,700.0f,500.0f,0.0f}));
        walls.add(new Wall(new float[]{700.0f,460.0f,0.0f,700.0f,500.0f,0.0f,740.0f,500.0f,0.0f,740.0f,460.0f,0.0f}));
        walls.add(new Wall(new float[]{280.0f,800.0f,10.0f,280.0f,920.0f,10.0f,400.0f,920.0f,10.0f,400.0f,800.0f,10.0f}));

    }

    @Override
    public void show() {
        //no need for depth...
        Gdx.gl.glDepthMask(false);

        //enable blending, for alpha
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Gdx.input.setInputProcessor(this);
        createWall();
    }

    private void gridRender(){
        renderer.setColor(Color.GRAY);
        for (int i=0;i<width;i+=cellSize)renderer.line(i,0,i,height);
        for (int j = 0; j < height; j+=cellSize)renderer.line(0,j,width,j);
    }

    private void wallRender(){
        renderer.setColor(Color.WHITE);
        for (Wall wall:walls){
            float [] v= wall.vertices;
            float x = Math.min(v[0],v[6]);
            float w = Math.max(v[0],v[6])-x;
            float y = Math.min(v[1],v[7]);
            float h = Math.max(v[1],v[7])-y;
            renderer.rect(x,y,Math.max(w,cellSize),Math.max(h,cellSize));
        }
        if (first.x!=-1&&second.x!=-1){
            float x = Math.min(first.x,second.x);
            float w = Math.max(first.x,second.x)-x+1;
            float y = Math.min(first.y,second.y);
            float h = Math.max(first.y,second.y)-y+1;
            renderer.rect(x*cellSize,y*cellSize,w*cellSize,h*cellSize);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        //gridRender();
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        wallRender();
        renderer.end();
    }

    @Override
    public void resize(int width, int height) {
        this.width=width;
        this.height=height;
        camera.viewportWidth=width;
        camera.viewportHeight=height;
        camera.update();
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
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.P:for(Wall w:walls)System.out.println(w+";");break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY=height-screenY;
        if(button==0) {
            if (first.x == -1) first.set((float) (screenX / cellSize), (float) (screenY / cellSize));
            else {
                second.set((float) (screenX / cellSize), (float) (screenY / cellSize));
                walls.add(new Wall(new float[]{
                        Math.min(first.x,second.x)*cellSize,Math.min(first.y,second.y)*cellSize,0,
                        Math.min(first.x,second.x)*cellSize,(Math.max(first.y,second.y)+1)*cellSize,0,
                        (Math.max(first.x,second.x)+1)*cellSize,(Math.max(first.y,second.y)+1)*cellSize,0,
                        (Math.max(first.x,second.x)+1)*cellSize,Math.min(first.y,second.y)*cellSize,0
                }));
                first.set(-1,-1);
                second.set(-1,-1);
            }
        }else if (button==1){
            first.set(-1,-1);
            second.set(-1,-1);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screenY=height-screenY;
        if (first.x!=-1)second.set((float) (screenX / cellSize), (float) (screenY / cellSize));
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
