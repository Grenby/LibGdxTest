package com.mygdx.projects.Collision;


public class TestClass {

    public static void main(String[] arg){
        AaBb a1=new AaBb(0,0,10,10);
        AaBb a2=new AaBb(0,0,20,20);
        System.out.println(new Section(new float[]{0,0,10,10}).intersection(0,10,10,0));
    }

}
