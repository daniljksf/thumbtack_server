package net.thumbtack.school.courses.server.exception;

public class ServerException extends Exception {
    private final ServerErrorCode errorCode;

    public ServerException(ServerErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public String getError() {
        return errorCode.getErrorString();
    }
}
