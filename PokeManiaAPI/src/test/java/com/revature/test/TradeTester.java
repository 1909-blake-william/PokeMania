package com.revature.test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.TradeStatus;
import com.revature.dao.PokemonDaoSql;
import com.revature.dao.TradeDaoSql;
import com.revature.dao.UserDaoSql;
import com.revature.model.Pokemon;
import com.revature.model.Trade;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class TradeTester {
	
	private static 			Pokemon			p1 		= new Pokemon(-1, -1, 0, 0, 0, 0, 0, 0, "Fire", null, "img", "img"),
											p2 		= new Pokemon(-2, -2, 0, 0, 0, 0, 0, 0, "Fire", null, "img", "img"),
											p3		= new Pokemon(-3, -3, 0, 0, 0, 0, 0, 0, "Fire", null, "img", "img"),
											p4		= new Pokemon(-4, -4, 0, 0, 0, 0, 0, 0, "Fire", null, "img", "img");
	private static 			User			u1 		= new User("Tester11", null, null, -1, 0, 0, 0),
											u2 		= new User("Tester22", null, null, -2, 0, 0, 0),
											u3		= new User("Tester33", null, null, -3, 0, 0, 0),
											u4		= new User("Tester44", null, null, -4, 0, 0, 0),
											u5		= new User("Tester55", null, null, -5, 0, 0, 0);
	private static final	UserDaoSql		udao	= UserDaoSql.getInstance();
	private static final	PokemonDaoSql	pdao	= PokemonDaoSql.getInstance();
	private static final	TradeDaoSql		tdao	= TradeDaoSql.getInstance();
	private static final 	String			DEL_PKM	= "DELETE FROM pokemon WHERE pokemon_id = ?",
											DEL_USR	= "DELETE FROM trainers WHERE trainer_id = ?",
											DEL_TRD	= "DELETE FROM trade_requests WHERE trainer_id1 = ? OR trainer_id2 = ?";
	
	@BeforeClass
	public static void init() throws SQLException {
		
		udao.add_TEST_newUser(u1, "pass");
		udao.add_TEST_newUser(u2, "pass");
		udao.add_TEST_newUser(u3, "pass");
		udao.add_TEST_newUser(u4, "pass");
		udao.add_TEST_newUser(u5, "pass");
		
		pdao.save_TEST_pokemon(p1);
		pdao.save_TEST_pokemon(p2);
		pdao.save_TEST_pokemon(p3);
		pdao.save_TEST_pokemon(p4);
		
		tdao.openTradeOffer(u1.getId(), u3.getId(), p1.getId());
		tdao.openTradeOffer(u1.getId(), u5.getId(), p1.getId());
		tdao.openTradeOffer(u2.getId(), u3.getId(), p2.getId());
		tdao.openTradeOffer(u3.getId(), u4.getId(), p3.getId());
		
	}
	
	@Test
	public void openRequest() {
		
		try {
			
			assertTrue(tdao.openTradeOffer(u1.getId(), u2.getId(), p1.getId()));
			
		} catch (SQLException e) {
			
			//Fail the test
			System.err.println(e.getMessage());
			assertTrue(false);
			
		}
		
	}
	
	@Test
	public void fetchRequest() {
		
		Trade[]		trades		= null;
		
		try {
			
			trades = tdao.fetchTrades(u4.getId());
			
			if(trades == null)
				
				throw new SQLException("Failed to fetch data");
			
			if(trades.length != 1)
				
				throw new SQLException("Failed to fetch correct # of records: " + Arrays.toString(trades));
			
			assertTrue(trades[0].getTrainer_id1() == u3.getId());
			
		} catch(SQLException e) {
			
			//Fail the test
			System.err.println(e.getMessage());
			assertTrue(false);
			
		}
		
	}
	
	@SuppressWarnings("unlikely-arg-type") //Eclipse ignoring toString()
	@Test
	public void updateRequest() {
		
		Trade trade = null;
		
		try {
			
			trade = tdao.fetchTrades(u4.getId())[0];
			
			if(!tdao.updateOffer(trade.getId(), p4.getId()))
				
				//Fail test
				assertTrue(false);
			
			trade = tdao.fetchTrades(u4.getId())[0];
			
			assertTrue(trade.getStatus().equals(TradeStatus.PENDING.toString()) && trade.getPokemon_id2() == p4.getId());
			
		} catch(SQLException e) {
			
			//Fail the test
			System.err.println(e.getMessage());
			assertTrue(false);
			
		}
		
	}
	
	@SuppressWarnings("unlikely-arg-type") //Eclipse ignoring toString()
	@Test
	public void acceptRequest() {
		
		Trade trade = null;
		
		try {
			
			trade = tdao.fetchTrades(u4.getId())[0];
			
			if(!tdao.accept(trade.getId()))
				
				//Fail test
				assertTrue(false);
			
			trade = tdao.fetchTrades(u4.getId())[0];
			
			assertTrue(trade.getStatus().equals(TradeStatus.ACCEPTED.toString()));
			
		} catch(SQLException e) {
			
			//Fail the test
			System.err.println(e.getMessage());
			assertTrue(false);
			
		}
		
	}
	
	@SuppressWarnings("unlikely-arg-type") //Eclipe ignoring toString()
	@Test
	public void declineRequest() {
		
		Trade trade = null;
		
		try {
			
			trade = tdao.fetchTrades(u5.getId())[0];
			
			if(!tdao.decline(trade.getId()))
				
				//Fail test
				assertTrue(false);
			
			trade = tdao.fetchTrades(u5.getId())[0];
			
			assertTrue(trade.getStatus().equals(TradeStatus.DECLINED.toString()));
			
		} catch(SQLException e) {
			
			//Fail the test
			System.err.println(e.getMessage());
			assertTrue(false);
			
		}
		
	}
	
	@AfterClass
	public static void cleanup() throws SQLException {
		
		PreparedStatement ps;
		
		try(Connection c = ConnectionUtil.getConnection()) {
			
			ps = c.prepareStatement(DEL_TRD);
			ps.setInt(1, u1.getId());
			ps.setInt(2, u1.getId());
			ps.addBatch();
			ps.setInt(1, u2.getId());
			ps.setInt(2, u2.getId());
			ps.addBatch();
			ps.setInt(1, u3.getId());
			ps.setInt(2, u3.getId());
			ps.addBatch();
			ps.setInt(1, u4.getId());
			ps.setInt(2, u4.getId());
			ps.addBatch();
			ps.setInt(1, u5.getId());
			ps.setInt(2, u5.getId());
			ps.addBatch();
			
			ps = c.prepareStatement(DEL_PKM);
			ps.setInt(1, p1.getId());
			ps.addBatch();
			ps.setInt(1, p2.getId());
			ps.addBatch();
			ps.setInt(1, p3.getId());
			ps.addBatch();
			ps.setInt(1, p4.getId());
			ps.addBatch();
			ps = c.prepareStatement(DEL_USR);
			ps.setInt(1, u1.getId());
			ps.addBatch();
			ps.setInt(1, u2.getId());
			ps.addBatch();
			ps.setInt(1, u3.getId());
			ps.addBatch();
			ps.setInt(1, u4.getId());
			ps.addBatch();
			ps.setInt(1, u5.getId());
			ps.addBatch();
			
			ps.executeBatch();
			
		} catch(SQLException e) {
			
			throw e;
			
		}
		
	}

}
