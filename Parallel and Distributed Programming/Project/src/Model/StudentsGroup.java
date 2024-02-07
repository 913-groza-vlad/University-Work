package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentsGroup implements Serializable {
    private int id;
    private int numberOfStudents;
    private List<CourseClass> classes;

    public StudentsGroup(int id, int numberOfStudents) {
        this.id = id;
        this.numberOfStudents = numberOfStudents;
        this.classes = new ArrayList<>();
    }

    public int getId() { return id; }

    public int getNumberOfStudents() { return numberOfStudents; }

    public List<CourseClass> getClasses() { return classes; }

    public void addClass(CourseClass courseClass) {
        classes.add(courseClass);
    }

    @Override
    public String toString() {
        return "StudentsGroup{" + "id=" + id + ", numberOfStudents=" + numberOfStudents + ", classes=" + classes.stream().map(courseClass -> courseClass.getCourse().getName()).toList() + '}';
    }

}
