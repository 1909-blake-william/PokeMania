package com.revature.dao;

import java.sql.SQLException;

import com.revature.model.Pokemon;

public interface PokemonDao {

	PokemonDao	currentImplimentation	= PokemonDaoSql.getInstance();
	
	Pokemon fetchPokemon(int id) throws SQLException;
	Pokemon[] fetchTeam(int userID) throws SQLException;
	Pokemon[] fetchBox(int userID) throws SQLException;
	boolean savePokemon(Pokemon pokemon) throws SQLException;
	boolean saveTeam(Pokemon[] pokemon) throws SQLException;
	boolean releasePoke(Pokemon pokemon) throws SQLException;
	
}
