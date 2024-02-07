import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ScalarProduct {

    public static void run() {
        int vectorSize = 8;

        List<Integer> firstVector = new ArrayList<>();
        List<Integer> secondVector = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < vectorSize; i++) {
            firstVector.add(random.nextInt(10));
            secondVector.add(random.nextInt(10));
        }

        System.out.println("First vector: " + firstVector);
        System.out.println("Second vector: " + secondVector + "\n");

        int queueSize = 100;
        Queue<Integer> queue = new LinkedList<>();
        Lock mutex = new ReentrantLock();
        Condition condition = mutex.newCondition();

        Thread producer = new Producer(queueSize, queue, mutex, condition, firstVector, secondVector);
        Thread consumer = new Consumer(queue, vectorSize, mutex, condition);

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nThe scalar product is " + ((Consumer) consumer).getScalarProduct());

        int expectedScalarProduct = 0;
        for (int i = 0; i < vectorSize; i++) {
            expectedScalarProduct += firstVector.get(i) * secondVector.get(i);
        }

        System.out.println("Expected scalar product: " + expectedScalarProduct);
    }
}
