package net.thumbtack.school.courses.server.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = {"courses"}, callSuper = true)
public class Teacher extends User implements Serializable {


    @Getter @Expose
    private String position;
    @Getter @Expose
    private String faculty;
    @Getter
    private List<Course> courses; //курсы которые ведет учитель

    public Teacher(String login, String password, String lastName, String firstName, String patronymic, String position, String faculty) {
        super(login, password, lastName, firstName, patronymic);
        this.faculty = faculty;
        this.position = position;
        courses = new ArrayList<>();
    }

    public void update(String updLogin, String updPass, String updPosition, String updFaculty){
        super.update(updLogin,updPass);
        this.faculty = updFaculty;
        this.position = updPosition;
    }

    public void registerCourse(Course course){
        courses.add(course);
    }

    public boolean containsCourse(Course course){
        return courses.contains(course);
    }

    public boolean deleteCourse(Course course){
        return courses.remove(course);
    }

}
