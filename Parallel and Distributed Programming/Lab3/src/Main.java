import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        int tasksCount = 50;
        boolean threadPool = false;
        String[] splitApproaches = { "row", "column", "k-th element" };
        String splitApproach = splitApproaches[2];

        Matrix A = new Matrix(100, 100);
        Matrix B = new Matrix(100, 100);

        Random rand = new Random();
        for (int i = 0; i < A.getRows(); i++) {
            for (int j = 0; j < A.getCols(); j++) {
                A.setElement(i, j, rand.nextInt(10));
            }
        }

        for (int i = 0; i < B.getRows(); i++) {
            for (int j = 0; j < B.getCols(); j++) {
                B.setElement(i, j, rand.nextInt(10));
            }
        }

        System.out.println("A: \n" + A);
        System.out.println("B: \n" + B);

        if (A.getCols() == B.getRows()) {
            Matrix M = new Matrix(A.getRows(), B.getCols());
            if (!threadPool) {
                System.out.println("A thread for each task approach and each task computes the elements by " + splitApproach + "\n");
                classicThreadApproach(A, B, M, tasksCount, splitApproach);
            }
            else {
                System.out.println("Thread pool used and each task computes the elements by " + splitApproach + "\n");
                threadPoolApproach(A, B, M, tasksCount, splitApproach);
            }
        }
        else
            System.out.println("Can't multiply these 2 matrices");

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("\nThe program execution took " + duration + " milliseconds.");

    }

    public static void classicThreadApproach(Matrix A, Matrix B, Matrix M, int threadsCount, String splitTasksMethod) {
        List<Thread> threads = new ArrayList<>();
        if (splitTasksMethod.equals("row")) {
            for (int i = 0; i < threadsCount; i++)
                threads.add(Operations.createRowTask(A, B, M, i, threadsCount));
        }
        else if (splitTasksMethod.equals("column")) {
            for (int i = 0; i < threadsCount; i++)
                threads.add(Operations.createColumnTask(A, B, M, i, threadsCount));
        }
        else {
            for (int i = 0; i < threadsCount; i++)
                threads.add(Operations.createKthElemTask(A, B, M, i, threadsCount));
        }

        for(Thread thread : threads){
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("M: \n" + M);
    }

    public static void threadPoolApproach(Matrix A, Matrix B, Matrix M, int tasksCount, String splitTasksMethod) {
        ExecutorService executorService = new ThreadPoolExecutor(16, 32, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(tasksCount));

        if (splitTasksMethod.equals("row")) {
            for (int i = 0; i < tasksCount; i++)
                executorService.submit(Operations.createRowTask(A, B, M, i, tasksCount));
        }
        else if (splitTasksMethod.equals("column")) {
            for (int i = 0; i < tasksCount; i++)
                executorService.submit(Operations.createColumnTask(A, B, M, i, tasksCount));
        }
        else {
            for (int i = 0; i < tasksCount; i++)
                executorService.submit(Operations.createKthElemTask(A, B, M, i, tasksCount));
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
            System.out.println("M: \n" + M);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}