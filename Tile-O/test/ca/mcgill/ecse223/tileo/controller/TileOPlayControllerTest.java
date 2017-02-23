package ca.mcgill.ecse223.tileo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;

public class TileOPlayControllerTest {

	private TileO tileO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// clear all data
		tileO = TileOApplication.getTileO();
		tileO.delete();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testStartGameSuccess() {

		String error = null;
		//creating a tileO application
		tileO = TileOApplication.getTileO();

		//setting the controller
		PlayController pc = new PlayController(tileO);

		int numberOfConnectionPieces = 32;
		//creating a new game
		Game currentGame = new Game(numberOfConnectionPieces, tileO);
		//adding the game to tileO and setting it to the current game
		tileO.addGame(currentGame);
		tileO.setCurrentGame(currentGame);

		//creating a wintile for the game
		WinTile wintile = new WinTile(1, 1, currentGame);
		currentGame.setWinTile(wintile);

		//getting the deck of the current game
		Deck deck = currentGame.getDeck();

		//creating an action card and adding it to the deck of cards
		for(int i=0; i<32; i++){
			RollDieActionCard card = new RollDieActionCard("roll die", deck);
			deck.addCard(card);
		}


		//creating to new players
		Player player1 = new Player(0, currentGame);
		Player player2 = new Player(2, currentGame);

		//adding the players to the current game
		currentGame.addPlayer(player1);
		currentGame.addPlayer(player2);

		//creating two tiles for the current game
		NormalTile tile1 = new NormalTile(1, 2, currentGame);
		NormalTile tile2 = new NormalTile(3, 4, currentGame);

		//setting the starting tile of the players
		player1.setStartingTile(tile1);
		player2.setStartingTile(tile2);

		try{
			pc.startGame(currentGame);
		}catch(InvalidInputException e){
			error=e.getMessage();
		}


	}

	@Test
	public void testStartGameNoCards() {

		String error = null;
		//creating a tileO application
		tileO = TileOApplication.getTileO();

		//setting the controller
		PlayController pc = new PlayController(tileO);

		int numberOfConnectionPieces = 32;
		//creating a new game
		Game currentGame = new Game(numberOfConnectionPieces, tileO);
		//adding the game to tileO and setting it to the current game
		tileO.addGame(currentGame);
		tileO.setCurrentGame(currentGame);

		//creating a wintile for the game
		WinTile wintile = new WinTile(1, 1, currentGame);	
		currentGame.setWinTile(wintile);

		//creating to new players
		Player player1 = new Player(0, currentGame);
		Player player2 = new Player(3, currentGame);

		//adding the players to the current game
		currentGame.addPlayer(player1);
		currentGame.addPlayer(player2);

		//creating two tiles for the current game
		NormalTile tile1 = new NormalTile(1, 2, currentGame);
		NormalTile tile2 = new NormalTile(3, 4, currentGame);

		//setting the starting tile of the players
		player1.setStartingTile(tile1);
		player2.setStartingTile(tile2);

		try{
			pc.startGame(currentGame);
		}catch(InvalidInputException e){
			error=e.getMessage();
		}

		assertEquals(error, "The game has no action cards.");

	}

	@Test
	public void testStartGameNoWinTile() {
		String error = null;
		//creating a tileO application
		tileO = TileOApplication.getTileO();

		//setting the controller
		PlayController pc = new PlayController(tileO);

		int numberOfConnectionPieces = 32;
		//creating a new game
		Game currentGame = new Game(numberOfConnectionPieces, tileO);
		//adding the game to tileO and setting it to the current game
		tileO.addGame(currentGame);
		tileO.setCurrentGame(currentGame);

		//getting the deck of the current game
		Deck deck = currentGame.getDeck();

		//creating an action card and adding it to the deck of cards
		for(int i=0; i<32; i++){
			RollDieActionCard card = new RollDieActionCard("roll die", deck);
			deck.addCard(card);
		}

		if(currentGame.hasPlayers()){
			fail();
		}

		//creating to new players
		Player player1 = new Player(0, currentGame);
		Player player2 = new Player(1, currentGame);

		//adding the players to the current game
		currentGame.addPlayer(player1);
		currentGame.addPlayer(player2);

		//creating two tiles for the current game
		NormalTile tile1 = new NormalTile(1, 2, currentGame);
		NormalTile tile2 = new NormalTile(3, 4, currentGame);

		//setting the starting tile of the players
		player1.setStartingTile(tile1);
		player2.setStartingTile(tile2);

		try{
			pc.startGame(currentGame);
		}catch(InvalidInputException e){
			error=e.getMessage();
		}

		assertEquals(error, "The game has no win tile.");
	}

	@Test
	public void testStartGameStartingPlayerTileNotDefined() {
		String error = null;
		//creating a tileO application
		tileO = TileOApplication.getTileO();

		//setting the controller
		PlayController pc = new PlayController(tileO);

		int numberOfConnectionPieces = 32;
		//creating a new game
		Game currentGame = new Game(numberOfConnectionPieces, tileO);
		//adding the game to tileO and setting it to the current game
		tileO.addGame(currentGame);
		tileO.setCurrentGame(currentGame);

		//creating a wintile for the game
		WinTile wintile = new WinTile(1, 1, currentGame);
		currentGame.setWinTile(wintile);

		//getting the deck of the current game
		Deck deck = currentGame.getDeck();

		//creating an action card and adding it to the deck of cards
		for(int i=0; i<32; i++){
			RollDieActionCard card = new RollDieActionCard("roll die", deck);
			deck.addCard(card);
		}


		//adding the players to the current game
		currentGame.addPlayer(0);
		currentGame.addPlayer(4);


		try{
			pc.startGame(currentGame);
		}catch(InvalidInputException e){
			error=e.getMessage();
		}

		assertEquals(error, "The starting tile of each player is not defined.");
	}

	@Test
	public void testLandSuccess(){
		String error = null;
		//creating a tileO application
		tileO = TileOApplication.getTileO();

		//setting the controller
		PlayController pc = new PlayController(tileO);

		int numberOfConnectionPieces = 32;
		//creating a new game
		Game currentGame = new Game(numberOfConnectionPieces, tileO);
		//adding the game to tileO and setting it to the current game
		tileO.addGame(currentGame);
		tileO.setCurrentGame(currentGame);

		NormalTile tile1 = new NormalTile(1, 2, currentGame);
		currentGame.addTile(tile1);
		NormalTile tile2 = new NormalTile(3, 4, currentGame);
		currentGame.addTile(tile2);
		NormalTile tile3 = new NormalTile(5, 6, currentGame);
		currentGame.addTile(tile3);
		NormalTile tile4 = new NormalTile(7, 8, currentGame);
		currentGame.addTile(tile4);

		//creating to new players
		Player player1 = new Player(0, currentGame);
		Player player2 = new Player(5, currentGame);

		//adding the players to the current game
		currentGame.addPlayer(player1);
		currentGame.addPlayer(player2);
		currentGame.setCurrentPlayer(player1);
		//setting the starting tile of the players
		player1.setStartingTile(tile1);
		player2.setStartingTile(tile2);
		
		player1.setCurrentTile(tile1);
		player2.setCurrentTile(tile2);

		try{
			pc.land(tile4);
		}catch(InvalidInputException e){
			error = e.getMessage();
		}
	}
	
	@Test
	public void testLandTileNotInList(){
		String error = null;
		//creating a tileO application
		tileO = TileOApplication.getTileO();

		//setting the controller
		PlayController pc = new PlayController(tileO);

		int numberOfConnectionPieces = 32;
		//creating a new game
		Game currentGame = new Game(numberOfConnectionPieces, tileO);
		Game anotherGame = new Game(numberOfConnectionPieces, tileO);
		//adding the game to tileO and setting it to the current game
		tileO.addGame(currentGame);
		tileO.setCurrentGame(currentGame);

		NormalTile tile1 = new NormalTile(1, 2, currentGame);
		currentGame.addTile(tile1);
		NormalTile tile2 = new NormalTile(3, 4, currentGame);
		currentGame.addTile(tile2);
		NormalTile tile3 = new NormalTile(5, 6, currentGame);
		currentGame.addTile(tile3);
		NormalTile tile4 = new NormalTile(7, 8, currentGame);
		currentGame.addTile(tile4);
		NormalTile tile5 = new NormalTile(9, 10, anotherGame);
		
		//creating to new players
		Player player1 = new Player(0, currentGame);
		Player player2 = new Player(6, currentGame);

		//adding the players to the current game
		currentGame.addPlayer(player1);
		currentGame.addPlayer(player2);
		currentGame.setCurrentPlayer(player1);
		//setting the starting tile of the players
		player1.setStartingTile(tile1);
		player2.setStartingTile(tile2);
		
		player1.setCurrentTile(tile1);
		player2.setCurrentTile(tile2);

		try{
			pc.land(tile5);
		}catch(InvalidInputException e){
			error = e.getMessage();
		}
		
		assertEquals(error, "The given tile is not found in the current game.");
	}


}
