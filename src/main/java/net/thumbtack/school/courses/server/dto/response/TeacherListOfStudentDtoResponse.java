package net.thumbtack.school.courses.server.dto.response;

import lombok.Getter;
import net.thumbtack.school.courses.server.model.Student;

import java.io.Serializable;
import java.util.Set;

public class TeacherListOfStudentDtoResponse implements Serializable {
    @Getter private final Set<Student> set;

    public TeacherListOfStudentDtoResponse(Set<Student> set) {
        this.set = set;
    }

}
