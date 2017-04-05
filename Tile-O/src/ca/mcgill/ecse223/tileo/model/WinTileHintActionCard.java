/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;


import java.util.List;

// line 400 "../../../../../TileO.ump"
public class WinTileHintActionCard extends ActionCard implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WinTileHintActionCard(String aInstructions, Deck aDeck)
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

  // line 404 "../../../../../TileO.ump"
   public boolean play(Tile tile){
    List<Tile> neighbours = tile.getNeighbours();
	  if(tile instanceof WinTile){
		  return true;
	  }
	  if(neighbours == null || neighbours.size() == 0){
		  return false;
	  }
	  for(Tile aTile : neighbours){
		if(aTile instanceof WinTile){
			return true;
		}
	  }
	  return false;
  }

  // line 420 "../../../../../TileO.ump"
   public Game.Mode getActionCardGameMode(){
    return Game.Mode.GAME_WINTILEHINTACTIONCARD;
  }
	
   private static final long serialVersionUID = -8765432123456789098L;

}