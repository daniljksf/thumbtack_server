package net.thumbtack.school.courses.server;

import net.thumbtack.school.courses.server.dto.response.MessageDtoResponse;
import net.thumbtack.school.courses.server.dto.response.StudentListOfCoursesDtoResponse;
import net.thumbtack.school.courses.server.dto.response.TeacherListOfStudentDtoResponse;
import net.thumbtack.school.courses.server.dto.response.TokenDtoResponse;
import net.thumbtack.school.courses.server.service.LaunchService;
import net.thumbtack.school.courses.server.service.StudentService;
import net.thumbtack.school.courses.server.service.TeacherService;
import net.thumbtack.school.courses.server.service.UserService;

public class Server {
    private LaunchService serverService;
    private StudentService studentService;
    private TeacherService teacherService;
    private UserService userService;
    private JsonConverter jsonConverter;

    public Server(){}

    // !--- Методы делегируемые ServerService
    public void startServer(String saveDataFile)  {
        serverService = new LaunchService();
        serverService.startServer(saveDataFile);
        jsonConverter = JsonConverter.getInstance();
        studentService = new StudentService();
        teacherService = new TeacherService();
        userService = new UserService();
    }

    public String stopServer(String saveDataFile) {
        return serverService.stop(saveDataFile);
    }
    // ---!

    // !--- Методы делегированные UserService
    public String login(String jsonRequest){
        return userService.login(jsonRequest);

    }

    public String logout(String jsonRequest){
        return userService.logout(jsonRequest);
    }

    public String exit(String jsonRequest){
        return userService.exit(jsonRequest);
    }
    // ---!

    // !--- Методы делегированные StudentService
    public String registerStudent(String jsonRequest){
        return studentService.insertStudent(jsonRequest);
    }

    public String updateStudent(String jsonRequest){
        return studentService.updateStudent(jsonRequest);
     }

    public String subscribeStudentForCourse(String jsonRequest){
        return studentService.subscribeForCourse(jsonRequest);
    }

    public String unsubscribeStudentOfCourse(String jsonRequest){
        return studentService.unsubscribeOfCourse(jsonRequest);
    }

    public String getSignedCourses(String jsonRequest){
        return studentService.getSignedCourses(jsonRequest);
    }

    public String getAvailableCourse(String jsonRequest){
        return studentService.getAvailableCourse(jsonRequest);
    }

    public String getTeacherCourses(String jsonRequest){
        return studentService.getTeacherCourses(jsonRequest);
    }
    // ---!

    // !--- Методы делегированные TeacherService
    public String registerTeacher(String jsonRequest){
        return teacherService.insertTeacher(jsonRequest);

    }

    public String updateTeacher(String jsonRequest){
        return teacherService.updateTeacher(jsonRequest);

    }

    public String registerCourse(String jsonRequest){
        return teacherService.registerCourse(jsonRequest);
    }

    public String deleteCourse(String jsonRequest){
        return teacherService.deleteCourse(jsonRequest);
    }

    public String getListAllSignedStudents(String jsonRequest){
        return teacherService.getStudentsOfTeacher(jsonRequest);

    }

    public String getStudentsEnrolledInCourse(String jsonRequest){
        return teacherService.getStudentsEnrolledInCourses(jsonRequest);

    }
    // ---!

}