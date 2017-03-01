/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

// line 72 "../../../../../TileO.ump"
public class ConnectTilesActionCard extends ActionCard
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
  
public void play(Tile tile1 , Tile tile2) {
	  
	  Game game = super.getDeck().getGame();
	  
	  Connection connection = new Connection (game);
	  
	  game.addConnection(connection) ;  
	  
	  tile1.addConnection(connection);
	  tile2.addConnection(connection);
		  
	  }
  
  public Game.Mode getActionCardGameMode(){
	  return Game.Mode.GAME_CONNECTTILESACTIONCARD;
  }
  
  public void delete()
  {
    super.delete();
  }

}