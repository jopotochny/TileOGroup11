package ca.mcgill.ecse223.tileo.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Die;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;


public class PersistenceTest {	
	
	private TileO tileo;
	
	@Before
	public void setUp() throws Exception {
		 tileo = new TileO();
		
		//creating a number of connections
		int numberOfConnectionPieces = 32;
		
		//creating a new game
		Game currentGame = new Game(numberOfConnectionPieces, tileo);
	
	
		tileo.setCurrentGame(currentGame);
		
		
		//Create deck
		Deck deck = currentGame.getDeck();
		
		//Create a die
		Die die = currentGame.getDie();
		
		
		//Create a connection
		Connection connection = new Connection(currentGame);
		
		
		//Create tiles
		NormalTile tile = new NormalTile(0, 0, currentGame);
		WinTile wintile = new WinTile(2 , 2, currentGame);
		
		//Create players
		Player player1 = new Player(0, currentGame);
		Player player2 = new Player(1, currentGame);
		
		
		//Create an Action Card
		RollDieActionCard rollActionCard = new RollDieActionCard("roll", deck);
		TeleportActionCard teleportCard = new TeleportActionCard("teleport",deck);
		
		//setting current player and connection pieces
		//adding the game to tileO and setting it to the current game
		
		currentGame.setCurrentPlayer(player1);
		
		deck.addCard(rollActionCard);
		deck.addCard(teleportCard);
		
		deck.setCurrentCard(rollActionCard);
		
		
	}
	@After
	public void tearDown() throws Exception{
		tileo.delete();
	}
	
	@Test
	public void test() throws ParseException {
		
		
		// initialize model file
	    PersistenceXStream.initializeModelManager("output"+File.separator+"data.xml");
	    
	    // save model that is loaded during test setup
	    if (!PersistenceXStream.saveToXMLwithXStream(tileo))
	        fail("Could not save file.");
	    
	    //clear model in memory
	    tileo.delete();
	    assertEquals(0, tileo.getGames().size());
	    
	   
	    
	    //load model
	    tileo = (TileO) PersistenceXStream.loadFromXMLwithXStream();
	    if (tileo == null)
	    	fail("Could not load file.");
	    
	    //check game
	    assertEquals(1, tileo.getGames().size());
	    assertEquals(1, tileo.getCurrentGame().getConnections().size());
	    
	    //check deck
	    assertEquals(2, tileo.getCurrentGame().getDeck().getCards().size());
	    assertEquals("roll", tileo.getCurrentGame().getDeck().getCard(0).getInstructions());
	    assertEquals("teleport", tileo.getCurrentGame().getDeck().getCard(1).getInstructions());

	    
	    //check players
	    assertEquals(2, tileo.getCurrentGame().getPlayers().size());
	    assertEquals(0, tileo.getCurrentGame().getPlayer(0).getNumber());
	    assertEquals(1, tileo.getCurrentGame().getPlayer(1).getNumber());	    
	    
	    
	}
}
	
	