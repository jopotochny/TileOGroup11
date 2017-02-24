package ca.mcgill.ecse223.tileo.controller;
import ca.mcgill.ecse223.tileo.model.*;
public class DesignController {
	private TileO tileo;
	public DesignController(TileO tileo){
		this.tileo = tileo;
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
	}
	public void removeTileFromBoard(Tile theTile) throws InvalidInputException{
		if(this.tileo.getCurrentGame().indexOfTile(theTile) == -1 || theTile == null){
			throw new InvalidInputException("Could not remove tile as it does not exist");
		}
		theTile.delete();
	}
}