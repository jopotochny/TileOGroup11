/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import java.util.List;

// line 76 "../../../../../TileO.ump"
public class RemoveConnectionActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RemoveConnectionActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

public void play(Connection connection){
	  
	  List<Tile> connectedTiles = connection.getTiles();
	  
	  for(Tile tile: connectedTiles){
		  connection.removeTile(tile);
	  }
	 
	  
  }

  public Game.Mode getActionCardGameMode(){
	  return Game.Mode.GAME_REMOVECONNECTIONACTIONCARD;
  }
  
  public void delete()
  {
    super.delete();
  }

}