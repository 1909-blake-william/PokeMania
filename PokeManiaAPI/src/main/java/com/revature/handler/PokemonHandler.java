package com.revature.handler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.PokemonDao;
import com.revature.dao.PokemonDaoSql;
import com.revature.model.Pokemon;
import com.revature.util.Json;

public class PokemonHandler {

	private static final PokemonDao dao = PokemonDaoSql.currentImplimentation;
	private static final Logger logger = LogManager.getLogger(PokemonHandler.class);

	public static void handleViewAllUsersPokemon(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		logger.warn("User ID: {}", userId);
		try {
			Pokemon[] pokemon = dao.fetchBox(Integer.parseInt(userId));
			if (pokemon != null) {
				try {
					response.setContentType(Json.CONTENT_TYPE);
					response.getOutputStream().write(Json.write(pokemon));
					return;
				} catch (IOException e) {
					logger.warn("Failed to write to Response Body: {}", e);
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void handleViewAllUsersTeam(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		logger.warn("User ID: {}", userId);
		try {
			Pokemon[] pokemon = dao.fetchTeam(Integer.parseInt(userId));
			if (pokemon != null) {
				try {
					response.setContentType(Json.CONTENT_TYPE);
					response.getOutputStream().write(Json.write(pokemon));
					return;
				} catch (IOException e) {
					logger.warn("Failed to write to Response Body: {}", e);
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void handleUpdateUsersTeam(HttpServletRequest request, HttpServletResponse response) {
		try {
			Pokemon[] pokemon = (Pokemon[]) Json.read(request.getInputStream(), Pokemon.class);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void handleCatchPokemon(HttpServletRequest request, HttpServletResponse response) {
		try {
			Pokemon pokemon = (Pokemon) Json.read(request.getInputStream(), Pokemon.class);
			boolean wasSuccessful = dao.savePokemon(pokemon);
			if (wasSuccessful) {
				response.setStatus(HttpServletResponse.SC_CREATED);
				return;
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		} catch (IOException e) {
			logger.warn("Failed to read the Request Body: {}", e);
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void handleReleasePokemon(HttpServletRequest request, HttpServletResponse response) {
		try {
			Pokemon pokemon = (Pokemon) Json.read(request.getInputStream(), Pokemon.class);
			System.out.println(pokemon.toString());
			boolean wasSuccessful = dao.releasePoke(pokemon);
			if (wasSuccessful) {
				response.setStatus(HttpServletResponse.SC_CREATED);
				return;
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		} catch (IOException e) {
			logger.warn("Failed to read the Request Body: {}", e);
			e.printStackTrace();
		} catch (SQLException e) {
			logger.warn("Failed to complete SQL operation: {}", e);
			e.printStackTrace();
		}
	}

}
