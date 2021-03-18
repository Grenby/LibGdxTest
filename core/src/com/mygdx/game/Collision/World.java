package com.mygdx.game.Collision;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

class World {

    ArrayList<Shape> shapes=new ArrayList<>();
    private TreeAaBb tree=new TreeAaBb();
    private ArrayList<Shape> drawList;



    World(){
    }

    void addShape(Shape shape){
        shapes.add(shape);
        tree.add(shape);
    }

    void render(ShapeRenderer renderer){
//        renderer.begin(ShapeRenderer.ShapeType.Section);
//        for (Shape shape:shapes) {
//            AaBb a=shape.getAaBb();
//            renderer.rect(a.getX1(),a.getY1(),a.getX2()-a.getX1(),a.getY2()-a.getY1());
//        }
//        renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        if (drawList!=null){
            for (Shape shape:drawList) {
                AaBb a = shape.getAaBb();
                renderer.rect(a.getX1(), a.getY1(), a.getX2() - a.getX1(), a.getY2() - a.getY1());
            }

        }
        renderer.end();
    }

    void rayCast(float x1,float y1,float x2,float y2){
        drawList=tree.getIntersection(x1,y1,x2,y2);
    }

    Vector2 rayCast1(float x1,float y1,float x2,float y2){
        ArrayList<Shape> list=tree.getIntersection(x1,y1,x2,y2);
        Vector2 v=null;
        float l=Float.MAX_VALUE;
        for (Shape shape :list) {
            Vector2 vv=shape.intersection(x1,y1,x2,y2);
            if (vv!=null){
                final float ll=vv.sub(x1,y1).len2();
                if (ll<l){
                    l=ll;
                    v=vv.add(x1,y1);
                }
            }
        }
        return v;
    }
}
