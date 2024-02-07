import GeneticAlgorithmParallel.Algorithm;
import GeneticAlgorithmParallel.Configuration;
import GeneticAlgorithmParallel.Schedule;
import Model.*;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
        Configuration.getInstance().parseFile("src/InputFiles/input1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Professor> professors = Configuration.getInstance().getProfessors();
        for (Professor professor : professors) {
            System.out.println(professor);
        }

        List<Course> courses = Configuration.getInstance().getCourses();
        for (Course course : courses) {
            System.out.println(course);
        }

        List<StudentsGroup> groups = Configuration.getInstance().getGroups();
        for (StudentsGroup group : groups) {
            System.out.println(group);
        }

        List<Classroom> classrooms = Configuration.getInstance().getClassrooms();
        for (Classroom classroom : classrooms) {
            System.out.println(classroom);
        }

        List<CourseClass> classes = Configuration.getInstance().getClasses();
        for (CourseClass courseClass : classes) {
            System.out.println(courseClass);
        }

        int numberOfCrossoverPoints = 3;
        int mutationProbability = 50;
        int crossoverProbability = 70;
        int mutationRate = 4;
        int populationSize = 16;
        int numberOfGenerations = 20;

        System.out.println("\n----------------------------------\n");

        Schedule firstSchedule = new Schedule(numberOfCrossoverPoints, mutationRate, crossoverProbability, mutationProbability);
        Algorithm ga = new Algorithm(firstSchedule, populationSize, numberOfGenerations, numberOfCrossoverPoints, mutationRate, crossoverProbability, mutationProbability);

        Long startTime = System.currentTimeMillis();
        ga.run();
        Long endTime = System.currentTimeMillis();

        Schedule solution = ga.getBestChromosome();
        System.out.println("Fitness: " + solution.getFitness() + "\n");
        System.out.println(solution + "\n");

        System.out.println("Timetable scheduling execution took: " + (endTime - startTime) + " ms");

    }
}