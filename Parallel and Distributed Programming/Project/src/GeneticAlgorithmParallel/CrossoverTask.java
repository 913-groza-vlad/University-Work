package GeneticAlgorithmParallel;

import java.util.Random;
import java.util.concurrent.RecursiveAction;

public class CrossoverTask extends RecursiveAction {
    private final Schedule[] population;
    private final int start;
    private final int end;
    private final int crossoverProbability;

    public CrossoverTask(Schedule[] population, int start, int end, int crossoverProbability) {
        this.population = population;
        this.start = start;
        this.end = end;
        this.crossoverProbability = crossoverProbability;
    }

    @Override
    protected void compute() {
        if (end - start <= 4) {
            // If the range is small, perform crossover sequentially
            for (int i = start; i < end; i++) {
                crossoverIndividual(i);
            }
        } else {
            // Split the range and invoke tasks recursively
            int mid = start + (end - start) / 2;
            CrossoverTask leftTask = new CrossoverTask(population, start, mid, crossoverProbability);
            CrossoverTask rightTask = new CrossoverTask(population, mid, end, crossoverProbability);

            invokeAll(leftTask, rightTask);
        }
    }

    private void crossoverIndividual(int i) {
        Random random = new Random();
        if (random.nextInt(100) < crossoverProbability) {
            int parent2 = selectParent(i);
            population[i] = population[i].crossover(population[parent2]);
        }
    }

    private int selectParent(int i) {
        int parent2 = (int) (Math.random() * population.length);
        for (int j = 0; j < population.length - 1; j++) {
            int tmp = (int) (Math.random() * population.length);
            if (population[i].getFitness() < population[tmp].getFitness()) {
                parent2 = tmp;
            }
        }
        return parent2;
    }
}
