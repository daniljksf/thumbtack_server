package net.thumbtack.school.courses.server;

import net.thumbtack.school.courses.server.dto.request.*;
import net.thumbtack.school.courses.server.exception.ServerErrorCode;
import net.thumbtack.school.courses.server.exception.ServerException;
import java.util.Objects;
import java.util.regex.Pattern;

public class Validator {

    public static void validateRequest(StudentsEnrolledGivenCoursesRequest request) throws ServerException {
        validateNull(request);
        validateNullOrEmptyString(request.getToken());
        if(Objects.isNull(request.getIdCourses()) || request.getIdCourses().length == 0){
            throw new ServerException(ServerErrorCode.WRONG_DATA_REQUEST);
        }
    }

    public static void validateRequest(LoginUserDtoRequest request) throws ServerException {
        validateNull(request);
        validateNullOrEmptyString(request.getLogin(), request.getPassword());
    }
    public static void validateRequest(TeacherCoursesDtoRequest request) throws ServerException {
        validateNull(request);
        validateNullOrEmptyString(request.getToken());
    }

    public static void validateRequest(RegisterTeacherDtoRequest request) throws ServerException {
        validateNull(request);
        validateNullOrEmptyString(request.getFirstName(), request.getLastName(), request.getLogin(), request.getPassword(), request.getFaculty(), request.getPosition());
        if(Objects.isNull(request.getPatronymic())){
            throw new ServerException(ServerErrorCode.WRONG_DATA_REQUEST);
        }
        validateLogin(request.getLogin());
        validatePassword(request.getPassword());
    }

    private static void validateNull(Object...objects) throws ServerException {
        for(Object obj : objects){
            if(obj == null){
                throw new ServerException(ServerErrorCode.WRONG_NULL_REQUEST);
            }
        }
    }

    public static void validateLogin(String login) throws ServerException {
        String regexLogin = "(?=\\S+$).{6,}";
        if(!Pattern.matches(regexLogin, login)){
            throw new ServerException(ServerErrorCode.BAD_LOGIN);
        }
    }

    public static void validatePassword(String password) throws ServerException {
        String regexPass = "(?=.*[0-9])(?=.*[a-z])(?=.*[!?@#$%^&+=])(?=\\S+$).{6,}";
        if (!Pattern.matches(regexPass, password)){
            throw new ServerException(ServerErrorCode.BAD_PASSWORD);
        }
    }

    public static void validateRequest(RegisterCourseDtoRequest registerCourseDtoRequest) throws ServerException{
        validateNull(registerCourseDtoRequest);
        validateNullOrEmptyString(registerCourseDtoRequest.getName(), registerCourseDtoRequest.getToken());
        if(registerCourseDtoRequest.getHours() % 2 != 0){
            throw new ServerException(ServerErrorCode.WRONG_DATA_REQUEST);
        }
        if(registerCourseDtoRequest.getCourseStudy() < 1 || registerCourseDtoRequest.getCourseStudy() > 7)
            throw new ServerException(ServerErrorCode.WRONG_COURSE_OF_STUDY);
    }

    public static void validateRequest(RegisterStudentDtoRequest request) throws ServerException {
        validateNull(request);
        validateNullOrEmptyString(request.getFirstName(), request.getLastName(), request.getLogin(), request.getPassword(), request.getGroup());
        if(Objects.isNull(request.getPatronymic())){
            throw new ServerException(ServerErrorCode.WRONG_DATA_REQUEST);
        }
        validateLogin(request.getLogin());
        validatePassword(request.getPassword());
        if(request.getCourse() < 1 || request.getCourse() > 7){
            throw new ServerException(ServerErrorCode.WRONG_COURSE_OF_STUDY);
        }

    }

    public static void validateRequest(CourseActionRequest request) throws ServerException {
        validateNull(request);
        validateNullOrEmptyString(request.getToken());
    }
    public static void validateRequest(TokenDtoRequest request) throws ServerException {
        validateNull(request);
        validateNullOrEmptyString(request.getToken());
    }


        public static void validateRequest(UpdateStudentRequest request) throws ServerException {
        validateNull(request);
        validateNullOrEmptyString(request.getToken(), request.getOldPassword(), request.getLogin(), request.getPassword(), request.getGroup());

        validateLogin(request.getLogin());
        validatePassword(request.getPassword());

        if(request.getCourse() < 1 || request.getCourse() > 7){
            throw new ServerException(ServerErrorCode.WRONG_COURSE_OF_STUDY);
        }
    }

    public static void validateRequest(UpdateTeacherRequest request) throws ServerException {
        validateNull(request);
        validateNullOrEmptyString(request.getToken(), request.getOldPassword(), request.getLogin(), request.getPassword(), request.getFaculty(), request.getPosition());

        validateLogin(request.getLogin());
        validatePassword(request.getPassword());
    }


    private static void validateNullOrEmptyString(String...strings) throws ServerException {
        for(String str : strings){
            if(str == null || str.equals("")){
                throw new ServerException(ServerErrorCode.WRONG_DATA_REQUEST);
            }
        }
    }


}
