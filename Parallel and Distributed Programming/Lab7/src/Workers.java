import mpi.MPI;

public class Workers {

    public static void masterWorker(Polynomial p, Polynomial q, int processes) {
        long startTime = System.currentTimeMillis();
        // double startTime = MPI.Wtime();

        int totalCoefficients = p.getCoefficients().size();
        // int totalCoefficients = p.getDegree() + q.getDegree() + 1;
        int processesToUse = processes - 1;
        int baseSize = totalCoefficients / processesToUse;
        int remainder = totalCoefficients % processesToUse;

        int start = 0;
        for (int i = 1; i  < processes; i++){
            int end = start + baseSize + (i <= remainder ? 1 : 0);

            MPI.COMM_WORLD.Send(new Object[]{p}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new Object[]{q}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{start}, 0, 1, MPI.INT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{end}, 0, 1, MPI.INT, i, 0);

            start = end;
        }

        Object[] results = new Object[processes - 1];
        for (int i = 1; i < processes; i++) {
            MPI.COMM_WORLD.Recv(results, i - 1, 1, MPI.OBJECT, i, 0);
        }

        Polynomial result = Algorithms.getMultiplicationResult(results);

        long endTime = System.currentTimeMillis();
        // double endTime = MPI.Wtime();
        long timeElapsed = endTime - startTime;
        System.out.println("P * Q = " + result);
        System.out.println("Execution time in milliseconds: " + timeElapsed);
    }

    public static void classicWorker(int me) {
        // System.out.println("Worker of rank " + MPI.COMM_WORLD.Rank() + " started");

        Object[] pObject = new Object[1];
        Object[] qObject = new Object[1];
        int[] start = new int[1];
        int[] end = new int[1];

        MPI.COMM_WORLD.Recv(pObject, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(qObject, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(start, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(end, 0, 1, MPI.INT, 0, 0);

        Polynomial p = (Polynomial) pObject[0];
        Polynomial q = (Polynomial) qObject[0];

        // System.out.println("Worker " + me + " computes the polynomial corresponding to the coefficients in the range of p " + start[0] + " and " + (end[0] - 1));

        Polynomial result = Algorithms.multiplySequence(p, q, start[0], end[0]);

        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);
    }

    public static void KaratsubaWorker(int me) {
        // System.out.println("Worker of rank " + MPI.COMM_WORLD.Rank() + " started");

        Object[] pObject = new Object[1];
        Object[] qObject = new Object[1];
        int[] start = new int[1];
        int[] end = new int[1];

        MPI.COMM_WORLD.Recv(pObject, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(qObject , 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(start, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(end, 0, 1, MPI.INT, 0, 0);

        Polynomial p = (Polynomial) pObject[0];
        Polynomial q = (Polynomial) qObject[0];

        for (int i = 0; i < start[0]; i++) {
            p.getCoefficients().set(i, 0);
        }
        for (int j = end[0]; j < p.getCoefficients().size(); j++) {
            p.getCoefficients().set(j, 0);
        }

        // System.out.println("Worker " + me + " computes the polynomial corresponding to the coefficients of p in the range " + start[0] + " and " + (end[0] - 1));

        Polynomial result = Algorithms.KaratsubaSequential(p, q);

        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);
    }
}
