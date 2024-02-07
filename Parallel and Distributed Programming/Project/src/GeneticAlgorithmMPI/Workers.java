package GeneticAlgorithmMPI;

import GeneticAlgorithmParallel.Schedule;
import mpi.MPI;

public class Workers {
    public static void MasterWorker(Algorithm ga, int size) {
        int processes = size - 1;
        int chromosomesPerProcess = ga.getPopulationSize() / processes;
        int remainder = ga.getPopulationSize() % processes;

        int start = 0;
        for (int i = 1; i <= processes; i++){
            int end = start + chromosomesPerProcess + (i <= remainder ? 1 : 0);

            Schedule[] populationSection = new Schedule[end - start];
            for (int j = 0; j < end - start; j++) {
                populationSection[j] = ga.getPopulation()[start + j];
            }

            MPI.COMM_WORLD.Send(new Object[]{populationSection}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{start}, 0, 1, MPI.INT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{end}, 0, 1, MPI.INT, i, 0);

            start = end;
        }

        for (int i = 1; i <= processes; i++) {
            int numberOfIndividuals = chromosomesPerProcess + (i <= remainder ? 1 : 0);
            Object[] populationArrayObject = new Object[1];
            MPI.COMM_WORLD.Recv(populationArrayObject, 0, 1, MPI.OBJECT, i, 0);
            Schedule[] populationSection = (Schedule[]) populationArrayObject[0];
            for (int j = 0; j < numberOfIndividuals; j++) {
                ga.setIndividual(j + (i - 1) * chromosomesPerProcess, populationSection[j]);
            }
        }

        ga.evaluateBestSchedules();

    }

    public static void FitnessEvaluationWorker(int me) {
        Object[] populationArrayObject = new Object[1];
        int[] startArray = new int[1];
        int[] endArray = new int[1];

        MPI.COMM_WORLD.Recv(populationArrayObject, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(startArray, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(endArray, 0, 1, MPI.INT, 0, 0);

        Schedule[] population = (Schedule[]) populationArrayObject[0];
        int start = startArray[0];
        int end = endArray[0];

        System.out.println("Worker of rank " + me + " computing fitness for the individuals in the range: " + start + " and " + (end - 1));

        // Perform fitness evaluation for the assigned range
        for (int i = start; i < end - start; i++) {
            population[i].calculateFitness();
        }

        MPI.COMM_WORLD.Send(new Object[]{population}, 0, 1, MPI.OBJECT, 0, 0);

    }
}
