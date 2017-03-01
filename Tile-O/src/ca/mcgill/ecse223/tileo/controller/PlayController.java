package ca.mcgill.ecse223.tileo.controller;

import java.util.List;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Die;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.RemoveConnectionActionCard;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;


public class PlayController {

	private TileO tileO;

	public PlayController(TileO tileO){
		this.tileO = tileO;
	}

	public void startGame(Game selectedGame) throws InvalidInputException{

		/*
		 * validation check:
		 * there need to be actionCards in the deck
		 * there need to be a winTile
		 * the starting tile of each player needs to be defined
		 */
		if(selectedGame.getDeck().hasCards() == false){
			throw new InvalidInputException("The game has no action cards.");
		}
		if(selectedGame.hasWinTile() == false){
			throw new InvalidInputException("The game has no win tile.");
		}

		List<Player> players = selectedGame.getPlayers();

		for(Player player: players){
			if(player.hasStartingTile() == false){
				throw new InvalidInputException("The starting tile of each player is not defined.");
			}
		}

		//set the current game to the selected game
		TileO tileOApplication = selectedGame.getTileO();
		tileOApplication.setCurrentGame(selectedGame);

		//get the deck of the current game

		Deck currentDeck = selectedGame.getDeck();
		currentDeck.shuffle();

		//set all the tiles in the current game to not visited
		List<Tile> tiles = selectedGame.getTiles();
		for(Tile tile: tiles){
			tile.setHasBeenVisited(false);
		}

		//get the starting tile for each player, set it the current tile of each one and set it to visited
		for(Player player: players){
			Tile startingTile = player.getStartingTile();
			player.setCurrentTile(startingTile);
			startingTile.setHasBeenVisited(true);
		}

		//setting player 1
		if(selectedGame.setCurrentPlayer(selectedGame.getPlayers().get(0))){

		}

		//set the current connection pieces to the spare connection pieces
		selectedGame.setCurrentConnectionPieces(selectedGame.SpareConnectionPieces);

		//set the mode of the current game to GAME
		selectedGame.setMode(Game.Mode.GAME);	

	}

	public List<Tile> rollDie(){
		//get the current game from the TileO application
		Game currentGame = tileO.getCurrentGame();

		//get die from the current game
		Die die = currentGame.getDie();

		//roll the die to generate a number

		int number = die.roll();

		//getting the current player from the current game
		Player currentPlayer = currentGame.getCurrentPlayer();

		//get the possible moves of the current player, depending on the generated number
		// TODO implement the get possible moves method
		List<Tile> tiles = currentPlayer.getPossibleMoves(number);

		return tiles;
	}

	public void land(Tile tile) throws InvalidInputException{
		//getting the current game
		Game currentGame = tileO.getCurrentGame();

		/*
		 * getting the list of tiles, followed by checking if the 
		 * input tile is in the tiles of the current game
		 */
		List<Tile> tiles = currentGame.getTiles();
		if(tiles.contains(tile) == false){
			throw new InvalidInputException("The given tile is not found in the current game.");
		}
		//if the tile is of type NormalTile
		if(tile instanceof NormalTile){
			//setting pointer of type normalTile pointing tile
			NormalTile normalTile = (NormalTile) tile;
			normalTile.land();
		}
		//if the tile is of type WinTile
		else if(tile instanceof WinTile){
			WinTile winTile = (WinTile) tile;
			winTile.land();
		}
		//if the tile is of type ActionTile
		else if(tile instanceof ActionTile){
			ActionTile actionTile = (ActionTile) tile;
			actionTile.land();
		}

	}

	public List<Tile> playRollDieActionCard() throws InvalidInputException{

		// get the current game

		Game currentGame = tileO.getCurrentGame();

		// get the deck of the current game

		Deck currentDeck = currentGame.getDeck();


		//check if the current card is a RollDieActionCard

		if (!(currentDeck.getCurrentCard() instanceof RollDieActionCard)){


			throw new InvalidInputException("The Current Card is not a RollDieActionCard.");
		}


		RollDieActionCard currentCard = (RollDieActionCard) currentDeck.getCurrentCard();
		
		//  TODO added play in RollDieActionCard
		

		List<Tile> listOfTiles = currentCard.play();


		// set the currentCard to be the next card so that the next
		// time a player draws a card , he gets the next card
		// if the current card is the last card of the deck
		// shuffle the deck


		if ( (currentDeck.indexOfCard(currentCard) + 1 )   == currentDeck.numberOfCards()){

			currentDeck.shuffle();			

		}
		else {


			currentDeck.setCurrentCard(currentDeck.getCard(currentDeck.indexOfCard(currentCard)+1 ));

		}


		// set the mode of the current game to GAME
		currentGame.setMode(Game.Mode.GAME);		


		return listOfTiles;

	}

	public void playConnectTilesActionCard(Tile tile1, Tile tile2) throws InvalidInputException{

		// get the current game

		Game currentGame = tileO.getCurrentGame();

		List<Tile> tiles = currentGame.getTiles();

		// check if both tiles passed in exist in the game

		if(! tiles.contains(tile1)){
			throw new InvalidInputException("tile1 is not found in the current game.");
		}
		if(! tiles.contains(tile2)){
			throw new InvalidInputException("tile2 is not found in the current game.");
		}


		// check if the tiles are adjacent
		// case1 if tile 1 and tile 2 have the same X , but Y's differ by 1
		// case2 if tile 1 and tile 2 have the same Y , but X's differ by 1

		if( ! ( (( tile1.getY() == tile2.getY() )  &&  (  ( tile1.getX() == tile2.getX() -1 ) || (tile1.getX() == tile2.getX() + 1)) )|| ( ( tile1.getX() == tile2.getX() )  &&  (  ( tile1.getY() == tile2.getY() -1 ) || (tile1.getY() == tile2.getY() + 1))))){

			throw new InvalidInputException("tile1 and tile2 are not adjacent.");

		}


		if(currentGame.getCurrentConnectionPieces() <= 0 ){

			throw new InvalidInputException("There are no connection pieces left.");

		}


		// get the currentDeck

		Deck currentDeck = currentGame.getDeck();


		// check if the current card is a ConnectTilesActionCard

		if (!(currentDeck.getCurrentCard() instanceof ConnectTilesActionCard)){


			throw new InvalidInputException("The Current Card is not a ConnectTilesActionCard.");


		}

		ConnectTilesActionCard currentCard = (ConnectTilesActionCard) currentDeck.getCurrentCard();

		// connect both tiles
		
		//  TODO added play in ConnectTilesActionCard

		currentCard.play(tile1, tile2);


		Player currentPlayer = currentGame.getCurrentPlayer();

		//getting the index of current player, and the number of total players
		int indexOfPlayer = currentGame.indexOfPlayer(currentPlayer);
		int numberOfPlayers = currentGame.numberOfPlayers();

		//if the current player is the last player
		if(indexOfPlayer == numberOfPlayers - 1){
			//getting the first player
			Player firstPlayer = currentGame.getPlayer(0);

			//setting the current player to the first player
			currentGame.setCurrentPlayer(firstPlayer);
		}else{
			//get the next player
			Player nextPlayer = currentGame.getPlayer(indexOfPlayer + 1);

			//setting the current player to the next player
			currentGame.setCurrentPlayer(nextPlayer);
		}




		// set the currentCard to be the next card so that the next
		// time a player draws a card , he gets the next card
		// if the current card is the last card of the deck
		// shuffle the deck


		if ( (currentDeck.indexOfCard(currentCard) + 1 )   == currentDeck.numberOfCards()){

			currentDeck.shuffle();			

		}
		else {


			currentDeck.setCurrentCard(currentDeck.getCard(currentDeck.indexOfCard(currentCard)+1 ));

		}

		// set the mode of the current game to GAME
		currentGame.setMode(Game.Mode.GAME);		



	}


	public void playRemoveConnectionActionCard(Connection connection) throws InvalidInputException{

		// get the current game

		Game currentGame = tileO.getCurrentGame();

		List<Connection> connections = currentGame.getConnections();

		// check if both tiles passed in exist in the game

		if(! connections.contains(connection)){

			throw new InvalidInputException("The connection is not found in the current game.");


		}

		// get the currentDeck

		Deck currentDeck = currentGame.getDeck();


		// check if the current card is a RemoveConnectionActionCard

		if (!(currentDeck.getCurrentCard() instanceof RemoveConnectionActionCard)){


			throw new InvalidInputException("The Current Card is not a RemoveConnectionActionCard.");


		}

		RemoveConnectionActionCard currentCard = (RemoveConnectionActionCard) currentDeck.getCurrentCard();

		// connect both tiles
		
		// TODO added play in RemoveConnectionActionCard

		currentCard.play(connection);


		Player currentPlayer = currentGame.getCurrentPlayer();

		//getting the index of current player, and the number of total players
		int indexOfPlayer = currentGame.indexOfPlayer(currentPlayer);
		int numberOfPlayers = currentGame.numberOfPlayers();

		//if the current player is the last player
		if(indexOfPlayer == numberOfPlayers - 1){
			//getting the first player
			Player firstPlayer = currentGame.getPlayer(0);

			//setting the current player to the first player
			currentGame.setCurrentPlayer(firstPlayer);
		}else{
			//get the next player
			Player nextPlayer = currentGame.getPlayer(indexOfPlayer + 1);

			//setting the current player to the next player
			currentGame.setCurrentPlayer(nextPlayer);
		}




		// set the currentCard to be the next card so that the next
		// time a player draws a card , he gets the next card
		// if the current card is the last card of the deck
		// shuffle the deck


		if ( (currentDeck.indexOfCard(currentCard) + 1 )   == currentDeck.numberOfCards()){

			currentDeck.shuffle();			

		}
		else {


			currentDeck.setCurrentCard(currentDeck.getCard(currentDeck.indexOfCard(currentCard)+1 ));

		}

		// set the mode of the current game to GAME
		currentGame.setMode(Game.Mode.GAME);		







	}

	public void playTeleportActionCard(Tile tile) throws InvalidInputException{



		// get the current game

		Game currentGame = tileO.getCurrentGame();

		List<Tile> tiles = currentGame.getTiles();

		// check if both tiles passed in exist in the game

		if(! tiles.contains(tile)){
			throw new InvalidInputException("The tile is not found in the current game.");
		}


		// get the currentDeck

		Deck currentDeck = currentGame.getDeck();


		// check if the current card is a RemoveConnectionActionCard

		if (!(currentDeck.getCurrentCard() instanceof TeleportActionCard)){


			throw new InvalidInputException("The Current Card is not a TeleportActionCard.");


		}

		TeleportActionCard currentCard = (TeleportActionCard) currentDeck.getCurrentCard();

		// teleport the player to the selected tile	
		
		// TODO added play in TeleportActionCard

		currentCard.play(tile);


		Player currentPlayer = currentGame.getCurrentPlayer();

		//getting the index of current player, and the number of total players
		int indexOfPlayer = currentGame.indexOfPlayer(currentPlayer);
		int numberOfPlayers = currentGame.numberOfPlayers();

		//if the current player is the last player
		if(indexOfPlayer == numberOfPlayers - 1){
			//getting the first player
			Player firstPlayer = currentGame.getPlayer(0);

			//setting the current player to the first player
			currentGame.setCurrentPlayer(firstPlayer);
		}else{
			//get the next player
			Player nextPlayer = currentGame.getPlayer(indexOfPlayer + 1);

			//setting the current player to the next player
			currentGame.setCurrentPlayer(nextPlayer);
		}




		// set the currentCard to be the next card so that the next
		// time a player draws a card , he gets the next card
		// if the current card is the last card of the deck
		// shuffle the deck


		if ( (currentDeck.indexOfCard(currentCard) + 1 )   == currentDeck.numberOfCards()){

			currentDeck.shuffle();			

		}
		else {


			currentDeck.setCurrentCard(currentDeck.getCard(currentDeck.indexOfCard(currentCard)+1 ));

		}

		// set the mode of the current game to GAME
		currentGame.setMode(Game.Mode.GAME);		


	}
	
	//get games from the model
	public List<Game> getGames(){
		return TileOApplication.getTileO().getGames();
	}
	
	
	//get the current player from the current game
	public Player getCurrentPlayer(){
		return TileOApplication.getTileO().getCurrentGame().getCurrentPlayer();
	}
	
	// Save design mode
	public static void saveGame(){
		TileOApplication.save();
	}
		
	// Load Design mode
	public static Game loadGame(int index){
		TileOApplication.load();
		return TileOApplication.getGame(index);
	}
	

}
