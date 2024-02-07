import mpi.*;

public class Main {
    static int pSize = 25, qSize = 25;
    static String approach = "classic";

    public static void main(String[] args) {
        MPI.Init(args);

        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (me == 0) {
            System.out.println("Master process of rank " + me + " started\n");
            System.out.println("Number of nodes computing the multiplication: " + (size - 1)  + "\n");
            Polynomial p = new Polynomial(pSize);
            Polynomial q = new Polynomial(qSize);
            System.out.println("P = " + p);
            System.out.println("Q = " + q + "\n");

            Workers.masterWorker(p, q, size);
        }
        else {
            if (approach.equals("classic")) {
                Workers.classicWorker(me);
            }
            if (approach.equals("Karatsuba")) {
                Workers.KaratsubaWorker(me);
            }
        }

        MPI.Finalize();
    }
}