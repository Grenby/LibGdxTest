package com.mygdx.game.TestThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

final class ThreadClass {

    static ExecutorService executor= Executors.newFixedThreadPool(4);
    static void addAction(Runnable runnable){
        executor.execute(runnable);
    }
    static Future add(Runnable runnable){
        return executor.submit(runnable);
    }
}
