
package ca.mcgill.ecse223.tileo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.TileO;

public class TileODesignControllerTest {

	private TileO tileO;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//PersistenceXStream.initializeModelManager("output"+File.separator+"data.xml");
	}
	@Before
	public void setUp() throws Exception {
		tileO = new TileO();
		tileO.delete();
		
	}
	
	@After
	public void tearDown() throws Exception {
		//tileO.delete();
		
	}

	@Test
	public void testAddConnectionDuringDesign() {
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		DesignController tc = new DesignController(tileO);
		NormalTile theTile1 = new NormalTile( 0, 40, tileO.getCurrentGame());
		NormalTile theTile2 = new NormalTile( 0, 80, tileO.getCurrentGame());
		String error = null;
		try {
			tc.addConnectionDuringDesign(theTile1, theTile2);
		} catch (InvalidInputException e) {
			//error = e.getMessage();
			fail();
		}
		//System.out.println(error);
		
		
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
	public void testAddTooManyConnections(){
		String error = "";
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		DesignController tc = new DesignController(tileO);
		
		for(int i =0 ; i <=32; i++){
			try {
				tc.addConnectionDuringDesign(new NormalTile(0+i*40, 0, theCurrentGame), new NormalTile(0+i*40, 40, theCurrentGame));
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				error += e.getMessage();
			}
		}
		assertEquals("Cant create more than 32 connections", error);
	}
	
	@Test 
	public void testRemoveTileWithConnection(){
		Game theCurrentGame = new Game(32, tileO);
		tileO.setCurrentGame(theCurrentGame);
		DesignController tc = new DesignController(tileO);
		NormalTile theTile1 = new NormalTile( 0, 0, tileO.getCurrentGame());
		NormalTile theTile2 = new NormalTile( 0, 40, tileO.getCurrentGame());
		Connection connect = new Connection(theCurrentGame);
		connect.addTile(theTile1);
		connect.addTile(theTile2);
		
		try{
			tc.removeTileFromBoard(theTile1);
		}catch(InvalidInputException e){
			fail();
		}
		assertEquals(theCurrentGame.getConnections().size(), 0);
		
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
			tc.removeConnectionDuringDesign(theTile1, theTile2);
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
			tc.removeConnectionDuringDesign(testTile1, testTile2);
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
	@Test
	public void testIdentifyWin() {
		DesignController controller = new DesignController(tileO);
		Game game = new Game(32, tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);

		try {
			controller.identifyWinTile(2, 5);
		} catch (InvalidInputException e) {
			fail();
		}

		assertEquals(true, game.hasWinTile());
	}

	@Test
	public void testWinTileExists() {
		DesignController controller = new DesignController(tileO);
		Game game = new Game(32, tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		try {
			controller.identifyWinTile(2, 3);
		} catch (InvalidInputException e1) {
			// TODO Auto-generated catch block
			fail();
		}
		String error = "";

		try {
			controller.identifyWinTile(2, 5);

		} catch (InvalidInputException e) {
			error += e.getMessage();
		}

		assertEquals("The win tile has already been set", error);
	}

	@Test
	public void testStartTile() {
		DesignController controller = new DesignController(tileO);
		tileO.setCurrentGame(null);
		Game game = new Game(32, tileO);
		tileO.setCurrentGame(game);
	
		

		//Player player = new Player(0, game);

		NormalTile tile = new NormalTile(0, 0, game);

		tileO.addGame(game);
		
		tileO.setCurrentGame(game);
		
		for(Player p : tileO.getCurrentGame().getPlayers()){
			game.removePlayer(p);
		}
		try {
			controller.identifyStartTile(tile);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(1, game.getPlayers().size());
	}

	@Test
	public void testToManyPlayers() {
		DesignController controller = new DesignController(tileO);
		Game game = new Game(32, tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);

		String error = "";
		NormalTile tile = new NormalTile(1, 1, game);
		Player player1 = new Player(1837671, game);
		Player player2 = new Player(1201937, game);
		Player player3 = new Player(1384829, game);
		Player player4 = new Player(1398746826, game);
		//player1.setStartingTile(tile);
		//Player player2 = new Player(25, game);

		try {
			controller.identifyStartTile(tile);
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}

		assertEquals("There are already 4 players", error);
	}
	@Test
	public void testPlayerAlreadyExists(){
		DesignController controller = new DesignController(tileO);
		Game game = new Game(32, tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);

		String error = "";
		NormalTile tile = new NormalTile(0, 0, game);
		NormalTile tile1 = new NormalTile(0, 0, game);
		Player player1 = new Player(123456774, game);
		player1.setStartingTile(tile);
		try {
			controller.identifyStartTile(tile1);
			
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}
		assertEquals("A player already exists at this tile", error);
	}
	
	@Test
	public void testConflictingActionTile() {
		String error = "";
		DesignController controller = new DesignController(tileO);
		Game game = new Game(32, tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);

		ActionTile action = new ActionTile(1, 2, game, 3);

		try {
			controller.identifyActionTile(1, 2, 2);
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}
		assertEquals("Action tile already exists", error);
	}

	@Test
	public void testCreateActionTile() {
		DesignController controller = new DesignController(tileO);
		Game game = new Game(32, tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);

		try {
			controller.identifyActionTile(1, 4, 2);
		} catch (InvalidInputException e) {
			fail();
		}

	}

	@Test
	public void testCreateCards() {
		DesignController controller = new DesignController(tileO);
		Game game = new Game(32, tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);

		int remove, connect, teleport, roll, lose;
		remove = 10;
		connect = 10;
		teleport = 10;
		roll = 1;
		lose = 1;

		try {
			controller.selectCards( connect, teleport, roll, remove, lose);
		} catch (InvalidInputException e) {
			fail();
		}

		assertEquals(game.getDeck().getCards().size(), 32);
	}

	@Test
	public void testTooManyCards() {
		String error = "";
		DesignController controller = new DesignController(tileO);
		Game game = new Game(32, tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		int remove, connect, teleport, roll, lose;
		remove = 20;
		connect = 5;
		teleport = 5;
		roll = 5;
		lose = 3;
		
		try {
			controller.selectCards(connect, teleport, roll, remove, lose);
		} catch (InvalidInputException e) {
			error += e.getMessage();

		}

		assertEquals("The amount of cards chosen is not 32", error);
	}
		
}