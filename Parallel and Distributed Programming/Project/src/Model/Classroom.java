package Model;

import java.io.Serializable;

public class Classroom implements Serializable {
    private static int idCounter = 1;
    private int id;
    private String name;
    private int capacity;
    private boolean isLab;

    public Classroom(String name, int capacity, boolean isLab) {
        this.id = idCounter++;
        this.name = name;
        this.capacity = capacity;
        this.isLab = isLab;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isLab() {
        return isLab;
    }

    @Override
    public String toString() {
        return "Classroom{" + "id=" + id + ", name=" + name + ", capacity=" + capacity + ", isLab=" + isLab + '}';
    }
}
