package net.thumbtack.school.courses.server.dao;

import net.thumbtack.school.courses.server.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.courses.server.dto.request.TokenDtoRequest;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.model.User;

public interface UserDao {
    String login(User user);

    void logout(User user);

    void exitStudent(Student student);

    void exitTeacher(Teacher teacher) throws ServerException;

    User getUserByToken(String token);

    User getUserByLogin(String token);

}
