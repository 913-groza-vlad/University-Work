import GeneticAlgorithmMPI.Algorithm;
import GeneticAlgorithmMPI.Workers;
import GeneticAlgorithmParallel.Configuration;
import GeneticAlgorithmParallel.Schedule;
import mpi.MPI;

import java.io.FileNotFoundException;

public class MainMPI {
    public static void main(String[] args) {
        MPI.Init(args);

        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        try {
            Configuration.getInstance().parseFile("src/InputFiles/input1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int numberOfCrossoverPoints = 3;
        int mutationProbability = 50;
        int crossoverProbability = 70;
        int mutationRate = 4;
        int populationSize = 16;
        int numberOfGenerations = 20;

        if (me == 0) {

            Schedule firstSchedule = new Schedule(numberOfCrossoverPoints, mutationRate, crossoverProbability, mutationProbability);
            Algorithm ga = new Algorithm(firstSchedule, populationSize, numberOfGenerations, numberOfCrossoverPoints, mutationRate, crossoverProbability, mutationProbability);
            ga.initialize();

            Long startTime = System.currentTimeMillis();

            for (int i = 0; i < numberOfGenerations; i++) {
                ga.crossover();
                ga.mutation();
                ga.selection();
                if (i == numberOfGenerations - 1)
                    Workers.MasterWorker(ga, size);
                else
                    ga.evaluateFitness();
            }

            Long endTime = System.currentTimeMillis();

            Schedule solution = ga.getBestChromosome();
            System.out.println("Fitness: " + solution.getFitness() + "\n");
            System.out.println(solution + "\n");

            System.out.println("Timetable scheduling execution took: " + (endTime - startTime) + " ms");

        }
        else {
            Workers.FitnessEvaluationWorker(me);
        }


        MPI.Finalize();
    }
}
