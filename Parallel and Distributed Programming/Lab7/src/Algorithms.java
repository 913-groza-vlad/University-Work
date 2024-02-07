import java.util.ArrayList;

public class Algorithms {
    public static Polynomial getMultiplicationResult(Object[] polynomials) {
        int size = ((Polynomial) polynomials[0]).getDegree();
        ArrayList<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i <= size; i++) {
            coefficients.add(0);
        }
        Polynomial result = new Polynomial(coefficients);

        for (Object polynomial : polynomials) {
            result = result.add((Polynomial) polynomial);
        }

        return result;
    }

    public static Polynomial multiplySequence(Polynomial p, Polynomial q, int start, int end) {
        ArrayList<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i < p.getDegree() + q.getDegree() + 1; i++) {
            coefficients.add(0);
        }
        Polynomial result = new Polynomial(coefficients);
        for (int i = start; i < end; i++) {
            for (int j = 0; j < q.getCoefficients().size(); j++) {
                result.getCoefficients().set(i + j,
                        result.getCoefficients().get(i + j) +
                                p.getCoefficients().get(i) * q.getCoefficients().get(j));
            }
        }
//        for (int i = start; i < end; i++) {
//            if (i > result.getCoefficients().size()) {
//                return result;
//            }
//            for (int j = 0; j <= i; j++) {
//                if (j < p.getCoefficients().size() && (i - j) < q.getCoefficients().size()) {
//                    int value = p.getCoefficients().get(j) * q.getCoefficients().get(i - j);
//                    result.getCoefficients().set(i, result.getCoefficients().get(i) + value);
//                }
//            }
//        }

        return result;
    }

    public static Polynomial classicSequential(Polynomial p, Polynomial q) {
        ArrayList<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i < p.getDegree() + q.getDegree() + 1; i++) {
            coefficients.add(0);
        }
        Polynomial result = new Polynomial(coefficients);

        for (int i = 0; i <= p.getDegree(); i++) {
            for (int j = 0; j <= q.getDegree(); j++) {
                result.getCoefficients().set(i + j,
                        result.getCoefficients().get(i + j) +
                                p.getCoefficients().get(i) * q.getCoefficients().get(j));
            }
        }

        return result;
    }

    public static Polynomial KaratsubaSequential(Polynomial p, Polynomial q) {
        if (p.getDegree() < 2 || q.getDegree() < 2) {
            return classicSequential(p, q);
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

        Polynomial z1 = KaratsubaSequential(pLow, qLow);
        Polynomial z2 = KaratsubaSequential(pLow.add(pHigh), qLow.add(qHigh));
        Polynomial z3 = KaratsubaSequential(pHigh, qHigh);

        Polynomial r1 = z3.increasePolynomialDegree(2 * m);
        Polynomial r2 = z2.subtract(z1).subtract(z3).increasePolynomialDegree(m);

        return r1.add(r2).add(z1);
    }
}
