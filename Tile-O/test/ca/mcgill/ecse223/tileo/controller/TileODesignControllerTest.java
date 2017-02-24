package ca.mcgill.ecse223.tileo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.controller.DesignController;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.TileO;

public class TileODesignControllerTest {

	private TileO tileO;
	@Before
	public void setUp() throws Exception {
		tileO = TileOApplication.getTileO();
		tileO.delete();
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddConnectionDuringDesign() {
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		DesignController tc = new DesignController(tileO);
		NormalTile theTile1 = new NormalTile( 1, 1, tileO.getCurrentGame());
		NormalTile theTile2 = new NormalTile( 1, 2, tileO.getCurrentGame());
		String error = null;
		try {
			tc.addConnectionDuringDesign(theTile1, theTile2);
		} catch (InvalidInputException e) {
			fail();
		}
		
		
	}
	@Test
	public void testAddNonAdjacentConnection(){
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		DesignController tc = new DesignController(tileO);
		NormalTile theTile1 = new NormalTile( 1, 1, tileO.getCurrentGame());
		NormalTile theTile2 = new NormalTile( 2, 2, tileO.getCurrentGame());
		String error = null;
		try {
			tc.addConnectionDuringDesign(theTile1, theTile2);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("tile1 and tile2 are not adjacent.", error);
		
	}
	@Test
	public void testAddNullTileConnection(){
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		TileO tileo2 = new TileO();
		Game otherGame = new Game(32, tileo2);
		DesignController tc = new DesignController(tileO);
		NormalTile theTile1 = new NormalTile( 1, 1, tileO.getCurrentGame());
		tileO.getCurrentGame().addTile(theTile1);
		NormalTile theTile2 = new NormalTile( 1, 2, tileO.getCurrentGame());
		otherGame.addTile(theTile2);
		String error = null;
		try {
			tc.addConnectionDuringDesign(theTile1, theTile2);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("One or more of the tiles do not exist", error);

	}
	@Test
	public void testAddTileToBoard(){
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		assertEquals(0, tileO.getCurrentGame().numberOfTiles());
		DesignController tc = new DesignController(tileO);
		int X = 1;
		int Y = 2;
		try {
			tc.addTileToBoard(X, Y);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(1, tileO.getCurrentGame().numberOfTiles());
		
	}
	@Test
	public void testTileAlreadyExistsAddTileToBoard(){
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		assertEquals(0, tileO.getCurrentGame().numberOfTiles());
		DesignController tc = new DesignController(tileO);
		NormalTile testTile = new NormalTile(1, 2, tileO.getCurrentGame());
		tileO.getCurrentGame().addTile(testTile);
		int X = 1;
		int Y = 2;
		String error = null;
		try {
			tc.addTileToBoard(X, Y);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("A tile already exists at those coordinates", error);
	}
	@Test
	public void testRemoveConnectionDuringDesign(){
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		DesignController tc = new DesignController(tileO);
		NormalTile theTile1 = new NormalTile( 1, 1, tileO.getCurrentGame());
		NormalTile theTile2 = new NormalTile( 1, 2, tileO.getCurrentGame());
		Connection theConnection = new Connection(tileO.getCurrentGame());
		theConnection.addTile(theTile1);
		theConnection.addTile(theTile2);
		assertEquals(1, tileO.getCurrentGame().numberOfConnections());
		try {
			tc.removeConnectionDuringDesign(theConnection);
		} catch (InvalidInputException e) {
			fail();
		}
		
		assertEquals(0, tileO.getCurrentGame().numberOfConnections());
		
		
	}
	@Test
	public void testRemoveConnectionDoesntExistRemoveConnectionDuringDesign(){
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		DesignController tc = new DesignController(tileO);
		String error = null;
		NormalTile theTile1 = new NormalTile( 1, 1, tileO.getCurrentGame());
		NormalTile theTile2 = new NormalTile( 1, 2, tileO.getCurrentGame());
		Game testGame = new Game(32, tileO);
		NormalTile testTile1 = new NormalTile( 1, 1, testGame);
		NormalTile testTile2 = new NormalTile( 1, 2, testGame);
		Connection theConnection = new Connection(testGame);
		theConnection.addTile(testTile1);
		theConnection.addTile(testTile2);
		assertEquals(0, tileO.getCurrentGame().numberOfConnections());
		try {
			tc.removeConnectionDuringDesign(theConnection);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Connection does not exist", error);
	}
	@Test
	public void testRemoveTileFromBoard(){
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		DesignController tc = new DesignController(tileO);
		NormalTile theTile1 = new NormalTile( 1, 1, tileO.getCurrentGame());
		tileO.getCurrentGame().addTile(theTile1);
		assertEquals(1, tileO.getCurrentGame().numberOfTiles());
		try {
			tc.removeTileFromBoard(theTile1);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(0, tileO.getCurrentGame().numberOfTiles());
	}
	@Test
	public void testTileDoesNotExistRemoveTileFromBoard(){
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		DesignController tc = new DesignController(tileO);
		String error = null;
		Game testGame = new Game(32, tileO);
		NormalTile theTile1 = new NormalTile( 1, 1, testGame);
		assertEquals(0, tileO.getCurrentGame().numberOfTiles());
		try {
			tc.removeTileFromBoard(theTile1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Could not remove tile as it does not exist", error);
	}
		
	}


