package algorath.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import algorath.user.model.User;

/*This class provides CRUD database operations for the users in the database*/

public class UserDAO {
	private String jdbcURL = "jdbc:mysql://127.0.0.1:3306/algorath_test";
	private String jdbcUser = "Kosta";
	private String jdbcPassword = "1234";
			
	private static final String INSERT_USERS = "INSERT INTO users" + "(id, name) VALUES " + " (?, ?);";
	//private static final String SELECT_USER_BY_ID = "select * from users where id = ?;";
	//private static final String SELECT_ALL_USERS = "select * from users;";
	private static final String DELETE_USER = "delete from users where id = ?;";
	private static final String UPDATE_USER = "update users set name = ?, where id = ?;";
	
	protected Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public void addConnection(User u1, User u2) {
		if(u1.getId() == u2.getId())	
			throw new Error("Cannot connect an user with himself");
		else {
			try(Connection connection = getConnection();
					PreparedStatement prepStatement = connection.prepareStatement(
							"INSERT INTO connection VALUES('" + u1.getId() + "', '" + u2.getId() + "');")){
				prepStatement.setInt(1, u1.getId());
				prepStatement.setInt(2, u2.getId());
				prepStatement.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteConnection(User u1, User u2) {
		if(u1.getId() == u2.getId())	
			throw new Error("Cannot connect an user with himself");
		else {
			try(Connection connection = getConnection();
					PreparedStatement prepStatement = connection.prepareStatement(
							"INSERT INTO connection VALUES('" + u1.getId() + "', '" + u2.getId() + "');")){
				prepStatement.setInt(1, u1.getId());
				prepStatement.setInt(2, u2.getId());
				prepStatement.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insertUser(User user) {
		try(Connection connection = getConnection();
				PreparedStatement prepStatement = connection.prepareStatement(INSERT_USERS)){
			prepStatement.setInt(1, user.getId());
			prepStatement.setString(2, user.getName());
			prepStatement.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateUser(User user) {
		boolean updated = false;
		
		try(Connection connection = getConnection();
				PreparedStatement prepStatement = connection.prepareStatement(UPDATE_USER)){
			prepStatement.setString(1, user.getName());
			prepStatement.executeUpdate();
			
			updated = prepStatement.executeUpdate() > 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return updated;
	}
	
	public User selectUser(int id) {
		User user = null;
		
		try(Connection connection = getConnection();
				PreparedStatement prepStatement = connection.prepareStatement("select * from users where id = " + id + ";")){
			prepStatement.setInt(1, id);
			//prepStatement.setString(2, user.getName());
			prepStatement.executeUpdate();
			System.out.println(prepStatement);
			
			ResultSet resultSet = prepStatement.executeQuery();
			
			while(resultSet.next()) {
				String name = resultSet.getString("name");
				user = new User(id, name);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	public List<User> selectAllUsers(){
		List<User> users = new ArrayList<>();

		try(Connection connection = getConnection();
				PreparedStatement prepStatement = connection.prepareStatement("select * from users;")){
			System.out.println(prepStatement);
			
			ResultSet rs = prepStatement.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				users.add(new User(id, name));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public boolean deleteUser(int id) throws SQLException{
		boolean deleted = false;
		
		try(Connection connection = getConnection();
				PreparedStatement prepStatement = connection.prepareStatement(DELETE_USER)){
			
			prepStatement.setInt(1, id);
			
			deleted = prepStatement.executeUpdate() > 0;
		}
		return deleted;
	}
	
	
}
