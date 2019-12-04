package com.revature.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.handler.UserHandler;

public class UserDispatcher implements Dispatcher {

	// Constructor with the package-private access
	UserDispatcher() {
	}

	@Override
	public boolean supports(HttpServletRequest request) {
		return isCreateUser(request) || isAddFriend(request);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		if (isCreateUser(request)) {
			UserHandler.handleCreateUserRequest(request, response);
		} else if (isAddFriend(request)) {
			
		}

	}

	public boolean isCreateUser(HttpServletRequest request) {
		return request.getMethod().equals("POST") && request.getRequestURI().equals("/PokeManiaAPI/api/createuser")
				&& request.getParameter("password") != null;
	}
	
	public boolean isAddFriend(HttpServletRequest request) {
		return request.getMethod().equals("POST") && request.getRequestURI().equals("/PokeManiaAPI/api/addfriend");
	}
	

}
