package net.thumbtack.school.courses.server.service;

import net.thumbtack.school.courses.server.JsonConverter;
import net.thumbtack.school.courses.server.Validator;
import net.thumbtack.school.courses.server.dao.StudentDao;
import net.thumbtack.school.courses.server.daoimpl.StudentDaoImpl;
import net.thumbtack.school.courses.server.dto.request.*;
import net.thumbtack.school.courses.server.dto.response.ErrorDtoResponse;
import net.thumbtack.school.courses.server.dto.response.StudentListOfCoursesDtoResponse;
import net.thumbtack.school.courses.server.dto.response.TokenDtoResponse;
import net.thumbtack.school.courses.server.exception.ServerErrorCode;
import net.thumbtack.school.courses.server.imapping.RegisterStudentDtoToStudent;
import net.thumbtack.school.courses.server.model.Course;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.model.User;



public class StudentService {
    private StudentDao studentDao;
    private final JsonConverter jsonConverter;

    public StudentService() {
        studentDao = new StudentDaoImpl();
        jsonConverter = JsonConverter.getInstance();
    }

    public String insertStudent(String jsonRequest){
        try {
            RegisterStudentDtoRequest request = jsonConverter.getClassFromJson(jsonRequest, RegisterStudentDtoRequest.class);
            Validator.validateRequest(request);

            return jsonConverter.serialize(new TokenDtoResponse(studentDao.insertStudent(
                                                   RegisterStudentDtoToStudent.MAPPER.convert(request))));
        } catch (ServerException e){
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }

    public String updateStudent(String jsonRequest)  {
        try{
            UpdateStudentRequest request = jsonConverter.getClassFromJson(jsonRequest, UpdateStudentRequest.class);
            Validator.validateRequest(request);

            Student studentByToken = getStudentByToken(request.getToken());

            if(!studentByToken.getPassword().equals(request.getOldPassword())){
                throw new ServerException(ServerErrorCode.WRONG_PASSWORD);
            }
            studentDao.updateUser(studentByToken, request.getLogin());
            studentByToken.update(request.getLogin(), request.getPassword(), request.getCourse(), request.getGroup());
            studentDao.updateStudent(studentByToken);
            return jsonConverter.serialize(new TokenDtoResponse(request.getToken()));
        }
        catch (ServerException e) {
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }

    public String subscribeForCourse(String jsonRequest){
        try {
            CourseActionRequest request = jsonConverter.getClassFromJson(jsonRequest, CourseActionRequest.class);
            Validator.validateRequest(request);
            Student student = getStudentByToken(request.getToken());
            Course course = getCourseById(request.getIdCourse());
            if(student.getCourseStudy() < course.getCourseStudy()){
                throw new ServerException(ServerErrorCode.WRONG_COURSE_OF_STUDY);
            }
            studentDao.registerStudentForCourse(student, course);
            return "";
        }
        catch (ServerException e){
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }

    public String unsubscribeOfCourse(String jsonRequest){
        try {
            CourseActionRequest request = jsonConverter.getClassFromJson(jsonRequest, CourseActionRequest.class);
            Validator.validateRequest(request);
            Student student = getStudentByToken(request.getToken());
            Course course = getCourseById(request.getIdCourse());
            if(student.getCourseStudy() < course.getCourseStudy()){
                throw new ServerException(ServerErrorCode.WRONG_COURSE_OF_STUDY);
            }
            studentDao.unsubscribeCourse(student, course);
            return "";
        }
        catch (ServerException e){
            return jsonConverter.serialize(new ErrorDtoResponse(e));

        }
    }

    public String getSignedCourses(String jsonRequest){
        try{
            TokenDtoRequest request = jsonConverter.getClassFromJson(jsonRequest, TokenDtoRequest.class);
            Validator.validateRequest(request);
            StudentListOfCoursesDtoResponse response = new StudentListOfCoursesDtoResponse(studentDao.getListSignedCourses(getStudentByToken(request.getToken())));
            return jsonConverter.serialize(response.getList());
        }
        catch (ServerException e){
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }

    public String getAvailableCourse(String jsonRequest){
        try{
            TokenDtoRequest request = jsonConverter.getClassFromJson(jsonRequest, TokenDtoRequest.class);
            Validator.validateRequest(request);
            StudentListOfCoursesDtoResponse response = new StudentListOfCoursesDtoResponse(studentDao.getListAllCourses(getStudentByToken(request.getToken())));
            return jsonConverter.serialize(response.getList());
                   }
        catch (ServerException e){
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }

    public String getTeacherCourses(String jsonRequest){
        try{
            TeacherCoursesDtoRequest request = jsonConverter.getClassFromJson(jsonRequest, TeacherCoursesDtoRequest.class);
            Validator.validateRequest(request);

            //проверка что это студент
            Student student = getStudentByToken(request.getToken());
            //достаем учителя
            User user = studentDao.getUserById(request.getIdTeacher());
            if(user instanceof Student){
                throw new ServerException(ServerErrorCode.WRONG_ID_USER);
            }
            StudentListOfCoursesDtoResponse response = new StudentListOfCoursesDtoResponse(studentDao.getListOfTeacherCourses(student, (Teacher) user));
            return jsonConverter.serialize(response.getList());
        }
        catch (ServerException e){
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }

    private Course getCourseById(int idCourse) throws ServerException {
        Course course = studentDao.getCourseById(idCourse);
        if(course == null){
            throw new ServerException(ServerErrorCode.WRONG_ID_COURSE);
        }
        return course;
    }

    private Student getStudentByToken(String token) throws ServerException {
        User student = studentDao.getUserByToken(token);
        if(student == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
        if(student instanceof Teacher){
            throw new ServerException(ServerErrorCode.WRONG_ACTION);
        }
        return (Student) student;
    }

}
