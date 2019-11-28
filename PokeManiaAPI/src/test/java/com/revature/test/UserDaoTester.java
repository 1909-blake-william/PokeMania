package com.revature.test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.UserDao;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class UserDaoTester {
	
	private final	String	TEST_USRNM	= "tester11",
							TEST_USRNM2	= "tester22",
							TEST_USRNM3 = "tester33",
							TEST_PSWD	= "pass",
							RM_TST_USR	= "DELETE FROM trainers WHERE trainer_name = ?",
							GET_ID		= "SELECT id FROM trainers WHERE trainer_name = ?";
	private 		UserDao dao 		= UserDao.currentImplementation;
	private 		Logger	log			= LogManager.getLogger(UserDaoTester.class);
	
	@Before
	public void addTestUser() {
		
		try {
			
			dao.addNewUser(new User(TEST_USRNM2, "Bob", "Man", 0, 0, 0, 0), TEST_PSWD);
			dao.addNewUser(new User(TEST_USRNM3, "Bob", "Man", 0, 0, 0, 0), TEST_PSWD);
			
		} catch(SQLException e) {
			
			log.error("Error: Failed to add test users in UserDaoTester\n" + e.getMessage());
			
		}
		
	}
	
	@Test
	public void registerUser() {
		
		try {
			
			assertTrue(dao.addNewUser(new User(TEST_USRNM2, "Bob", "Man", 0, 0, 0, 0), TEST_PSWD));
			
		} catch(SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
	}
	
	@Test
	public void fetchUser() {
		
		try {
			
			assertTrue(dao.fetchUser(TEST_USRNM) != null);
			
		} catch (SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
	}
	
	@Test
	public void getPassword() {
		
		try {
			
			assertTrue(TEST_PSWD.equals(dao.getPassword(TEST_USRNM)));
			
		} catch(SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
	}
	
	@Test
	public void addFriend() {
		
		try {
			
			assertTrue(dao.addFriend(TEST_USRNM2, TEST_USRNM3));
			
		} catch(SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
	}
	
	@Test
	public void getFriend() {
		
		PreparedStatement	ps	= null;
		ResultSet			rs	= null;
		int					id	= 0;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(GET_ID);
			ps.setString(1, TEST_USRNM2);
			rs = ps.executeQuery();
			
			if(rs.next())
				
				id = rs.getInt(1);
			
			else
				
				//Test failure
				assertTrue(false);
			
			assertTrue(TEST_USRNM3.equals(dao.getFriends(id)[0]));
			
		} catch(SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
	}
	
	@Test
	public void getFriend2() { //Swaps the two users around to make sure we can still get the friend pair
		
		PreparedStatement	ps	= null;
		ResultSet			rs	= null;
		int					id	= 0;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(GET_ID);
			ps.setString(1, TEST_USRNM3);
			rs = ps.executeQuery();
			
			if(rs.next())
				
				id = rs.getInt(1);
			
			else
				
				//Test failure
				assertTrue(false);
			
			assertTrue(TEST_USRNM2.equals(dao.getFriends(id)[0]));
			
		} catch(SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
	}
	
	@After
	public void removeTestUser() {
		
		PreparedStatement ps;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(RM_TST_USR);
			ps.setString(1, TEST_USRNM);
			ps.executeUpdate();
			
			ps = c.prepareStatement(RM_TST_USR);
			ps.setString(1, TEST_USRNM2);
			ps.executeUpdate();
			
			ps = c.prepareStatement(RM_TST_USR);
			ps.setString(1, TEST_USRNM3);
			ps.executeUpdate();
			
		} catch(SQLException e) {
			
			log.error("Error: Failed to remove test users in UserDaoTester\n" + e.getMessage());
			
		}
		
	}

}
