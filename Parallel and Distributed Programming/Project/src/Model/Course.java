package Model;

import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String name;

    public Course(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", name=" + name + '}';
    }

    public void setName(String value) {
        this.name = value;
    }
}
