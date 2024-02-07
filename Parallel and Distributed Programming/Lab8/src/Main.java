import mpi.MPI;
import mpi.MPIException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws InterruptedException, MPIException {
        MPI.Init(args);

        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        Map<String, Integer> variables = new HashMap<>(Map.of(
                "x", 1,
                "y", 2,
                "z", 3
        ));
        Map<String, Set<Integer>> subscribers = new HashMap<>(Map.of(
                "x", new HashSet<>(),
                "y", new HashSet<>(),
                "z", new HashSet<>()
        ));

        DSM dsm = new DSM(subscribers, variables);
        if (me == 0) {
            System.out.println("Number of nodes: " + size);
            Thread subscriber = new Thread(new Subscriber(dsm));
            subscriber.start();

            dsm.subscribe("x");
            dsm.subscribe("y");
            dsm.subscribe("z");
            dsm.compareAndExchange("x", 1, 12);
            dsm.compareAndExchange("y", 2, 13);
            dsm.compareAndExchange("z", 30, 100);

            Thread.sleep(1000);
            dsm.close();
            subscriber.join();
        }
        else if (me == 1) {
            Thread subscriber2 = new Thread(new Subscriber(dsm));
            subscriber2.start();

            dsm.subscribe("x");
            dsm.subscribe("y");
            dsm.compareAndExchange("y", 2, 20);

            subscriber2.join();
        }
        else if (me == 2) {
            Thread subscriber3 = new Thread(new Subscriber(dsm));
            subscriber3.start();

            dsm.subscribe("y");
            dsm.subscribe("z");
            dsm.compareAndExchange("y", 2, 15);
            dsm.compareAndExchange("z", 3, 18);

            subscriber3.join();
        }
        else if (me == 3) {
            Thread subscriber4 = new Thread(new Subscriber(dsm));
            subscriber4.start();

            dsm.subscribe("z");
            dsm.compareAndExchange("z", 3, 40);
            dsm.compareAndExchange("x", 1, 20);

            subscriber4.join();
        }
        MPI.Finalize();

    }
}