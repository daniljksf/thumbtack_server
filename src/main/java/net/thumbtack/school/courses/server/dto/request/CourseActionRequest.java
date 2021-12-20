package net.thumbtack.school.courses.server.dto.request;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CourseActionRequest {
    @Getter @Expose private final String token;
    @Getter @Expose private final int idCourse;
}

