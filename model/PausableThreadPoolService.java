package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PausableThreadPoolService extends ThreadPoolExecutor {

    private boolean isPaused;
    private Lock pauseLock;
    private Condition resumed;

    public PausableThreadPoolService(
            int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

        isPaused = false;
        pauseLock = new ReentrantLock();
        resumed = pauseLock.newCondition();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
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

    public void pause(){
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            resumed.signal();
        } finally {
            pauseLock.unlock();
        }
    }
}
