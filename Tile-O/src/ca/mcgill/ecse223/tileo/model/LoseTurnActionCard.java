/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

// line 84 "../../../../../TileO.ump"
public class LoseTurnActionCard extends ActionCard
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
  public Game.Mode getActionCardGameMode(){
	  return Game.Mode.GAME_LOSETURNACTIONCARD;
  }
  
  public void delete()
  {
    super.delete();
  }

public void play() {
	
	
	
	 //get the current game from the TileO application
	  Game currentGame = super.getDeck().getGame();
	  
	//getting the current player from the current game
	Player currentPlayer = currentGame.getCurrentPlayer();
	
	currentPlayer.setTurnsUntilActive(1);
	
}

}