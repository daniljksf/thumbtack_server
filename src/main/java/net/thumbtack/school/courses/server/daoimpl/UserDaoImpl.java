package net.thumbtack.school.courses.server.daoimpl;

import net.thumbtack.school.courses.server.dao.UserDao;
import net.thumbtack.school.courses.server.database.Database;
import net.thumbtack.school.courses.server.exception.ServerErrorCode;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.model.User;

public class UserDaoImpl implements UserDao {

    private final Database database;

    public UserDaoImpl() {
        database = Database.getDatabase();
    }

    @Override
    public String login(User user){
        return database.login(user);
    }

    @Override
    public void logout(User user){
        database.logout(user);
    }

    @Override
    public void exitStudent(Student student) {
        database.deleteStudent(student);
    }

    @Override
    public void exitTeacher(Teacher teacher) throws ServerException {
        database.deleteTeacher(teacher);
    }

    @Override
    public User getUserByToken(String token)   {
        return database.getUserByToken(token);
    }
    @Override
    public User getUserByLogin(String login){
        return database.getUserByLogin(login);
    }
}
