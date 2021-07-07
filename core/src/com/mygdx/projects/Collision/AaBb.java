package com.mygdx.projects.Collision;


class AaBb {

    private float [] vertices;

    AaBb(float x1,float y1,float x2,float y2){
        vertices=new float[]{x1,y1,x2,y2};
    }

//    AaBb(AaBb a){
//        vertices=new float[]{a.getX1(),a.getY1(),a.getX2(),a.getY2()};
//    }
//    AaBb(AaBb ... a){
//        vertices=new float[]{a[0].getX1(),a[0].getY1(),a[0].getX2(),a[0].getY2()};
//        for (int i = 1; i <a.length ; i++) {
//            if (a[i].getX1()<vertices[0])vertices[0]=a[i].getX1();
//            if (a[i].getY1()<vertices[1])vertices[1]=a[i].getY1();
//            if (a[i].getX2()>vertices[2])vertices[2]=a[i].getX2();
//            if (a[i].getY2()>vertices[3])vertices[3]=a[i].getY2();
//        }
//    }

    AaBb(AaBb a,AaBb b){
        vertices=new float[]{a.getX1(),a.getY1(),a.getX2(),a.getY2()};
        if (b.getX1()<vertices[0])vertices[0]=b.getX1();
        if (b.getY1()<vertices[1])vertices[1]=b.getY1();
        if (b.getX2()>vertices[2])vertices[2]=b.getX2();
        if (b.getY2()>vertices[3])vertices[3]=b.getY2();
    }

    float getX1(){
        return vertices[0];
    }

    float getX2(){
        return vertices[2];
    }

    float getY1(){
        return vertices[1];
    }

    float getY2(){
        return vertices[3];
    }

    float area(){
        return (vertices[2]-vertices[0])*(vertices[3]-vertices[1]);
    }

    static float area(AaBb a,AaBb b){
        final float x1=Math.min(a.getX1(),b.getX1());
        final float y1=Math.min(a.getY1(),b.getY1());
        final float x2=Math.max(a.getX2(),b.getX2());
        final float y2=Math.max(a.getY2(),b.getY2());
        return (x2-x1)*(y2-y1);
    }

    boolean intersectionBox(AaBb a){
        if (a.getX1()>getX2()||getX1()>a.getX2())return false;
        return !(a.getY1() > getY2()) && !(getY1() > a.getY2());
    }

    boolean intersectionBox(float x1,float y1,float x2,float y2){
        if (x1>getX2()||getX1()>x2)return false;
        return !(y1 > getY2()) && !(getY1() > y2);
    }

    boolean intersectionPoint(float x,float y){
        return vertices[0]<=x&&x<=vertices[2]&&vertices[1]<=y&&y<=vertices[3];
    }

    boolean intersectionSection(float x1,float y1,float x2,float y2){
        float []v=vertices;
        if (intersectionPoint(x1,y1)||intersectionPoint(x2,y2))return true;
        if(Section.intersection(x1,y1,x2,y2,v[0],v[1],v[0],v[3]))return true;
        if(Section.intersection(x1,y1,x2,y2,v[0],v[1],v[2],v[1]))return true;
        if(Section.intersection(x1,y1,x2,y2,v[0],v[3],v[2],v[3]))return true;
        return Section.intersection(x1, y1, x2, y2, v[2], v[1], v[2], v[3]);
    }

    void set(AaBb a,AaBb b){
        vertices[0]=Math.min(a.getX1(),b.getX1());
        vertices[1]=Math.min(a.getY1(),b.getY1());
        vertices[2]=Math.max(a.getX2(),b.getX2());
        vertices[3]=Math.max(a.getY2(),b.getY2());
    }

}
