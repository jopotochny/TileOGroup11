/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import java.util.ArrayList;
import java.util.List;

// line 412 "../../../../../TileO.ump"
public class SetActionTilesInactiveActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SetActionTilesInactiveActionCard(String aInstructions, Deck aDeck)
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
  
  public void play(){
	  Game currentGame = this.getDeck().getGame();
	  List<Tile> tiles = currentGame.getTiles();
	  for(Tile tile : tiles){
		  if(tile instanceof ActionTile){
			  ActionTile actionTile = (ActionTile) tile;
			  actionTile.deactivate();
		  }
	  }
  }
  
  public Game.Mode getActionCardGameMode(){
	    return Game.Mode.GAME_SETACTIONTILESINACTIVEACTIONCARD;
  }
  
}