package by.epam.training.studentcourses.dao.impl.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPoolImpl implements ConnectionPool {
	private String dbUrl;
	private String username;
	private String password;
	private int initialPoolSize;
	private boolean autoCommit = false;
    private BlockingQueue<Connection> connectionPool;
    private List<Connection> usedConnections;
    
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }
    
    @Override
    public void setParams(String url, String username, String password, int initialPoolSize) {
    	this.dbUrl = url;
    	this.username = username;
    	this.password = password;
    	this.initialPoolSize = initialPoolSize;
    	
    }
    
    @Override
    public void init() throws SQLException {
		try {
			Driver mySqlDriver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(mySqlDriver);	
		} catch (SQLException e) {
			throw new SQLException(e);
		}
    	if (dbUrl == null || username == null || password == null) {
    		throw new SQLException();
    	}
		usedConnections = new ArrayList<>();
		connectionPool = new ArrayBlockingQueue<>(initialPoolSize);
		Connection connection;
		for (int i = 0; i < initialPoolSize; i++) {
			connection = createConnection();
			connection.setAutoCommit(autoCommit);
			connectionPool.add(connection);
		}
	}

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = null;
		try {
			connection = connectionPool.take();
		} catch (InterruptedException e) {
			close();
			Thread.currentThread().interrupt();
		}
        usedConnections.add(connection);
        return connection;
    }
    
    @Override
    public boolean releaseConnection(Connection connection) {
    	if (connection == null) return false;
    	if (usedConnections.remove(connection)) {
    		connectionPool.add(connection);
    		return true;
    	}
    	return false;
    }

	@Override
	public void close() throws SQLException {
		try {
			for (int i = 0; i < connectionPool.size(); i ++) {
				connectionPool.take().close();
			}
			for (int i = 0; i < usedConnections.size(); i ++) {
				usedConnections.get(i).close();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
