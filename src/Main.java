import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        int count = 5;
        Lock[] forks = new ReentrantLock[count];
        Philosopher[] philosophers = new Philosopher[count];
        Thread[] threads = new Thread[count];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new ReentrantLock();
        }
        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(forks[i], forks[(i + 1) % count]);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(philosophers[i]);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Все философы закончили есть");
    }
}