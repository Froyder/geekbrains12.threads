package ru.geek.homeworks.lesson12.threads;

public class MyFirstThread extends Thread {
    float[] array;

    public MyFirstThread (float[] array) {
        this.array = array;
    }

    public void run() {
            for (int i = 0; i < array.length; i++){
                    array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
    }
}
