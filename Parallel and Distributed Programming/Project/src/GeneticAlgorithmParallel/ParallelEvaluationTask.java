package GeneticAlgorithmParallel;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class ParallelEvaluationTask extends RecursiveAction {
    private Schedule[] population;
    private int start;
    private int end;
    private final int[] bestSchedule;
    private boolean[] bestFlags;

    public ParallelEvaluationTask(Schedule[] population, int start, int end, int[] bestSchedule, boolean[] bestFlags) {
        this.population = population;
        this.start = start;
        this.end = end;
        this.bestSchedule = bestSchedule;
        this.bestFlags = bestFlags;
    }

    @Override
    protected void compute() {
        if (end - start <= 4) {
            // Evaluate fitness for a small subset
            for (int i = start; i < end; i++) {
                population[i].calculateFitness();
                updateBestSchedule(i);
            }
        } else {
            // Split the task into smaller subtasks
            int mid = start + (end - start) / 2;
            ParallelEvaluationTask left = new ParallelEvaluationTask(population, start, mid, bestSchedule, bestFlags);
            ParallelEvaluationTask right = new ParallelEvaluationTask(population, mid, end, bestSchedule, bestFlags);
            invokeAll(left, right);
        }
    }

    private void updateBestSchedule(int index) {
        synchronized (bestSchedule) {
            if (bestSchedule[0] == -1 || population[index].getFitness() > population[bestSchedule[0]].getFitness()) {
                bestSchedule[0] = index;
                resetBestFlags();
            } else if (population[index].getFitness() == population[bestSchedule[0]].getFitness()) {
                bestFlags[index] = true;
            }
        }
    }

    private void resetBestFlags() {
        Arrays.fill(bestFlags, false);
    }

}
