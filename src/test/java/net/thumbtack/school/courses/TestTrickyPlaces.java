

package net.thumbtack.school.courses;

import com.google.gson.Gson;
import net.thumbtack.school.courses.server.Server;
import net.thumbtack.school.courses.server.dto.request.*;
import net.thumbtack.school.courses.server.dto.response.TokenDtoResponse;
import net.thumbtack.school.courses.server.exception.ServerErrorCode;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



/* в файле test.out
            Teacher:
                login     password      lastName    firstName   patronymic  position    faculty
                assembler GlobalUser23$ Ashaev      Igor        Viktorovich Docent      Security
                thumbtack GlobalUser23$ Dvorkin     Pavel       Lazarevich  Professor       POZI
            Student:
                login           password        lastName    firstName   patronymic      courseStudy    group
                shnayderdanila  GlobalUser23$   Shnayder    Danila      Vladimirovich       3          MPB-904
                akimovalalina   GlobalUser23$   Akimova     Alina       Alexandrovna        3          MPB-903
                silaevdmitriy   GlobalUser23$   Silaev      Dmitriy     Vyacheslavovich     3          MPB-904
                evgendam  G     lobalUser23$    Damm        Evgeniy     Alexandrovich       1          MPB-004
             Course:
                name        courseStudy hours teacherLogin signedStudentsLogin
                assembler       3       106    assembler   shnayder, akimova, silaev
                math            1       106    assembler   akimova, evgendam
                thumbtack       3       106    thumbtack   shnayder, akimova, silaev
        */
public class TestTrickyPlaces {

    private Server server;
    private Gson gson;

    @Before
    public void startServer() {
        server = new Server();
        server.startServer("test.out");
        gson = new Gson();
    }

    @After
    public void stopServer(){
        server.stopServer(null);
    }

    @Test
    //проверка что учитель не можешь работать с курсами других преподавателей
    public  void  testWrongTeacherAction(){
        LoginUserDtoRequest loginTeacherThumbtack = new LoginUserDtoRequest("thumbtack","GlobalUser23$");
        String token = server.login(gson.toJson(loginTeacherThumbtack));
        StudentsEnrolledGivenCoursesRequest getWrongCourse = new StudentsEnrolledGivenCoursesRequest(gson.fromJson(token, TokenDtoResponse.class).getToken(), new int[]{1});
        CourseActionRequest deleteWrongCourse = new CourseActionRequest(gson.fromJson(token, TokenDtoResponse.class).getToken(), 1);
        assertAll(
                ()-> Assert.assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_ACTION.getErrorString() + "\"" + '}',server.getStudentsEnrolledInCourse(gson.toJson(getWrongCourse))),
                ()-> Assert.assertEquals( "{\"error\":"+ "\"" + ServerErrorCode.WRONG_ACTION.getErrorString() + "\"" + '}',server.deleteCourse(gson.toJson(deleteWrongCourse)))
        );

    }
    //проверка что при понижении курса студента удаляются все курсы у которых курс обучения больше
    @Test
    public void testUpdateStudentBadPassword() {

        LoginUserDtoRequest loginStudentShnayder = new LoginUserDtoRequest("shnayderdanila","GlobalUser23$");
        String token = server.login(gson.toJson(loginStudentShnayder));

        UpdateStudentRequest updateStudent = new UpdateStudentRequest(gson.fromJson(token, TokenDtoResponse.class).getToken(), "GlobalUser23$", "goggabangd", "GlobalUser23#", 1, "MPB-904");
        String coursesBeforeUpdate = server.getSignedCourses(token);

        server.updateStudent(gson.toJson(updateStudent));

        String coursesAfterUpdate = server.getSignedCourses(token);

        assertAll(
                ()-> assertNotEquals(gson.fromJson(coursesBeforeUpdate, ArrayList.class).size(), gson.fromJson(coursesAfterUpdate, ArrayList.class).size()),
                ()->assertEquals(2, gson.fromJson(coursesBeforeUpdate, ArrayList.class).size()),
                ()->assertEquals(0, gson.fromJson(coursesAfterUpdate, ArrayList.class).size())
        );
    }

}

