package com.revature.handler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.UserDao;
import com.revature.dao.UserDaoSql;
import com.revature.model.User;
import com.revature.util.Json;

public class UserHandler {

	private static final UserDao dao = UserDaoSql.currentImplementation;
	private static final Logger logger = LogManager.getLogger(UserHandler.class);
	
	public static void handleCreateUserRequest(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inside Create User Request");
		
		try {
			User user = (User) Json.read(request.getInputStream(), User.class);
			String password = request.getParameter("password");
			boolean wasSuccessful = dao.addNewUser(user, password);
			if(wasSuccessful) {
				response.setStatus(HttpServletResponse.SC_CREATED);
				logger.info("Successfully Added New User");
				return;
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				logger.info("New User NOT Created. Bad Request");
				return;
			}
			
		} catch (IOException | SQLException e) {
			logger.warn("Exception occured: {}", e);
		}
	}
	
}
