package net.thumbtack.school.courses.server.service;

import net.thumbtack.school.courses.server.JsonConverter;
import net.thumbtack.school.courses.server.Validator;
import net.thumbtack.school.courses.server.dao.TeacherDao;
import net.thumbtack.school.courses.server.daoimpl.TeacherDaoImpl;
import net.thumbtack.school.courses.server.dto.request.*;
import net.thumbtack.school.courses.server.dto.response.ErrorDtoResponse;
import net.thumbtack.school.courses.server.dto.response.TeacherListOfStudentDtoResponse;
import net.thumbtack.school.courses.server.dto.response.TokenDtoResponse;
import net.thumbtack.school.courses.server.exception.ServerErrorCode;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.imapping.RegisterCourseDtoToCourse;
import net.thumbtack.school.courses.server.imapping.RegisterTeacherDtoToTeacher;
import net.thumbtack.school.courses.server.model.Course;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.model.User;

import java.util.ArrayList;
import java.util.List;

public class TeacherService {

    private TeacherDao teacherDao;
    private final JsonConverter jsonConverter;

    public TeacherService(){
        teacherDao = new TeacherDaoImpl();
        jsonConverter = JsonConverter.getInstance();
    }

    public String insertTeacher(String jsonRequest){
        try{
            RegisterTeacherDtoRequest request = jsonConverter.getClassFromJson(jsonRequest, RegisterTeacherDtoRequest.class);
            Validator.validateRequest(request);

            return jsonConverter.serialize(new TokenDtoResponse(teacherDao.insertTeacher(
                    RegisterTeacherDtoToTeacher.MAPPER.convert(request))));
        }
        catch (ServerException e){
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }

    public String updateTeacher(String jsonRequest) {
        try{
            UpdateTeacherRequest request = jsonConverter.getClassFromJson(jsonRequest, UpdateTeacherRequest.class);
            Validator.validateRequest(request);
            Teacher teacher = getTeacherByToken(request.getToken());

            if(!teacher.getPassword().equals(request.getOldPassword())){
                throw new ServerException(ServerErrorCode.WRONG_PASSWORD);
            }
            teacherDao.updateTeacher(teacher, request.getLogin());
            teacher.update(request.getLogin(), request.getPassword(), request.getPosition(), request.getFaculty());
            return jsonConverter.serialize(new TokenDtoResponse(request.getToken()));
        }
        catch (ServerException e) {
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }

    public String registerCourse(String jsonRequest){
        try{
            RegisterCourseDtoRequest registerCourseDtoRequest = jsonConverter.getClassFromJson(jsonRequest, RegisterCourseDtoRequest.class);
            Validator.validateRequest(registerCourseDtoRequest);

            Teacher teacher = getTeacherByToken(registerCourseDtoRequest.getToken());
            Course course = RegisterCourseDtoToCourse.MAPPER.convert(registerCourseDtoRequest);

            teacherDao.registerCourse(teacher, course);
            return "";
        }
        catch (ServerException exception){
            return jsonConverter.serialize(new ErrorDtoResponse(exception));
        }
    }

    public String deleteCourse(String jsonRequest){
        try{
            CourseActionRequest request = jsonConverter.getClassFromJson(jsonRequest, CourseActionRequest.class);
            Validator.validateRequest(request);

            teacherDao.deleteCourse(getTeacherByToken(request.getToken()),
                                    getCourseById(request.getIdCourse()));

            return "";
        }
        catch (ServerException exception){
            return jsonConverter.serialize(new ErrorDtoResponse(exception));
        }
    }

    public String getStudentsOfTeacher(String jsonRequest){
        try{
            TokenDtoRequest request = jsonConverter.getClassFromJson(jsonRequest, TokenDtoRequest.class);
            Validator.validateRequest(request);
            TeacherListOfStudentDtoResponse response = new TeacherListOfStudentDtoResponse(teacherDao.getStudentsOfTeacher(getTeacherByToken(request.getToken())));
            return jsonConverter.serialize(response.getSet());
        } catch (ServerException e) {
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }

    public String getStudentsEnrolledInCourses(String jsonRequest){
        try{
            StudentsEnrolledGivenCoursesRequest request = jsonConverter.getClassFromJson(jsonRequest, StudentsEnrolledGivenCoursesRequest.class);
            Validator.validateRequest(request);
            Teacher teacher = getTeacherByToken(request.getToken());
            List<Course> courses = new ArrayList<>();
            //получает курсы по id
            for(int id : request.getIdCourses()){
                Course course = getCourseById(id);
                if(!teacher.containsCourse(course)){
                    throw new ServerException(ServerErrorCode.WRONG_ACTION);
                }
                courses.add(course);
            }
            TeacherListOfStudentDtoResponse response = new TeacherListOfStudentDtoResponse(teacherDao.getStudentsEnrolledInCourse(teacher, courses));
            return jsonConverter.serialize(response.getSet());
        } catch (ServerException e) {
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }


    private Teacher getTeacherByToken(String token) throws ServerException {
        User teacher = teacherDao.getUserByToken(token);
        if(teacher == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
        if(teacher instanceof Student){
            throw new ServerException(ServerErrorCode.WRONG_ACTION);
        }
        return (Teacher) teacher;
    }

    private Course getCourseById(int idCourse) throws ServerException {
        Course course = teacherDao.getCourseById(idCourse);
        if(course == null){
            throw new ServerException(ServerErrorCode.WRONG_ID_COURSE);
        }
        return teacherDao.getCourseById(idCourse);
    }
}
