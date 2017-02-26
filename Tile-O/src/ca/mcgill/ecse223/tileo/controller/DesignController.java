package ca.mcgill.ecse223.tileo.controller;
import java.util.List;

import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.persistence.PersistenceXStream;
public class DesignController {
	private TileO tileo;
	public DesignController(TileO tileO){
		this.tileo = tileO;
	}
	public void addConnectionDuringDesign(Tile tileOne, Tile tileTwo) throws InvalidInputException{
		if((this.tileo.getCurrentGame().indexOfTile(tileOne) == -1) || (this.tileo.getCurrentGame().indexOfTile(tileTwo) == -1)){
			throw new InvalidInputException("One or more of the tiles do not exist");
		}
		if( ! ( (( tileOne.getY() == tileTwo.getY() )  &&  (  ( tileOne.getX() == tileTwo.getX() -1 ) || (tileOne.getX() == tileTwo.getX() + 1)) )|| ( ( tileOne.getX() == tileTwo.getX() )  &&  (  ( tileOne.getY() == tileTwo.getY() -1 ) || (tileOne.getY() == tileTwo.getY() + 1))))){

			throw new InvalidInputException("tile1 and tile2 are not adjacent.");
		}
		Connection theConnection = new Connection(this.tileo.getCurrentGame());	
		theConnection.addTile(tileOne);
		theConnection.addTile(tileTwo);
		
			
	}
	public void addTileToBoard(int X, int Y) throws InvalidInputException{
		for(Tile tile : this.tileo.getCurrentGame().getTiles()){
			if((tile.getX() == X) && (tile.getY() == Y)){
				throw new InvalidInputException("A tile already exists at those coordinates");
			}
		}
		NormalTile theTile = new NormalTile(X, Y, this.tileo.getCurrentGame());
		
	}
	public void removeConnectionDuringDesign(Connection theConnection)throws InvalidInputException{
		if(this.tileo.getCurrentGame().indexOfConnection(theConnection) == -1 || theConnection ==  null){
			throw new InvalidInputException("Connection does not exist");
		}
		theConnection.delete();
		PersistenceXStream.saveToXMLwithXStream(tileo);
	}
	public void removeTileFromBoard(Tile theTile) throws InvalidInputException{
		if(this.tileo.getCurrentGame().indexOfTile(theTile) == -1 || theTile == null){
			throw new InvalidInputException("Could not remove tile as it does not exist");
		}
		theTile.delete();
		
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
			ActionTile actionT = new ActionTile(x, y, game, inactivityPeriod);
		}
		

	}

	public void identifyStartTile(NormalTile aStart, int player) throws InvalidInputException {
		Game game = tileo.getCurrentGame();
		//System.out.println("the x is " + game.getPlayer(0).getStartingTile().getY());
		for (int i = 0 ; i<game.getPlayers().size();i++) {
			//if(p == null) continue;
			if(game.getPlayers().size() > 1){
				final Tile startingTile = game.getPlayer(i).getStartingTile();
				//if(startingTile == null) continue;
				if (startingTile.getX() == aStart.getX() && startingTile.getY() == aStart.getY()) {
					throw new InvalidInputException("There is already a player on this starting tile");
				}
			}else {
				game.getPlayer(player).setStartingTile(aStart);
			}
		}
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
	}
}