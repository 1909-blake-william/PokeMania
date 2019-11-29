package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.model.User;
import com.revature.util.ConnectionUtil;
import com.revature.util.Password;

/**
 * Handles the saving and fetching of user data with the remote SQL database on
 * AWS. Fetch a single user or save a new one. Set a friend or fetch all friends.
 * Also fetch the password of a user for comparison for login.
 * 
 * @author Kristoffer Spencer
 */
public class UserDaoSql implements UserDao {
	
	private static final UserDaoSql	instance 		= new UserDaoSql();
	private static final Logger		logger			= LogManager.getLogger(UserDaoSql.class);
	private static final String		GET_USER_SQL 	= "SELECT * FROM trainers WHERE trainer_name = ?",
									INSERT_USER_SQL	= "INSERT INTO trainers (trainer_name, trainer_password, first_name, last_name, badges, wins, losses)"
													+ " VALUES (?, ?, ?, ?, ?, ?, ?)",
									INSERT_TEST_SQL	= "INSERT INTO trainers VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
									FETCH_PSWD_SQL	= "SELECT trainer_password FROM trainers WHERE trainer_name = ?",
									FETCH_FRND_IDS	= "SELECT * FROM friends WHERE trainer_id1 = ? OR trainer_id2 = ?",
									FETCH_FRND_NMS	= "SELECT trainer_name FROM trainers WHERE trainer_id = ",
									ADD_FRND		= "INSERT INTO friends VALUES ((SELECT trainer_id FROM trainers WHERE trainer_name = ?), "
													+ "(SELECT trainer_id FROM trainers WHERE trainer_name = ?))";

	/**
	 * Package private getter for the instance. The instance is meant to be retrieved
	 * from the interface so that if a different implementation is used to manage data,
	 * no code beyond the interface is needed to be modified.
	 * 
	 * @return
	 */
	static public UserDaoSql getInstance() {
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
		
		User 				user	= null;
		PreparedStatement	ps		= null;
		ResultSet			rs		= null;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(GET_USER_SQL);
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			if(rs.next())
				
				user = new User(rs.getString(2), rs.getString(4), rs.getString(5), rs.getInt(1), rs.getInt(6), rs.getInt(7), rs.getInt(8));
				
			
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
		
		PreparedStatement	ps	= null;
		
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
	 * TEST VERSION allows to set the id in db
	 * Register a new user to the system by passing in all the user info needed. Writes to the db
	 * 
	 * @param User object with the user's data. Password for the user for loggin in
	 * @return Whether the user was saved successfully
	 * @exception Thrown when there's an error writing to the db
	 */
	public boolean add_TEST_newUser(User user, String password) throws SQLException {
		//(trainer_name, trainer_password, first_name, last_name, badges, wins, losses)
		
		PreparedStatement	ps	= null;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(INSERT_TEST_SQL);
			ps.setInt(1, user.getId());
			ps.setString(2, user.getUsername());
			ps.setString(3, Password.transformPasswd(password, user.getUsername()));
			ps.setString(4, user.getFirstname());
			ps.setString(5, user.getLastname());
			ps.setInt(6, user.getBadges());
			ps.setInt(7, user.getWins());
			ps.setInt(8, user.getLosses());
			
			return ps.executeUpdate() == 1;
			
		} catch(SQLException e) {
			
			logger.warn("Error: Failed to add new user to DB\n" + e.getMessage());
			throw e; //Exception is propagated for higher level exception handling
			
		}
		
	}
	
	/**
	 * Method to add an entry into the friends table to forever mark a trainer and friend as friends
	 * 
	 * @param username: The name of the logged in user
	 * @param friendName: The name of the friend to add
	 * @return Whether it was successful
	 * @exception SQLException thrown if issue talking with db
	 */
	public boolean	addFriend(String username, String friendName) throws SQLException {
		
		PreparedStatement	ps	= null;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(ADD_FRND);
			ps.setString(1, username);
			ps.setString(2, friendName);
			
			return ps.executeUpdate() == 1;
			
		} catch(SQLException e) {
			
			logger.warn("Error: Could not add friend!\n" + e.getMessage());
			throw e;
			
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
		
		PreparedStatement	ps	= null;
		ResultSet			rs	= null;
		
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

	/**
	 * Get a list of the logged in user's friends' usernames
	 * 
	 * @param Takes the ID of the logged in user to find the friends of said user
	 * @return Returns a list of the friends' names
	 * @exception Throws a SQLException if there's an issue with fetching from DB
	 */
	@Override
	public String[] getFriends(int userID) throws SQLException {
		
		LinkedList<Integer>		friendIDs	= getFriendIDs(userID);
		List<String>			friendNames	= null;
		
		if(friendIDs.size() == 0) //No friends
			
			return null;
		
		friendNames = getFriendNames(createIDsString(friendIDs));
		
		return friendNames.toArray(new String[0]);
		
	}
	
	/**
	 * Helper method to break up code. Takes the user's ID and gets the list of their
	 * friends' ids
	 * 
	 * @param The id of the logged in user
	 * @return A LinkedList of the ids of the friends. Works as a queue
	 * @throws SQLException Thrown if there's an issue communicating with the db
	 */
	private LinkedList<Integer> getFriendIDs(int userID) throws SQLException {
		
		PreparedStatement	ps			= null;
		ResultSet			rs			= null;
		LinkedList<Integer>	friendIDs	= new LinkedList<>();
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(FETCH_FRND_IDS);
			ps.setInt(1, userID);
			ps.setInt(2, userID);
			rs = ps.executeQuery();
			
			//Gather up the ids of the friends. Value pairs can have the friend
			//in either column
			while(rs.next()) {
				
				int id1 = rs.getInt(1),
					id2 = rs.getInt(2);
				
				if(id1 == userID)
					
					friendIDs.add(id2);
				
				else
					
					friendIDs.add(id1);
				
			}
			
			return friendIDs;
			
		} catch(SQLException e) {
			
			logger.warn("Error: Failed to fetch friends\n" + e.getMessage());
			throw e;
			
		}
		
	}
	
	/**
	 * Helper method to cut down the size of methods. Uses the list of friend IDs to make
	 * a string to finish the query WHERE clause
	 * 
	 * @param LinkedList of friendIDs
	 * @return A string that of the ids. Ex: "1 OR 3 OR 2"
	 */
	private String createIDsString(LinkedList<Integer> friendIDs) {
		
		StringBuilder	sb	= new StringBuilder("");
		
		//Create the OR list for the query
		sb.append(friendIDs.removeFirst());
		
		while(friendIDs.size() > 0) {
			
			sb.append(" OR ");
			sb.append(friendIDs.removeFirst());
			
		}
		
		return sb.toString();
		
	}
	
	/**
	 * Helper method to break up code. Takes in the formated id string and retreives
	 * the list of the friends' usernames
	 * 
	 * @param String of ids ex: "1 OR 3 OR 2"
	 * @return An ArrayList of the friends' usernames
	 * @throws SQLException Thrown if there's an issue talking to the DB
	 */
	private List<String> getFriendNames(String idList) throws SQLException {
		
		PreparedStatement	ps;
		ResultSet			rs;
		List<String>		names	= new ArrayList<>();
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(FETCH_FRND_NMS + idList);
			rs = ps.executeQuery();
			
			while(rs.next())
				
				names.add(rs.getString(1));
			
			return names;
			
		} catch(SQLException e) {
			
			logger.warn("Error: Failed to fetch friends\n" + e.getMessage());
			throw e;
			
		}
		
	}

}
