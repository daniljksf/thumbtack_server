package net.thumbtack.school.courses.server.daoimpl;

import net.thumbtack.school.courses.server.dao.LaunchDao;
import net.thumbtack.school.courses.server.database.Database;

public class LaunchDaoImpl implements LaunchDao {

    private Database database;

    @Override
    public void startServer(String savedNameFile) {
        database = Database.startDatabase(savedNameFile);
    }

    @Override
    public void stop(String savedNameFile) {
        database.stop(savedNameFile);
    }
}
