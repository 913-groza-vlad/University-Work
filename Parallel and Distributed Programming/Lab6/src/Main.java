import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int startVertex = 3;

        Graph graph = new Graph(100);
        System.out.println(graph);
        HamiltonianCycleSolution solution1 = new HamiltonianCycleSolution(graph);
        long startTime = System.currentTimeMillis();
        solution1.search(startVertex);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Hamiltonian cycle search took " + duration + " milliseconds.\n");

        List<Integer> vertices = List.of(0, 1, 2, 3, 4);
        HashMap<Integer, List<Integer>> edges = new HashMap<>();
        edges.put(0, List.of(1, 2));
        edges.put(1, List.of(0, 2, 3));
        edges.put(2, List.of(4));
        edges.put(3, List.of(2, 3));
        edges.put(4, List.of(0, 1, 3));
        Graph graph2 = new Graph(vertices, edges);
        System.out.println(graph2);
        HamiltonianCycleSolution solution = new HamiltonianCycleSolution(graph2);
        solution.search(startVertex);
    }
}