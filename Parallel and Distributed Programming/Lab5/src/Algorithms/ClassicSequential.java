package Algorithms;

import Polynomial.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class ClassicSequential {

    public Polynomial multiply(Polynomial a, Polynomial b) {
        ArrayList<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i < a.getDegree() + b.getDegree() + 1; i++) {
            coefficients.add(0);
        }

        Polynomial result = new Polynomial(coefficients);
        for (int i = 0; i <= a.getDegree(); i++)
            for (int j = 0; j <= b.getDegree(); j++)
                result.getCoefficients().set(i + j,
                        result.getCoefficients().get(i + j) +
                                a.getCoefficients().get(i) * b.getCoefficients().get(j));

        return result;
    }
}
