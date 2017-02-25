package ca.mcgill.ecse223.tileo.persistence;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.PlayController;
import ca.mcgill.ecse223.tileo.model.ActionCard;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Die;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.persistence.PersistenceXStream;


public class PersistenceTest {
	
	private static String filename = "testdata.Game";
	private Game game;
	private TileO tileo;
	
	@Before
	public void setUp() throws Exception {
		//creating a tileO application
		TileO tileO = TileOApplication.getTileO();
		
		int numberOfConnectionPieces = 32;
		
		//creating a new game
		Game currentGame = new Game(numberOfConnectionPieces, tileO);
		
		//Create deck
		Deck deck = new Deck(currentGame);
		
		//Create connection
		Connection connection = new Connection(currentGame);
		
		//Create die
		Die die = new Die(currentGame);
		
		//Create tiles
		NormalTile tile = new NormalTile(0, 0, currentGame);
		
		//Create player
		Player player = new Player(0, null);
		
		//setting current player and connection pieces
		//adding the game to tileO and setting it to the current game
		tileO.addGame(currentGame);
		tileO.setCurrentGame(currentGame);
		currentGame.setCurrentPlayer(player);
		currentGame.setCurrentConnectionPieces(15);
		deck.addCard("move up");
		deck.addCard("move down");
		tile.setX(5);
		tile.setY(7);
		
		
	}
	@After
	public void tearDown() throws Exception{
		game.delete();
	}
	
	@Test
	public void testPersistence() throws ParseException {
		// initialize model file
	    PersistenceXStream.initializeModelManager("output"+File.separator+"data.xml");
	    
	    // save model that is loaded during test setup
	    if (!PersistenceXStream.saveToXMLwithXStream(game))
	        fail("Could not save file.");
	    
	    //clear model in memory
	    game.delete();
	    assertEquals(0, game.getPlayers().size());
	    assertEquals(0, game.getConnections().size());
	    assertEquals(0, game.getTiles().size());
	}
}
	
	