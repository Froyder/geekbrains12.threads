package ru.geek.homeworks.lesson12.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MainClass {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    CyclicBarrier cb = new CyclicBarrier(3);

    public static void main(String[] args) {
        MainClass example = new MainClass();
        new Thread(() -> example.method1()).start();
        new Thread(() -> {
            try {
                example.method2();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //первый метод
    public void method1() {
        float[] arr = new float[SIZE];

        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        System.out.println("Первый массив заполнен единицами.");

        long startTime = System.currentTimeMillis();
        System.out.println("Засекаем время...");

        for (int i = 0; i < arr.length; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Первый массив пересчитан по новой формуле!");

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("С запкска первого метода прошло " + estimatedTime + " единиц времени.");
    }

    //второй метод (с двумя потоками)
    public void method2() throws BrokenBarrierException, InterruptedException {
        float[] arr = new float[SIZE];

        //заполняем базовый массив единцами
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        System.out.println("Второй массив заполнен единицами.");

        long startTime = System.currentTimeMillis();
        System.out.println("Засекаем время...");

        //создаем "половинки" массива, заполняем их
        float[] arr1 = new float[HALF];
        System.arraycopy(arr, 0, arr1, 0, HALF);
        float[] arr2 = new float[HALF];
        System.arraycopy(arr, arr.length/2, arr2, 0, HALF);

        //в первом потоке пересчитываем по новой формуле arr1
        new Thread(new MyFirstThread(arr1, cb)).start();

        //во втором потоке пересчитываем по новой формуле arr2
        new Thread(new MyFirstThread(arr2, cb)).start();

        cb.await();

        //"склеиваем" половинки в общий массив
        System.arraycopy(arr1, 0, arr, 0, arr1.length);
        System.arraycopy(arr2, 0, arr, arr.length/2, arr2.length);

        System.out.println("Второй массив пересчитан по новой формуле!");

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("С запуска второго метода прошло " + estimatedTime + " единиц времени.");

    }

}
