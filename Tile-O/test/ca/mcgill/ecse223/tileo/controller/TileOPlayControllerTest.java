package ca.mcgill.ecse223.tileo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.RemoveConnectionActionCard;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;

public class TileOPlayControllerTest {

	private TileO tileO;
	private PlayController pc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// clear all data
		tileO = new TileO();
		this.pc = new PlayController(tileO);
		tileO.delete();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testStartGameSuccess() {

		String error = null;
		//creating a tileO application
		tileO = new TileO();

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
		Player player1 = new Player(0564, currentGame);
		Player player2 = new Player(2888, currentGame);

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
			pc.doStartGame(currentGame);
		}catch(InvalidInputException e){
			error=e.getMessage();
		}


	}

	@Test
	public void testStartGameNoCards() {

		String error = null;
		//creating a tileO application
		tileO = new TileO();

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
		Player player2 = new Player(346, currentGame);

		//adding the players to the current game
		currentGame.addPlayer(player1);
		currentGame.addPlayer(player2);

		//creating two tiles for the current game
		NormalTile tile1 = new NormalTile(1, 2, currentGame);
		NormalTile tile2 = new NormalTile(3, 4, currentGame);

		//setting the starting tile of the players
		player1.setStartingTile(tile1);
		player2.setStartingTile(tile2);
		
		tileO.setCurrentGame(currentGame);
		try{
			pc.doStartGame(currentGame);
		}catch(InvalidInputException e){
			error=e.getMessage();
		}

		assertEquals(error, "The game has no action cards.");

	}

	@Test
	public void testStartGameNoWinTile() {
		String error = null;
		//creating a tileO application
		tileO = new TileO();

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
		Player player1 = new Player(9809, currentGame);
		Player player2 = new Player(1999, currentGame);

		//adding the players to the current game
		currentGame.addPlayer(player1);
		currentGame.addPlayer(player2);

		//creating two tiles for the current game
		NormalTile tile1 = new NormalTile(1, 2, currentGame);
		NormalTile tile2 = new NormalTile(3, 4, currentGame);

		//setting the starting tile of the players
		player1.setStartingTile(tile1);
		player2.setStartingTile(tile2);
		
		tileO.setCurrentGame(currentGame);

		try{
			pc.doStartGame(currentGame);
		}catch(InvalidInputException e){
			error=e.getMessage();
		}

		assertEquals(error, "The game has no win tile.");
	}

	@Test
	public void testStartGameStartingPlayerTileNotDefined() {
		String error = null;
		//creating a tileO application
		tileO = new TileO();


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
		currentGame.addPlayer(4885665);

		tileO.setCurrentGame(currentGame);
		try{
			pc.doStartGame(currentGame);
		}catch(InvalidInputException e){
			error=e.getMessage();
		}

		assertEquals(error, "The starting tile of each player is not defined.");
	}

	@Test
	public void testLandSuccess(){
		String error = null;
		//creating a tileO application
		tileO = new TileO();
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
		Player player2 = new Player(5666, currentGame);

		//adding the players to the current game
		currentGame.addPlayer(player1);
		currentGame.addPlayer(player2);
		currentGame.setCurrentPlayer(player1);
		//setting the starting tile of the players
		player1.setStartingTile(tile1);
		player2.setStartingTile(tile2);
		
		player1.setCurrentTile(tile1);
		player2.setCurrentTile(tile2);
		
		tileO.setCurrentGame(currentGame);
		
		try{
			pc.doLandTile(tile4);
		}catch(InvalidInputException e){
			error = e.getMessage();
		}
	}
	
	@Test
	public void testLandTileNotInList(){
		String error = null;
		//creating a tileO application
		tileO = new TileO();
		PlayController pc = new PlayController(tileO);
		int numberOfConnectionPieces = 32;
		//creating a new game
		Game currentGame = new Game(numberOfConnectionPieces, tileO);
		Game anotherGame = new Game(numberOfConnectionPieces, tileO);
		//adding the game to tileO and setting it to the current game
		tileO.addGame(currentGame);
		tileO.addGame(anotherGame);
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
			pc.doLandTile(tile5);
		}catch(InvalidInputException e){
			error = e.getMessage();
		}
		
		assertEquals("The given tile is not found in the current game.", error);
	}
	
	@Test
	public void testActionCardIsRollDieActionCardSuccess() {
		
		String error = null;
		String instruction ="roll";
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		
		Deck deck = game.getDeck();
		
		NormalTile tile = new NormalTile(0 , 0, game);
		
		RollDieActionCard drawnCard = new RollDieActionCard(instruction , deck);
		for(int i = 0 ; i < 32 ; i++){
			deck.addCard(drawnCard);
		}
		deck.setCurrentCard(drawnCard);
		
		
		Player player = new Player(19 , game);
		
		game.addPlayer(player);
		game.setCurrentPlayer(player);
		
		player.setCurrentTile(tile);
		
		// create a game
		// test if the playRollDieActionCard passes the test
		tileO.setCurrentGame(game);
		
		try{
			pc.doPlayRollDieActionCard();
		}catch (InvalidInputException e){
		    error = e.getMessage();

		}
		
	}
		@Test
	public void testActionCardIsNotRollDieActionCard() {
			
			String error = null;
			String instruction ="roll";
			
			Game game = new Game  (32 , tileO);
			tileO.addGame(game);
			tileO.setCurrentGame(game);
			
			Deck deck = game.getDeck();
			
			TeleportActionCard drawnWrongCard = new TeleportActionCard(instruction , deck);
			deck.setCurrentCard(drawnWrongCard);
			
			// create a game
			// test if the playRollDieActionCard passes the test
			
			
			try{
				pc.doPlayRollDieActionCard();
			}catch (InvalidInputException e){
			    error = e.getMessage();

			}
			tileO.setCurrentGame(game);
			// check error
			  assertEquals("The Current Card is not a RollDieActionCard.", error);	
		
	}
	
	
	@Test
	public void testConnectTilesActionCardSuccess() {
		
		String error = null;
		String instruction = "connect";
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		Deck deck = game.getDeck();
		
		ConnectTilesActionCard drawnCard = new ConnectTilesActionCard(instruction , deck);
		for(int i = 0 ; i < 32 ; i++){
			deck.addCard(drawnCard);
		}
		deck.setCurrentCard(drawnCard);
		//create two new tiles 
		//add them to the current game list of tiles
		NormalTile tile1 = new NormalTile ( 0 , 0 , game);
		game.addTile(tile1);
		NormalTile tile2 = new NormalTile ( 0 , 1 , game);
		game.addTile(tile2);
		
		Player player1 = new Player(1 , game);
		Player player2 = new Player(2 , game);
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		tileO.setCurrentGame(game);
		
		try {
			pc.doPlayConnectTilesActionCard(tile1, tile2);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		
		
	}
	
	@Test
	public void testConnectTilesActionCardTile1DoesntExist(){
		
		
		String error = null;
		String instruction = "connect";
		
		Game game = new Game  (32 , tileO);
		Game anotherGame = new Game(32, tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		Deck deck = game.getDeck();
		
		ConnectTilesActionCard drawnCard = new ConnectTilesActionCard(instruction , deck);
		deck.setCurrentCard(drawnCard);
		//create two new tiles 
		//add them to the current game list of tiles
		NormalTile tile1 = new NormalTile ( 0 , 0 , anotherGame);
		//game.addTile(tile1);
		NormalTile tile2 = new NormalTile ( 0 , 1 , game);
		game.addTile(tile2);
		
		tileO.setCurrentGame(game);
		try {
			pc.doPlayConnectTilesActionCard(tile1, tile2);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("tile1 is not found in the current game.", error);
	
		
		
	
		
	}
	
	@Test
	public void testConnectTilesActionCardTile2DoesntExist(){
		
		
		String error = null;
		String instruction = "connect";
		
		Game game = new Game  (32 , tileO);
		Game anotherGame = new Game(32, tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		Deck deck = game.getDeck();
		
		ConnectTilesActionCard drawnCard = new ConnectTilesActionCard(instruction , deck);
		deck.setCurrentCard(drawnCard);
		//create two new tiles 
		//add them to the current game list of tiles
		NormalTile tile1 = new NormalTile ( 0 , 0 , game);
		game.addTile(tile1);
		NormalTile tile2 = new NormalTile ( 0 , 1 , anotherGame);
		//game.addTile(tile2);
		tileO.setCurrentGame(game);
		
		try {
			pc.doPlayConnectTilesActionCard(tile1, tile2);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("tile2 is not found in the current game.", error);
	
		
		
	
		
	}
	
	@Test
	public void testConnectTilesActionCardTilesAreNotAdjacent() {
		
		String error = null;
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		//create two new tiles 
		//add them to the current game list of tiles
		NormalTile tile1 = new NormalTile ( 0 , 0 , game);
		game.addTile(tile1);
		NormalTile tile2 = new NormalTile ( 1 , 1 , game);
		game.addTile(tile2);

		tileO.setCurrentGame(game);
		try {
			pc.doPlayConnectTilesActionCard(tile1, tile2);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("tile1 and tile2 are not adjacent.", error);
		
	}
	
	@Test
	public void testConnectTilesActionCardHasNoConnectionPieces() {
		
		String error = null;
		
		Game game = new Game  (0 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		//create two new tiles 
		//add them to the current game list of tiles
		NormalTile tile1 = new NormalTile ( 0 , 0 , game);
		game.addTile(tile1);
		NormalTile tile2 = new NormalTile ( 40 , 0 , game);
		game.addTile(tile2);
		tileO.setCurrentGame(game);
		try {
			pc.doPlayConnectTilesActionCard(tile1, tile2);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("There are no connection pieces left.", error);
			
	}
	
	@Test
	public void testConnectTilesActionCardIsWrongTypeOfCard() {
		
		String error = null;
		
		
		String instruction ="connect";
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		
		Deck deck = game.getDeck();
		
		RollDieActionCard drawnCard = new RollDieActionCard(instruction , deck);
		deck.setCurrentCard(drawnCard);
		
		
		
		//create two new tiles 
		//add them to the current game list of tiles
		NormalTile tile1 = new NormalTile ( 0 , 0 , game);
		game.addTile(tile1);
		NormalTile tile2 = new NormalTile ( 0 , 40 , game);
		game.addTile(tile2);
		
		tileO.setCurrentGame(game);
		try {
			pc.doPlayConnectTilesActionCard(tile1, tile2);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("The Current Card is not a ConnectTilesActionCard.", error);
		
		
		
	}
	
	
	@Test
	public void testRemoveConnectionSuccess() {
		
		String error = null;
		String instruction = "remove";
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		Deck deck = game.getDeck();
		
		RemoveConnectionActionCard drawnCard = new RemoveConnectionActionCard(instruction , deck);
		
		for(int i = 0 ; i < 32 ; i++){
			deck.addCard(drawnCard);
		}
		
		deck.setCurrentCard(drawnCard);
		//create two new tiles 
		//add them to the current game list of tiles
		Connection connection = new Connection (game);
		game.addConnection(connection);
		
		Player player1 = new Player(5 , game);
		Player player2 = new Player(608098098 , game);
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		
		tileO.setCurrentGame(game);
		try {
			pc.doPlayRemoveConnectionActionCard(connection);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		
		
	}
	
	
	@Test
	public void testRemoveConnectionConnectionDoesntExist() {
		
		String error = null;
		String instruction = "remove";
		
		Game game = new Game  (32 , tileO);
		Game anotherGame = new Game (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		Deck deck = game.getDeck();
		
		RemoveConnectionActionCard drawnCard = new RemoveConnectionActionCard(instruction , deck);
		deck.setCurrentCard(drawnCard);
		//create two new tiles 
		//add them to the current game list of tiles
		Connection connection = new Connection (anotherGame);
		anotherGame.addConnection(connection);
		
		Player player1 = new Player(7 , game);
		Player player2 = new Player(8 , game);
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		
		tileO.setCurrentGame(game);
		try {
			pc.doPlayRemoveConnectionActionCard(connection);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("The connection is not found in the current game.", error);
		
		
		
		
	}
	
	@Test
	public void testRemoveConnectionIsWrongType() {
		
		String error = null;
		String instruction = "remove";
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		Deck deck = game.getDeck();
		
		RollDieActionCard drawnCard = new RollDieActionCard(instruction , deck);
		deck.setCurrentCard(drawnCard);
		//create two new tiles 
		//add them to the current game list of tiles
		Connection connection = new Connection (game);
		game.addConnection(connection);
		
		Player player1 = new Player(3 , game);
		Player player2 = new Player(4 , game);
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		tileO.setCurrentGame(game);
		
		try {
			pc.doPlayRemoveConnectionActionCard(connection);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("The Current Card is not a RemoveConnectionActionCard.", error);
	
		
		
	}
	
	@Test
	public void testTeleportSuccess() {
		
		String error = null;
		String instruction = "teleport";
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		Deck deck = game.getDeck();
		
		TeleportActionCard drawnCard = new TeleportActionCard(instruction , deck);
		
		for(int i = 0 ; i < 32 ; i++){
			deck.addCard(drawnCard);
		}
		deck.setCurrentCard(drawnCard);
		//create two new tiles 
		//add them to the current game list of tiles
		NormalTile tile = new NormalTile (1 ,1 ,game);
		game.addTile(tile);
		
		Player player1 = new Player(20 , game);
		Player player2 = new Player(21 , game);
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		
		game.setCurrentPlayer(player1);
		tileO.setCurrentGame(game);
		try {
			pc.playTeleportActionCard(tile);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		
		
	}
	
	
	@Test
	public void testTeleportTileNotFound() {
		
		String error = null;
		String instruction = "teleport";
		
		Game game = new Game  (32 , tileO);
		Game anotherGame = new Game (32 , tileO);
		
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		Deck deck = game.getDeck();
		
		TeleportActionCard drawnCard = new TeleportActionCard(instruction , deck);
		deck.setCurrentCard(drawnCard);
		//create two new tiles 
		//add them to the current game list of tiles
		NormalTile tile = new NormalTile (1 ,1 ,anotherGame);
		anotherGame.addTile(tile);
		
		Player player1 = new Player(30 , game);
		Player player2 = new Player(31 , game);
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		tileO.setCurrentGame(game);
		try {
			pc.doPlayTeleportActionCard(tile);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("The tile is not found in the current game.", error);
	
		
		
		
	}
	
	@Test
	public void testTeleportWrongTypeActionCard() {
		
		String error = null;
		String instruction = "teleport";
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		Deck deck = game.getDeck();
		
		RollDieActionCard drawnCard = new RollDieActionCard(instruction , deck);
		deck.setCurrentCard(drawnCard);
		//create two new tiles 
		//add them to the current game list of tiles
		NormalTile tile = new NormalTile (1 ,1 ,game);
		game.addTile(tile);
		
		Player player1 = new Player(50 , game);
		Player player2 = new Player(61 , game);
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		tileO.setCurrentGame(game);
		try {
			pc.doPlayTeleportActionCard(tile);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("The Current Card is not a TeleportActionCard.", error);
	
		
		
		
	}
	
	
	
}



