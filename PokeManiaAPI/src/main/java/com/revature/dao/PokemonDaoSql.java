package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.revature.model.Pokemon;
import com.revature.util.ConnectionUtil;

/**
 * Pokemon DAO which handles the pokemon data. Save a pokemon to your box,
 * save your whole team, fetch a single pokemon, fetch your team, fetch your box.
 * 
 * @author Kristoffer Spencer
 */
public class PokemonDaoSql implements PokemonDao {

	private final static	PokemonDaoSql	instance		= new PokemonDaoSql();
	private final static	Logger			log				= LogManager.getLogger(PokemonDaoSql.class);
	
	private final 			String			GET_ONE_SQL		= "SELECT * FROM pokemon WHERE id = ?",
											GET_ALL_SQL		= "SELECT * FROM pokemon WHERE t_id = ?",
											GET_TEAM_SQL	= "SELECT * FROM teams WHERE t_id = ?",
											SAVE_ONE_SQL	= "INSERT INTO pokemon (data) VALUES (data)",
											SAVE_TEAM_SQL	= "INSERT INTO teams (t_id, p_id) VALUES (?, ?)",
											CLEAR_TEAM_SQL	= "DELETE FROM team WHERE t_id = ?";
											
	
	/**
	 * Fetch the singleton instance of the DAO
	 * 
	 * @return the single instance of this DAo
	 */
	public static PokemonDaoSql getInstance() {
		
		return instance;
		
	}

	/**
	 * Fetch a single pokemon from the user's box
	 * 
	 * @param id of the pokemon
	 * @return The pokemon
	 * @exception Throws a SQLException if an issue happens when talking to the db
	 */
	@Override
	public Pokemon fetchPokemon(int id) throws SQLException {
		
		PreparedStatement	ps;
		ResultSet			rs;
		Pokemon				pokemon		= null;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(GET_ONE_SQL);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
		} catch(SQLException e) {
			
			log.warn("Error: Failed to fetch single pokemon\n" + e.getMessage());
			throw e;
			
		}
		
		if(rs.next()) {
			
			//Make new pokemon
			//Need table data
			
		}
		
		return pokemon;
		
	}

	/**
	 * Fetch the saved team of the user
	 * 
	 * @param The user's id
	 * @return The last team the user had saved
	 * @exception Throws a SQLException if an issue happens when talking to the db
	 */
	@Override
	public Pokemon[] fetchTeam(int userID) throws SQLException {
		
		PreparedStatement	ps;
		ResultSet			rs;
		List<Pokemon>		pokemon		= new ArrayList<>();
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(GET_TEAM_SQL);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			
		} catch(SQLException e) {
			
			log.warn("Error: Failed to fetch team\n" + e.getMessage());
			throw e;
			
		}
		
		while(rs.next())
	
			//Need table data
			pokemon.add(new Pokemon(0, 0, 0, 0, 0, 0, 0, 0, null, null, null, null));
		
		return (Pokemon[]) pokemon.toArray();
				
	}

	/**
	 * Fetch all the pokemon the user owns
	 * 
	 * @param The user's id
	 * @return All the pokemon the user owns
	 * @exception Throws a SQLException if an issue happens when talking to the db
	 */
	@Override
	public Pokemon[] fetchBox(int userID) throws SQLException {
		
		PreparedStatement	ps;
		ResultSet			rs;
		List<Pokemon>		pokemon		= new ArrayList<>();
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(GET_ALL_SQL);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			
		} catch(SQLException e) {
			
			log.warn("Error: Failed to fetch all box pokemon\n" + e.getMessage());
			throw e;
			
		}
		
		while(rs.next())
			
			//Missing table data
			pokemon.add(new Pokemon(0, 0, 0, 0, 0, 0, 0, 0, null, null, null, null));
		
		return (Pokemon[]) pokemon.toArray();
		
	}

	/**
	 * Save a single pokemon to the db
	 * 
	 * @param The pokemon to save
	 * @return Whether save was successful
	 * @exception Throws a SQLException if an issue happens when talking to the db
	 */
	@Override
	public boolean savePokemon(Pokemon pokemon) throws SQLException {
		
		PreparedStatement	ps;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(SAVE_ONE_SQL);
			//ps.set data
			return ps.executeUpdate() == 1;
			
		} catch(SQLException e) {
			
			log.warn("Error: Failed to save single pokemon\n" + e.getMessage());
			throw e;
			
		}
		
	}

	/**
	 * Saves the current team of the user
	 * 
	 * @param The team to save
	 * @return Whether writing all was successful
	 * @exception Throws a SQLException if an issue happens when talking to the db
	 */
	@Override
	public boolean saveTeam(Pokemon[] pokemon) throws SQLException {
		
		PreparedStatement	ps;
		int[]				results;
		
		clearTeam(pokemon[0].getTrainerId());
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(SAVE_TEAM_SQL);
			
			//Add each pokemon to a batch for an efficient whole team write
			for(Pokemon poke : pokemon) {
				
				ps.setInt(1, poke.getTrainerId());
				ps.setInt(2, poke.getId());
				ps.addBatch();
				ps.clearParameters();
				
			}

			results = ps.executeBatch();
			
		} catch(SQLException e) {
			
			log.warn("Error: Failed to fetch single pokemon\n" + e.getMessage());
			throw e;
			
		}
		
		//Check if all successful
		for(int i : results)
			
			if(i != 1)
				
				return false;
		
		return true;
		
	}
	
	/**
	 * Called before saving a team to wipe the state so that it can be overwritten
	 * 
	 * @param userID The id of the logged in user
	 * @throws SQLException Thrown if the DB has an issue
	 */
	private void clearTeam(int userID) throws SQLException {
		
		PreparedStatement	ps;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(CLEAR_TEAM_SQL);
			ps.setInt(1, userID);
			ps.executeUpdate();
			
		} catch(SQLException e) {
			
			log.warn("Error: Failed to clear last saved team\n" + e.getMessage());
			throw e;
			
		}
		
	}
	
}
