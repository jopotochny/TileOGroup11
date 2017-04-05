/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import java.io.Serializable;
import java.util.List;

// line 388 "../../../../../TileO.ump"
public class NextPlayerRollsOneActionCard extends ActionCard implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public NextPlayerRollsOneActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 396 "../../../../../TileO.ump"
   public Game.Mode getActionCardGameMode(){
    return Game.Mode.GAME_NEXTPLAYERROLLSONEACTIONCARD;
  }

   public List<Tile> play(){
	    //get the current game from the TileO application
			  Game currentGame = super.getDeck().getGame();
			  	
			  	currentGame.determineNextPlayer();

				//getting the current player from the current game
				Player currentPlayer = currentGame.getCurrentPlayer();

				//get the possible moves of the current player, depending on the generated number
				List<Tile> tiles = currentPlayer.getPossibleMoves(currentPlayer.getCurrentTile(), 1);

				return tiles;
	  }
   private static final long serialVersionUID = -8080808080808080808L;

}