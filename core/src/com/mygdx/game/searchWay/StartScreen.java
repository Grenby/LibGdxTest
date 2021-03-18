package com.mygdx.game.searchWay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.HashMap;
import java.util.LinkedList;


public class StartScreen implements Screen {


    private HashMap<Integer,Cell> map;

    private LinkedList<Cell> way1=new LinkedList<>();
    private LinkedList<Cell> way2=new LinkedList<>();
    private LinkedList<Cell> way3=new LinkedList<>();

    private final Search search;

    //private ExecutorService executor= Executors.newFixedThreadPool(3);

    ///size cell
    private int size;
    ///width and height window in cell
    private int wCell,hCell;

    private ShapeRenderer renderer;
    private OrthographicCamera camera;
    private Input input;

    {

        size=10;
        wCell=100;
        hCell=50;
        ///width and height window in pixel
        float width = (wCell) * size;
        float height = (hCell) * size;
        renderer=new ShapeRenderer();
        map=new HashMap<>(hCell*wCell);
        camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(width /2, height /2,0);
        camera.update();

        search= new Search();
        search.start();

        input=new Input(this);
        Gdx.input.setInputProcessor(input);
    }

    public StartScreen() {
    }

    void addCell(int x,int y){
        if (!way1.isEmpty())way1.clear();
        if (!way2.isEmpty())way2.clear();
        if (!way3.isEmpty())way3.clear();
        final float k=(1-camera.zoom)/2;
        final float x1 =x*camera.zoom+camera.viewportWidth*k;
        final float y1 =y*camera.zoom+camera.viewportHeight*k;
        final int xx=(int)((x1+camera.position.x-Gdx.graphics.getWidth()/2)/size);
        final int yy=(int)((y1+camera.position.y-Gdx.graphics.getHeight()/2)/size);
        if(xx>0 && yy>0 && xx<wCell&&yy<hCell)map.get(Cell.getHash(xx,yy)).setFree(false);
    }

    void removeCell(int x,int y){
        if (!way1.isEmpty())way1.clear();
        if (!way2.isEmpty())way2.clear();
        if (!way3.isEmpty())way3.clear();
        final float k=(1-camera.zoom)/2;
        final float x1 =x*camera.zoom+camera.viewportWidth*k;
        final float y1 =y*camera.zoom+camera.viewportHeight*k;
        final int xx=(int)((x1+camera.position.x-Gdx.graphics.getWidth()/2)/size);
        final int yy=(int)((y1+camera.position.y-Gdx.graphics.getHeight()/2)/size);
        if(xx>0 && yy>0 && xx<wCell&&yy<hCell)map.get(Cell.getHash(xx,yy)).setFree(true);
    }

    void zoom(int k){
        if (k>0){
            if (camera.zoom<5){
                camera.zoom+=0.1f;
                camera.update();
            }
        }else {
            if (camera.zoom>0.3){
                camera.zoom-=0.1f;
                camera.update();
            }
        }
    }

    void move(int x,int y){
        camera.position.add(x*size,y*size,0);
        camera.update();
    }

    void search(){
        if (!way1.isEmpty())way1.clear();
        if (!way2.isEmpty())way2.clear();
        if (!way3.isEmpty())way3.clear();
        search.setSearchOption(map, cells ->{
            if (way1.isEmpty())way1=cells;
            else if (way2.isEmpty())way2=cells;
            else way3=cells;
        }, map.get(Cell.getHash(1, 1)), map.get(Cell.getHash(wCell - 2, hCell - 2)));
        synchronized (search){
            search.notify();
        }
    }

    void rebuild(){
        if (!way1.isEmpty())way1.clear();
        if (!way2.isEmpty())way2.clear();
        if (!way3.isEmpty())way3.clear();
        for (int i=0;i<wCell;i++){
            for(int u=0;u<hCell;u++){
                if (!(i==0 || i==wCell-1||u==0||u==hCell-1))map.get(Cell.getHash(i,u)).setFree(true);
            }
        }
        randomMap();
    }

    private void randomMap(){
        int num=wCell*hCell*30/100;
        for (int i=0;i<num;i++){
            int x=(int)Math.round(Math.random()*(wCell-1));
            int y=(int)Math.round(Math.random()*(hCell-1));
            while (!map.get(Cell.getHash(x,y)).isFree()||(x==1&&y==1)||(x==(wCell-2)&&y==(hCell-2))){
                x=(int)Math.round(Math.random()*(wCell-1));
                y=(int)Math.round(Math.random()*(hCell-1));
            }
            map.get(Cell.getHash(x,y)).setFree(false);
        }

    }


    @Override
    public void show() {
        Gdx.gl.glClearColor(1,1,1,1);
        for (int i=0;i<wCell;i++){
            for(int u=0;u<hCell;u++){
                Cell c=new Cell(i,u);
                if (i==0 || i==wCell-1||u==0||u==hCell-1)c.setFree(false);
                map.put(c.hashCode(),c);
            }
        }
//        for (int i=2;i<hCell-2;i++){
//            if (i!=9)map.get(Cell.getHash(3,i)).setFree(false);
//        }
//        for (int i=3;i<wCell;i++){
//            map.get(Cell.getHash(i,10)).setFree(false);
//        }
//        for (int i=1;i<wCell-1;i++){
//            if (i<3)map.get(Cell.getHash(i,8)).setFree(false);
//            else if (i>5)map.get(Cell.getHash(i,7)).setFree(false);
//        }
//        for (int i=1;i<7;i++){
//            map.get(Cell.getHash(6,i)).setFree(false);
//        }
        randomMap();
    }

    @Override
    public void render(float delta) {
        input.act(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setProjectionMatrix(camera.combined);
        renderMap();
        if (search.isReadyForRender()) {
            HashMap<Cell, Search.Node>c=Search.getMapNode();
            if (c!=null) {
                for (Search.Node e:c.values()){
                    renderer.begin(ShapeRenderer.ShapeType.Filled);
                    renderer.setColor(Color.BLUE);
                    renderer.rect(e.getC().getX()*size,e.getC().getY()*size,size,size);
                    renderer.end();
                }

            }
            search.go();
        }
        if (!way1.isEmpty()){
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Color.RED);
            for (Cell e:way1) renderer.rect(e.getX()*size,e.getY()*size,size,size);
            renderer.end();
        }
        if (!way2.isEmpty()){
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Color.GREEN);
            for (Cell e:way2)renderer.rect(e.getX()*size,e.getY()*size,size,size);
            renderer.end();
        }
        if (!way3.isEmpty()){
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Color.FIREBRICK);
            for (Cell e:way3)renderer.rect(e.getX()*size,e.getY()*size,size,size);
            renderer.end();
        }
    }

    private void renderMap(){
        //renderer.begin(ShapeRenderer.ShapeType.Section);
        //renderer.setColor(Color.BLACK);
        //for (int x=1;x<wCell;x++)renderer.line(x*size,size,x*size,height);
        //for (int y=1;y<hCell;y++)renderer.line(size,y*size,width,y*size);
        //renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Cell e:map.values()){
            if (!e.isFree()) {
                renderer.setColor(e.getColor());
                renderer.rect(e.getX() * size, e.getY() * size, size, size);
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


}
