package com.mygdx.projects.searchWay;
import java.util.PriorityQueue;

public class DopClass {

    static class Node implements Comparable<Node>{
        int a,b;
        Node(int a,int b){
            this.a=a;
            this.b=b;
        }


        @Override
        public int compareTo(Node o) {
            if (this.a>o.a)return 1;
            else if (this.a<o.a)return -1;
            else if (this.b>o.b)return 1;
            else if (this.b<o.b)return -1;
            else return 0;
        }

        @Override
        public String toString() {
            return "a="+a+" b="+b;
        }
    }

    public static void main(String[] arg){
        PriorityQueue<Node> queue=new PriorityQueue<>();
        for (int i=0;i<3;i++)
            for (int u=0;u<3;u++)queue.add(new Node (i,u));
        int s=queue.size();
        for (int i=0;i<s;i++){
            System.out.println(queue.poll());
        }
    }

}
