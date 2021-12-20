package net.thumbtack.school.courses.server.dto.response;

import lombok.Getter;
import net.thumbtack.school.courses.server.model.Course;

import java.io.Serializable;
import java.util.List;

public class StudentListOfCoursesDtoResponse implements Serializable {
    @Getter private  List<Course> list;

    public StudentListOfCoursesDtoResponse(List<Course> list) {
        this.list = list;
    }

}
