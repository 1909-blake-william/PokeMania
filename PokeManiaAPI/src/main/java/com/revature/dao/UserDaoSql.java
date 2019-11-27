package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.revature.model.User;
import com.revature.util.ConnectionUtil;
import com.revature.util.Password;

/**
 * Handles the saving and fetching of user data with the remote SQL database on
 * AWS. Fetch a single user or save a new one.
 * 
 * @author Kristoffer Spencer
 */
public class UserDaoSql implements UserDao {
	
	private static final UserDaoSql	instance 		= new UserDaoSql();
	private static final Logger		logger			= LogManager.getLogger(UserDaoSql.class);
	private static final String		GET_USER_SQL 	= "SELECT * FROM trainers WHERE username = ?",
									INSERT_USER_SQL	= "INSERT INTO trainers (trainer_name, trainer_password, first_name, last_name, badges, wins, losses)"
													+ " VALUES (?, ?, ?, ?, ?, ?, ?)",
									FETCH_PSWD_SQL	= "SELECT trainer_password FROM trainers WHERE username = ?";

	/**
	 * Package private getter for the instance. The instance is meant to be retrieved
	 * from the interface so that if a different implementation is used to manage data,
	 * no code beyond the interface is needed to be modified.
	 * 
	 * @return
	 */
	static UserDaoSql getInstance() {
		return instance;
	}
	
	/**
	 * Fetch a user from the database by the username.
	 * 
	 * @exception SQLException thrown upon when there's a failure to read from the db or if the user doesn't exist
	 * @param username The user name of the user whose info is needed
	 * @return The user obj of all the user's data based on username
	 */
	public User fetchUser(String username) throws SQLException {
		
		User 				user;
		PreparedStatement	ps;
		ResultSet			rs;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(GET_USER_SQL);
			ps.setString(1,username);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				
				user = new User(rs.getString(2), rs.getString(4), rs.getString(5), rs.getInt(1), rs.getInt(6), rs.getInt(7), rs.getInt(8));
				
			} else
				
				// User not found
				user = null;
			
		} catch(SQLException e) {
			
			logger.warn("Error: Could not fetch user\n" + e.getMessage());
			throw e; //Exception is propagated for higher level exception handling
			
		}
		
		return user;
		
	}

	/**
	 * Register a new user to the system by passing in all the user info needed. Writes to the db
	 * 
	 * @param User object with the user's data. Password for the user for loggin in
	 * @return Whether the user was saved successfully
	 * @exception Thrown when there's an error writing to the db
	 */
	public boolean addNewUser(User user, String password) throws SQLException {
		//(trainer_name, trainer_password, first_name, last_name, badges, wins, losses)
		
		PreparedStatement	ps;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(INSERT_USER_SQL);
			ps.setString(1, user.getUsername());
			
			//My own custom password hashing thingie
			ps.setString(2, Password.transformPasswd(password, user.getUsername()));
			
			ps.setString(3, user.getFirstname());
			ps.setString(4, user.getLastname());
			ps.setInt(5, 0);
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			
			return ps.executeUpdate() == 1;
			
		} catch(SQLException e) {
			
			logger.warn("Error: Failed to add new user to DB\n" + e.getMessage());
			throw e; //Exception is propagated for higher level exception handling
			
		}
		
	}

	/**
	 * Get the password for the username to check if login is valid
	 * 
	 * @param The username that matches the password
	 * @return The password that matches with the username
	 * @exception Error if reading the db fails
	 */
	@Override
	public String getPassword(String username) throws SQLException {
		
		PreparedStatement	ps;
		ResultSet			rs;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(FETCH_PSWD_SQL);
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			if(rs.next())
				
				return Password.transformPasswd(rs.getString(1), username);
			
			else
				
				throw new SQLException("Unable to find user in db");
			
		} catch(SQLException e) {
			
			logger.warn("Error: Failed to retrieve password for user: " + username + "\n" + e.getMessage());
			throw e;
			
		}
		
	}

}
