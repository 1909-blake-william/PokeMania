package com.revature.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.revature.util.Json;

import com.revature.dao.UserDao;
import com.revature.model.LoginForm;

public class AuthDispatcher implements Dispatcher {
	
	private final UserDao userDao = UserDao.currentImplementation;
	private final Logger logger = LogManager.getLogger(getClass());
	
	AuthDispatcher() {}

	@Override
	public boolean supports(HttpServletRequest request) {
		return isForLogin(request) || isForUserInfo(request);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			LoginForm form = (LoginForm) Json.read(request.getInputStream(), LoginForm.class)
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isForLogin(HttpServletRequest request) {
		return request.getMethod().equals("POST") && request.getRequestURI().equals("");
	}

	private boolean isForUserInfo(HttpServletRequest request) {
		return request.getMethod().equals("GET") && request.getRequestURI().equals("");
	}
}
