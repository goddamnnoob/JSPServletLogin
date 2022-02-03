package com.authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
	public DAO() {
	}
	Connection getConnection() throws ClassNotFoundException, SQLException {

		String dbDriver = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql:// localhost:3306/";
        String dbName = "authentication";
        String dbUsername = "root";
        String dbPassword = "root1234";
  
        Class.forName(dbDriver);
        Connection connection = DriverManager.getConnection(dbURL + dbName,
                                                     dbUsername, 
                                                     dbPassword);
		return connection;
	}
	boolean checkUsernameAvailability(String username) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM authentication WHERE username=?");
		preparedStatement.setString(1, username);
		ResultSet resultSet = preparedStatement.executeQuery();
		boolean result = resultSet.next();
		connection.close();
		return result;
	}
	int createNew(String username,String password) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO authentication (username, password) values(?,?)");
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		int result = preparedStatement.executeUpdate();
		connection.close();
		return result;
	}
	boolean authenticate(String username, String password) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM authentication WHERE username = ? AND password = ?");
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		ResultSet rs = preparedStatement.executeQuery();
		boolean result = rs.next();
		connection.close();
		return result;
	}
}
