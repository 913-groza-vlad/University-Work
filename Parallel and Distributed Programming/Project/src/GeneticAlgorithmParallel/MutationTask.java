package GeneticAlgorithmParallel;

import java.util.Random;
import java.util.concurrent.RecursiveAction;

public class MutationTask  extends RecursiveAction {
    private final Schedule[] population;
    private final int start;
    private final int end;
    private final int mutationProbability;

    public MutationTask(Schedule[] population, int start, int end, int mutationProbability) {
        this.population = population;
        this.start = start;
        this.end = end;
        this.mutationProbability = mutationProbability;
    }

    @Override
    protected void compute() {
        if (end - start <= 4) {
            // If the range is small, perform mutation sequentially
            for (int i = start; i < end; i++) {
                mutateIndividual(i);
            }
        } else {
            // Split the range and invoke tasks recursively
            int mid = start + (end - start) / 2;
            MutationTask leftTask = new MutationTask(population, start, mid, mutationProbability);
            MutationTask rightTask = new MutationTask(population, mid, end, mutationProbability);

            invokeAll(leftTask, rightTask);
        }
    }

    private void mutateIndividual(int i) {
        Random random = new Random();
        if (random.nextInt(100) > mutationProbability) {
            return;
        }

        population[i].mutation();
    }
}
