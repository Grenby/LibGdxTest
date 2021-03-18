package com.mygdx.game.TestThread;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class Ray {

    Vector2 start=new Vector2(-20,-20);
    Vector2 end=new Vector2(-20,15);
    Vector2 f=new Vector2();
    Vector2 point,normal;

    private RayCastCallback rayCastCallback= (fixture, point, normal, fraction) -> {
        Ray.this.point=point;
        Ray.this.normal=normal;
        return 0;
    };


    Ray(){
    }


    void cast(){
        f.set(end).sub(start).rotateRad(0.01f);
        end.set(start).add(f);
        ThreadClass.add(() ->MyWorld.getMyWorld().world.rayCast(rayCastCallback,start,end));
    }


    void draw(ShapeRenderer renderer){
        if (point!=null) renderer.line(point.x,point.y,point.x+normal.x,point.y+normal.y);
        renderer.line(start,end);
    }

}
