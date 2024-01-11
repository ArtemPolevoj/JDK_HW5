import java.time.LocalTime;
import java.util.concurrent.locks.Lock;

public class Philosopher implements Runnable {
    private static int count = 0;
    private final int id = ++count;
    private final Lock leftFork;
    private final Lock rightFork;
    private int mealsEaten = 0;

    public Philosopher(Lock leftFork, Lock rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        while (mealsEaten < 3) {
            think();
            eat();
        }
    }

    private void think() {
        System.out.println("Философ " + id + " думает");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void eat() {
        leftFork.lock();
        rightFork.lock();

        LocalTime time = LocalTime.now();
        switch (mealsEaten) {
            case 0 -> System.out.println("Философ " + id + " позавтракал в " + time);
            case 1 -> System.out.println("Философ " + id + " пообедал в " + time);
            case 2 -> System.out.println("Философ " + id + " поужинал в " + time);
        }
        mealsEaten++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        rightFork.unlock();
        leftFork.unlock();
    }
}
