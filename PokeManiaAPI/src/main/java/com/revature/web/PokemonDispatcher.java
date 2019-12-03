package com.revature.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.handler.PokemonHandler;

public class PokemonDispatcher implements Dispatcher {

	private final Logger logger = LogManager.getLogger(getClass());

	// Constructor with the package-private access
	PokemonDispatcher() {
	}

	@Override
	public boolean supports(HttpServletRequest request) {
		return 	isViewAllUsersPokemon(request) ||
				isCatchPokemon(request);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		if(isViewAllUsersPokemon(request)) {
			PokemonHandler.handleViewAllUsersPokemon(request, response);
		} else if (isCatchPokemon(request)) {
			PokemonHandler.handleCatchPokemon(request, response);
		} else if (isReleasePokemon(request)) {
			
		} 
	}

	public boolean isViewAllUsersPokemon(HttpServletRequest request) {
		return request.getMethod().equals("GET") && request.getRequestURI().equals("/PokeManiaAPI/pokemon");
	}

	public boolean isCatchPokemon(HttpServletRequest request) {
		return request.getMethod().contentEquals("POST") && request.getRequestURI().equals("/PokeManiaAPI/pokemon");
	}

	public boolean isReleasePokemon(HttpServletRequest request) {
		return request.getMethod().contentEquals("DELETE") && request.getRequestURI().equals("PokeManiaAPI/pokemon");
	}

}
