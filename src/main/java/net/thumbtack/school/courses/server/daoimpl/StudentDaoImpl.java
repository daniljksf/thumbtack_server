package net.thumbtack.school.courses.server.daoimpl;

import net.thumbtack.school.courses.server.dao.StudentDao;
import net.thumbtack.school.courses.server.database.Database;
import net.thumbtack.school.courses.server.model.Course;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.model.User;

import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private Database database;

    public StudentDaoImpl() {
        database = Database.getDatabase();
    }

    @Override
    public String insertStudent(Student student) throws ServerException {
        return database.insert(student);
    }

    @Override
    public void updateStudent(Student student)  {
        database.updateStudent(student);
    }

    @Override
    public void updateUser(Student student, String updLogin) throws ServerException {
        database.updateUser(student, updLogin);
    }

    @Override
    public void registerStudentForCourse(Student student, Course course) {
        database.subscribeStudentForCourse(student, course);
    }

    @Override
    public void unsubscribeCourse(Student student, Course course) {
        database.unsubscribeStudentCourse(student, course);
    }

    @Override
    public List<Course> getListSignedCourses(Student student) {
        return database.getListSignedCourses(student);
    }

    @Override
    public List<Course> getListAllCourses(Student student) {
        return database.getListAllStudentCourses(student);
    }

    @Override
    public List<Course> getListOfTeacherCourses(Student student, Teacher teacher) {
        return database.getTeacherCourses(student, teacher);
    }

    @Override
    public User getUserByToken(String token) {
        return database.getUserByToken(token);
    }

    public User getUserByLogin(String token) {
        return database.getUserByToken(token);
    }

    @Override
    public Course getCourseById(int idCourse) {
        return database.getCourseById(idCourse);
    }

    @Override
    public User getUserById(int idUser){
        return database.getUserById(idUser);
    }


}