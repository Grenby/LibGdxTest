package com.mygdx.game.Collision;

import com.badlogic.gdx.math.Vector2;

class Shape {

    private AaBb aaBb;
    private float [] vertices;


    AaBb getAaBb() {
        return aaBb;
    }

    Shape(float [] vertices){
        if (vertices.length<3)throw new RuntimeException("Few vertices");
        this.vertices=vertices;
        createAaBb();
    }

    private void createAaBb(){
        float []v=vertices;
        float x1,x2,y1,y2;
        x1=v[0];
        y1=v[1];
        x2=v[0];
        y2=v[1];
        for (int i = 2; i <v.length; i+=2) {
            if (v[i]<x1)x1=v[i];
            if (v[i+1]<y1)y1=v[i+1];
            if (v[i]>x2)x2=v[i];
            if (v[i+1]>y2)y2=v[i+1];

        }
        aaBb=new AaBb(x1,y1,x2,y2);
    }

    Vector2 intersection(float x1,float y1,float x2,float y2){
        return null;
    }

    protected float [] getVertices(){
        return vertices;
    }

}
