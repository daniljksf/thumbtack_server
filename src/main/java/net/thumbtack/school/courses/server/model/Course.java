package net.thumbtack.school.courses.server.model;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// REVU а можно просто @Data - вместо всего этого
@EqualsAndHashCode(exclude = {"students", "idCourse"})
public class Course implements Serializable {
    @Getter @Expose private final String name;
    @Getter @Expose private final int courseStudy;
    @Getter @Expose private final int hours; //должно быть четным
    @Getter private List<Student> students; //студенты зарегестрированные на курс
    @Getter @Setter private int idCourse; //id курсов

    public Course(String name, int courseStudy, int hours) {
        this.name = name;
        this.courseStudy = courseStudy;
        this.hours = hours;
        students = new ArrayList<>();
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

}