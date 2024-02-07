import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer extends Thread {
    private Queue<Integer> queue;
    private int sum;
    private int size;
    private Lock mutex;
    private Condition condition;

    public Consumer(Queue<Integer> queue, int size, Lock mutex, Condition condition) {
        this.queue = queue;
        this.sum = 0;
        this.size = size;
        this.mutex = mutex;
        this.condition = condition;
    }

    @Override
    public void run() {
        for (int i = 0; i < size; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.sum += consumeProduct();
        }
    }

    public int getScalarProduct() {
        return this.sum;
    }

    public int consumeProduct() {
        mutex.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println("The queue is empty. Consumer is waiting");
                condition.await();
            }
            int product = queue.remove();
            System.out.println("Consumer gets the product " + product);
            condition.signal();
            return product;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            return 0;
        } finally {
            mutex.unlock();
        }
    }
}
