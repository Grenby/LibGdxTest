package com.mygdx.projects.mazeGen;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public abstract class PooledGraph<N> implements Graph<N> {

    private final Pool<Connection<N>> pool;
    private final Array<Connection<N>> connections = new Array<>(4);

    public PooledGraph(){
        this(10);
    }

    public PooledGraph(int capacity){
        pool = new Pool<Connection<N>>(capacity) {
            @Override
            protected Connection<N> newObject() {
                return new DefaultConnection<N>(null,null);
            }
        };
    }

    public void free(Connection<N> object) {
        pool.free(object);
    }

    public void freeAll(Array<Connection<N>> objects) {
        pool.freeAll(objects);
    }

}
