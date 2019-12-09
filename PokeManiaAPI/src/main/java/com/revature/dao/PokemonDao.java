package com.revature.dao;

import java.sql.SQLException;

import com.revature.model.Pokemon;

public interface PokemonDao {

	PokemonDao	currentImplimentation	= PokemonDaoSql.getInstance();
	
	Pokemon fetchPokemon(int id) throws SQLException;
	Pokemon[] fetchTeam(int userID) throws SQLException;
	Pokemon[] fetchBox(int userID) throws SQLException;
	boolean savePokemon(Pokemon pokemon) throws SQLException;
	boolean saveTeam(int userID, int[] pokeTeam) throws SQLException;
	boolean releasePoke(int pokemonID) throws SQLException;
	
}
