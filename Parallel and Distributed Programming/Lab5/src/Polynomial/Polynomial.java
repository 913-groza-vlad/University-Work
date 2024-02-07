package Polynomial;

import java.util.ArrayList;
import java.util.Random;

public class Polynomial {
    private ArrayList<Integer> coefficients;
    private int degree;

    public Polynomial(ArrayList<Integer> coefficients) {
        this.coefficients = coefficients;
        this.degree = coefficients.size() - 1;
    }

    private void generateCoefficients(int degree) {
        Random random = new Random();
        for (int i = 0; i < degree; i++) {
            this.coefficients.add(random.nextInt(-9,10));
        }
        this.coefficients.add(random.nextInt(1,10));
    }

    public Polynomial(int degree) {
        this.degree = degree;
        this.coefficients = new ArrayList<>();
        this.generateCoefficients(degree);
    }

    public int getDegree() {
        return this.degree;
    }

    public ArrayList<Integer> getCoefficients() {
        return this.coefficients;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = coefficients.size() - 1; i >= 0; i--) {
            int coefficient = coefficients.get(i);

            if (coefficient != 0) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append((coefficient > 0) ? " + " : " - ");
                } else if (coefficient < 0) {
                    stringBuilder.append("-");
                }

                if (i == 0) {
                    stringBuilder.append(Math.abs(coefficient));
                } else {
                    if (Math.abs(coefficient) != 1) {
                        stringBuilder.append(Math.abs(coefficient));
                    }

                    stringBuilder.append("X");

                    if (i > 1) {
                        stringBuilder.append("^").append(i);
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    public Polynomial add(Polynomial polynomial) {
        ArrayList<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i < Math.max(this.degree, polynomial.degree) + 1; i++) {
            coefficients.add(0);
        }

        Polynomial result = new Polynomial(coefficients);
        for (int i = 0; i <= this.degree; i++) {
            result.getCoefficients().set(i, result.getCoefficients().get(i) + this.coefficients.get(i));
        }
        for (int i = 0; i <= polynomial.degree; i++) {
            result.getCoefficients().set(i, result.getCoefficients().get(i) + polynomial.coefficients.get(i));
        }

        return result;
    }

    public Polynomial subtract(Polynomial polynomial) {
        ArrayList<Integer> coefficients = new ArrayList<>();
        int maxSize = Math.max(this.coefficients.size(), polynomial.coefficients.size());
        for (int i = 0; i < maxSize; i++) {
            coefficients.add(0);
        }

        Polynomial result = new Polynomial(coefficients);
        for (int i = 0; i < this.coefficients.size(); i++) {
            result.getCoefficients().set(i, result.getCoefficients().get(i) + this.coefficients.get(i));
        }
        for (int i = 0; i < polynomial.coefficients.size(); i++) {
            result.getCoefficients().set(i, result.getCoefficients().get(i) - polynomial.coefficients.get(i));
        }

        int i = coefficients.size() - 1;
        while (i > 0 && result.getCoefficients().get(i) == 0) {
            result.getCoefficients().remove(i);
            i--;
        }

        return result;
    }

    public Polynomial increasePolynomialDegree(int degree) {
        ArrayList<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i < degree; i++) {
            coefficients.add(0);
        }

        coefficients.addAll(this.coefficients);
        return new Polynomial(coefficients);
    }

}
