/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

// line 80 "../../../../../TileO.ump"
public class TeleportActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TeleportActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

  
  public void play(Tile tile){
		
	  
		 Deck deck = super.getDeck();
		 
		 Game game = deck.getGame();
		 
		 Player player = game.getCurrentPlayer();
		 
		 player.setCurrentTile(tile);
		 
		 
			
		if( tile instanceof ActionTile){
			
			ActionTile actionTile = (ActionTile) tile;
			
			actionTile.land();
		
			
		}else if(tile instanceof WinTile){
			
			WinTile winTile = (WinTile) tile;
			
			winTile.land();
			
		}else if(tile instanceof NormalTile){
			
			NormalTile normalTile = (NormalTile) tile;		
			
			normalTile.land();
		}
		  
		  
		  
	  }

  
  public Game.Mode getActionCardGameMode(){
	  return Game.Mode.GAME_TELEPORTACTIONCARD;
  }
  
  public void delete()
  {
    super.delete();
  }

}