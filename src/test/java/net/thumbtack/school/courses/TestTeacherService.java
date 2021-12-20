
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
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TestTeacherService {

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
    public void testInsertTeacher()  {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String response = server.registerTeacher(gson.toJson(request));
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
    public void testInsertTeacherWrongLogin()  {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        server.registerTeacher(gson.toJson(request));
        String response = server.registerTeacher(gson.toJson(request));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_LOGIN.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testInsertTeacherWrongDataRequest()  {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String response = server.registerTeacher(gson.toJson(request));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testInsertTeacherBadLogin()  {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("bler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String response = server.registerTeacher(gson.toJson(request));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_LOGIN.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testInsertTeacherBadPass()  {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String response = server.registerTeacher(gson.toJson(request));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_PASSWORD.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testUpdateTeacher()   {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));

        UpdateTeacherRequest updateTeacherRequest = new UpdateTeacherRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "GlobalUser23$", "Assembler", "updatePassword23%", "Docent","Security");
        String response = server.updateTeacher(gson.toJson(updateTeacherRequest));

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
    public void testUpdateTeacherWrongLogin()   {
        RegisterTeacherDtoRequest request1 = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        RegisterTeacherDtoRequest request2 = new RegisterTeacherDtoRequest("goggabangd", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request1));
        server.registerTeacher(gson.toJson(request2));

        UpdateTeacherRequest updateTeacherRequest = new UpdateTeacherRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "GlobalUser23$", "goggabangd", "updatePassword23%", "Docent","Security");
        String response = server.updateTeacher(gson.toJson(updateTeacherRequest));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_LOGIN.getErrorString() + "\"" + '}',response);

    }

    @Test
    public void testUpdateTeacherWrongDataRequest()   {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));

        UpdateTeacherRequest updateTeacherRequest = new UpdateTeacherRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "GlobalUser23$", "", "updatePassword23%", "Docent","Security");
        String response = server.updateTeacher(gson.toJson(updateTeacherRequest));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}',response);

    }

    @Test
    public void testUpdateTeacherBadLogin()   {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));

        UpdateTeacherRequest updateTeacherRequest = new UpdateTeacherRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "GlobalUser23$", "ler", "updatePassword23%", "Docent","Security");
        String response = server.updateTeacher(gson.toJson(updateTeacherRequest));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_LOGIN.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testUpdateTeacherBadPassword()   {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));

        UpdateTeacherRequest updateTeacherRequest = new UpdateTeacherRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "GlobalUser23$", "Assembler", "updatePassword23", "Docent","Security");
        String response = server.updateTeacher(gson.toJson(updateTeacherRequest));

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.BAD_PASSWORD.getErrorString() + "\"" + '}',response);
    }

    @Test
    public void testRegisterCourse()   {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));
        RegisterCourseDtoRequest registerCourseRequest = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        String m =server.registerCourse(gson.toJson(registerCourseRequest));
        assertEquals("", m);
    }

    @Test
    public void testRegisterCourseWrongHours()   {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));
        RegisterCourseDtoRequest registerCourseRequest = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 105);
        String m =server.registerCourse(gson.toJson(registerCourseRequest));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}', m);
    }

    @Test
    public void testRegisterCourseWrongCourseStudy() {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));
        RegisterCourseDtoRequest registerCourseRequest = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 11, 106);
        String m =server.registerCourse(gson.toJson(registerCourseRequest));
        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_COURSE_OF_STUDY.getErrorString() + "\"" + '}', m);
    }

    @Test
    public void testDeleteCourse() {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));
        RegisterCourseDtoRequest registerCourseRequest = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        server.registerCourse(gson.toJson(registerCourseRequest));

        CourseActionRequest courseActionRequest = new CourseActionRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), 1);

        String response = server.deleteCourse(gson.toJson(courseActionRequest));

        assertEquals("",response);
    }

    @Test
    public void testDeleteCourseException() {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));
        RegisterCourseDtoRequest registerCourseRequest = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        server.registerCourse(gson.toJson(registerCourseRequest));

        CourseActionRequest courseActionRequestWrongID = new CourseActionRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), 2);
        CourseActionRequest courseActionRequestWrongData = new CourseActionRequest("", 1);

        String responseTeacherWrongData = server.deleteCourse(gson.toJson(courseActionRequestWrongData));
        String responseTeacherWrongId = server.deleteCourse(gson.toJson(courseActionRequestWrongID));

        assertAll(
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}', responseTeacherWrongData),
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_ID_COURSE.getErrorString() + "\"" + '}', responseTeacherWrongId)
         );
    }



    @Test
    public void testGetListAllSignedStudents() {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));
        RegisterCourseDtoRequest registerCourseAssembler = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterCourseDtoRequest registerCourseMath = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "math", 3, 106);
        server.registerCourse(gson.toJson(registerCourseAssembler));
        server.registerCourse(gson.toJson(registerCourseMath));

        RegisterStudentDtoRequest registerStudent1 = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        RegisterStudentDtoRequest registerStudent2 = new RegisterStudentDtoRequest("akimovalalina", "GlobalUser23$", "Akimova", "Alina", "Alexandrovna", 3, "MPB-903");
        RegisterStudentDtoRequest registerStudent3 = new RegisterStudentDtoRequest("silaevdmitriy", "GlobalUser23$", "Silaev", "Dmitriy", "Vyacheslavovich", 3, "MPB-904");

        String student1 = server.registerStudent(gson.toJson(registerStudent1));
        String student2 = server.registerStudent(gson.toJson(registerStudent2));
        String student3 = server.registerStudent(gson.toJson(registerStudent3));

        String responseZero = server.getListAllSignedStudents(tokenTeacher);

        CourseActionRequest courseActionRequest1 = new CourseActionRequest(gson.fromJson(student1, TokenDtoResponse.class).getToken(), 1);
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest1));
        String responseOne = server.getListAllSignedStudents(tokenTeacher);

        CourseActionRequest courseActionRequest2 = new CourseActionRequest(gson.fromJson(student2, TokenDtoResponse.class).getToken(), 1);
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest2));
        String responseTwo = server.getListAllSignedStudents(tokenTeacher);
        CourseActionRequest courseActionRequest3 = new CourseActionRequest(gson.fromJson(student3, TokenDtoResponse.class).getToken(), 2);
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest3));
        String responseThree = server.getListAllSignedStudents(tokenTeacher);

        assertAll(
                ()-> assertEquals(0,gson.fromJson(responseZero, ArrayList.class).size()),
                ()-> assertEquals(1,gson.fromJson(responseOne, ArrayList.class).size()),
                ()-> assertEquals(2,gson.fromJson(responseTwo, ArrayList.class).size()),
                ()-> assertEquals(3,gson.fromJson(responseThree, ArrayList.class).size())
        );

    }

    @Test
    public void testGetListAllSignedStudentsWrongData() {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));
        RegisterCourseDtoRequest registerCourseAssembler = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        server.registerCourse(gson.toJson(registerCourseAssembler));
        String response = server.getListAllSignedStudents("{\"token\":\"\"}");

        assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}',response);

    }

    @Test
    public void testGetStudentsEnrolledInCourse() {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));
        RegisterCourseDtoRequest registerCourseAssembler = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        RegisterCourseDtoRequest registerCourseMath = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "math", 3, 106);
        server.registerCourse(gson.toJson(registerCourseAssembler));
        server.registerCourse(gson.toJson(registerCourseMath));

        RegisterStudentDtoRequest registerStudent1 = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        RegisterStudentDtoRequest registerStudent2 = new RegisterStudentDtoRequest("akimovalalina", "GlobalUser23$", "Akimova", "Alina", "Alexandrovna", 3, "MPB-903");
        RegisterStudentDtoRequest registerStudent3 = new RegisterStudentDtoRequest("silaevdmitriy", "GlobalUser23$", "Silaev", "Dmitriy", "Vyacheslavovich", 3, "MPB-904");

        String student1 = server.registerStudent(gson.toJson(registerStudent1));
        String student2 = server.registerStudent(gson.toJson(registerStudent2));
        String student3 = server.registerStudent(gson.toJson(registerStudent3));

        CourseActionRequest courseActionRequest1 = new CourseActionRequest(gson.fromJson(student1, TokenDtoResponse.class).getToken(), 1);
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest1));
        CourseActionRequest courseActionRequest2 = new CourseActionRequest(gson.fromJson(student2, TokenDtoResponse.class).getToken(), 1);
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest2));
        CourseActionRequest courseActionRequest3 = new CourseActionRequest(gson.fromJson(student3, TokenDtoResponse.class).getToken(), 2);
        server.subscribeStudentForCourse(gson.toJson(courseActionRequest3));

        StudentsEnrolledGivenCoursesRequest assembler = new StudentsEnrolledGivenCoursesRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), new int[]{1});
        StudentsEnrolledGivenCoursesRequest math = new StudentsEnrolledGivenCoursesRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), new int[]{2});
        StudentsEnrolledGivenCoursesRequest assemblerAndMath = new StudentsEnrolledGivenCoursesRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), new int[]{1, 2});

        String responseAssembler = server.getStudentsEnrolledInCourse(gson.toJson(assembler));
        String responseMath = server.getStudentsEnrolledInCourse(gson.toJson(math));
        String responseAssemblerAndMath = server.getStudentsEnrolledInCourse(gson.toJson(assemblerAndMath));

        assertAll(
                ()->assertEquals(2, gson.fromJson(responseAssembler, ArrayList.class).size()),
                ()->assertEquals(1, gson.fromJson(responseMath, ArrayList.class).size()),
                ()->assertEquals(3, gson.fromJson(responseAssemblerAndMath, ArrayList.class).size())
        );
    }

    @Test
    public void testGetStudentsEnrolledInCoursesException() {
        RegisterTeacherDtoRequest request = new RegisterTeacherDtoRequest("assembler", "GlobalUser23$", "Ashaev", "Igor", "Viktorovich", "Docent", "Security");
        String tokenTeacher = server.registerTeacher(gson.toJson(request));
        RegisterCourseDtoRequest registerCourseAssembler = new RegisterCourseDtoRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        server.registerCourse(gson.toJson(registerCourseAssembler));

        StudentsEnrolledGivenCoursesRequest wrongToken = new StudentsEnrolledGivenCoursesRequest("", new int[]{1});
        StudentsEnrolledGivenCoursesRequest wrongId = new StudentsEnrolledGivenCoursesRequest(gson.fromJson(tokenTeacher, TokenDtoResponse.class).getToken(), new int[]{2});

        String response1 = server.getStudentsEnrolledInCourse(gson.toJson(wrongToken));
        String response2 = server.getStudentsEnrolledInCourse(gson.toJson(wrongId));

        assertAll(
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_DATA_REQUEST.getErrorString() + "\"" + '}',response1),
                ()->assertEquals("{\"error\":"+ "\"" + ServerErrorCode.WRONG_ID_COURSE.getErrorString() + "\"" + '}', response2)
        );
    }

    @Test
    public void testNullRequest() {
        String expected = "{\"error\":"+ "\"" + ServerErrorCode.WRONG_NULL_REQUEST.getErrorString() + "\"" + '}';
        assertAll(
                ()->assertEquals(expected, server.registerTeacher(null)),
                ()->assertEquals(expected, server.registerCourse(null)),
                ()->assertEquals(expected, server.updateTeacher(null)),
                ()->assertEquals(expected, server.deleteCourse(null)),
                ()->assertEquals(expected, server.getListAllSignedStudents(null)),
                ()->assertEquals(expected, server.getStudentsEnrolledInCourse(null))
        );
    }

    @Test
    public void testWrongActionToken() {
        RegisterStudentDtoRequest registerStudent = new RegisterStudentDtoRequest("shnayderdanila", "GlobalUser23$", "Shnayder", "Danila", "Vladimirovich", 3, "MPB-904");
        String tokenStudent = server.registerStudent(gson.toJson(registerStudent));

        RegisterCourseDtoRequest registerCourse = new RegisterCourseDtoRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), "assembler", 3, 106);
        UpdateTeacherRequest updateTeacherRequest = new UpdateTeacherRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), "GlobalUser23$", "Assembler", "updatePassword23%", "Docent","Security");
        CourseActionRequest courseActionRequest = new CourseActionRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), 1);
        StudentsEnrolledGivenCoursesRequest assembler = new StudentsEnrolledGivenCoursesRequest(gson.fromJson(tokenStudent, TokenDtoResponse.class).getToken(), new int[]{1});

        String expected2 = "{\"error\":"+ "\"" + ServerErrorCode.WRONG_ACTION.getErrorString() + "\"" + '}';

        assertAll(
                ()->assertEquals(expected2, server.registerCourse(gson.toJson(registerCourse))),
                ()->assertEquals(expected2, server.updateTeacher(gson.toJson(updateTeacherRequest))),
                ()->assertEquals(expected2, server.deleteCourse(gson.toJson(courseActionRequest))),
                ()->assertEquals(expected2, server.getListAllSignedStudents(tokenStudent)),
                ()->assertEquals(expected2, server.getStudentsEnrolledInCourse(gson.toJson(assembler)))
        );
    }

    @Test
    public void testWrongToken() {
        RegisterCourseDtoRequest registerCourse = new RegisterCourseDtoRequest("tokenTeacher", "assembler", 3, 106);
        UpdateTeacherRequest updateTeacherRequest = new UpdateTeacherRequest("tokenTeacher", "GlobalUser23$", "Assembler", "updatePassword23%", "Docent","Security");
        CourseActionRequest courseActionRequest = new CourseActionRequest("tokenTeacher", 1);
        StudentsEnrolledGivenCoursesRequest assembler = new StudentsEnrolledGivenCoursesRequest("tokenTeacher", new int[]{1});

        String expected = "{\"error\":"+ "\"" + ServerErrorCode.WRONG_TOKEN.getErrorString() + "\"" + '}';

        assertAll(
                ()->assertEquals(expected, server.registerCourse(gson.toJson(registerCourse))),
                ()->assertEquals(expected, server.updateTeacher(gson.toJson(updateTeacherRequest))),
                ()->assertEquals(expected, server.deleteCourse(gson.toJson(courseActionRequest))),
                ()->assertEquals(expected, server.getListAllSignedStudents("{\"token\":\"token\"}")),
                ()->assertEquals(expected, server.getStudentsEnrolledInCourse(gson.toJson(assembler)))
        );
    }


}
