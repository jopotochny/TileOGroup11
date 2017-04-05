/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;

// line 570 "../../../../../TileO.ump"
public class LoseTurnActionCard extends ActionCard implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LoseTurnActionCard(String aInstructions, Deck aDeck)
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

  // line 575 "../../../../../TileO.ump"
   public void play(){
    //get the current game from the TileO application
		Game currentGame = super.getDeck().getGame();

		//getting the current player from the current game
		Player currentPlayer = currentGame.getCurrentPlayer();

		currentPlayer.loseTurns(1);
  }

  // line 586 "../../../../../TileO.ump"
   public Game.Mode getActionCardGameMode(){
    return Game.Mode.GAME_LOSETURNACTIONCARD;
  }
   private static final long serialVersionUID = -6060606060606060606L;

}