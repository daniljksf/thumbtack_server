package net.thumbtack.school.courses.server.dto.request;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@RequiredArgsConstructor
public class RegisterStudentDtoRequest implements Serializable {
    @Getter @Expose private final String login;
    @Getter @Expose private final String password;
    @Getter @Expose private final String lastName;
    @Getter @Expose private final String firstName;
    @Getter @Expose private final String patronymic;
    @Getter @Expose private final int course;
    @Getter @Expose private final String group;

}
