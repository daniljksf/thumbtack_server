package net.thumbtack.school.courses.server.dao;

import net.thumbtack.school.courses.server.exception.ServerException;

public interface LaunchDao {

    void startServer(String savedNameFile);

    void stop(String savedNameFile) throws ServerException;
}
