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
		return isCreateUser(request) || isAddFriend(request) || isGetFriends(request);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		if (isCreateUser(request)) {
			UserHandler.handleCreateUserRequest(request, response);
		} else if (isAddFriend(request)) {
			UserHandler.handleAddFriend(request, response);
		} else if (isGetFriends(request)) {

		}

	}

	public boolean isCreateUser(HttpServletRequest request) {
		return request.getMethod().equals("POST") && request.getRequestURI().equals("/PokeManiaAPI/api/createuser")
				&& request.getParameter("password") != null;
	}

	public boolean isAddFriend(HttpServletRequest request) {
		return request.getMethod().equals("POST") && request.getRequestURI().equals("/PokeManiaAPI/api/addfriend")
				&& request.getParameter("friendusername") != null;
	}

	public boolean isGetFriends(HttpServletRequest request) {
		return request.getMethod().equals("GET") && request.getRequestURI().equals("/PokeManiaAPI/api/getfriends")
				&& request.getParameter("userid") != null;
	}

}
