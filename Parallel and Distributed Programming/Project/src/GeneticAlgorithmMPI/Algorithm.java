package GeneticAlgorithmMPI;

import GeneticAlgorithmParallel.Schedule;

import java.util.Random;


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

    public Schedule getFirstSchedule() {
        return firstSchedule;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public Schedule[] getPopulation() {
        return population;
    }


    public Schedule getBestChromosome() {
        return population[bestSchedule[0]];
    }

    public void initialize() {
        for (int i = 0; i < populationSize; i++) {
            population[i] = firstSchedule.createNewChromosome();
            bestSchedule[i] = -1;
            bestFlags[i] = false;
        }
    }

    public void crossover() {
        for (int i = 0; i < populationSize; i++) {
            Random random = new Random();
            if (random.nextInt(100) < crossoverProbability) {
                int parent2 = selectParent(i);
                population[i] = population[i].crossover(population[parent2]);
            }
        }
    }

    private int selectParent(int i) {
        int parent2 = (int) (Math.random() * populationSize);
        for (int j = 0; j < numberOfCrossoverPoints - 1; j++) {
            int tmp = (int) (Math.random() * populationSize);
            if (population[i].getFitness() < population[tmp].getFitness()) {
                parent2 = tmp;
            }
        }
        return parent2;
    }

    public void mutation() {
        for (int i = 0; i < populationSize; i++) {
            Random random = new Random();
            if (random.nextInt(100) > mutationProbability) {
                continue;
            }

            population[i].mutation();
        }
    }

    public void selection() {
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

    private boolean isInBestChromosomeGroup(int i) {
        return bestFlags[i];
    }

    public void evaluateBestSchedules() {
        for (int i = 0; i < populationSize; i++) {
            if (bestSchedule[0] == -1 || (bestSchedule[i] != -1 && population[i].getFitness() < population[bestSchedule[i]].getFitness())) {
                bestSchedule[i] = i;
                bestFlags[i] = true;
            }
            else {
                bestFlags[i] = false;
            }
        }
    }

    public void evaluateFitness() {
        for (int i = 0; i < populationSize; i++) {
            population[i].calculateFitness();
            if (bestSchedule[0] == -1 || (bestSchedule[i] != -1 && population[i].getFitness() < population[bestSchedule[i]].getFitness())) {
                bestSchedule[i] = i;
                bestFlags[i] = true;
            }
            else {
                bestFlags[i] = false;
            }
        }
    }

    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public void setIndividual(int index, Schedule schedule) {
        population[index] = schedule;
    }
}
