/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.24.0-dab6b48 modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;


// line 394 "../../../../../TileO.ump"
public class RevealTileActionCard extends ActionCard implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RevealTileActionCard(String aInstructions, Deck aDeck)
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

  // line 398 "../../../../../TileO.ump"
   public String play(Tile aTile){
    String type = null;
		if (aTile instanceof NormalTile) {
				type = "Normal Tile";
			}
			else if (aTile instanceof ActionTile) {
				type = "Action Tile";
			}
			else if (aTile instanceof WinTile) {
				type = "Win Tile";
			}
			return type;
  }

  // line 412 "../../../../../TileO.ump"
   public Game.Mode getActionCardGameMode(){
    return Game.Mode.GAME_REVEALTILEACTIONCARD;
  }
   private static final long serialVersionUID = -0202020202020202020L;

}