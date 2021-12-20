package net.thumbtack.school.courses.server.daoimpl;

import net.thumbtack.school.courses.server.dao.TeacherDao;
import net.thumbtack.school.courses.server.database.Database;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.model.Course;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.model.User;

import java.util.List;
import java.util.Set;

public class TeacherDaoImpl implements TeacherDao {

    private Database database;

    public TeacherDaoImpl(){
        database = Database.getDatabase();
    }

    @Override
    public String insertTeacher(Teacher teacher) throws ServerException {
        return database.insert(teacher);
    }

    @Override
    public void registerCourse(Teacher teacher, Course course) throws ServerException {
        database.registerCourse(teacher, course);
    }

    @Override
    public void deleteCourse(Teacher teacher, Course course) throws ServerException {
        database.deleteCourse(teacher, course);
    }

    @Override
    public void updateTeacher(Teacher teacher, String updLogin) throws ServerException {
        database.updateUser(teacher, updLogin);
    }

    @Override
    public Set<Student> getStudentsOfTeacher(Teacher teacher) throws ServerException {
        return database.getTeacherStudents(teacher);
    }

    @Override
    public Set<Student> getStudentsEnrolledInCourse(Teacher teacher, List<Course> courses) throws ServerException {
        return database.getStudentsEnrolledInCourses(teacher, courses);
    }

    @Override
    public User getUserByToken(String token) {
        return database.getUserByToken(token);
    }

    @Override
    public Course getCourseById(int idCourse) {
        return database.getCourseById(idCourse);
    }

}
