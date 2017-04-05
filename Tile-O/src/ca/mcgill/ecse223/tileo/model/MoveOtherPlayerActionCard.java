/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;

// line 578 "../../../../../TileO.ump"
public class MoveOtherPlayerActionCard extends ActionCard implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MoveOtherPlayerActionCard(String aInstructions, Deck aDeck)
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

  // line 583 "../../../../../TileO.ump"
   public void play(Player player, Tile tile){
    Deck deck = super.getDeck();
		 
		 Game game = deck.getGame();
		 
		 Player currentPlayer = game.getCurrentPlayer();
		 
		 game.setCurrentPlayer(player);
		 player.setCurrentTile(tile);
		 
		 
			
		if( tile instanceof ActionTile){
			
			ActionTile actionTile = (ActionTile) tile;
			
			actionTile.land();
			game.setCurrentPlayer(currentPlayer);
			
		}else if(tile instanceof WinTile){
			
			WinTile winTile = (WinTile) tile;
			
			winTile.land();
			
		}else if(tile instanceof NormalTile){
			
			NormalTile normalTile = (NormalTile) tile;		
			
			normalTile.land();
			game.setCurrentPlayer(currentPlayer);
		}
		
		
  }

  // line 616 "../../../../../TileO.ump"
   public Game.Mode getActionCardGameMode(){
    return Game.Mode.GAME_MOVEOTHERPLAYERACTIONCARD;
  }
   private static final long serialVersionUID = -8080808080808080809L;

}