package com.mygdx.game.RayM.dimension2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class View {

    Vector2 position = new Vector2(0,0),
            direction = new Vector2(1,0);
    boolean renderRay=false;

    private ArrayList<Figure> list=new ArrayList<>();

    private final float angle = 2*MathUtils.PI;
    private final float step=1000;
    private final float sin = MathUtils.sin(angle/step);
    private final float cos= MathUtils.cos(angle/step);

    void addFigure(Figure f){
        list.add(f);
    }

    void render(ShapeRenderer renderer){
        direction.nor();
        Vector2 d =direction.cpy().rotateRad(-angle/2);
        Vector2 p=position.cpy();
        Vector2 p1=null;
        for (Figure f :list
             ) {
         //   f.render(renderer);
        }
        renderer.circle(position.x,position.y,5);
        renderer.setColor(Color.RED);
        for (int i=0;i<step;i++){
            float distance=100f;
            for (int k = 0; k < 100; k++) {
                distance = list.get(0).distance(p);
                for (int j = 1; j < list.size(); j++) {
                    float dopDistance = list.get(j).distance(p);
                    distance = distance < -dopDistance ? distance : dopDistance;
                }
                if(renderRay&&i==step/2)renderer.circle(p.x,p.y,distance);
                p = p.add(d.x*distance,d.y*distance);
                //if (distance<0.01f)break;
            }
            if (p1==null)p1=p.cpy();
            else {
                renderer.point(p.x,p.y,0);
                p1.set(p);
            }

            float xx=d.x;
            d.x=d.x*cos-d.y*sin;
            d.y=xx*sin+d.y*cos;
            p.set(position);
        }
        renderer.setColor(Color.WHITE);
    }

}
