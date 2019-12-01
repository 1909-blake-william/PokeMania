package com.revature.web;

import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LogManager.getLogger();
	private final Dispatcher dispatcherChain = DispatcherChain.getInstance();
}
