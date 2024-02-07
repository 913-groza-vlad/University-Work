package GeneticAlgorithmParallel;

import Model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private static Configuration instance = new Configuration();

    private Configuration() {}

    public static Configuration getInstance() {
        return instance;
    }

    private List<Professor> professors = new ArrayList<>();
    private List<StudentsGroup> groups = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Classroom> classrooms = new ArrayList<>();
    private List<CourseClass> classes = new ArrayList<>();

    public List<Professor> getProfessors() {
        return professors;
    }

    public List<StudentsGroup> getGroups() {
        return groups;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public Professor getProfessorById(int id) {
        return professors.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public int numberOfProfessors() {
        return professors.size();
    }

    public StudentsGroup getGroupById(int id) {
        return groups.stream().filter(g -> g.getId() == id).findFirst().orElse(null);
    }

    public int numberOfGroups() {
        return groups.size();
    }

    public Course getCourseById(int id) {
        return courses.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public int numberOfCourses() {
        return courses.size();
    }

    public Classroom getClassroomById(int id) {
        return classrooms.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public int numberOfClassrooms() {
        return classrooms.size();
    }

    public List<CourseClass> getClasses() {
        return classes;
    }

    public int numberOfClasses() {
        return classes.size();
    }

    public void parseFile(String fileName) throws FileNotFoundException {

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String entity = "";

            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    entity = line.substring(1).trim();
                } else if (!line.isEmpty()) {
                    String[] parts = line.split("=");
                    String property = parts[0].trim();
                    String value = parts[1].trim();

                    if (entity.equals("prof")) {
                        if (property.equals("id")) {
                            int id = Integer.parseInt(value);
                            professors.add(new Professor(id, ""));
                        } else if (property.equals("name")) {
                            professors.get(professors.size() - 1).setName(value);
                        }
                    }
                    else if (entity.equals("course")) {
                        if (property.equals("id")) {
                            int id = Integer.parseInt(value);
                            courses.add(new Course(id, ""));
                        } else if (property.equals("name")) {
                            courses.get(courses.size() - 1).setName(value);
                        }
                    }
                    else if (entity.equals("room")) {
                        if (property.equals("name")) {
                            String name = value;
                            boolean lab = false;
                            int size = 0;

                            while ((line = br.readLine()) != null && !line.startsWith("#") && !line.isEmpty()) {
                                parts = line.split("=");
                                property = parts[0].trim();
                                value = parts[1].trim();

                                if (property.equals("lab")) {
                                    lab = Boolean.parseBoolean(value);
                                } else if (property.equals("size")) {
                                    size = Integer.parseInt(value);
                                }
                            }

                            classrooms.add(new Classroom(name, size, lab));
                        }
                    }
                    else if (entity.equals("group")) {
                        if (property.equals("id")) {
                            int id = Integer.parseInt(value);
                            int size = 0;

                            while ((line = br.readLine()) != null && !line.isEmpty() && !line.startsWith("#")) {
                                parts = line.split("=");
                                property = parts[0].trim();
                                value = parts[1].trim();

                                if (property.equals("size")) {
                                    size = Integer.parseInt(value);
                                }
                            }

                            groups.add(new StudentsGroup(id, size));
                        }
                    }
                    else if (entity.equals("class")) {
                        if (property.equals("professor")) {
                            int professorId = Integer.parseInt(value);
                            int courseId = 0;
                            int duration = 0;
                            boolean lab = false;
                            List<StudentsGroup> groups = new ArrayList<>();

                            while ((line = br.readLine()) != null && !line.isEmpty() && !line.startsWith("#")) {
                                parts = line.split("=");
                                property = parts[0].trim();
                                value = parts[1].trim();

                                if (property.equals("course")) {
                                    courseId = Integer.parseInt(value);
                                } else if (property.equals("duration")) {
                                    duration = Integer.parseInt(value);
                                } else if (property.equals("group")) {
                                    StudentsGroup group = getGroupById(Integer.parseInt(value));
                                    if (group != null) {
                                        groups.add(group);
                                    }
                                }
                                else if (property.equals("lab")) {
                                    lab = Boolean.parseBoolean(value);
                                }
                            }

                            classes.add(new CourseClass(getProfessorById(professorId), getCourseById(courseId), groups, lab, duration));
                        }
                    }
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
