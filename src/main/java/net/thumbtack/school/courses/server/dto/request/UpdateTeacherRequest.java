package net.thumbtack.school.courses.server.dto.request;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateTeacherRequest {
    @Getter @Expose private final String token;
    @Getter @Expose private final String oldPassword;
    @Getter @Expose private final String login;
    @Getter @Expose private final String password;
    @Getter @Expose private final String position;
    @Getter @Expose private final String faculty;

}
