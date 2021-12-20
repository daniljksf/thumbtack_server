package net.thumbtack.school.courses.server.model;

import com.google.gson.annotations.Expose;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(exclude = {"courses"}, callSuper = true)
public class Student extends User implements Serializable {

    @Getter @Expose
    private int courseStudy;
    @Getter @Expose
    private String group;
    @Getter
    private List<Course> courses; // курсы на которые ходит студент

    public Student(String login, String password, String lastName, String firstName, String patronymic, int course, String group) {
        super(login, password, lastName, firstName, patronymic);
        this.courseStudy = course;
        this.group = group;
        courses = new ArrayList<>();
    }

    public void update(String updLogin, String updPass, int updCourse, String updGroup){
        super.update(updLogin, updPass);
        this.courseStudy = updCourse;
        this.group = updGroup;
    }

    public void subscribeCourse(Course course){
        courses.add(course);
    }

    public boolean unsubscribeCourse(Course course){
        return courses.remove(course);
    }

}
