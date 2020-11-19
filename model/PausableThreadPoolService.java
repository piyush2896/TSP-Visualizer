package model;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PausableThreadPoolService extends ThreadPoolExecutor {

    private boolean isPaused;
    private ArrayList<Runnable> runnables;
    private Lock pauseLock;
    private Condition resumed;

    public PausableThreadPoolService(){
        super(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

        isPaused = false;
        pauseLock = new ReentrantLock();
        resumed = pauseLock.newCondition();
        runnables = new ArrayList<>();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        if(isPaused){
            pauseLock.lock();
            try{
                while (isPaused) {
                    resumed.await();
                }
            }catch (InterruptedException e) {
                t.interrupt();
            } finally {
                pauseLock.unlock();
            }
        }
        super.beforeExecute(t, r);
    }

    @Override
    public void execute(Runnable command) {
        runnables.add(command);
        super.execute(command);
    }

    public void pause(){
        pauseLock.lock();
        try {
            isPaused = true;
            for(Runnable r: runnables) {
                ((KnowledgeSource)r).pause();
            }
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            if(isPaused){
                isPaused = false;
                for(Runnable r: runnables) {
                    ((KnowledgeSource)r).resume();
                }
                resumed.signalAll();
            }
        } finally {
            pauseLock.unlock();
        }
    }
}
