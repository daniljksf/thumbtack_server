package net.thumbtack.school.courses.server.service;

import net.thumbtack.school.courses.server.JsonConverter;
import net.thumbtack.school.courses.server.Validator;
import net.thumbtack.school.courses.server.dao.UserDao;
import net.thumbtack.school.courses.server.daoimpl.UserDaoImpl;
import net.thumbtack.school.courses.server.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.courses.server.dto.request.TokenDtoRequest;
import net.thumbtack.school.courses.server.dto.response.ErrorDtoResponse;
import net.thumbtack.school.courses.server.dto.response.TokenDtoResponse;
import net.thumbtack.school.courses.server.exception.ServerErrorCode;
import net.thumbtack.school.courses.server.exception.ServerException;
import net.thumbtack.school.courses.server.model.Student;
import net.thumbtack.school.courses.server.model.Teacher;
import net.thumbtack.school.courses.server.model.User;

public class UserService {

    private UserDao userDao;
    private final JsonConverter jsonConverter;
    public UserService(){
        userDao = new UserDaoImpl();
        jsonConverter = JsonConverter.getInstance();
    }

    public String login(String jsonRequest){
        try{
            LoginUserDtoRequest userDtoRequest = jsonConverter.getClassFromJson(jsonRequest, LoginUserDtoRequest.class);
            Validator.validateRequest(userDtoRequest);

            User user = userDao.getUserByLogin(userDtoRequest.getLogin());
            if(user == null){
                throw new ServerException(ServerErrorCode.INCORRECT_LOGIN);
            }
            if(!user.getPassword().equals(userDtoRequest.getPassword())){
                throw new ServerException(ServerErrorCode.WRONG_PASSWORD);
            }

            return jsonConverter.serialize(new TokenDtoResponse(userDao.login(user)));
        }
        catch (ServerException e){
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }

    }


    public String logout(String jsonRequest){

        try{
            TokenDtoRequest request = jsonConverter.getClassFromJson(jsonRequest, TokenDtoRequest.class);
            Validator.validateRequest(request);

            userDao.logout(
                    getUserByToken(request.getToken())
            );

            return "";
        }
        catch (ServerException e){
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }

    }

    public String exit(String jsonRequest){
        try{
            TokenDtoRequest request = jsonConverter.getClassFromJson(jsonRequest, TokenDtoRequest.class);
            Validator.validateRequest(request);
            User user = getUserByToken(request.getToken());
            if(user instanceof Teacher){
                userDao.exitTeacher((Teacher) user);
            }
            else {
                userDao.exitStudent((Student) user);
            }
            return "";
        }
        catch (ServerException e){
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }

    }

    private User getUserByToken(String token) throws ServerException {
        User user = userDao.getUserByToken(token);
        if(user == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
        return user;
    }
}
