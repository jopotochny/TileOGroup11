/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import java.util.List;

// line 462 "../../../../../TileO.ump"
public class RollDieActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RollDieActionCard(String aInstructions, Deck aDeck)
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

  // line 467 "../../../../../TileO.ump"
   public List<Tile> play(){
    //get the current game from the TileO application
		  Game currentGame = super.getDeck().getGame();

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

  // line 488 "../../../../../TileO.ump"
   public Game.Mode getActionCardGameMode(){
    return Game.Mode.GAME_ROLLDIEACTIONCARD;
  }

}