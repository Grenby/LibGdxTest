package com.mygdx.projects.searchWay;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.function.Consumer;

public class Search extends Thread {


    public static class Node implements Comparable<Node> {

        private int distance,priority,weight;

        private Cell c;
        //indicates the previous cell to build the path
        private Node previous;

        Node(){}

        Node (Cell c){
            this.c=c;
        }

        int getPriority() {
            return priority;
        }

        void setPriority(int priority) {
            this.priority = priority;
        }

        void setWeight(int weight) {
            this.weight = weight;
        }

        int getWeight() {
            return weight;
        }

        int getDistance() {
            return distance;
        }

        void setDistance(int distance) {
            this.distance = distance;
        }

        Cell getC() {
            return c;
        }

        void setC(Cell c) {
            this.c = c;
        }

        @Override
        public int hashCode() {
            return c.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof  Node){
                return c.equals(((Node) obj).getC());
            }
            return false;
        }

        @Override
        public int compareTo(Node o) {
            if (o.getPriority()>this.getPriority())return -1;
            else if (o.getPriority()<this.getPriority())return 1;
            else if(o.getDistance()>this.getDistance())return -1;
            else if(o.getDistance()<this.getDistance())return 1;
            else if (o.getWeight()>this.getWeight())return -1;
            else if(o.getWeight()<this.getWeight())return 1;
            else return 0;
        }
    }

    private static HashMap<Cell,Node> mapNode;
    private LinkedList<Cell>way;
    static private final short []o_X={0,1,0,-1};
    static private final short []o_Y={1,0,-1,0};

    private HashMap<Integer,Cell> map;
    private Cell from,to;

    private volatile boolean readyForRender=false;
    private boolean draw=true;
    private boolean searching=false;


    private Consumer<LinkedList<Cell>> consumer;

    Search(){}

    private  void createWay(Node last){
        way=new LinkedList<>();
        //от зацикливаний
        int p=0;
        while(last.previous!=null){
            p++;
            if(p>100000){
                throw new RuntimeException("Цикл в построении пути");
            }
            way.addFirst(last.c);
            last=last.previous;
        }
        way.addFirst(last.c);
        //System.out.println(way.size()+" create way");
        consumer.accept(way);
    }

    private void searchWide(Cell from, Cell to){
        if (mapNode==null)mapNode = new HashMap<>(map.size());
        else mapNode.clear();

        LinkedList<Node> list = new LinkedList<>();

        Node n=new Node(from);
        list.add(n);
        mapNode.put(from,n);
        while (true) {
                Node c = list.pollFirst();
                if (c == null) return;
                for (int y = 0; y < 4; y++) {
                    Cell cc = map.get(Cell.getHash(c.c.getX() + o_X[y], c.c.getY() + o_Y[y]));
                    if (cc != null && cc.isFree() && !mapNode.containsKey(cc)) {
                        n = new Node(cc);
                        n.previous = c;
                        list.addLast(n);
                        if (cc.equals(to)) {
                            createWay(list.getLast());
                            return;
                        }
                        mapNode.put(cc, n);
                    }
                }
        }

    }

    private void searchWideDraw(Cell from, Cell to) throws InterruptedException {
        if (mapNode==null)mapNode = new HashMap<>(map.size());
        else mapNode.clear();

        LinkedList<Node> list = new LinkedList<>();

        Node n = new Node(from);
        list.add(n);
        mapNode.put(from,n);
        while (true) {
            int num=list.size();
            if (num==0)return;
            for (int nn=0;nn<10;nn++) {
                Node c = list.pollFirst();
                if (c == null) return;
                for (int y = 0; y < 4; y++) {
                    Cell cc = map.get(Cell.getHash(c.c.getX() + o_X[y], c.c.getY() + o_Y[y]));
                    if (cc != null && cc.isFree() && !mapNode.containsKey(cc)) {
                        n = new Node(cc);
                        n.previous = c;
                        list.addLast(n);
                        if (cc.equals(to)) {
                            createWay(n);
                            return;
                        }
                        mapNode.put(cc,n);
                    }
                }
            }
            readyForRender = true;
            synchronized (this) {
                wait();
            }
        }

    }

    private void greedySearch(Cell from,Cell to){

        if (mapNode==null)mapNode = new HashMap<>(map.size());
        else mapNode.clear();

        PriorityQueue<Node> queue=new PriorityQueue<>();

        Node n=new Node(from);
        n.setPriority(0);
        queue.add(n);
        mapNode.put(from,n);
        while (true) {
            Node c = queue.poll();
            if (c == null) return;
            for (int y = 0; y < 4; y++) {
                Cell cc = map.get(Cell.getHash(c.c.getX() + o_X[y], c.c.getY() + o_Y[y]));
                if (cc != null && cc.isFree() && !mapNode.containsKey(cc)) {
                    n = new Node(cc);
                    n.setPriority(distance(to, cc));
                    n.previous = c;
                    queue.add(n);
                    if (cc.equals(to)) {
                        createWay(n);
                        return;
                    }
                    mapNode.put(cc, n);
                }
            }
        }
    }

    private void greedySearchDraw(Cell from,Cell to) throws InterruptedException{

        if (mapNode==null)mapNode = new HashMap<>(map.size());
        else mapNode.clear();

        PriorityQueue<Node> queue=new PriorityQueue<>();

        Node n=new Node (from);
        n.setPriority(0);
        queue.add(n);
        mapNode.put(from,n);
        while (true) {
            for (int i=0;i<10;i++) {
                Node c = queue.poll();
                if (c == null) return;
                for (int y = 0; y < 4; y++) {
                    Cell cc = map.get(Cell.getHash(c.c.getX() + o_X[y], c.c.getY() + o_Y[y]));
                    if (cc != null && cc.isFree() && !mapNode.containsKey(cc)) {
                        n=new Node(cc);
                        n.setPriority(distance(to, cc));
                        n.previous = c;
                        queue.add(n);
                        if (cc.equals(to)) {
                            createWay(n);
                            return;
                        }
                        mapNode.put(cc, n);
                    }
                }
            }
            readyForRender = true;
            synchronized (this) {
                wait();
            }
        }

    }

    private void AStarSearch(Cell from,Cell to){

        if (mapNode==null)mapNode = new HashMap<>(map.size());
        else mapNode.clear();

        PriorityQueue<Node> queue=new PriorityQueue<>();

        Node n=new Node();

        n.setPriority(0);
        n.setWeight(0);
        n.setC(from);

        queue.add(n);
        mapNode.put(from,n);
        while (true) {
            Node c = queue.poll();
            if (c == null) return;
            for (int y = 0; y < 4; y++) {
                Cell cc = map.get(Cell.getHash(c.c.getX() + o_X[y], c.c.getY() + o_Y[y]));
                if (cc != null && cc.isFree()) {
                    if (mapNode.containsKey(cc)) {
                        n = mapNode.get(cc);
                        if (n.getWeight() > c.getWeight() + c.getC().getPrice()) {
                            n.previous = c;
                            n.setWeight(c.getC().getPrice() + c.getWeight());
                            n.setPriority(n.getDistance() + n.getWeight());
                           if (queue.remove(n))queue.add(n);
                        }
                    } else {
                        n = new Node(cc);
                        n.previous = c;
                        if (cc.equals(to)) {
                            createWay(n);
                            return;
                        }
                        n.setWeight(c.getC().getPrice() + c.getWeight());
                        n.setDistance(distance(cc, to));
                        n.setPriority(n.getDistance() + n.getWeight());
                        queue.offer(n);
                        mapNode.put(cc, n);
                    }
                }
            }
        }
    }

    private void AStarSearchDraw(Cell from,Cell to) throws InterruptedException{

        if (mapNode==null)mapNode = new HashMap<>(map.size());
        else mapNode.clear();

        PriorityQueue<Node> queue=new PriorityQueue<>();

        Node n=new Node();

        n.setPriority(0);
        n.setWeight(0);
        n.setC(from);

        queue.add(n);
        mapNode.put(from,n);
        while (true) {
            for (int i=0;i<10;i++) {
                Node c = queue.poll();
                if (c == null) return;
                for (int y = 0; y < 4; y++) {
                    Cell cc = map.get(Cell.getHash(c.c.getX() + o_X[y], c.c.getY() + o_Y[y]));
                    if (cc != null && cc.isFree()) {
                        if (mapNode.containsKey(cc)) {
                            n = mapNode.get(cc);
                            if (n.getWeight() > c.getWeight()+c.getC().getPrice()) {
                                n.previous = c;
                                n.setWeight(c.getWeight()+c.getC().getPrice());
                                n.setPriority(n.getDistance() + n.getWeight());
                                if (queue.remove(n))queue.add(n);
                            }
                        } else {
                            n = new Node(cc);
                            n.previous = c;
                            if (cc.equals(to)) {
                                createWay(n);
                                return;
                            }
                            n.setWeight(c.getC().getPrice() + c.getWeight());
                            n.setDistance(distance(cc, to));
                            n.setPriority(n.getDistance() + n.getWeight());
                            queue.add(n);
                            mapNode.put(cc, n);
                        }
                    }
                }
            }
            readyForRender = true;
            synchronized (this) {
                wait();
            }
        }

    }

    private int distance(Cell n,Cell p){
        return (Math.abs(n.getX()-p.getX())+Math.abs(n.getY()-p.getY()))*(Cell.averagePrice);
    }

    boolean isReadyForRender() {
        return readyForRender;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    void go(){
        synchronized (this) {
            readyForRender=false;
            notify();
        }
    }

    static HashMap<Cell, Node> getMapNode() {
        return mapNode;
    }

    boolean isSearching() {
        return searching;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (map != null) {
                    searching=true;
                    if (draw) {
                        AStarSearchDraw(from, to);
                        searchWideDraw(from,to);
                        greedySearchDraw(from,to);
                    }
                    else {



                        long time =System.currentTimeMillis();
                        System.out.println("a star");
                        AStarSearch(from, to);
                        System.out.println("time: "+(System.currentTimeMillis()-time)+" ms. ");
                        if (way!=null) System.out.println("path:" +way.size());


                        System.out.println("wide ");
                        time =System.currentTimeMillis();
                        searchWide(from,to);
                        System.out.println("time: "+(System.currentTimeMillis()-time)+" ms. ");
                        if (way!=null) System.out.println("path:" +way.size());





//                        System.out.println("Greedy");
//
//                        time =System.currentTimeMillis();
//                        greedySearch(from,to);
//                        System.out.println("time: "+(System.currentTimeMillis()-time)+" ms. "+mapNode.size()+" elements ");

                        System.out.println("____________________________");

                    }
                    readyForRender = false;
                }
                searching=false;
                synchronized (this) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param map is collection all cell
     * @param consumer set Linked list
     * @param from is start cell
     * @param to is finish cell
     */
    void setSearchOption(HashMap<Integer,Cell> map,Consumer<LinkedList<Cell>> consumer,Cell from,Cell to){
        if (map==null||from==null||to==null||consumer==null) new NullPointerException().printStackTrace();
        else {
            this.consumer=consumer;
            this.map = map;
            this.from = from;
            this.to = to;
        }
    }

}
