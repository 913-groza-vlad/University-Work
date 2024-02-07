package Algorithms;

import Polynomial.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class ClassicParallel {

    public Polynomial multiply(Polynomial a, Polynomial b) {
        int nrThreads = 250;

        int resultDegree = a.getDegree() + b.getDegree() + 1;
        ArrayList<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i < a.getDegree() + b.getDegree() + 1; i++) {
            coefficients.add(0);
        }
        Polynomial result = new Polynomial(coefficients);

        List<Thread> threads = new ArrayList<>(nrThreads);
        int step = resultDegree / nrThreads;
        if (step == 0) {
            step = 1;
        }

        for (int i = 0; i < resultDegree; i += step) {
            int end = i + step;
            Task task = new Task(a, b, result, i, end);
            threads.add(new Thread(task));
        }

        threads.forEach(Thread::start);

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        return result;
    }
}
