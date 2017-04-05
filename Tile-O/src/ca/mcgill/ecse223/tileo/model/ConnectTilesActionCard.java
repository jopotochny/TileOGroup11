/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;

// line 490 "../../../../../TileO.ump"
public class ConnectTilesActionCard extends ActionCard implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ConnectTilesActionCard(String aInstructions, Deck aDeck)
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

  // line 495 "../../../../../TileO.ump"
   public void play(Tile tile1, Tile tile2){
	   Game game = super.getDeck().getGame();
	  
	  Connection connection = new Connection (game);
	  
	  game.addConnection(connection) ;  
	  
	  tile1.addConnection(connection);
	  tile2.addConnection(connection);
	  
	  //decrementing the number of connection pieces
	  game.setCurrentConnectionPieces(game.getCurrentConnectionPieces() - 1);
  }

  // line 507 "../../../../../TileO.ump"
   public Game.Mode getActionCardGameMode(){
    return Game.Mode.GAME_CONNECTTILESACTIONCARD;
  }

   private static final long serialVersionUID = -3030303030303030303L;

}