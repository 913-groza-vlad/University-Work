package GeneticAlgorithmParallel;

import Model.Classroom;
import Model.CourseClass;

import java.io.Serializable;
import java.util.*;

public class Schedule implements Serializable {
    private static final int HOURS = 12;
    private static final int DAYS = 5;
    private int numberOfCrossoverPoints;
    private int mutationRate;
    private int crossoverProbability;
    private int mutationProbability;
    private double fitness;
    private List<Boolean> criteria;
    private Vector<List<CourseClass>> slots;
    private Map<CourseClass, Integer> classes;

    public Schedule(int numberOfCrossoverPoints, int mutationRate, int crossoverProbability, int mutationProbability) {
        this.numberOfCrossoverPoints = numberOfCrossoverPoints;
        this.mutationRate = mutationRate;
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
        this.fitness = 0.0;
        this.criteria = new ArrayList<>(Configuration.getInstance().numberOfClasses() * 5);
        for (int i = 0; i < Configuration.getInstance().numberOfClasses() * 5; i++) {
            criteria.add(false);
        }
        this.slots = new Vector<>(DAYS * HOURS * Configuration.getInstance().numberOfClassrooms());
        for (int i = 0; i < DAYS * HOURS * Configuration.getInstance().numberOfClassrooms(); i++) {
            slots.add(new ArrayList<>());
        }
        this.classes = new HashMap<>();
    }

    public Schedule(Schedule schedule, boolean setup) {
        if (!setup) {
            this.fitness = schedule.fitness;
            this.criteria = schedule.criteria;
            this.slots = schedule.slots;
            this.classes = schedule.classes;
        }
        else {
            this.fitness = 0.0;
            this.criteria = new ArrayList<>(Configuration.getInstance().numberOfClasses() * 5);
            for (int i = 0; i < Configuration.getInstance().numberOfClasses() * 5; i++) {
                criteria.add(false);
            }
            this.slots = new Vector<>(DAYS * HOURS * Configuration.getInstance().numberOfClassrooms());
            for (int i = 0; i < DAYS * HOURS * Configuration.getInstance().numberOfClassrooms(); i++) {
                slots.add(new ArrayList<>());
            }
            this.classes = new HashMap<>();
        }
        this.numberOfCrossoverPoints = schedule.numberOfCrossoverPoints;
        this.mutationRate = schedule.mutationRate;
        this.crossoverProbability = schedule.crossoverProbability;
        this.mutationProbability = schedule.mutationProbability;
    }

    public Schedule createCopy(boolean setup) {
        return new Schedule(this, setup);
    }

    public double getFitness() {
        return fitness;
    }

    public List<Boolean> getCriteria() {
        return criteria;
    }

    public Vector<List<CourseClass>> getSlots() {
        return slots;
    }

    public Map<CourseClass, Integer> getClasses() {
        return classes;
    }

    public void calculateFitness() {
        int score = 0;
        int numberOfRooms = Configuration.getInstance().numberOfClassrooms();
        int possibleClassesInDay = numberOfRooms * HOURS;

        int crt = 0;
        for (var classCourse: classes.entrySet()) {
            int p = classCourse.getValue();
            int day = p / possibleClassesInDay;
            int time = p % possibleClassesInDay;
            int room = time / HOURS;
            time = time % HOURS;
            int duration = classCourse.getKey().getDuration();

            // first criterion is that room is available
            boolean roomOverlaps = false;
            for (int i = duration - 1; i >= 0; i--) {
                if (slots.get(p + i).size() > 1) {
                    roomOverlaps = true;
                    break;
                }
            }

            if (!roomOverlaps) {
                score++;
            }

            criteria.set(crt, !roomOverlaps);

            // second criterion - if a class is located in a room with enough seats we increase the score
            CourseClass cc = classCourse.getKey();
            Classroom classroom = Configuration.getInstance().getClassroomById(room + 1);
            criteria.set(crt + 1, classroom.getCapacity() >= cc.getNumberOfSeats());
            if (criteria.get(crt + 1)) {
                score++;
            }

            // third criterion - if the class needs computers and the room has computers, or the class doesn't need computers increase the score
            criteria.set(crt + 2, !cc.requiresComputers() || (cc.requiresComputers() && classroom.isLab()));
            if (criteria.get(crt + 2)) {
                score++;
            }

            // fourth criterion - checks overlapping of classes for professors and students groups
            boolean professorOverlaps = false;
            boolean groupOverlaps = false;
            int i = numberOfRooms;
            int t = day * possibleClassesInDay + time;
            while (i > 0) {
                for (int j = duration - 1; j >= 0; j--) {
                    if (slots.get(t + j).size() > 1) {
                        for (CourseClass courseClass : slots.get(t + j)) {
                            if (courseClass.professorOverlaps(cc)) {
                                professorOverlaps = true;
                                break;
                            }
                            if (courseClass.groupOverlaps(cc)) {
                                groupOverlaps = true;
                                break;
                            }
                        }
                    }
                    if (professorOverlaps || groupOverlaps) {
                        break;
                    }
                }
                i--;
                t += HOURS;
            }

            criteria.set(crt + 3, !professorOverlaps);
            if (!professorOverlaps) {
                score++;
            }
            criteria.set(crt + 4, !groupOverlaps);
            if (!groupOverlaps) {
                score++;
            }

            crt += 5;
        }

        fitness = (double) score / (Configuration.getInstance().numberOfClasses() * 5);
    }

    public Schedule createNewChromosome() {
        Schedule newChromosome = new Schedule(this, true);
        var classes = Configuration.getInstance().getClasses();
        for (CourseClass courseClass : classes) {
            Random random = new Random();
            int duration = courseClass.getDuration();
            int numberOfRooms = Configuration.getInstance().numberOfClassrooms();
            int day = random.nextInt(DAYS);
            int classRoom = random.nextInt(numberOfRooms);
            int time = random.nextInt(HOURS - duration + 1);
            int slot = day * numberOfRooms * HOURS + classRoom * HOURS + time;

            for (int i = duration - 1; i >= 0; i--) {
                newChromosome.slots.get(slot + i).add(courseClass);
            }
            
            newChromosome.classes.put(courseClass, slot);
        }
        
        newChromosome.calculateFitness();
        
        return newChromosome;
    }

    public Schedule crossover(Schedule parent2) {
        Random random = new Random();
        if (random.nextInt(100) > crossoverProbability) {
            return new Schedule(this, false);
        }

        Schedule child = new Schedule(this, true);
        List<Boolean> crossoverPoints = new ArrayList<>(classes.size());
        for (int i = 0; i < classes.size(); i++) {
            crossoverPoints.add(false);
        }

        for (int i = 0; i < numberOfCrossoverPoints; i++) {
            while (true) {
                int cp = (int) (Math.random() * classes.size());
                if (!crossoverPoints.get(cp)) {
                    crossoverPoints.set(cp, true);
                    break;
                }
            }
        }

        // make child by combining parents using crossover points
        boolean first = Math.random() > 0.5;
        int classesSize = Math.min(classes.size(), parent2.classes.size());
        for (int i = 0; i < classesSize; i++) {
            if (first) {
                CourseClass courseClass = (CourseClass) this.classes.keySet().toArray()[i];
                child.classes.put(courseClass, classes.get(courseClass));

                for (int j = courseClass.getDuration() - 1; j >= 0; j--) {
                    child.slots.get(classes.get(courseClass) + j).add(courseClass);
                }
            }
            else {
                CourseClass courseClass = (CourseClass) parent2.classes.keySet().toArray()[i];
                child.classes.put(courseClass, parent2.classes.get(courseClass));

                for (int j = courseClass.getDuration() - 1; j >= 0; j--) {
                    child.slots.get(parent2.classes.get(courseClass) + j).add(courseClass);
                }
            }

            if (crossoverPoints.get(i)) {
                first = !first;
            }
        }

        child.calculateFitness();

        return child;
    }

    public void mutation() {
        Random random = new Random();
        if (random.nextInt(100) > mutationProbability) {
            return;
        }

        for (int i = mutationRate; i > 0; i--) {
            int mpos = random.nextInt(classes.size());
            int pos1 = 0;
            CourseClass courseClass1 = null;

            for (HashMap.Entry<CourseClass, Integer> entry : classes.entrySet()) {
                if (mpos == 0) {
                    courseClass1 = entry.getKey();
                    pos1 = entry.getValue();
                    break;
                }
                mpos--;
            }

            int numberOfRooms = Configuration.getInstance().numberOfClassrooms();
            assert courseClass1 != null;
            int duration = courseClass1.getDuration();
            int day = random.nextInt(DAYS);
            int classRoom = random.nextInt(numberOfRooms);
            int time = random.nextInt(HOURS - duration + 1);
            int slot = day * numberOfRooms * HOURS + classRoom * HOURS + time;

            for (int j = duration - 1; j >= 0; j--) {
                slots.get(pos1 + j).remove(courseClass1);
                slots.get(slot + j).add(courseClass1);
            }
            classes.put(courseClass1, slot);
        }

        this.calculateFitness();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            result.append("Day ").append(i + 1).append(":  ");
            for (int j = 8; j < 20; j++) {
                result.append("  ").append(j).append(":00  ");
            }
            result.append("\n");
            for (int j = 0; j < Configuration.getInstance().numberOfClassrooms(); j++) {
                result.append("Room ").append(j + 1).append(": ");
                for (int k = 0; k < 12; k++) {
                    int slot = i * Configuration.getInstance().numberOfClassrooms() * 12 + j * 12 + k;
                    if (!slots.get(slot).isEmpty()) {
                        result.append("Class ").append(slots.get(slot).get(0).getId()).append(" ");
                    }
                    else {
                        result.append("-------- ");
                    }
                }
                result.append("\n");
            }
            result.append("\n");
        }
        return result.toString();
    }

}
