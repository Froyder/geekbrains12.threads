package ru.geek.homeworks.lesson12.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MyFirstThread extends Thread {
    float[] array;
    CyclicBarrier cb;

    public MyFirstThread (float[] array, CyclicBarrier cb) {
        this.array = array;
        this.cb = cb;
    }

    public void run() {
        try {
            for (int i = 0; i < array.length; i++){
                    array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
