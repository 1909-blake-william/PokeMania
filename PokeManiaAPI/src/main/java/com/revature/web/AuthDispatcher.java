package com.revature.web;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.revature.util.Json;

import com.revature.dao.UserDao;
import com.revature.model.LoginForm;
import com.revature.model.User;

public class AuthDispatcher implements Dispatcher {

	private final UserDao userDao = UserDao.currentImplementation;
	private final Logger logger = LogManager.getLogger(getClass());

	AuthDispatcher() {
	}

	@Override
	public boolean supports(HttpServletRequest request) {
		return isForLogin(request) || isForUserInfo(request);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {

			// Converting request body (request.getInputStream()
			// INTO Object of type LoginForm (found in com.revature.model.LoginForm)
			LoginForm form = (LoginForm) Json.read(request.getInputStream(), LoginForm.class);

			//FIX THIS LOGIN METHOD!
			User info = userDao.login(form.getUsername(), form.getPassword());

			if (info != null) {
				response.setContentType(Json.CONTENT_TYPE);
				Cookie cookie = new Cookie("currentUser", info.getUsername());
				cookie.setPath("/PokeMania/api");
				response.addCookie(cookie);
				
				response.getOutputStream().write(Json.write(info));
				return;
			} else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isForLogin(HttpServletRequest request) {
		return request.getMethod().equals("POST") && request.getRequestURI().equals("/PokeManiaAPI/api/login");
	}

	private boolean isForUserInfo(HttpServletRequest request) {
		return request.getMethod().equals("GET") && request.getRequestURI().equals("/PokeManiaAPI/api/info");
	}
}
