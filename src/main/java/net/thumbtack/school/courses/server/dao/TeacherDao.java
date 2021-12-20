package net.thumbtack.school.courses.server.dao;

import net.thumbtack.school.courses.server.dto.request.*;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.model.Course;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.model.User;

import java.util.List;
import java.util.Set;

public interface TeacherDao {

    //добавить учителя в бд
    String insertTeacher(Teacher teacher) throws ServerException;

    //регистрация курса
    void registerCourse(Teacher teacher, Course course) throws ServerException;

    //
    void deleteCourse(Teacher teacher, Course course) throws  ServerException;

    //обновление данных учителя
    void updateTeacher(Teacher teacher, String updLogin) throws ServerException;

    //список всех студентов записавшихся на спецкрусы преподавателя
    Set<Student> getStudentsOfTeacher(Teacher teacher) throws ServerException;

    //список записавшихся на спецкурс преподавателя
    Set<Student> getStudentsEnrolledInCourse(Teacher teacher, List<Course> courses) throws ServerException;

    User getUserByToken(String token);

    Course getCourseById(int idCourse);
}
