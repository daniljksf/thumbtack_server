package net.thumbtack.school.courses.server.dto.response;

import com.google.gson.annotations.Expose;
import net.thumbtack.school.courses.server.exception.ServerException;

import java.io.Serializable;

public class ErrorDtoResponse implements Serializable {
    @Expose
    private final String error;

    public ErrorDtoResponse(ServerException e){
        error = e.getError();
    }

}
