/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.*;

// line 46 "../../../../../TileO.ump"
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
  
  public void land(){
	  //getting the current game for which the tile belongs to
	  Game currentGame = this.getGame();
	  
	  //getting the current player
	  Player currentPlayer = currentGame.getCurrentPlayer();
	  
	  //setting the current tile of the player to this tile
	  currentPlayer.setCurrentTile(this);
	  
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
	  
	  //set the current tile to has been visited
	  this.setHasBeenVisited(true);
	  
	  //set the mode of game of GAME
	  currentGame.setMode(Game.Mode.GAME);	
  }
  
  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}