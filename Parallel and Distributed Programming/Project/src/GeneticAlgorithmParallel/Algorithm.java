package GeneticAlgorithmParallel;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Algorithm {
    private Schedule firstSchedule;
    private int populationSize;
    private int numberOfGenerations;
    private int numberOfCrossoverPoints;
    private int mutationRate;
    private int crossoverProbability;
    private int mutationProbability;
    private Schedule[] population;
    private int[] bestSchedule;
    private boolean[] bestFlags;

    private ForkJoinPool forkJoinPool = new ForkJoinPool();

    public Algorithm(Schedule firstSchedule, int populationSize, int numberOfGenerations, int numberOfCrossoverPoints, int mutationRate, int crossoverProbability, int mutationProbability) {
        this.firstSchedule = firstSchedule;
        this.populationSize = populationSize;
        this.numberOfGenerations = numberOfGenerations;
        this.numberOfCrossoverPoints = numberOfCrossoverPoints;
        this.mutationRate = mutationRate;
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
        this.population = new Schedule[populationSize];
        this.bestSchedule = new int[populationSize];
        this.bestFlags = new boolean[populationSize];
    }

    public void run() {
        initialize();
        for (int i = 0; i < numberOfGenerations; i++) {
            crossover();
            mutation();
            selection();
            evaluate();
        }
    }

    private void initialize() {
        for (int i = 0; i < populationSize; i++) {
            population[i] = firstSchedule.createNewChromosome();
            bestSchedule[i] = -1;
            bestFlags[i] = false;
        }
    }

    private void evaluate() {
        forkJoinPool.invoke(new ParallelEvaluationTask(population, 0, populationSize, bestSchedule, bestFlags));
    }

    private void selection() {
        Schedule[] tmpPopulation = new Schedule[populationSize];
        for (int i = 0; i < populationSize; i++) {
            tmpPopulation[i] = new Schedule(population[i], false);
        }
        for (int i = 0; i < populationSize; i++) {
            int r1 = (int) (Math.random() * populationSize);
            int r2 = (int) (Math.random() * populationSize);
            if (!isInBestChromosomeGroup(i)) {
                if (tmpPopulation[r1].getFitness() > tmpPopulation[r2].getFitness()) {
                    population[i] = new Schedule(tmpPopulation[r1], false);
                }
                else {
                    population[i] = new Schedule(tmpPopulation[r2], false);
                }
            }
        }
    }

    private void crossover() {
        CrossoverTask crossoverTask = new CrossoverTask(population, 0, populationSize, crossoverProbability);
        forkJoinPool.invoke(crossoverTask);
    }

    private void mutation() {
        MutationTask mutationTask = new MutationTask(population, 0, populationSize, mutationProbability);
        forkJoinPool.invoke(mutationTask);
    }

    public Schedule getBestChromosome() {
        return population[bestSchedule[0]];
    }

    private boolean isInBestChromosomeGroup(int index) {
        return bestFlags[index];
    }

    private void addToBestChromosomeGroup(int index) {
        bestFlags[index] = true;
    }
}
