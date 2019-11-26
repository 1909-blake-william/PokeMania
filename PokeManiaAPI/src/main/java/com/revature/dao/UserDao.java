package com.revature.dao;

import java.sql.SQLException;

import com.revature.model.User;

/**
 * Interface to use the underlining SQL dao to manage user data
 * 
 * @author Kristoffer Spencer
 */
public interface UserDao {
	
	//Please access dao from here with UserDao.currentImplementation
	UserDao	currentImplementation = UserDaoSql.getInstance();
	
	User 	fetchUser(String username) throws SQLException;
	boolean	addNewUser(User user, String password) throws SQLException;
	String	getPassword(String username) throws SQLException;
	
}
