package ca.mcgill.ecse223.tileo.controller;
import java.util.List;

import ca.mcgill.ecse223.tileo.model.ActionTile;
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
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;
import ca.mcgill.ecse223.tileo.persistence.PersistenceXStream;
public class DesignController {
	private TileO tileo;
	public DesignController(TileO tileo){
		this.tileo = tileo;
	}
	
	public void createGame(){
		Game game = new Game(32, tileo);
		tileo.setCurrentGame(game);
	}
	
	public void addConnectionDuringDesign(Tile tileOne, Tile tileTwo) throws InvalidInputException{
		//System.out.println("tile one coordinates " + tileOne.getX() + " " + tileOne.getY() + " tile2 coords " + tileTwo.getX() + " " + tileTwo.getY());
		if((this.tileo.getCurrentGame().indexOfTile(tileOne) == -1) || (this.tileo.getCurrentGame().indexOfTile(tileTwo) == -1)){
			
			throw new InvalidInputException("One or more of the tiles do not exist");
		}
		if(!((tileOne.getX() - tileTwo.getX() == 0 && tileOne.getY() - tileTwo.getY() == 40) || (tileOne.getX() - tileTwo.getX() == 0 && tileOne.getY() - tileTwo.getY() == -40) || (tileOne.getY() - tileTwo.getY() == 0 && tileOne.getX() - tileTwo.getX() == 40) || (tileOne.getY() - tileTwo.getY() == 0 && tileOne.getX() - tileTwo.getX() == -40) )){
			
			throw new InvalidInputException("tile1 and tile2 are not adjacent.");
			
		}
	
		Connection theConnection = new Connection(this.tileo.getCurrentGame());	
		theConnection.addTile(tileOne);
		theConnection.addTile(tileTwo);
		//PersistenceXStream.saveToXMLwithXStream(tileo);
	}
	public void addTileToBoard(int X, int Y) throws InvalidInputException{
		for(Tile tile : this.tileo.getCurrentGame().getTiles()){
			if((tile.getX() == X) && (tile.getY() == Y)){
				throw new InvalidInputException("A tile already exists at those coordinates");
			}
		}
		NormalTile theTile = new NormalTile(X, Y, this.tileo.getCurrentGame());
		//PersistenceXStream.saveToXMLwithXStream(tileo);
		//System.out.println("in the controller " + this.tileo.getCurrentGame().getTiles().size());
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
				//System.out.println("has win tile in remove is " + this.tileo.getCurrentGame().hasWinTile());
				
			} 
		}
		else {
			theTile.delete();

		}
		//PersistenceXStream.saveToXMLwithXStream(tileo);
	}
	public void identifyWinTile(int x, int y) throws InvalidInputException {
		Game game = tileo.getCurrentGame();
		if (game.hasWinTile() == true) {
			System.out.println(game.getWinTile().getX() + " ***** " + game.getWinTile().getY());
			throw new InvalidInputException("The win tile has already been set");
		} else {
			WinTile winTile = new WinTile(x, y, game);
			game.setWinTile(winTile);
		}
		//System.out.println("has win tile in identify is " + this.tileo.getCurrentGame().hasWinTile());
		//PersistenceXStream.saveToXMLwithXStream(tileo);
	}
	


	public void identifyActionTile(int x, int y, int inactivityPeriod) throws InvalidInputException {
		Game game = tileo.getCurrentGame();
		System.out.println("in action tiles");
		List<Tile> tiles = game.getTiles();

		for (Tile t : tiles) {
			if (t.getX() == x && t.getY() == y) {
				throw new InvalidInputException("Action tile already exists");
			}
			
		}
		System.out.println("im in action tile ***");
		ActionTile actionT = new ActionTile(x, y, game, inactivityPeriod);
		//PersistenceXStream.saveToXMLwithXStream(tileo);
	}

	public void identifyStartTile(Tile aStart) throws InvalidInputException {
		Game game = tileo.getCurrentGame();
		boolean conflictingStart = false;
		if(game.getPlayers().size() == 4){
			throw new InvalidInputException("There are already 4 players");
		}
		
		if(game.getPlayers().size() == 0){
			Player newPlayer = new Player(game.getPlayers().size()+1, game);
			newPlayer.setStartingTile(aStart);
		}else {
			
		//System.out.println("the x is " + game.getPlayer(0).getStartingTile().getY());
			for (int i = 0; i < game.getPlayers().size()-1; i++) {
				//if(p == null) continue;
				//if(game.getPlayers().size() > 1){
					Tile startingTile = game.getPlayer(i).getStartingTile();
					//if(startingTile == null) continue;
					//System.out.println("succ");
					if (startingTile.getX() == aStart.getX() && startingTile.getY() == aStart.getY()) {
						conflictingStart = true;
						break;
						
					}
				
			}
			if(conflictingStart){
				throw new InvalidInputException("A player already exists at this tile");
			} else {
				Player newPlayer = new Player(game.getPlayers().size()+1, game);
				newPlayer.setStartingTile(aStart);
			}
		}
		//PersistenceXStream.saveToXMLwithXStream(tileo);
	}

	public void selectCards(int loseTurn, int connect, int rollDie, int remove, int teleport)
			throws InvalidInputException {

		Game game = tileo.getCurrentGame();

		Deck deck = game.getDeck(); 

		if ((loseTurn + connect + rollDie + remove + teleport) != 32) {
			throw new InvalidInputException("The amount of cards chosen is not 32");
		}
		for (int i = 1; i <= loseTurn; i++) {
			LoseTurnActionCard loseCard = new LoseTurnActionCard("Lose your turn", deck);
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
		//PersistenceXStream.saveToXMLwithXStream(tileo);
	}
}
