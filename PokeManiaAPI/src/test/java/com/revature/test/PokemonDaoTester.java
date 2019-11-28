package com.revature.test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.PokemonDao;
import com.revature.model.Pokemon;
import com.revature.util.ConnectionUtil;

public class PokemonDaoTester {
	
	private static final 	PokemonDao	dao		= PokemonDao.currentImplimentation;
	private static final	Logger		log		= LogManager.getLogger(PokemonDaoTester.class);
	private static final	String		RM_POKE	= "DELETE FROM pokemon WHERE id = ?";
	private 				Pokemon		p1,
										p2,
										p3,
										p4,
										p5;
	private final			int			t1 		= -1,
										t2		= -2,
										t3		= -3;
	
	@Before
	public void setup() {
		
		//Only the first two args are important. id and t_id
		p1 = new Pokemon(-1, t1, 0, 0, 0, 0, 0, 0, null, null, null, null);
		p2 = new Pokemon(-2, t1, 0, 0, 0, 0, 0, 0, null, null, null, null);
		p3 = new Pokemon(-3, t1, 0, 0, 0, 0, 0, 0, null, null, null, null);
		p4 = new Pokemon(-4, t2, 0, 0, 0, 0, 0, 0, null, null, null, null);
		
		try {
			
			dao.savePokemon(p1);
			dao.savePokemon(p2);
			dao.savePokemon(p3);
			dao.savePokemon(p4);
			
		} catch(SQLException e) {
			
			log.error("Error: Failed to add test pokemon to db\n" + e.getMessage());
			
		}
		
	}
	
	@Test
	public void testFetch() {
		
		try {
			
			assertTrue(p1.equals(dao.fetchPokemon(p1.getId())));
			
		} catch(SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
	}

	@Test
	public void testTeamFetch() {
		
		Pokemon[]	pokemon		= {p1, p2};
		boolean		result		= false;
		
		try {

			if(!dao.saveTeam(pokemon))
				
				//Fail the test
				assertTrue(false);
			
			pokemon = dao.fetchTeam(t1);
			
			
		} catch(SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
		if(pokemon.length != 2)
			
			//Fail the test
			assertTrue(false);
		
		//check that the two pokemon came back out. Order might be different
		if(p1.equals(pokemon[0]))
			
			if(p2.equals(pokemon[1]))
				
				result = true;
		
			else ;
		
		else if(p2.equals(pokemon[0]))
			
			if(p1.equals(pokemon[1]))
				
				result = true;
		
		assertTrue(result);
		
	}

	@Test
	public void testFetchBox() {
		
		Pokemon[] pokemon	= null;
		
		try {

			pokemon = dao.fetchBox(t1);
			
		} catch(SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
		assertTrue(allThreeExist(pokemon));
		
	}
	
	@Test
	public void testSavePokemon() {
		
		p5	= new Pokemon(-5, t3, 0, 0, 0, 0, 0, 0, null, null, null, null);
		
		try {
			
			dao.savePokemon(p5);
			assertTrue(p5.equals(dao.fetchPokemon(-5)));
			
		} catch(SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
	}

	@Test
	public void testSaveTeam() {
		
		Pokemon[]	pokemon		= {p4};
		
		try {
			
			dao.saveTeam(pokemon);
			pokemon = dao.fetchTeam(t2);
			
		} catch(SQLException e) {
			
			//Fail the test
			assertTrue(false);
			
		}
		
		if(pokemon.length != 1)
			
			//Fail the test
			assertTrue(false);
		
		assertTrue(p4.equals(pokemon[0]));
		
	}
	
	private boolean allThreeExist(Pokemon[] pokemon) {
		
		boolean result = false;
		
		//Check for all three pokemon. Order might be different
		if(p1.equals(pokemon[0]))
			
			if(p2.equals(pokemon[1]))
				
				if(p3.equals(pokemon[2]))
					
					result = true;
		
				else ;
		
			else if(p3.equals(pokemon[1]))
				
				if(p2.equals(pokemon[2]))
					
					result = true;
		
				else;
		
			else;
		
		else if(p2.equals(pokemon[0]))
			
			if(p1.equals(pokemon[1]))
				
				if(p3.equals(pokemon[2]))
					
					result = true;
		
				else ;
		
			else if(p3.equals(pokemon[1]))
				
				if(p1.equals(pokemon[2]))
					
					result = true;
		
				else;
		
			else;
		
		else if(p3.equals(pokemon[0]))
			
			if(p2.equals(pokemon[1]))
				
				if(p1.equals(pokemon[2]))
					
					result = true;
		
				else ;
		
			else if(p1.equals(pokemon[1]))
				
				if(p2.equals(pokemon[2]))
					
					result = true;
		
				else;
		
			else;
		
		return result;
		
	}
	
	@After
	public void cleanUp() {
		
		PreparedStatement	ps;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(RM_POKE);
			ps.setInt(1, p1.getId());
			ps.addBatch();
			ps.clearParameters();
			ps.setInt(1, p2.getId());
			ps.addBatch();
			ps.clearParameters();
			ps.setInt(1, p3.getId());
			ps.addBatch();
			ps.clearParameters();
			ps.setInt(1, p4.getId());
			ps.addBatch();
			ps.clearParameters();
			ps.setInt(1, p5.getId());
			ps.addBatch();
			
			ps.executeBatch();
			
		} catch(SQLException e) {
			
			log.error("Error: Failed to clean up test pokemon\n" + e.getMessage());
			
		}
		
	}

}
