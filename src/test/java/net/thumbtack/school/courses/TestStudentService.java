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

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TestStudentService {

    private Server server;
    private Gson gson;

    @Before
    public void startServer() {
        server = new Server();
        server.startServer(null);
        gson = new Gson();
    }

    @After
    public void stopServer(){
        server.stopServer(null);
    }

    @Test
    public void testInsertStudent() {

        RegisterStudentDtoRequest request = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");

        String response = server.registerStudent(gson.toJson(request));

        assertAll(
            ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_NULL_REQUEST.getErrorString() + "\"" + '}',response),
            ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_LOGIN.getErrorString() + "\"" + '}',response),
            ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}',response),
            ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_COURSE_OF_STUDY.getErrorString() + "\"" + '}',response),
            ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_LOGIN.getErrorString() + "\"" + '}',response),
            ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_PASSWORD.getErrorString() + "\"" + '}',response)
        );

    }

    @Test
    public void testInsertStudentWrongLogin() {
        RegisterStudentDtoRequest request = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");

        server.registerStudent(gson.toJson(request));
        String response = server.registerStudent(gson.toJson(request));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_LOGIN.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testInsertStudentWrongDataRequest() {
        RegisterStudentDtoRequest request = new RegisterStudentDtoRequest("", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");


        String response = server.registerStudent(gson.toJson(request));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testInsertStudentWrongCourseOfStudy() {
        RegisterStudentDtoRequest request = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 8, "MPB-904");
        String response = server.registerStudent(gson.toJson(request));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_COURSE_OF_STUDY.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testInsertStudentBadLogin() {
        RegisterStudentDtoRequest request = new RegisterStudentDtoRequest("shna", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        String response = server.registerStudent(gson.toJson(request));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_LOGIN.getErrorString() + "\"" + '}',response);
    }


    @Test
    public void testInsertStudentBadPass() {
        RegisterStudentDtoRequest request = new RegisterStudentDtoRequest("shnayder", "globalUser23", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        String response = server.registerStudent(gson.toJson(request));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_PASSWORD.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testUpdateStudent() {
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        String token = server.registerStudent(gson.toJson(registerStudent));
        UpdateStudentRequest updateStudent = new UpdateStudentRequest(gson.fromJson(token, TokenDtoResponse.class).getToken(), "globalUser23$", "ShnayderDanila", "GlobalUser23#", 3, "MPB-904");
        String response = server.updateStudent(gson.toJson(updateStudent));
        assertAll(
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_NULL_REQUEST.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_LOGIN.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_COURSE_OF_STUDY.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_LOGIN.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_PASSWORD.getErrorString() + "\"" + '}',response)
        );
    }

    @Test
    public void testUpdateStudentWrongLogin() {
        RegisterStudentDtoRequest registerStudent1 = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        RegisterStudentDtoRequest registerStudent2 = new RegisterStudentDtoRequest("goggabangd", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        String token = server.registerStudent(gson.toJson(registerStudent1));
        server.registerStudent(gson.toJson(registerStudent2));
        UpdateStudentRequest updateStudent = new UpdateStudentRequest(gson.fromJson(token, TokenDtoResponse.class).getToken(), "GlobalUser23$", "goggabangd", "GlobalUser23$#", 3, "MPB-904");
        String response = server.updateStudent(gson.toJson(updateStudent));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_LOGIN.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testUpdateStudentWrongDataRequest() {
        RegisterStudentDtoRequest registerStudent1 = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        String token = server.registerStudent(gson.toJson(registerStudent1));
        UpdateStudentRequest updateStudent = new UpdateStudentRequest(gson.fromJson(token, TokenDtoResponse.class).getToken(), "GlobalUser23$", "", "GlobalUser23$#", 3, "MPB-904");
        String response = server.updateStudent(gson.toJson(updateStudent));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}',response);

    }

    @Test
    public void testUpdateStudentWrongCourseOfStudy() {
        RegisterStudentDtoRequest registerStudent1 = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        String token = server.registerStudent(gson.toJson(registerStudent1));
        UpdateStudentRequest updateStudent = new UpdateStudentRequest(gson.fromJson(token, TokenDtoResponse.class).getToken(), "GlobalUser23$", "goggabangd", "GlobalUser23$#", 8, "MPB-904");
        String response = server.updateStudent(gson.toJson(updateStudent));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_COURSE_OF_STUDY.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testUpdateStudentBadLogin() {
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        String token = server.registerStudent(gson.toJson(registerStudent));
        UpdateStudentRequest updateStudent = new UpdateStudentRequest(gson.fromJson(token, TokenDtoResponse.class).getToken(), "globalUser23$", "ShnayderDanila", "GlobalUser23#", 3, "MPB-904");
        String response = server.updateStudent(gson.toJson(updateStudent));
        assertAll(
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_NULL_REQUEST.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_LOGIN.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_COURSE_OF_STUDY.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_LOGIN.getErrorString() + "\"" + '}',response),
                ()-> assertNotEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_PASSWORD.getErrorString() + "\"" + '}',response)
        );
    }


    @Test
    public void testUpdateStudentDowngradeCourse() {
        RegisterStudentDtoRequest registerStudent1 = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        String token = server.registerStudent(gson.toJson(registerStudent1));
        UpdateStudentRequest updateStudent = new UpdateStudentRequest(gson.fromJson(token, TokenDtoResponse.class).getToken(), "GlobalUser23$", "goggabangd", "GlobalUser23", 3, "MPB-904");
        String response = server.updateStudent(gson.toJson(updateStudent));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_PASSWORD.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testRegisterForCourse() {

        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourse = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-902");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));
        CourseActionRequest registerForCourse = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 1);

        server.registerCourse(gson.toJson(registerCourse));

        String m = server.subscribeStudentForCourse(gson.toJson(registerForCourse));
        assertEquals("", m);

    }

    @Test
    public void testRegisterForCourseWrongCourseOfStudy() {

        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourse = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 1, "MPB-902");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));
        CourseActionRequest registerForCourse = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 1);

        server.registerCourse(gson.toJson(registerCourse));
        String m = server.subscribeStudentForCourse(gson.toJson(registerForCourse));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_COURSE_OF_STUDY.getErrorString() + "\"" + '}', m);

    }

    @Test
    public void testRegisterForCourseWrongDataRequest() {
        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourse = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-902");
        server.registerStudent(gson.toJson(registerStudent));
        CourseActionRequest registerForCourse = new CourseActionRequest("", 1);

        server.registerCourse(gson.toJson(registerCourse));
        String m = server.subscribeStudentForCourse(gson.toJson(registerForCourse));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}', m);
    }

    @Test
    public void testRegisterForCourseWrongIdCourse() {
        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourse = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-902");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));
        CourseActionRequest registerForCourse = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 3);

        server.registerCourse(gson.toJson(registerCourse));
        String m = server.subscribeStudentForCourse(gson.toJson(registerForCourse));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_ID_COURSE.getErrorString() + "\"" + '}', m);
    }

    @Test
    public void testUnsubscribeOfCourse() {
        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourse = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-902");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));
        CourseActionRequest registerForCourse = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 1);

        server.registerCourse(gson.toJson(registerCourse));

        server.subscribeStudentForCourse(gson.toJson(registerForCourse));

        CourseActionRequest unregisterForCourse = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 1);

        String response = server.unsubscribeStudentOfCourse(gson.toJson(unregisterForCourse));

        assertEquals("", response);

    }

    @Test
    public void testUnsubscribeOfCourseWrongData() {
        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourse = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-902");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));
        CourseActionRequest registerForCourse = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 1);

        server.registerCourse(gson.toJson(registerCourse));

        server.subscribeStudentForCourse(gson.toJson(registerForCourse));

        CourseActionRequest unregisterForCourse = new CourseActionRequest("", 1);

        String response = server.unsubscribeStudentOfCourse(gson.toJson(unregisterForCourse));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}', response);

    }

    @Test
    public void testUnsubscribeOfCourseWrongIdOfCourse() {
        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourse = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-902");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));
        CourseActionRequest registerForCourse = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 1);

        server.registerCourse(gson.toJson(registerCourse));

        server.subscribeStudentForCourse(gson.toJson(registerForCourse));

        CourseActionRequest unregisterForCourse = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 5);

        String response = server.unsubscribeStudentOfCourse(gson.toJson(unregisterForCourse));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_ID_COURSE.getErrorString() + "\"" + '}', response);
    }

    @Test
    public void testGetSignedCourses() {
        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourseAssembler = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterCourseDtoRequest registerCourseAlgem = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "algem", 3, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-902");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));

        CourseActionRequest registerForAssembler = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 1);
        CourseActionRequest registerForAlgem = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 1);

        server.registerCourse(gson.toJson(registerCourseAssembler));
        server.registerCourse(gson.toJson(registerCourseAlgem));

        server.subscribeStudentForCourse(gson.toJson(registerForAssembler));
        String firstResponse = server.getSignedCourses(tokenStudent);

        server.subscribeStudentForCourse(gson.toJson(registerForAlgem));
        String secondResponse = server.getSignedCourses(tokenStudent);

        assertAll(
                ()-> assertEquals(1, gson.fromJson(firstResponse, ArrayList.class).size()),
                ()-> assertEquals(2, gson.fromJson(secondResponse, ArrayList.class).size())
        );
    }

    @Test
    public void testGetSignedCoursesWrongDataRequest() {
        String firstResponse = server.getSignedCourses("{\"token\":\"\"}");
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}', firstResponse);
    }

    @Test
    public void testGetAvailableCourse() {
        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourseAssembler = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterCourseDtoRequest registerCourseAlgem = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "algem", 3, 106);
        RegisterCourseDtoRequest registerCourseMath = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "Math", 5, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-902");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));

        server.registerCourse(gson.toJson(registerCourseAssembler));
        server.registerCourse(gson.toJson(registerCourseAlgem));
        server.registerCourse(gson.toJson(registerCourseMath));
        String response = server.getAvailableCourse(tokenStudent);

        assertEquals(2, gson.fromJson(response, ArrayList.class).size());
    }

    @Test
    public void testGetAvailableCourseWrongDataRequest() {
        String response = server.getAvailableCourse("{\"token\":\"\"}");
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}', response);
    }

    @Test
    public void getTeacherCourses() {
        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourseAssembler = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterCourseDtoRequest registerCourseAlgem = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "algem", 3, 106);
        RegisterCourseDtoRequest registerCourseMath = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "Math", 5, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-902");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));

        TeacherCoursesDtoRequest getTeacherCoursesRequest = new TeacherCoursesDtoRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 1);

        server.registerCourse(gson.toJson(registerCourseAssembler));
        server.registerCourse(gson.toJson(registerCourseAlgem));
        server.registerCourse(gson.toJson(registerCourseMath));

        String response = server.getTeacherCourses(gson.toJson(getTeacherCoursesRequest));

        assertEquals(2, gson.fromJson(response, ArrayList.class).size());
    }

    @Test
    public void getTeacherCoursesWrongDataRequest() {
        String response = server.getTeacherCourses("{\"token\":\""+"\",\"idTeacher\":\"1\"}");
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}', response);
    }

    @Test
    public void getTeacherCoursesWrongIdTeacher() {
        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        RegisterCourseDtoRequest registerCourseAssembler = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterCourseDtoRequest registerCourseAlgem = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "algem", 3, 106);
        RegisterCourseDtoRequest registerCourseMath = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "Math", 5, 106);
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("ShnayderDanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-902");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));

        TeacherCoursesDtoRequest getTeacherCoursesRequest = new TeacherCoursesDtoRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 2);

        server.registerCourse(gson.toJson(registerCourseAssembler));
        server.registerCourse(gson.toJson(registerCourseAlgem));
        server.registerCourse(gson.toJson(registerCourseMath));

        String response = server.getTeacherCourses(gson.toJson(getTeacherCoursesRequest));

        assertEquals( "{\"error\":"+ "\"" + ServerErrorCode.WRONG_ID_USER.getErrorString() + "\"" + '}', response);
    }

    @Test
    public void testNullRequest() {
        String expected = "{\"error\":"+ "\"" + ServerErrorCode.WRONG_NULL_REQUEST.getErrorString() + "\"" + '}';
        assertAll(
                ()->assertEquals(expected, server.registerStudent(null)),
                ()->assertEquals(expected, server.subscribeStudentForCourse(null)),
                ()->assertEquals(expected, server.updateStudent(null)),
                ()->assertEquals(expected, server.getSignedCourses(null)),
                ()->assertEquals(expected, server.getTeacherCourses(null)),
                ()->assertEquals(expected, server.unsubscribeStudentOfCourse(null)),
                ()->assertEquals(expected, server.getAvailableCourse(null))
        );
    }

    @Test
    public void testWrongActionToken() {
        RegisterTeacherDtoRequest registerTeacher = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");

        String tokenTeacher = server.registerTeacher(gson.toJson(registerTeacher));
        String stringTokenTeacher = gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken();
        CourseActionRequest courseAction = new CourseActionRequest(stringTokenTeacher, 1);
        UpdateStudentRequest updateStudentRequest = new UpdateStudentRequest(stringTokenTeacher, "ddasdas", "dsadas", "dsadsA#232", 3, "MPB-902");
        TeacherCoursesDtoRequest getTeacherCoursesRequest = new TeacherCoursesDtoRequest(stringTokenTeacher, 1);

        String expected = "{\"error\":"+ "\"" + ServerErrorCode.WRONG_ACTION.getErrorString() + "\"" + '}';

        assertAll(
                ()->assertEquals(expected, server.subscribeStudentForCourse(gson.toJson(courseAction))),
                ()->assertEquals(expected, server.updateStudent(gson.toJson(updateStudentRequest))),
                ()->assertEquals(expected, server.getSignedCourses(tokenTeacher)),
                ()->assertEquals(expected, server.getTeacherCourses(gson.toJson(getTeacherCoursesRequest))),
                ()->assertEquals(expected, server.unsubscribeStudentOfCourse(gson.toJson(courseAction))),
                ()->assertEquals(expected, server.getAvailableCourse(tokenTeacher))
        );
    }

    @Test
    public void testWrongToken() {
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        server.registerStudent(gson.toJson(registerStudent));

        CourseActionRequest courseAction = new CourseActionRequest("tokenStudent", 1);
        UpdateStudentRequest updateStudentRequest = new UpdateStudentRequest("tokenStudent", "ddasdas", "dsadas", "dsadsA#232", 3, "MPB-902");
        TeacherCoursesDtoRequest getTeacherCoursesRequest = new TeacherCoursesDtoRequest("tokenStudent", 1);

        String expected = "{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}';

        assertAll(
                ()->assertEquals(expected, server.subscribeStudentForCourse(gson.toJson(courseAction))),
                ()->assertEquals(expected, server.updateStudent(gson.toJson(updateStudentRequest))),
                ()->assertEquals(expected, server.getSignedCourses("{\"token\":\"tokenStudent\"}")),
                ()->assertEquals(expected, server.getTeacherCourses(gson.toJson(getTeacherCoursesRequest))),
                ()->assertEquals(expected, server.unsubscribeStudentOfCourse(gson.toJson(courseAction))),
                ()->assertEquals(expected, server.getAvailableCourse("{\"token\":\"tokenStudent\"}"))
        );
    }

}
