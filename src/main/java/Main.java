import java.util.Arrays;

public class Main {
    static final int size = 10_000_000;
    static final int h = size / 2;
    static float[] arr = new float[size];
    static float[] ar1 = new float[h];
    static float[] ar2 = new float[h];

    public static void main(String[] args) {
        Arrays.fill(arr, 1);
        arrOfFunction();
        Arrays.fill(arr, 1);
        threadArrOfFunction();
    }

    private static void arrOfFunction() {
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
        }
        System.out.println(System.currentTimeMillis() - a + " ms");
    }

    private static void threadArrOfFunction() {
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, ar1, 0, h);
        System.arraycopy(arr, h, ar2, 0, h);
        Thread t1 = new Thread(new Thread_1());
        Thread t2 = new Thread(new Thread_2());
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(ar1, 0, arr, 0, h);
        System.arraycopy(ar2, 0, arr, h, h);
        System.out.println(System.currentTimeMillis() - a + " ms");
    }

    public static class Thread_1 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < ar1.length; i++) {
                ar1[i] = (float) (ar1[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
            }
        }
    }

    public static class Thread_2 implements Runnable {
        @Override
        public void run() {
            for (int j = 0; j < ar2.length; j++) {
                int i = j + h;
                ar2[j] = (float) (ar2[j] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
            }
        }
    }
}