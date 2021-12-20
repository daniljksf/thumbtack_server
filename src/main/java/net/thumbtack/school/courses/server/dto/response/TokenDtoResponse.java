package net.thumbtack.school.courses.server.dto.response;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenDtoResponse {
    @Getter @Expose
    private final String token;
}
