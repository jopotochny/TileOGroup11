/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.*;

// line 427 "../../../../../TileO.ump"
public class WinTile extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WinTile(int aX, int aY, Game aGame)
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

  // line 431 "../../../../../TileO.ump"
   public void land(){
    //getting the current game for which the tile belongs to
		Game currentGame = this.getGame();

		//set the current tile to has been visited
		this.setHasBeenVisited(true);

		//set the mode of game of GAME
		currentGame.setMode(Game.Mode.GAME_WON);
  }

}