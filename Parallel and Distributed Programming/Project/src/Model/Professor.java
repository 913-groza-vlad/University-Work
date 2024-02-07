package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Professor implements Serializable {
    private int id;
    private String name;
    private List<CourseClass> classes;

    public Professor(int id, String name) {
        this.id = id;
        this.name = name;
        this.classes = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<CourseClass> getClasses() { return classes; }

    public void addClass(CourseClass courseClass) {
        classes.add(courseClass);
    }

    @Override
    public String toString() {
        return "Professor{" + "id=" + id + ", name=" + name + ", classes=" + classes + '}';
    }

    public void setName(String value) {
        this.name = value;
    }
}
