package net.thumbtack.school.courses.server.database;

import net.thumbtack.school.courses.server.model.Course;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.exception.ServerErrorCode;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.model.User;
import org.apache.commons.collections4.*;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Database implements Serializable{
    private static volatile Database database;

    private Map<String, User> loginUser; //login -> User
    transient private BidiMap<String, User> activeUser; //token -> User
    private Map<Integer, Course> idToCourse;
    private Map<Integer, User> idToUser ;
    private int userCount;
    private int courseCount;


    private Database() {
        loginUser = new TreeMap<>();
        idToCourse = new HashMap<>();
        idToUser = new HashMap<>();
        userCount = 0;
        courseCount = 0;
        activeUser = new DualHashBidiMap<>();
    }

    public static Database startDatabase(String nameFile) {
        Database result = database;
        if(result != null) {
            return result;
        }
        synchronized (Database.class){
            if(database == null){
                if (nameFile == null){
                    database = new Database();
                }
                else {
                    try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream(nameFile))){
                        database = (Database) stream.readObject();
                        database.activeUser = new DualHashBidiMap<>();
                    } catch (IOException | ClassNotFoundException e) {
                        database = new Database();
                    }
                }
            }
            return database;
        }
    }

    public static Database getDatabase() {
        Database result = database;
        if(result != null) {
            return result;
        }
        synchronized (Database.class){
            return database;
        }
    }

    private void save(String nameFile) {
        if(nameFile != null && !nameFile.equals("")){
            try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(nameFile))) {
                stream.writeObject(database);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(String nameFile) {
        save(nameFile);
        database.loginUser.clear();
        database.activeUser.clear();
        database.idToCourse.clear();
        database.idToUser.clear();
        database.loginUser = null;
        database.activeUser = null;
        database.idToCourse = null;
        database.idToUser = null;
        database = null;
    }

    public String insert(User user) throws ServerException {
        if(loginUser.putIfAbsent(user.getLogin(), user) != null){
            throw new ServerException(ServerErrorCode.WRONG_LOGIN);
        }
        String token = UUID.randomUUID().toString();
        activeUser.put(token, user);
        userCount++;
        user.setIdUser(userCount);
        idToUser.put(userCount, user);
        return token;
    }


    public String login(User user){
        String token = activeUser.getKey(user);
        if(token != null){
            return token;
        }
        token = UUID.randomUUID().toString();
        activeUser.put(token, user);
        return token;
    }

    public void updateStudent(Student student) {
        List<Course> courses = new ArrayList<>(student.getCourses());
        for(Course course : courses){
            if(course.getCourseStudy() > student.getCourseStudy())
                unsubscribeStudentCourse(student, course);
        }
    }

    public void updateUser(User user, String updLogin) throws ServerException {
        if(loginUser.putIfAbsent(updLogin, user) != null){
            throw new ServerException(ServerErrorCode.WRONG_LOGIN);
        }
        loginUser.remove(user.getLogin(), user);
    }

    public void logout(User user) {
        activeUser.removeValue(user);
    }

    private void deleteUser(User user) {
        activeUser.removeValue(user);
        loginUser.remove(user.getLogin(), user);
        idToUser.remove(user.getIdUser());
    }

    public void deleteTeacher(Teacher teacher) throws ServerException {
        Iterator<Course> itCourse = teacher.getCourses().iterator();
        while (itCourse.hasNext()) {
            deleteCourse(teacher, itCourse.next());
            itCourse = teacher.getCourses().iterator();
        }
        deleteUser(teacher);
    }

    public void deleteStudent(Student student) {
        Iterator<Course> itCourse = student.getCourses().iterator();
        while (itCourse.hasNext()) {
            unsubscribeStudentCourse(student, itCourse.next());
            itCourse = student.getCourses().iterator();
        }
        deleteUser(student);
    }

    public User getUserByLogin(String login){
        return loginUser.get(login);
    }

    public User getUserByToken(String token) {
        return activeUser.get(token);
    }

    public User getUserById(int id){
        return idToUser.get(id);
    }

    public void registerCourse(Teacher teacher, Course course) {
        teacher.registerCourse(course);
        courseCount++;
        course.setIdCourse(courseCount);
        idToCourse.put(courseCount, course);
    }

    public Course getCourseById(int id) {
        return idToCourse.get(id);
    }

    public void subscribeStudentForCourse(Student student, Course course) {
        student.subscribeCourse(course);
        course.addStudent(student);
    }

    public void unsubscribeStudentCourse(Student student, Course course) {
        student.unsubscribeCourse(course);
        course.removeStudent(student);
    }
    public void deleteCourse(Teacher teacher, Course course) throws ServerException {
        if(!teacher.deleteCourse(course)){
            throw new ServerException(ServerErrorCode.WRONG_ACTION);
        }
        for (Student student : course.getStudents()) {
            student.unsubscribeCourse(course);
        }
        course.getStudents().clear();
        idToCourse.remove(course.getIdCourse());
    }

    public Set<Student> getTeacherStudents(Teacher teacher) {
        return getStudentsCourses(teacher.getCourses());
    }

    public Set<Student> getStudentsEnrolledInCourses(Teacher teacher, List<Course> courses)   {
        List<Course> coursesTeacher = new ArrayList<>(teacher.getCourses());
        coursesTeacher.retainAll(courses);
        return getStudentsCourses(coursesTeacher);
    }

    private Set<Student> getStudentsCourses(List<Course> courses){
        Set<Student> teacherStudents = new HashSet<>();
        for(Course course : courses){
            teacherStudents.addAll(course.getStudents());
        }
        return teacherStudents;
    }

    public List<Course> getListSignedCourses(Student student)   {
        return student.getCourses();
    }
    public List<Course> getTeacherCourses(Student student, Teacher teacher)   {
        return teacher.getCourses().stream().filter(course -> course.getCourseStudy() <= student.getCourseStudy()).collect(Collectors.toList());
    }

    public List<Course> getListAllStudentCourses(Student student) {
        return idToCourse.values().stream()
                         .filter(course -> course.getCourseStudy() <= student.getCourseStudy())
                         .collect(Collectors.toList());
    }
}
