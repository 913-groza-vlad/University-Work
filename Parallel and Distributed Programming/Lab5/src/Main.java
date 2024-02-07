import Algorithms.*;
import Polynomial.Polynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> coefficients = new ArrayList<>(List.of(4, 3, 0, 1, 9));
        Polynomial polynomial = new Polynomial(coefficients);
        System.out.println(polynomial);
        System.out.println(new Polynomial(10));
        System.out.println();

        ArrayList<Integer> coefficientsA = new ArrayList<>() { {
            add(5);
            add(0);
            add(4);
            add(8);
        } };
        ArrayList<Integer> coefficientsB = new ArrayList<>() { {
            add(-1);
            add(-6);
            add(8);
        } };
        Polynomial a = new Polynomial(coefficientsA);
        Polynomial b = new Polynomial(coefficientsB);
        System.out.println("A = " + a);
        System.out.println("B = " + b);
        System.out.println("A * B = " + new ClassicSequential().multiply(a, b) + "\n");

        Polynomial p = new Polynomial(2000);
        Polynomial q = new Polynomial(2000);
        System.out.println("P = " + p);
        System.out.println("Q = " + q + "\n");

        // classic sequential algorithm
        long startTime = System.currentTimeMillis();
        System.out.println("P * Q = " + new ClassicSequential().multiply(p, q));
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Multiplication using the sequential classic approach took " + duration + " milliseconds.\n");

        startTime = System.currentTimeMillis();
        System.out.println("P * Q = " + new ClassicParallel().multiply(p, q));
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Multiplication using the parallel classic approach took " + duration + " milliseconds.\n");

        startTime = System.currentTimeMillis();
        System.out.println("P * Q = " + new KaratsubaSequential().multiply(p, q));
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Multiplication using the sequential Karatsuba approach took " + duration + " milliseconds.\n");

        startTime = System.currentTimeMillis();

        try {
            System.out.println("P * Q = " + new KaratsubaParallel(ThreadPool.forkJoinPool).multiply(p, q));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Multiplication using the parallel Karatsuba approach took " + duration + " milliseconds.\n");
    }
}