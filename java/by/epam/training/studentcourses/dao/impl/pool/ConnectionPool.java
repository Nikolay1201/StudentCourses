package by.epam.training.studentcourses.dao.impl.pool;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
	void setParams(String url, String username, String password, int initialPoolSize);
	void init() throws SQLException;
    Connection getConnection() throws SQLException;
    boolean releaseConnection(Connection connection);
	void close() throws SQLException;
}
