package net.thumbtack.school.courses.server.dao;

import net.thumbtack.school.courses.server.dto.request.*;
import net.thumbtack.school.courses.server.model.Course;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.model.User;

import java.util.List;

public interface StudentDao {

    //добавление студента в бд
    String insertStudent(Student student) throws ServerException;

    //обновление данных студента
    void updateStudent(Student student);

    void updateUser(Student student, String updLogin) throws ServerException;

    //регистрация студента на курс
    void registerStudentForCourse(Student student, Course course);

    void unsubscribeCourse(Student student, Course course);
    //список спецкурсов на которые записался
    List<Course> getListSignedCourses(Student student);

    //список всех спецкурсов доступных студенту
    List<Course> getListAllCourses(Student student);

    //получить список тех спецкурсов которые есть у конкретного преподавателя
    List<Course> getListOfTeacherCourses(Student student, Teacher teacher);

    User getUserByToken(String token);

    Course getCourseById(int idCourse);

    User getUserById(int idUser);

}
