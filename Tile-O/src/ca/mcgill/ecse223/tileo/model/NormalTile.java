/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.*;

// line 348 "../../../../../TileO.ump"
public class NormalTile extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public NormalTile(int aX, int aY, Game aGame)
  {
    super(aX, aY, aGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 352 "../../../../../TileO.ump"
   public void land(){
    //getting the current game for which the tile belongs to
		Game currentGame = this.getGame();

		//getting the current player
		Player currentPlayer = currentGame.getCurrentPlayer();

		//setting the current tile of the player to this tile
		currentPlayer.setCurrentTile(this);
		
		//set the current tile to has been visited
		this.setHasBeenVisited(true);
		
		//determining the next player
		currentGame.determineNextPlayer();
	
		//decrement the inactivity of action tiles
		currentGame.updateTileStatus();

		//set the mode of game of GAME
		currentGame.setMode(Game.Mode.GAME);
  }

}