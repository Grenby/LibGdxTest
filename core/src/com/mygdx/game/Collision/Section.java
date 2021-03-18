package com.mygdx.game.Collision;

import com.badlogic.gdx.math.Vector2;

class Section extends Shape {

    Section(float[] vertices) {
        super(vertices);
    }

    static boolean intersection(float x11, float y11, float x12, float y12, float x21, float y21, float x22, float y22){
        float k1=(x21-x11)*(y12-y11)-(y21-y11)*(x12-x11);
        float k2=(x22-x11)*(y12-y11)-(y22-y11)*(x12-x11);
        if (k1*k2<=0){
            k1=(x11-x21)*(y22-y21)-(y11-y21)*(x22-x21);
            k2=(x12-x21)*(y22-y21)-(y12-y21)*(x22-x21);
            return k1 * k2 <= 0;
        }else return false;
    }

    float getX1(){
        return getVertices()[0];
    }

    float getX2(){
        return  getVertices()[2];
    }

    float getY1(){
        return  getVertices()[1];
    }

    float getY2(){
        return  getVertices()[3];
    }

    @Override
    Vector2 intersection(float x1, float y1, float x2, float y2) {
        x2-=x1;
        y2-=y1;
        float k1=(getX1()-x1)*y2-(getY1()-y1)*x2;
        float k2=(getX2()-x1)*y2-(getY2()-y1)*x2;
        if (k1*k2<=0){
            k1=Math.abs(k1);
            k2= Math.abs(k2);
            final float x=k1/(k1+k2);
            Vector2 vector2 = new Vector2(getX1()+x*(getX2()-getX1()),getY1()+x*(getY2()-getY1()));
            return vector2.sub(x1,y1).dot(x2,y2)>0? vector2.add(x1,y1):null;
            //  k1=(getX1()-x1)*(y2-y1)-(getY1()-y1)*(x2-x1);
//            k2=(getX2()-x1)*(y2-y1)-(getY2()-y1)*(x2-x1);
//            if (k1*k2<=0){
//                k1=Math.abs(k1);
//                k2= Math.abs(k2);
//                final float x=k2/(k1+k2);
//                return new Vector2(getX2()+x*(getX1()-getX2()),getY2()+x*(getY1()-getY2()));
//            }else return null;
        }else return null;
    }

    @Override
    public String toString() {
        return " x1,y1:"+getX1()+","+getY1()+" x2,y2:"+getX2()+","+getY2();
    }
}
