package Algorithms;

import Polynomial.Polynomial;

import java.util.ArrayList;

public class KaratsubaSequential {
    public Polynomial multiply(Polynomial p, Polynomial q) {
        if (p.getDegree() < 2 || q.getDegree() < 2) {
            return new ClassicSequential().multiply(p, q);
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

        Polynomial z1 = multiply(pLow, qLow);
        Polynomial z2 = multiply(pLow.add(pHigh), qLow.add(qHigh));
        Polynomial z3 = multiply(pHigh, qHigh);

        Polynomial r1 = z3.increasePolynomialDegree(2 * m);
        Polynomial r2 = z2.subtract(z1).subtract(z3).increasePolynomialDegree(m);

        return r1.add(r2).add(z1);
    }
}
