import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Graph {
    private List<Integer> vertices;
    private HashMap<Integer, List<Integer>> edges;

    public Graph(List<Integer> vertices, HashMap<Integer, List<Integer>> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public Graph(int nodes) {
        this.vertices = new ArrayList<>();
        this.edges = new HashMap<>();
        for (int i = 0; i < nodes; i++) {
            this.vertices.add(i);
            this.edges.put(i, new ArrayList<>());
        }
        generateEdges();
    }

    public void addEdge(int u, int v) {
        if (!edges.get(u).contains(v))
            edges.get(u).add(v);
    }

    private void generateEdges() {
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (Math.random() < 0.5) {
                    addEdge(i, j);
                }
            }
        }
    }

    public List<Integer> getVertices() {
        return vertices;
    }

    public HashMap<Integer, List<Integer>> getEdges() {
        return edges;
    }

    public int size() {
        return vertices.size();
    }

    public int edgesSize() {
        int count = 0;
        for (Integer source : edges.keySet()) {
            count += edges.get(source).size();
        }
        return count;
    }

    public String edgesToString() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        sb.append("{");
        for (Integer source : edges.keySet()) {
            for (Integer destination : edges.get(source)) {
                sb.append("(").append(source).append(", ").append(destination).append(")");
                if (count < edgesSize() - 1)
                    sb.append(", ");
                count++;
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Graph{" +
                "vertices = " + vertices +
                ", edges = " + edgesToString() +
                '}';
    }
}
