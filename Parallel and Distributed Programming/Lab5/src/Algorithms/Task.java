package Algorithms;

import Polynomial.Polynomial;

public class Task implements Runnable {
    private final Polynomial polynomial1, polynomial2, result;
    private final int startCoefficient, endCoefficient;

    public Task(Polynomial polynomial1, Polynomial polynomial2, Polynomial result, int startCoefficient, int endCoefficient) {
        this.polynomial1 = polynomial1;
        this.polynomial2 = polynomial2;
        this.result = result;
        this.startCoefficient = startCoefficient;
        this.endCoefficient = endCoefficient;
    }

    @Override
    public void run() {
        for (int i = startCoefficient; i < endCoefficient; i++) {
            if (i > result.getCoefficients().size()) {
                return;
            }
            for (int j = 0; j <= i; j++) {
                if (j < polynomial1.getCoefficients().size() && (i - j) < polynomial2.getCoefficients().size()) {
                    int value = polynomial1.getCoefficients().get(j) * polynomial2.getCoefficients().get(i - j);
                    result.getCoefficients().set(i, result.getCoefficients().get(i) + value);
                }
            }
        }
    }
}
