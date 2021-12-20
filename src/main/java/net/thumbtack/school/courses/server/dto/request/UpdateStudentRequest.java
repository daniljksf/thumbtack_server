package net.thumbtack.school.courses.server.dto.request;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@RequiredArgsConstructor
public class UpdateStudentRequest implements Serializable {
    @Getter @Expose private final String token;
    @Getter @Expose private final String oldPassword;
    @Getter @Expose private final String login;
    @Getter @Expose private final String password;
    @Getter @Expose private final int course;
    @Getter @Expose private final String group;

}
