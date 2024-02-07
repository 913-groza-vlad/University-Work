import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Producer extends Thread {
    private int queueSize;
    private Queue<Integer> queue;
    private Lock mutex;
    private Condition condition;
    private List<Integer> firstVector;
    private List<Integer> secondVector;

    public Producer(int queueSize, Queue<Integer> queue, Lock mutex, Condition condition, List<Integer> firstVector, List<Integer> secondVector) {
        this.queueSize = queueSize;
        this.queue = queue;
        this.mutex = mutex;
        this.condition = condition;
        this.firstVector = firstVector;
        this.secondVector = secondVector;
    }

    @Override
    public void run() {
        for (int i = 0; i < firstVector.size(); i++) {

            computeProduct(firstVector.get(i), secondVector.get(i));
        }
    }

    private void computeProduct(int firstVectorElement, int secondVectorElement) {
        mutex.lock();
        try {
            while (queue.size() == queueSize) {
                System.out.println("The queue is full. Producer is waiting");
                condition.await();
            }
            queue.add(firstVectorElement * secondVectorElement);
            System.out.println("Producer produced the product: " + firstVectorElement + " * " + secondVectorElement + " = " + firstVectorElement * secondVectorElement);
            condition.signal();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            mutex.unlock();
        }
    }
}
