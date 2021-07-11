package com.mygdx.projects.mazeGen;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool;

import java.util.Collection;

public class Maze implements Graph<Vector2>{

    public enum PavingType{
        Square,Hexagon
    }

    public interface PavingGenerator{
        float[] getOX();
        float[] getOY();
        Connection<Vector2> getNodes();
        Graph<Vector2> getMazeGraph();
    }

    private final Pool<Connection<Vector2>> connectionPool = new Pool<Connection<Vector2>>() {
        @Override
        protected Connection<Vector2> newObject() {
            return new DefaultConnection<>(null,null);
        }
    };

    private final MazeGenerator<Vector2> mazeGenerator;
    private Iterable<Vector2> nodes;
    private ObjectMap<Vector2,Integer> exits;
    private Graph<Vector2> mazeGraph;

    //for iterating over neighbors nodes
    private float [] oX,oY;

    private final int mazeWidth;
    private final int mazeHeight;
    private final float cellSize;

    public Maze() {
        this(10,10,10,PavingType.Square);
    }

    public Maze(int mazeWidth,int mazeHeight, float cellSize,PavingType type){
        mazeGenerator = new MazeGenerator<>();
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        this.cellSize = cellSize;
    }


    public Vector2 getNearNode(){
        return null;
    }

    public Iterable<Vector2> getNodes() {
        return nodes;
    }

    @Override
    public Array<Connection<Vector2>> getConnections(Vector2 fromNode) {
        return mazeGraph.getConnections(fromNode);
    }

    public float[] getOX() {
        return oX;
    }

    public float[] getOY() {
        return oY;
    }

    public boolean hasExit(Vector2 v,int i){
        int a = 1 << i;
        return (exits.get(v) & a) == a;
    }

    private void generateExist(){
        final Graph<Vector2> maze = this.mazeGraph;
        final float[] oX = this.oX;
        final float[] oY = this.oY;
        final ObjectMap<Vector2,Integer> exits = this.exits;
        final Iterable<Vector2> nodes = this.nodes;

        final Vector2 tmp = new Vector2();

        for (Vector2 v:nodes) {
            for (int i=0;i<oX.length-1;i++){
                tmp.set(v).add(oX[i],oY[i]);
                for (Connection<Vector2> c:maze.getConnections(v)) {
                    if (c.getToNode().epsilonEquals(tmp)){
                        int a = 1<<i;
                        a|= exits.get(c.getToNode(),a);
                        exits.put(c.getToNode(),a);
                    }
                }
            }
        }

    }

    public void generateEnvironment(PavingGenerator generator){
        ///TODO...
    }

    private void squarePaving(){
        ObjectSet<Vector2> vectors = new ObjectSet<>(mazeHeight*mazeWidth);
        DefaultGraph<Vector2> graph = new DefaultGraph<>();

        Vector2[][] maps = new Vector2[mazeWidth][mazeHeight];
        for (int i=0;i<mazeWidth;i++){
            for (int j=0;j<mazeHeight;j++) {
                maps[i][j] = new Vector2(cellSize*i, cellSize*j);
                vectors.add(maps[i][j]);
            }
        }
        for (int i=0;i<mazeWidth;i++){
            for (int j=0;j<mazeHeight;j++) {
                Array<Connection<Vector2>> connections = new Array<>(8);
                if (i!=0) {
                    connections.add(new DefaultConnection<>(maps[i][j], maps[i-1][j]));
                }
                if (j!=0)
                    connections.add(new DefaultConnection<>(maps[i][j],maps[i][j-1]));
                if (i!=mazeWidth-1) {
                    connections.add(new DefaultConnection<>(maps[i][j], maps[i+1][j]));
                }
                if (j!=mazeHeight-1)
                    connections.add(new DefaultConnection<>(maps[i][j],maps[i][j+1]));
                graph.addNode(maps[i][j],connections);
            }
        }

        float d = cellSize/2;
        oX = new float[]{d,-d,-d,d,d};
        oY = new float[]{d,d,-d,-d,d};
        nodes = vectors;
        mazeGraph = mazeGenerator.generate(graph,maps[0][0],mazeHeight*mazeWidth);

    }

    private void hexagonPaving(){

    }

    public void generateEnvironment(PavingType type, int w,int h){
        switch (type){
            case Square:
                squarePaving();
                break;
            case Hexagon:
                hexagonPaving();
                break;
            default:
                System.out.println("can't make this type");
                break;
        }
        generateExist();
    }

}
