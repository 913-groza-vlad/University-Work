package Model;

import java.io.Serializable;
import java.util.List;

public class CourseClass implements Serializable {
    private static int idCounter = 1;
    private int id;
    private Professor professor;
    private Course course;
    private List<StudentsGroup> studentsGroups;
   private int numberOfSeats;
   private boolean requiresComputers;
   private int duration;

   public CourseClass(Professor professor, Course course, List<StudentsGroup> studentsGroups, boolean requiresComputers, int duration) {
       this.professor = professor;
       this.course = course;
       this.studentsGroups = studentsGroups;
       this.numberOfSeats = 0;
       this.requiresComputers = requiresComputers;
       this.duration = duration;

       this.id = idCounter++;

       bindCourseToProfessor();
       bindCourseToStudentsGroups();
   }

   private void bindCourseToProfessor() {
       professor.addClass(this);
   }

   private void bindCourseToStudentsGroups() {
       for (StudentsGroup studentsGroup : studentsGroups) {
           studentsGroup.addClass(this);
           this.numberOfSeats += studentsGroup.getNumberOfStudents();
       }
   }

   public int getId() {
       return id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Course getCourse() {
        return course;
    }

    public List<StudentsGroup> getStudentsGroups() {
        return studentsGroups;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public boolean requiresComputers() {
        return requiresComputers;
    }

    public int getDuration() {
        return duration;
    }

    public boolean professorOverlaps(CourseClass other) {
        return this.professor.getId() == other.professor.getId();
    }

    public boolean groupOverlaps(CourseClass other) {
        for (StudentsGroup studentsGroup : this.studentsGroups) {
            for (StudentsGroup otherStudentsGroup : other.studentsGroups) {
                if (studentsGroup.getId() == otherStudentsGroup.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "CourseClass{" + "id=" + id + ", professor=" + professor.getName() + ", course=" + course.getName() + ", studentsGroups=" + studentsGroups.stream().map(StudentsGroup::getId).toList() + ", numberOfSeats=" + numberOfSeats + ", requiresComputers=" + requiresComputers + ", duration=" + duration + '}';
    }
}
