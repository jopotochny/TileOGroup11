package ca.mcgill.ecse223.tileo.controller;

import static org.junit.Assert.assertEquals;

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
import ca.mcgill.ecse223.tileo.model.LoseTurnActionCard;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.RemoveConnectionActionCard;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.TileO;

public class TestTileOPlayController {

	private TileO tileO;

	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tileO = TileOApplication.getTileO();
	}

	@After
	public void tearDown() throws Exception {
		
		//	tileO.delete();
	}

	@Test
	public void testActionCardIsRollDieActionCardSuccess() {
		
		String error = null;
		String instruction ="roll";
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		
		NormalTile tile1 = new NormalTile ( 0 , 0 , game);
		
		
		Deck deck = game.getDeck();
		
		RollDieActionCard drawnCard = new RollDieActionCard(instruction , deck);
		for(int i = 0 ; i < 32 ; i++){
			deck.addCard(drawnCard);
		}
		deck.setCurrentCard(drawnCard);
		
		Player player = new Player(19 , game);
		player.setCurrentTile(tile1);
		game.addPlayer(player);
		game.setCurrentPlayer(player);
		
		// create a game
		// test if the playRollDieActionCard passes the test
		
		PlayController pc = new PlayController(tileO);
		
		try{
			pc.playRollDieActionCard();
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
			
			PlayController pc = new PlayController(tileO);
			
			try{
				pc.playRollDieActionCard();
			}catch (InvalidInputException e){
			    error = e.getMessage();

			}
			
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
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playConnectTilesActionCard(tile1, tile2);
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
		
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playConnectTilesActionCard(tile1, tile2);
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
		
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playConnectTilesActionCard(tile1, tile2);
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
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playConnectTilesActionCard(tile1, tile2);
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
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playConnectTilesActionCard(tile1, tile2);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("There are no connection pieces left.", error);
			
	}
	
	// IDK WHAT THE FUCK THIS IS I'LL CHCK LATER
	// TODO
	
	
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
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playConnectTilesActionCard(tile1, tile2);
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
		Player player2 = new Player(6 , game);
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playRemoveConnectionActionCard(connection);
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
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playRemoveConnectionActionCard(connection);
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
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playRemoveConnectionActionCard(connection);
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
		
		PlayController pc = new PlayController(tileO);
		
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
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playTeleportActionCard(tile);
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
		
		PlayController pc = new PlayController(tileO);
		
		try {
			pc.playTeleportActionCard(tile);
		}catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		  assertEquals("The Current Card is not a TeleportActionCard.", error);
	
		
		
		
	}
	
	
	@Test
	public void testLoseTurnActionCardSuccess(){
		
		
		String error = null;
		String instruction ="loseNextTurn";
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		
		Deck deck = game.getDeck();
		
		LoseTurnActionCard drawnCard = new LoseTurnActionCard(instruction , deck);
		for(int i = 0 ; i < 32 ; i++){
			deck.addCard(drawnCard);
		}
		deck.setCurrentCard(drawnCard);
		
		Player player = new Player(19897 , game);
		game.addPlayer(player);
		game.setCurrentPlayer(player);
		
		// create a game
		// test if the playRollDieActionCard passes the test
		
		PlayController pc = new PlayController(tileO);
		
		try{
			pc.playLoseTurnActionCard();
		}catch (InvalidInputException e){
		    error = e.getMessage();

		}
		
		
	}
	
	@Test
	public void testLoseTurnActionCardWrongType(){
		
		String error = null;
		String instruction ="losenextTurn";
		
		Game game = new Game  (32 , tileO);
		tileO.addGame(game);
		tileO.setCurrentGame(game);
		
		Deck deck = game.getDeck();
		
		TeleportActionCard drawnWrongCard = new TeleportActionCard(instruction , deck);
		deck.setCurrentCard(drawnWrongCard);
		
		// create a game
		// test if the playRollDieActionCard passes the test
		
		PlayController pc = new PlayController(tileO);
		
		try{
			pc.playLoseTurnActionCard();
		}catch (InvalidInputException e){
		    error = e.getMessage();

		}
		
		// check error
		  assertEquals("The Current Card is not a LoseTurnActionCard.", error);
	
		
			
		
		
	
	
	}

	
	
}
