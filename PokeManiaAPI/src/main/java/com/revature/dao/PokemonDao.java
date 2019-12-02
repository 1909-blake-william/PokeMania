package com.revature.dao;

import java.sql.SQLException;

import com.revature.model.Pokemon;

/**
 * An interface that defines how the pokemon dao will work and what its jobs are
 * and how to use this dao
 * 
 * @author Kristoffer Spencer
 */
public interface PokemonDao {

	PokemonDao	currentImplimentation	= PokemonDaoSql.getInstance();
	
	Pokemon fetchPokemon(int id) throws SQLException;
	Pokemon[] fetchTeam(int userID) throws SQLException;
	Pokemon[] fetchBox(int userID) throws SQLException;
	boolean savePokemon(Pokemon pokemon) throws SQLException;
	boolean saveTeam(Pokemon[] pokemon) throws SQLException;
	boolean releasePoke(Pokemon pokemon) throws SQLException;
	
}
