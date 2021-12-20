package net.thumbtack.school.courses.server.service;

import net.thumbtack.school.courses.server.JsonConverter;
import net.thumbtack.school.courses.server.dao.LaunchDao;
import net.thumbtack.school.courses.server.daoimpl.LaunchDaoImpl;
import net.thumbtack.school.courses.server.dto.response.ErrorDtoResponse;
import net.thumbtack.school.courses.server.exception.ServerException;

public class LaunchService {

    private final LaunchDao launcherDao;
    private final JsonConverter jsonConverter;

    public LaunchService(){
        launcherDao = new LaunchDaoImpl();
        jsonConverter = JsonConverter.getInstance();
    }

    public void startServer(String savedNameFile) {
        launcherDao.startServer(savedNameFile);
    }

    public String stop(String savedNameFile){
        try {
            launcherDao.stop(savedNameFile);
            return "";
        } catch (ServerException e) {
            return jsonConverter.serialize(new ErrorDtoResponse(e));
        }
    }
}
