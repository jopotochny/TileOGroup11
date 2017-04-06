package ca.mcgill.ecse223.tileo.controller;
import java.util.Iterator;
import java.util.List;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Die;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.LoseTurnActionCard;
import ca.mcgill.ecse223.tileo.model.MoveOtherPlayerActionCard;
import ca.mcgill.ecse223.tileo.model.NextPlayerRollsOneActionCard;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.Player.Color;
import ca.mcgill.ecse223.tileo.model.RemoveConnectionActionCard;
import ca.mcgill.ecse223.tileo.model.RevealTileActionCard;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.SetActionTilesInactiveActionCard;
import ca.mcgill.ecse223.tileo.model.SwapPlayerPositionActionCard;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;
import ca.mcgill.ecse223.tileo.model.WinTileHintActionCard;



public class DesignController {
		private TileO tileo;
		private int maxPlayers = 4;
		
		
		
		public DesignController(TileO tileo){
			this.tileo = tileo;
		}
		
		public Game createGame(){
			Game game = new Game(32, tileo);
			tileo.setCurrentGame(game);
			return game;
		}
		
		public void saveGame(){
			TileOApplication.save();
		}

		public void addConnectionDuringDesign(Tile tileOne, Tile tileTwo) throws InvalidInputException{
			if(tileo.getCurrentGame().getConnections().size() == 32 ){
				throw new InvalidInputException("Cant create more than 32 connections");
			}
			for(Connection c : tileo.getCurrentGame().getConnections()){
			
				if((c.getTile(0) == tileOne && c.getTile(1) == tileTwo) || (c.getTile(1) == tileOne && c.getTile(0) == tileTwo)){
					
					throw new InvalidInputException("This connection already exists");
				}
			}
			if((this.tileo.getCurrentGame().indexOfTile(tileOne) == -1) || (this.tileo.getCurrentGame().indexOfTile(tileTwo) == -1)){
				
				throw new InvalidInputException("One or more of the tiles do not exist");
			}
			if(!((tileOne.getX() - tileTwo.getX() == 0 && tileOne.getY() - tileTwo.getY() == 40) || (tileOne.getX() - tileTwo.getX() == 0 && tileOne.getY() - tileTwo.getY() == -40) || (tileOne.getY() - tileTwo.getY() == 0 && tileOne.getX() - tileTwo.getX() == 40) || (tileOne.getY() - tileTwo.getY() == 0 && tileOne.getX() - tileTwo.getX() == -40) )){
				
				throw new InvalidInputException("tile1 and tile2 are not adjacent.");
				
			}
		
			Connection theConnection = new Connection(this.tileo.getCurrentGame());	
			theConnection.addTile(tileOne);
			theConnection.addTile(tileTwo);
			//decrementing the number of connection pieces
			//tileo.getCurrentGame().setCurrentConnectionPieces(tileo.getCurrentGame().getCurrentConnectionPieces() - 1);
			//PersistenceXStream.saveToXMLwithXStream(tileo);
		}
		public void addTileToBoard(int X, int Y) throws InvalidInputException{
			for(Tile tile : this.tileo.getCurrentGame().getTiles()){
				if((tile.getX() == X) && (tile.getY() == Y)){
					throw new InvalidInputException("A tile already exists at those coordinates");
				}
			}
			NormalTile theTile = new NormalTile(X, Y, this.tileo.getCurrentGame());
			//System.out.println("tile size is " + this.tileo.getCurrentGame().numberOfTiles());
			//PersistenceXStream.saveToXMLwithXStream(tileo);
			
		}
		public void removeConnectionDuringDesign(Tile tileOne, Tile tileTwo)throws InvalidInputException{
			Connection theConnection = null;
		
			
			for(Connection c : this.tileo.getCurrentGame().getConnections()){
				if(c.getTiles().contains(tileOne) && c.getTiles().contains(tileTwo)){
					theConnection = c;
				}
					
			}
			if(this.tileo.getCurrentGame().indexOfConnection(theConnection) == -1 || theConnection ==  null){
				throw new InvalidInputException("Connection does not exist");
			}
			theConnection.delete();
			//PersistenceXStream.saveToXMLwithXStream(tileo);
		}
		public void removeTileFromBoard(Tile theTile) throws InvalidInputException{
			
			
			
			if(this.tileo.getCurrentGame().indexOfTile(theTile) == -1 || theTile == null){
				throw new InvalidInputException("Could not remove tile as it does not exist");
			}
			
			if(this.tileo.getCurrentGame().getWinTile() != null){
				if(theTile.getX() == tileo.getCurrentGame().getWinTile().getX() && theTile.getY() == tileo.getCurrentGame().getWinTile().getY()){
					this.tileo.getCurrentGame().getWinTile().delete();
					this.tileo.getCurrentGame().setWinTile(null);
					
					
				} 
			}
			
			
			else {
				int counter = 0;
				
				Iterator<Connection> iter = theTile.getConnections().iterator();
				while(iter.hasNext() && counter<theTile.getConnections().size()){
					removeConnectionDuringDesign(theTile, theTile.getConnection(counter).getTile(counter));
					
					
				}
				theTile.delete();

			}
			//PersistenceXStream.saveToXMLwithXStream(tileo);
		}
		public void identifyWinTile(int x, int y) throws InvalidInputException {
			Game game = tileo.getCurrentGame();
			if (game.hasWinTile() == true) {
				
				throw new InvalidInputException("The win tile has already been set");
			} else {
				WinTile winTile = new WinTile(x, y, game);
				game.setWinTile(winTile);
			}
			
		}
		


		
		public void identifyActionTile(int x, int y, int inactivityPeriod) throws InvalidInputException {
			Game game = tileo.getCurrentGame();
			
			List<Tile> tiles = game.getTiles();

			for (Tile t : tiles) {
				if (t.getX() == x && t.getY() == y) {
					throw new InvalidInputException("Action tile already exists");
				}
				
			}
			
			ActionTile actionT = new ActionTile(x, y, game, inactivityPeriod);
			
		}

		public void identifyStartTile(Tile aStart) throws InvalidInputException {
			Game game = tileo.getCurrentGame();
			boolean conflictingStart = false;
			if(game.getPlayers().size() == this.maxPlayers){
				throw new InvalidInputException("There are already " + this.maxPlayers + " players");
			}
			
			if(game.getPlayers().size() == 0){
				Player newPlayer = new Player(game.getPlayers().size()+1, game);
				newPlayer.setColor(Color.values()[0]);
				newPlayer.setStartingTile(aStart);
			}else {
				for (int i = 0; i < game.getPlayers().size(); i++) { //changed from getplayers.size to getplayers.size -1
					
					
						Tile startingTile = game.getPlayer(i).getStartingTile();
						//if(startingTile == null) continue;
						if (startingTile.getX() == aStart.getX() && startingTile.getY() == aStart.getY()) {
							conflictingStart = true;
							break;
							
						}
					
				}
				
				if(conflictingStart){
					throw new InvalidInputException("A player already exists at this tile");
				} else {
					
					Player newPlayer = new Player(game.getPlayers().size()+1, game);
					newPlayer.setColor(Color.values()[game.getPlayers().size()-1]);
					newPlayer.setStartingTile(aStart);
				}
			}
			
		}

		public void selectCards(int connect, int rollDie, int remove, int teleport, int lose, int hint , int next, int inactivity, int moveotherplayer, int reveal, int swap)
				throws InvalidInputException {

			Game game = tileo.getCurrentGame();

			Deck deck = game.getDeck(); 
			if(deck != null){
				
			}

			if (( reveal+connect + rollDie + remove + teleport + lose + hint + next + inactivity + moveotherplayer + swap) != 32) {
				throw new InvalidInputException("The amount of cards chosen is not 32");
			}
			for(int i = 1; i<=reveal; i++){
				RevealTileActionCard revealCard = new RevealTileActionCard("Reveal a tile", deck);
			}
			
			for(int j = 1; j<= hint; j++){
				WinTileHintActionCard hintCard = new WinTileHintActionCard("Chose a tile", deck) ;
					
			}

			for (int i = 1; i <= connect; i++) {
				ConnectTilesActionCard connectCard = new ConnectTilesActionCard("Connect tiles", deck);
			}

			for (int i = 1; i <= rollDie; i++) {
				RollDieActionCard rollCard = new RollDieActionCard("Roll again", deck);
			}

			for (int i = 1; i <= remove; i++) {
				RemoveConnectionActionCard removeCard = new RemoveConnectionActionCard("Remove connection", deck);
			}

			for (int i = 1; i <= teleport; i++) {
				TeleportActionCard teleportCard = new TeleportActionCard("Move to any tile", deck);
			}
			
			for (int i = 1; i <= lose; i++) {
				LoseTurnActionCard loseTurnCard = new LoseTurnActionCard("Lose next turn", deck);
			}
			
			for (int i = 1; i <= next; i++) {
				NextPlayerRollsOneActionCard nextCard = new NextPlayerRollsOneActionCard("Next Player rolls one", deck);
			}
			
			for (int i = 1; i <= inactivity; i++) {
				SetActionTilesInactiveActionCard nextCard = new SetActionTilesInactiveActionCard("Setting all action card inactive", deck);
			}
			for (int i = 1; i <= moveotherplayer; i++) {
				MoveOtherPlayerActionCard nextCard = new MoveOtherPlayerActionCard("Move other player", deck);
			}
			for (int i = 1; i <= swap; i++) {
				SwapPlayerPositionActionCard nextCard = new SwapPlayerPositionActionCard("Swap two players", deck);
			}
			//PersistenceXStream.saveToXMLwithXStream(tileo);
		}
	
	// Create a game which also creates deck and die
	public void createGame(int numberOfPlayers) {
		/*int numberOfConnectionPieces = 32;
		Game currentGame = new Game(numberOfConnectionPieces, tileo);
		tileo.setCurrentGame(currentGame);
		for(int i=1;i<numberOfPlayers+1;i++){
			Player player = new Player(i, currentGame);*/
		this.maxPlayers = numberOfPlayers;
		//return currentGame;
	}
	
	// Create Deck
	public void createDeck() throws InvalidInputException {
		Game currentGame = TileOApplication.getGame();
		try {
			Deck deck = currentGame.getDeck();

		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	// Create Dice
	public void createDice() throws InvalidInputException {
		Game currentGame = TileOApplication.getGame();
		try {
			Die die = currentGame.getDie();
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	
	}
	
	// Save design mode
	public static void saveDesign(){
		TileOApplication.save();
	}
	
	// Load Design mode
	public Game loadDesign(int index){
		TileO tileo = TileOApplication.load();
		Game game = tileo.getGame(index);
		return game;
	}
	
}