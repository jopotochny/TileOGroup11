/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.controller;
import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Die;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.LoseTurnActionCard;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.RemoveConnectionActionCard;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;
import java.util.*;
import ca.mcgill.ecse223.tileo.model.*;

// line 3 "../../../../../PlayControllerStatus.ump"
public class PlayController
{

	//------------------------
	// MEMBER VARIABLES
	//------------------------

	// line 83 ../../../../../PlayControllerStatus.ump
	private TileO tileO ;
	//PlayController State Machines
	public enum Mode { Ready, Roll, Move, ActionCard, Won }
	private Mode mode;

	//PlayController Associations
	private List<Tile> possibleMoves;

	//------------------------
	// CONSTRUCTOR
	//------------------------

	public PlayController(TileO tileO)
	{
		this.tileO = tileO;
		possibleMoves = new ArrayList<Tile>();
		setMode(Mode.Ready);
	}

	//------------------------
	// INTERFACE
	//------------------------

	public String getModeFullName()
	{
		String answer = mode.toString();
		return answer;
	}

	public Mode getMode()
	{
		return mode;
	}

	public boolean startGame() throws InvalidInputException
	{
		boolean wasEventProcessed = false;

		Mode aMode = mode;
		switch (aMode)
		{
		case Ready:
			// line 24 "../../../../../PlayControllerStatus.ump"
			doStartGame(tileO.getCurrentGame());
			setMode(Mode.Roll);
			wasEventProcessed = true;
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean load(Game selectedGame)
	{
		boolean wasEventProcessed = false;

		Mode aMode = mode;
		switch (aMode)
		{
		case Ready:
			if (isInGameMode(selectedGame))
			{
				// line 27 "../../../../../PlayControllerStatus.ump"
				doLoad(selectedGame);
				setMode(Mode.Roll);
				wasEventProcessed = true;
				break;
			}
			if (isInWonMode(selectedGame))
			{
				// line 30 "../../../../../PlayControllerStatus.ump"
				doLoad(selectedGame);
				setMode(Mode.Won);
				wasEventProcessed = true;
				break;
			}
			if (isNotInGameOrWonMode(selectedGame))
			{
				// line 33 "../../../../../PlayControllerStatus.ump"
				doLoad(selectedGame);
				setMode(Mode.ActionCard);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean rollDie()
	{
		boolean wasEventProcessed = false;

		
		if(getGameMode().equals(Game.Mode.GAME) && getMode().equals(PlayController.Mode.ActionCard)){
			setMode(Mode.Roll);
		}
		
		Mode aMode = mode;
		
		switch (aMode)
		{
		case Roll:
			// line 38 "../../../../../PlayControllerStatus.ump"
			possibleMoves = doRollDie();
			setMode(Mode.Move);
			wasEventProcessed = true;
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean land(Tile tile) throws InvalidInputException
	{
		boolean wasEventProcessed = false;

		Mode aMode = mode;
		switch (aMode)
		{
		case Move:
			if (isNormalTile(tile))
			{
				// line 44 "../../../../../PlayControllerStatus.ump"
				doLandTile(tile);
				setMode(Mode.Roll);
				wasEventProcessed = true;
				break;
			}
			if (isWinTile(tile))
			{
				// line 47 "../../../../../PlayControllerStatus.ump"
				doLandTile(tile);
				setMode(Mode.Won);
				wasEventProcessed = true;
				break;
			}
			if (isActionTile(tile))
			{
				// line 50 "../../../../../PlayControllerStatus.ump"
				doLandTile(tile);
				setMode(Mode.ActionCard);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean playRollDieActionCard() throws InvalidInputException
	{
		boolean wasEventProcessed = false;

		Mode aMode = mode;
		switch (aMode)
		{
		case ActionCard:
			if (isRollDieActionCard())
			{
				// line 56 "../../../../../PlayControllerStatus.ump"
				possibleMoves = doPlayRollDieActionCard();
				setMode(Mode.Move);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean playConnectTilesActionCard(Tile tile1,Tile tile2) throws InvalidInputException
	{
		boolean wasEventProcessed = false;

		Mode aMode = mode;
		switch (aMode)
		{
		case ActionCard:
			if (isConnectTilesActionCard())
			{
				// line 59 "../../../../../PlayControllerStatus.ump"
				doPlayConnectTilesActionCard(tile1, tile2);
				setMode(Mode.Roll);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	

	public boolean playRemoveConnectionActionCard(Connection c) throws InvalidInputException
	{
		boolean wasEventProcessed = false;

		Mode aMode = mode;
		switch (aMode)
		{
		case ActionCard:
			if (isRemoveConnectionActionCard())
			{
				// line 62 "../../../../../PlayControllerStatus.ump"
				doPlayRemoveConnectionActionCard(c);
				setMode(Mode.Roll);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}


	public boolean playTeleportActionCard(Tile tile) throws InvalidInputException
	{
		boolean wasEventProcessed = false;

		Mode aMode = mode;
		switch (aMode)
		{
		case ActionCard:
			if (isTeleportAndNormalTile(tile))
			{
				// line 65 "../../../../../PlayControllerStatus.ump"
				doPlayTeleportActionCard(tile);
				setMode(Mode.Roll);
				wasEventProcessed = true;
				break;
			}
			if (isTeleportAndWinTile(tile))
			{
				// line 68 "../../../../../PlayControllerStatus.ump"
				doPlayTeleportActionCard(tile);
				setMode(Mode.Won);
				wasEventProcessed = true;
				break;
			}
			if (isTeleportAndActionTile(tile))
			{
				// line 71 "../../../../../PlayControllerStatus.ump"
				doPlayTeleportActionCard(tile);
				setMode(Mode.ActionCard);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	

	public boolean playLoseTurnActionCard() throws InvalidInputException
	{
		boolean wasEventProcessed = false;

		Mode aMode = mode;
		switch (aMode)
		{
		case ActionCard:
			if (isLoseTurnActionCard())
			{
				// line 74 "../../../../../PlayControllerStatus.ump"
				doPlayLoseTurnActionCard();
				setMode(Mode.Roll);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}


	private void setMode(Mode aMode)
	{
		mode = aMode;
	}

	public Tile getPossibleMove(int index)
	{
		Tile aPossibleMove = possibleMoves.get(index);
		return aPossibleMove;
	}

	public List<Tile> getPossibleMoves()
	{
		List<Tile> newPossibleMoves = Collections.unmodifiableList(possibleMoves);
		return newPossibleMoves;
	}

	public int numberOfPossibleMoves()
	{
		int number = possibleMoves.size();
		return number;
	}

	public boolean hasPossibleMoves()
	{
		boolean has = possibleMoves.size() > 0;
		return has;
	}

	public int indexOfPossibleMove(Tile aPossibleMove)
	{
		int index = possibleMoves.indexOf(aPossibleMove);
		return index;
	}

	public static int minimumNumberOfPossibleMoves()
	{
		return 0;
	}

	public boolean addPossibleMove(Tile aPossibleMove)
	{
		boolean wasAdded = false;
		if (possibleMoves.contains(aPossibleMove)) { return false; }
		possibleMoves.add(aPossibleMove);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removePossibleMove(Tile aPossibleMove)
	{
		boolean wasRemoved = false;
		if (possibleMoves.contains(aPossibleMove))
		{
			possibleMoves.remove(aPossibleMove);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	public boolean addPossibleMoveAt(Tile aPossibleMove, int index)
	{  
		boolean wasAdded = false;
		if(addPossibleMove(aPossibleMove))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfPossibleMoves()) { index = numberOfPossibleMoves() - 1; }
			possibleMoves.remove(aPossibleMove);
			possibleMoves.add(index, aPossibleMove);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMovePossibleMoveAt(Tile aPossibleMove, int index)
	{
		boolean wasAdded = false;
		if(possibleMoves.contains(aPossibleMove))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfPossibleMoves()) { index = numberOfPossibleMoves() - 1; }
			possibleMoves.remove(aPossibleMove);
			possibleMoves.add(index, aPossibleMove);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addPossibleMoveAt(aPossibleMove, index);
		}
		return wasAdded;
	}

	public void delete()
	{
		possibleMoves.clear();
	}

	// line 87 "../../../../../PlayControllerStatus.ump"
	public void doStartGame(Game selectedGame) throws InvalidInputException{
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

		if(players.isEmpty() || players.size() == 1){

			throw new InvalidInputException("2 or more players must be defined.");

		}

		if(selectedGame.numberOfConnections() > 32){
			throw new InvalidInputException("The game should have more than 32 connection tiles.");
		}

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

		currentDeck.setCurrentCard(currentDeck.getCard(0));

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
		selectedGame.setCurrentPlayer(selectedGame.getPlayers().get(0));


		//set the current connection pieces to the spare connection pieces
		selectedGame.setCurrentConnectionPieces(selectedGame.SpareConnectionPieces);

		//set the mode of the current game to GAME
		selectedGame.setMode(Game.Mode.GAME);
	}

	// line 156 "../../../../../PlayControllerStatus.ump"
	public List<Tile> doRollDie(){
		//get the current game from the TileO application
		Game currentGame = tileO.getCurrentGame();

		//get die from the current game
		Die die = currentGame.getDie();

		//roll the die to generate a number

		int number = die.roll();
		//getting the current player from the current game
		Player currentPlayer = currentGame.getCurrentPlayer();

		//get the possible moves of the current player, depending on the generated number
		List<Tile> tiles = currentPlayer.getPossibleMoves(currentPlayer.getCurrentTile(), number);
		
		

		return tiles;
	}


	/**
	 * TODO check if it's necessary to set the mode here (already set in the model)
	 */
	// line 178 "../../../../../PlayControllerStatus.ump"
	public void doLandTile(Tile tile) throws InvalidInputException{
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
			currentGame.setMode(Game.Mode.GAME);
		}
		//if the tile is of type WinTile
		else if(tile instanceof WinTile){
			WinTile winTile = (WinTile) tile;
			winTile.land();
			currentGame.setMode(Game.Mode.GAME_WON);
		}
		//if the tile is of type ActionTile
		else if(tile instanceof ActionTile){
			ActionTile actionTile = (ActionTile) tile;

			//if the action tile is active, call the land of the action tile and set the inactivity period
			//otherwise, it acts like a normal tile

			actionTile.land();
		}
	}

	// line 215 "../../../../../PlayControllerStatus.ump"
	public List<Tile> doPlayRollDieActionCard() throws InvalidInputException{
		// get the current game

		Game currentGame = tileO.getCurrentGame();

		// get the deck of the current game

		Deck currentDeck = currentGame.getDeck();


		//check if the current card is a RollDieActionCard

		if (!(currentDeck.getCurrentCard() instanceof RollDieActionCard)){


			throw new InvalidInputException("The Current Card is not a RollDieActionCard.");
		}


		RollDieActionCard currentCard = (RollDieActionCard) currentDeck.getCurrentCard();



		List<Tile> listOfTiles = currentCard.play();


		// set the currentCard to be the next card so that the next
		// time a player draws a card , he gets the next card
		// if the current card is the last card of the deck
		// shuffle the deck

		if ( (currentDeck.indexOfCard(currentCard) + 1 )   == currentDeck.numberOfCards()){
			currentDeck.shuffle();			
			currentDeck.setCurrentCard(currentDeck.getCard(0));
		}
		else {
			currentDeck.setCurrentCard(currentDeck.getCard(currentDeck.indexOfCard(currentCard)+1 ));
		}
		// set the mode of the current game to GAME
		currentGame.setMode(Game.Mode.GAME);		


		return listOfTiles;
	}

	// line 266 "../../../../../PlayControllerStatus.ump"
	public void doPlayConnectTilesActionCard(Tile tile1, Tile tile2) throws InvalidInputException{
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

		if( ! ( (( tile1.getY() == tile2.getY() )  &&  (  ( tile1.getX() == tile2.getX() -40 ) || (tile1.getX() == tile2.getX() + 40)) )|| ( ( tile1.getX() == tile2.getX() )  &&  (  ( tile1.getY() == tile2.getY() -40 ) || (tile1.getY() == tile2.getY() + 40))))){

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
		currentCard.play(tile1, tile2);

		//determining the next player
		currentGame.determineNextPlayer();

		//decrement the inactivity period of inactive action tiles
		currentGame.updateTileStatus();

		// set the currentCard to be the next card so that the next
		// time a player draws a card , he gets the next card
		// if the current card is the last card of the deck
		// shuffle the deck


		if ( (currentDeck.indexOfCard(currentCard) + 1 )   == currentDeck.numberOfCards()){

			currentDeck.shuffle();
			currentDeck.setCurrentCard(currentDeck.getCard(0));
		}
		else {

			currentDeck.setCurrentCard(currentDeck.getCard(currentDeck.indexOfCard(currentCard)+1 ));

		}

		// set the mode of the current game to GAME
		currentGame.setMode(Game.Mode.GAME);
	}

	// line 400 "../../../../../PlayControllerStatus.ump"
	public void doPlayRemoveConnectionActionCard(Connection connection) throws InvalidInputException{
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



		currentCard.play(connection);

		//determining the next player
		currentGame.determineNextPlayer();

		//decrement the inactivity period of inactive action tiles
		currentGame.updateTileStatus();
		
		// set the currentCard to be the next card so that the next
		// time a player draws a card , he gets the next card
		// if the current card is the last card of the deck
		// shuffle the deck

		if ( (currentDeck.indexOfCard(currentCard) + 1 )   == currentDeck.numberOfCards()){
			currentDeck.shuffle();		
			currentDeck.setCurrentCard(currentDeck.getCard(0));
		}
		else {
			currentDeck.setCurrentCard(currentDeck.getCard(currentDeck.indexOfCard(currentCard)+1 ));
		}

		// set the mode of the current game to GAME
		currentGame.setMode(Game.Mode.GAME);
	}

	// line 509 "../../../../../PlayControllerStatus.ump"
	public void doPlayTeleportActionCard(Tile tile) throws InvalidInputException{
		// get the current game

		Game currentGame = tileO.getCurrentGame();

		List<Tile> tiles = currentGame.getTiles();

		// check if both tiles passed in exist in the game

		if(! tiles.contains(tile)){
			throw new InvalidInputException("The tile is not found in the current game.");
		}


		// get the currentDeck

		Deck currentDeck = currentGame.getDeck();


		// check if the current card is a TeleportActionCard

		if (!(currentDeck.getCurrentCard() instanceof TeleportActionCard)){


			throw new InvalidInputException("The Current Card is not a TeleportActionCard.");


		}

		TeleportActionCard currentCard = (TeleportActionCard) currentDeck.getCurrentCard();

		// teleport the player to the selected tile	

		currentCard.play(tile);

		//determining the next player
		currentGame.determineNextPlayer();

		//decrement the inactivity period of inactive action tiles
		currentGame.updateTileStatus();

		// set the currentCard to be the next card so that the next
		// time a player draws a card , he gets the next card
		// if the current card is the last card of the deck
		// shuffle the deck

		if ( (currentDeck.indexOfCard(currentCard) + 1 )   == currentDeck.numberOfCards()){
			currentDeck.shuffle();	
			currentDeck.setCurrentCard(currentDeck.getCard(0));

		}
		else {
			currentDeck.setCurrentCard(currentDeck.getCard(currentDeck.indexOfCard(currentCard)+1 ));
		}

		// set the mode of the current game to GAME
		currentGame.setMode(Game.Mode.GAME);
	}

	// line 616 "../../../../../PlayControllerStatus.ump"
	public void doPlayLoseTurnActionCard() throws InvalidInputException{
		// get the current game

		Game currentGame = tileO.getCurrentGame();


		// get the currentDeck

		Deck currentDeck = currentGame.getDeck();


		// check if the current card is a LoseTurnActionCard

		if (!(currentDeck.getCurrentCard() instanceof LoseTurnActionCard)){
			throw new InvalidInputException("The Current Card is not a LoseTurnActionCard.");
		}

		LoseTurnActionCard currentCard = (LoseTurnActionCard) currentDeck.getCurrentCard();

		// the player that drew the card loses his Next turn

		currentCard.play();


		//determining the next player
		currentGame.determineNextPlayer();

		//decrement the inactivity period of inactive action tiles
		currentGame.updateTileStatus();

		// set the currentCard to be the next card so that the next
		// time a player draws a card , he gets the next card
		// if the current card is the last card of the deck
		// shuffle the deck

		if ( (currentDeck.indexOfCard(currentCard) + 1 )   == currentDeck.numberOfCards()){
			currentDeck.shuffle();	
			currentDeck.setCurrentCard(currentDeck.getCard(0));

		}
		else {
			currentDeck.setCurrentCard(currentDeck.getCard(currentDeck.indexOfCard(currentCard)+1 ));
		}

		// set the mode of the current game to GAME
		currentGame.setMode(Game.Mode.GAME);
	}


	/**
	 * TODO
	 * this method takes care of 1 case: when the player is stuck on a tile in which
	 * he can move nowhere. We should not let him move
	 */
	// line 716 "../../../../../PlayControllerStatus.ump"
	public void noMoves(){
		Game currentGame = tileO.getCurrentGame();
		//getting the current player
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

		//set the mode of game of GAME
		currentGame.setMode(Game.Mode.GAME);
	}


	/**
	 * check if the chose is a valid move for the player
	 */
	// line 744 "../../../../../PlayControllerStatus.ump"
	public Boolean isValidMove(List<Tile> possibleMoves, Tile tile){
		for(Tile acceptableTile : possibleMoves){
			if( (acceptableTile.getX() == tile.getX()) && (acceptableTile.getY() == tile.getY()) ){
				return true;
			}
		}
		return false;
	}

	/**
	 * get games from the model
	 */
	// line 773 "../../../../../PlayControllerStatus.ump"
	public List<Game> getGames(){
		return tileO.getGames();
	}

	// line 777 "../../../../../PlayControllerStatus.ump"
	public Game.Mode getGameMode(){
		return tileO.getCurrentGame().getMode();
	}


	/**
	 * get the current player from the current game
	 */
	// line 783 "../../../../../PlayControllerStatus.ump"
	public Player getCurrentPlayer(){
		return tileO.getCurrentGame().getCurrentPlayer();
	}

	// line 787 "../../../../../PlayControllerStatus.ump"
	public int getCurrentPlayerIndex(){
		return tileO.getCurrentGame().indexOfPlayer(tileO.getCurrentGame().getCurrentPlayer());
	}

	// line 792 "../../../../../PlayControllerStatus.ump"
	public void saveGame(){
		TileOApplication.save();
	}

	// line 796 "../../../../../PlayControllerStatus.ump"
	public int getNumberRemainingPieces(){
		return Game.SpareConnectionPieces - tileO.getCurrentGame().numberOfConnections();
	}


	/**
	 * Load Game in Play Mode
	 */
	// line 801 "../../../../../PlayControllerStatus.ump"
	public static  Game doLoad(Game selectedGame){
		TileO tileo = TileOApplication.load();
		return selectedGame;
	}

	
	private boolean isNotInGameOrWonMode(Game selectedGame) {
		if(selectedGame.getMode().equals(Game.Mode.GAME) || (selectedGame.getMode().equals(Game.Mode.GAME_WON)) ){
			return false;
		}
		return true;
	}

	private boolean isInWonMode(Game selectedGame) {
		if(selectedGame.getMode().equals(Game.Mode.GAME_WON)){
			return true;
		}
		return false;
	}

	private boolean isInGameMode(Game selectedGame) {
		if(selectedGame.getMode().equals(Game.Mode.GAME)){
			return true;
		}
		return false;
	}
	
	private boolean isActionTile(Tile tile) {
		if(tile instanceof ActionTile){
			return true;
		}
		return false;
	}

	private boolean isWinTile(Tile tile) {
		if(tile instanceof WinTile){
			return true;
		}
		return false;
	}

	private boolean isNormalTile(Tile tile) {
		if(tile instanceof NormalTile){
			return true;
		}
		return false;
	}
	
	private boolean isLoseTurnActionCard() {
		if(tileO.getCurrentGame().getDeck().getCurrentCard() instanceof LoseTurnActionCard){
			return true;
		}
		return false;
	}
	
	private boolean isTeleportAndActionTile(Tile tile) {
		if(tileO.getCurrentGame().getDeck().getCurrentCard() instanceof TeleportActionCard){
			if(tile instanceof ActionTile){
				return true;
			}
		}
		return false;
	}

	private boolean isTeleportAndWinTile(Tile tile) {
		if(tileO.getCurrentGame().getDeck().getCurrentCard() instanceof TeleportActionCard){
			if(tile instanceof WinTile){
				return true;
			}
		}
		return false;
	}

	private boolean isTeleportAndNormalTile(Tile tile) {
		if(tileO.getCurrentGame().getDeck().getCurrentCard() instanceof TeleportActionCard){
			if(tile instanceof NormalTile){
				return true;
			}
		}
		return false;
	}
	
	private boolean isRemoveConnectionActionCard() {
		if(tileO.getCurrentGame().getDeck().getCurrentCard() instanceof RemoveConnectionActionCard){
			return true;
		}
		return false;
	}
	private boolean isConnectTilesActionCard() {
		if(tileO.getCurrentGame().getDeck().getCurrentCard() instanceof ConnectTilesActionCard){
			return true;
		}
		return false;
	}
	private boolean isRollDieActionCard() {
		if(tileO.getCurrentGame().getDeck().getCurrentCard() instanceof RollDieActionCard){
			return true;
		}
		return false;
	}
	

}