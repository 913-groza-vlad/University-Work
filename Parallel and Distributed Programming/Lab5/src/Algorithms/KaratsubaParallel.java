package Algorithms;

import Polynomial.Polynomial;

import java.util.ArrayList;
import java.util.concurrent.*;

public class KaratsubaParallel {
    private static final int THRESHOLD = 200;
    ForkJoinPool forkJoinPool;

    public KaratsubaParallel(ForkJoinPool forkJoinPool) {
        this.forkJoinPool = forkJoinPool;
    }

    public Polynomial multiply(Polynomial p, Polynomial q) throws ExecutionException, InterruptedException {
        if (p.getDegree() < THRESHOLD || q.getDegree() < THRESHOLD) {
            return new KaratsubaSequential().multiply(p, q);
        }

        int m = Math.max(p.getDegree(), q.getDegree()) / 2;
        ArrayList<Integer> pLowCoefficients = new ArrayList<>();
        ArrayList<Integer> pHighCoefficients = new ArrayList<>();
        ArrayList<Integer> qLowCoefficients = new ArrayList<>();
        ArrayList<Integer> qHighCoefficients = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            if (i < p.getCoefficients().size()) {
                pLowCoefficients.add(p.getCoefficients().get(i));
            }
            else {
                pLowCoefficients.add(0);
            }
            if (i < q.getCoefficients().size()) {
                qLowCoefficients.add(q.getCoefficients().get(i));
            }
            else {
                qLowCoefficients.add(0);
            }
        }
        for (int i = m; i < p.getCoefficients().size(); i++) {
            pHighCoefficients.add(p.getCoefficients().get(i));
        }
        for (int i = m; i < q.getCoefficients().size(); i++) {
            qHighCoefficients.add(q.getCoefficients().get(i));
        }

        Polynomial pLow = new Polynomial(pLowCoefficients);
        Polynomial pHigh = new Polynomial(pHighCoefficients);
        Polynomial qLow = new Polynomial(qLowCoefficients);
        Polynomial qHigh = new Polynomial(qHighCoefficients);

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        MultiplyTask task = new MultiplyTask(pLow, qLow);
        Polynomial z1 = forkJoinPool.invoke(task);

        task = new MultiplyTask(pLow.add(pHigh), qLow.add(qHigh));
        Polynomial z2 = forkJoinPool.invoke(task);

        task = new MultiplyTask(pHigh, qHigh);
        Polynomial z3 = forkJoinPool.invoke(task);

        forkJoinPool.shutdown();

        Polynomial r1 = z3.increasePolynomialDegree(2 * m);
        Polynomial r2 = z2.subtract(z1).subtract(z3).increasePolynomialDegree(m);

        return r1.add(r2).add(z1);
    }

    private static class MultiplyTask extends RecursiveTask<Polynomial> {
        private final Polynomial p;
        private final Polynomial q;

        MultiplyTask(Polynomial p, Polynomial q) {
            this.p = p;
            this.q = q;
        }

        @Override
        protected Polynomial compute() {
            try {
                return new KaratsubaParallel(ThreadPool.forkJoinPool).multiply(p, q);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
