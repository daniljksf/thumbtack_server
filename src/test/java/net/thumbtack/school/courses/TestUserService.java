package net.thumbtack.school.courses;


import com.google.gson.Gson;
import net.thumbtack.school.courses.server.Server;
import net.thumbtack.school.courses.server.dto.request.*;
import net.thumbtack.school.courses.server.dto.response.TokenDtoResponse;
import net.thumbtack.school.courses.server.exception.ServerErrorCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;



import static org.junit.Assert.assertEquals;


public class TestUserService {
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

    /*@Test
    public  void  test(){
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        RegisterTeacherDtoRequest request1 = new RegisterTeacherDtoRequest("thumbtack", "GlobalUser23$", "Dvorkin", "Pavel", "Lazarevich", "Professor", "POZI");

        String tokenTeacherAssembler = server.registerTeacher(gson.toJson(request));
        String tokenTeacherThumbtack = server.registerTeacher(gson.toJson(request1));

        RegisterCourseDtoRequest registerCourseAssembler = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacherAssembler, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterCourseDtoRequest registerCourseMath = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacherAssembler, TokenDtoResponse.class).getToken(), "math", 1, 106);
        RegisterCourseDtoRequest registerCourseThumbtack = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacherThumbtack, TokenDtoResponse.class).getToken(), "thumbtack", 3, 106);

        server.registerCourse(gson.toJson(registerCourseAssembler));
        server.registerCourse(gson.toJson(registerCourseMath));
        server.registerCourse(gson.toJson(registerCourseThumbtack));

        RegisterStudentDtoRequest registerStudent1 = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        RegisterStudentDtoRequest registerStudent2 = new RegisterStudentDtoRequest("akimovalalina", "GlobalUser23$", "Akimova", "Alina", "Alexandrovna", 3, "MPB-903");
        RegisterStudentDtoRequest registerStudent3 = new RegisterStudentDtoRequest("silaevdmitriy", "GlobalUser23$", "Silaev", "Dmitriy", "Vyacheslavovich", 3, "MPB-904");
        RegisterStudentDtoRequest registerStudent4 = new RegisterStudentDtoRequest("evgendam", "GlobalUser23$", "Damm", "Evgeniy", "Alexandrovich", 1, "MPB-004");

        String student1 = server.registerStudent(gson.toJson(registerStudent1));
        String student2 = server.registerStudent(gson.toJson(registerStudent2));
        String student3 = server.registerStudent(gson.toJson(registerStudent3));
        String student4 = server.registerStudent(gson.toJson(registerStudent4));

        CourseActionRequest courseActionRequest1 = new CourseActionRequest(gson.fromJson(student1, TokenDtoResponse.class).getToken(), 1);
        CourseActionRequest courseActionRequest2 = new CourseActionRequest(gson.fromJson(student1, TokenDtoResponse.class).getToken(), 3);
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest1));
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest2));

        CourseActionRequest courseActionRequest3 = new CourseActionRequest(gson.fromJson(student2, TokenDtoResponse.class).getToken(), 1);
        CourseActionRequest courseActionRequest4 = new CourseActionRequest(gson.fromJson(student2, TokenDtoResponse.class).getToken(), 2);
        CourseActionRequest courseActionRequest5 = new CourseActionRequest(gson.fromJson(student2, TokenDtoResponse.class).getToken(), 3);
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest3));
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest4));
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest5));

        CourseActionRequest courseActionRequest6 = new CourseActionRequest(gson.fromJson(student3, TokenDtoResponse.class).getToken(), 1);
        CourseActionRequest courseActionRequest7 = new CourseActionRequest(gson.fromJson(student3, TokenDtoResponse.class).getToken(), 3);
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest6));
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest7));

        CourseActionRequest courseActionRequest8 = new CourseActionRequest(gson.fromJson(student4, TokenDtoResponse.class).getToken(), 2);
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest8));
    }
    */@Test
    public void testLogin() {
        LoginUserDtoRequest loginTeacherAssembler = new LoginUserDtoRequest("assembler","GlobalUser23$");
        LoginUserDtoRequest loginTeacherThumbtack = new LoginUserDtoRequest("thumbtack","GlobalUser23$");
        LoginUserDtoRequest loginStudentShnayder = new LoginUserDtoRequest("shnayderdanila","GlobalUser23$");
        LoginUserDtoRequest loginStudentAkimova = new LoginUserDtoRequest("akimovalalina","GlobalUser23$");
        LoginUserDtoRequest loginStudentSilaev = new LoginUserDtoRequest("silaevdmitriy","GlobalUser23$");
        LoginUserDtoRequest loginStudentDam = new LoginUserDtoRequest("evgendam","GlobalUser23$");

        String tokenAssembler = server.login(gson.toJson(loginTeacherAssembler));
        String tokenThumbtack = server.login(gson.toJson(loginTeacherThumbtack));
        String tokenShnayder = server.login(gson.toJson(loginStudentShnayder));
        String tokenAkimova = server.login(gson.toJson(loginStudentAkimova));
        String tokenSilaev = server.login(gson.toJson(loginStudentSilaev));
        String tokenDam = server.login(gson.toJson(loginStudentDam));

        assertAll(
                ()-> assertEquals(4, gson.fromJson(server.getListAllSignedStudents(tokenAssembler), ArrayList.class).size()),
                ()-> assertEquals(3, gson.fromJson(server.getListAllSignedStudents(tokenThumbtack),ArrayList.class).size()),
                ()-> assertEquals(2, gson.fromJson(server.getSignedCourses(tokenShnayder),ArrayList.class).size()),
                ()-> assertEquals(3, gson.fromJson(server.getSignedCourses(tokenAkimova),ArrayList.class).size()),
                ()-> assertEquals(2, gson.fromJson(server.getSignedCourses(tokenSilaev),ArrayList.class).size()),
                ()-> assertEquals(1, gson.fromJson(server.getSignedCourses(tokenDam),ArrayList.class).size())
        );
    }

    @Test
    public void testLoginReLogin(){
        LoginUserDtoRequest loginTeacherAssembler = new LoginUserDtoRequest("assembler","GlobalUser23$");

        String tokenAssembler = server.login(gson.toJson(loginTeacherAssembler));

        String tokenAssembler2 = server.login(gson.toJson(loginTeacherAssembler));

        assertEquals(tokenAssembler, tokenAssembler2);

    }

    @Test
    public void testLoginWrongData() {
        LoginUserDtoRequest loginTeacherAssembler = new LoginUserDtoRequest("","GlobalUser23$");
        LoginUserDtoRequest loginTeacherThumbtack = new LoginUserDtoRequest("thumbtack","");

        String response1 = server.login(gson.toJson(loginTeacherAssembler));
        String response2 = server.login(gson.toJson(loginTeacherThumbtack));
        assertAll(
                ()-> assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}', response1),
                ()-> assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}', response2)
        );
    }

    @Test
    public void testLoginWrongPassAndLogin() {
        LoginUserDtoRequest loginTeacherAssembler = new LoginUserDtoRequest("assembler","GlobalUser3$");
        LoginUserDtoRequest loginTeacherThumbtack = new LoginUserDtoRequest("thumb","GlobalUser23$");

        String response1 = server.login(gson.toJson(loginTeacherAssembler));
        String response2 = server.login(gson.toJson(loginTeacherThumbtack));

        assertAll(
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_PASSWORD.getErrorString() + "\"" + '}', response1),
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.INCORRECT_LOGIN.getErrorString() + "\"" + '}', response2)

        );
    }

    @Test
    public void testLogout() {
        LoginUserDtoRequest loginTeacherAssembler = new LoginUserDtoRequest("assembler","GlobalUser23$");
        LoginUserDtoRequest loginTeacherThumbtack = new LoginUserDtoRequest("thumbtack","GlobalUser23$");
        LoginUserDtoRequest loginStudentShnayder = new LoginUserDtoRequest("shnayderdanila","GlobalUser23$");
        LoginUserDtoRequest loginStudentAkimova = new LoginUserDtoRequest("akimovalalina","GlobalUser23$");
        LoginUserDtoRequest loginStudentSilaev = new LoginUserDtoRequest("silaevdmitriy","GlobalUser23$");
        LoginUserDtoRequest loginStudentDam = new LoginUserDtoRequest("evgendam","GlobalUser23$");

        String tokenAssembler = server.login(gson.toJson(loginTeacherAssembler));
        String tokenThumbtack = server.login(gson.toJson(loginTeacherThumbtack));
        String tokenShnayder = server.login(gson.toJson(loginStudentShnayder));
        String tokenAkimova = server.login(gson.toJson(loginStudentAkimova));
        String tokenSilaev = server.login(gson.toJson(loginStudentSilaev));
        String tokenDam = server.login(gson.toJson(loginStudentDam));

        assertAll(
                ()->assertEquals("", server.logout(tokenAssembler) ),
                ()->assertEquals("", server.logout(tokenThumbtack) ),
                ()->assertEquals("", server.logout(tokenShnayder) ),
                ()->assertEquals("", server.logout(tokenAkimova) ),
                ()->assertEquals("", server.logout(tokenSilaev) ),
                ()->assertEquals("", server.logout(tokenDam) ),
                ()-> assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}',server.getSignedCourses(tokenShnayder)),
                ()-> assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}',server.getSignedCourses(tokenAkimova)),
                ()-> assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}',server.getSignedCourses(tokenSilaev)),
                ()-> assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}',server.getSignedCourses(tokenDam)),
                ()-> assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}',server.getListAllSignedStudents(tokenAssembler)),
                ()-> assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}',server.getListAllSignedStudents(tokenThumbtack))
        );
    }

    @Test
    public void testLogoutWrongData() {
          assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}', server.logout("{\"token\":\"\"}"));
    }
    @Test
    public void testExit1()  {
        LoginUserDtoRequest loginTeacherAssembler = new LoginUserDtoRequest("assembler","GlobalUser23$");
        LoginUserDtoRequest loginStudentAkimova = new LoginUserDtoRequest("akimovalalina","GlobalUser23$");
        LoginUserDtoRequest loginStudentDam = new LoginUserDtoRequest("evgendam","GlobalUser23$");

        String tokenAssembler = server.login(gson.toJson(loginTeacherAssembler));
        String tokenAkimova = server.login(gson.toJson(loginStudentAkimova));
        String tokenDam = server.login(gson.toJson(loginStudentDam));
        server.exit(tokenAssembler);
        assertAll(
                ()-> assertEquals(1,gson.fromJson(server.getSignedCourses(tokenAkimova), ArrayList.class).size()),
                ()-> assertEquals(0,gson.fromJson(server.getSignedCourses(tokenDam), ArrayList.class).size()),
                ()-> assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}', server.getListAllSignedStudents(tokenAssembler)),
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.INCORRECT_LOGIN.getErrorString() + "\"" + '}', server.login(gson.toJson(loginTeacherAssembler)))
        );

    }

    @Test
    public void testExit2() {

        LoginUserDtoRequest loginTeacherAssembler = new LoginUserDtoRequest("assembler","GlobalUser23$");
        LoginUserDtoRequest loginTeacherThumbtack = new LoginUserDtoRequest("thumbtack","GlobalUser23$");
        LoginUserDtoRequest loginStudentAkimova = new LoginUserDtoRequest("akimovalalina","GlobalUser23$");

        String tokenAssembler = server.login(gson.toJson(loginTeacherAssembler));
        String tokenThumbtack = server.login(gson.toJson(loginTeacherThumbtack));
        String tokenAkimova = server.login(gson.toJson(loginStudentAkimova));

        String beforeExit1 = server.getListAllSignedStudents(tokenAssembler);
        String beforeExit2 = server.getListAllSignedStudents(tokenThumbtack);

        server.exit(tokenAkimova);

        assertAll(
                ()-> assertEquals(4,gson.fromJson(beforeExit1, ArrayList.class).size()),
                ()-> assertEquals(3,gson.fromJson(beforeExit2, ArrayList.class).size()),
                ()-> assertEquals(3,gson.fromJson(server.getListAllSignedStudents(tokenAssembler), ArrayList.class).size()),
                ()-> assertEquals(2,gson.fromJson(server.getListAllSignedStudents(tokenThumbtack), ArrayList.class).size()),
                ()-> assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}', server.getSignedCourses(tokenAkimova)),
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.INCORRECT_LOGIN.getErrorString() + "\"" + '}', server.login(gson.toJson(loginStudentAkimova)))
        );

    }

    @Test
    public void testExitWrongData() {
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}',server.exit("{\"token\":\"\"}"));
    }

    @Test
    public void testNullRequest() {
        assertAll(
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_NULL_REQUEST.getErrorString() + "\"" + '}',server.login(null)),
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_NULL_REQUEST.getErrorString() + "\"" + '}',server.logout(null)),
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_NULL_REQUEST.getErrorString() + "\"" + '}',server.exit(null))
        );
    }

}
