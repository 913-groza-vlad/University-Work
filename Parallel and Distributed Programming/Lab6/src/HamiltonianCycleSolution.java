import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class HamiltonianCycleSolution {
    private Graph graph;

    public HamiltonianCycleSolution(Graph graph) {
        this.graph = graph;
    }

    private class HamiltonianTask extends RecursiveTask<List<Integer>> {
        private final List<Integer> path;
        private final int position;

        HamiltonianTask(List<Integer> path, int position) {
            this.path = path;
            this.position = position;
        }

        @Override
        protected List<Integer> compute() {
            if (position == path.size()) {
                if (graph.getEdges().get(path.get(position - 1)).contains(path.get(0)))
                    return path;
                else
                    return new ArrayList<>();
            }

            List<HamiltonianTask> tasks = new ArrayList<>();

            for (int v : graph.getEdges().getOrDefault(path.get(position - 1), new ArrayList<>())) {
                if (isVertexValid(v, position, path)) {
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.set(position, v);
                    HamiltonianTask task = new HamiltonianTask(newPath, position + 1);
                    tasks.add(task);
                    task.fork();
                }
            }

            for (HamiltonianTask task : tasks) {
                List<Integer> result = task.join();
                if (!result.isEmpty()) {
                    return result;
                }
            }

            return new ArrayList<>();
        }
    }

    private boolean isVertexValid(int vertex, int position, List<Integer> path) {
        if (!graph.getEdges().get(path.get(position - 1)).contains(vertex))
            return false;
        for (int i = 0; i < position; i++)
            if (path.get(i) == vertex)
                return false;
        return true;
    }

    public void search(int startVertex) {
        List<Integer> path = new ArrayList<>(graph.size());
        for (int i = 0; i < graph.size(); i++) {
            path.add(-1);
        }
        path.set(0, startVertex);

        HamiltonianTask task = new HamiltonianTask(path, 1);
        List<Integer> resultPath = task.invoke();

        if (resultPath.isEmpty()) {
            System.out.println("No Hamiltonian Cycle found.");
        } else {
            System.out.print("Hamiltonian Cycle found: ");
            for (int vertex : resultPath) {
                System.out.print(vertex + " ");
            }
            System.out.println(resultPath.get(0));
        }
    }
}
