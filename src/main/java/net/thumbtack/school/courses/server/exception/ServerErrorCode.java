package net.thumbtack.school.courses.server.exception;

public enum ServerErrorCode {
    INCORRECT_LOGIN("Wrong login"),
    WRONG_SAVE_SERVER("Save to file error"),
    WRONG_LOGIN("This username already exists"),
    WRONG_NULL_REQUEST("Invalid request "),
    BAD_PASSWORD("Easy password"),
    WRONG_JSON("Incorrect json"),
    BAD_LOGIN("Easy login"),
    WRONG_PASSWORD("Incorrect password"),
    WRONG_DATA_REQUEST("Incorrect data for request"),
    WRONG_ACTION("It is impossible to perform the action"),
    WRONG_COURSE_OF_STUDY("Inappropriate course of study"),
    WRONG_ID_COURSE("Course not found"),
    WRONG_ID_USER("User not found"),
    WRONG_TOKEN("Invalid user");
    private final String errorString;

    private ServerErrorCode(String errorString){
        this.errorString = errorString;
    }

    public String getErrorString(){return errorString;}
}
