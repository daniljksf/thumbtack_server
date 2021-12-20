package net.thumbtack.school.courses.server.dto.request;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterCourseDtoRequest {
    @Getter @Expose private final String token;
    @Getter @Expose private final String name;
    @Getter @Expose private final int courseStudy; //должно быть четным
    @Getter @Expose private final int hours; //должно быть четным
}
