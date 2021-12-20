package net.thumbtack.school.courses.server.dto.request;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUserDtoRequest {
    @Getter @Expose private final String login;
    @Getter @Expose private final String password;

}
