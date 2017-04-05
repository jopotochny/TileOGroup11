/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;
import java.util.*;

// line 359 "../../../../../TileO.ump"
public class NormalTile extends Tile implements Serializable
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

  // line 363 "../../../../../TileO.ump"
   public void land(){
    //getting the current game for which the tile belongs to
		Game currentGame = this.getGame();

		//getting the current player
		Player currentPlayer = currentGame.getCurrentPlayer();

		//setting the current tile of the player to this tile
		currentPlayer.setCurrentTile(this);
		
		//set the current tile to has been visited
		this.setHasBeenVisited(true);
		
		//if(!(currentGame.getMode().equals(Game.Mode.GAME_TELEPORTACTIONCARD))){	
			//determining the next player
			currentGame.determineNextPlayer();
		//}

		//decrement the inactivity of action tiles
		currentGame.updateTileStatus();

		//set the mode of game of GAME
		currentGame.setMode(Game.Mode.GAME);
  }

   private static final long serialVersionUID = -6666666666666666666L;

}