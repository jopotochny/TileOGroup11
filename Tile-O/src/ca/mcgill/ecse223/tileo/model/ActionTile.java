/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.*;

// line 2 "../../../../../TileOStates.ump"
// line 224 "../../../../../TileO.ump"
public class ActionTile extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ActionTile Attributes
  private int inactivityPeriod;
  private int turnsUntilActive;

  //ActionTile State Machines
  public enum ActionTileStatus { Active, Inactive }
  private ActionTileStatus actionTileStatus;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ActionTile(int aX, int aY, Game aGame, int aInactivityPeriod)
  {
    super(aX, aY, aGame);
    inactivityPeriod = aInactivityPeriod;
    turnsUntilActive = 0;
    setActionTileStatus(ActionTileStatus.Active);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTurnsUntilActive(int aTurnsUntilActive)
  {
    boolean wasSet = false;
    turnsUntilActive = aTurnsUntilActive;
    wasSet = true;
    return wasSet;
  }

  public int getInactivityPeriod()
  {
    return inactivityPeriod;
  }

  public int getTurnsUntilActive()
  {
    return turnsUntilActive;
  }

  public String getActionTileStatusFullName()
  {
    String answer = actionTileStatus.toString();
    return answer;
  }

  public ActionTileStatus getActionTileStatus()
  {
    return actionTileStatus;
  }

  public boolean deactivate()
  {
    boolean wasEventProcessed = false;
    
    ActionTileStatus aActionTileStatus = actionTileStatus;
    switch (aActionTileStatus)
    {
      case Active:
        if (getInactivityPeriod()>0)
        {
        // line 5 "../../../../../TileOStates.ump"
          setTurnsUntilActive(getInactivityPeriod() + 1 );
          setActionTileStatus(ActionTileStatus.Inactive);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean takeTurn()
  {
    boolean wasEventProcessed = false;
    
    ActionTileStatus aActionTileStatus = actionTileStatus;
    switch (aActionTileStatus)
    {
      case Inactive:
        if (getTurnsUntilActive()>1)
        {
        // line 10 "../../../../../TileOStates.ump"
          setTurnsUntilActive(getTurnsUntilActive() - 1);
          setActionTileStatus(ActionTileStatus.Inactive);
          wasEventProcessed = true;
          break;
        }
        if (getTurnsUntilActive()<=1)
        {
        // line 13 "../../../../../TileOStates.ump"
          setTurnsUntilActive(0);
          setActionTileStatus(ActionTileStatus.Active);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setActionTileStatus(ActionTileStatus aActionTileStatus)
  {
    actionTileStatus = aActionTileStatus;
  }

  public void delete()
  {
    super.delete();
  }

  // line 231 "../../../../../TileO.ump"
   public void land(){
    //getting the current game for which the tile belongs to
		Game currentGame = this.getGame();

		//getting the current player
		Player currentPlayer = currentGame.getCurrentPlayer();

		//setting the current tile of the player to this tile
		currentPlayer.setCurrentTile(this);

		//set the current tile to has been visited
		this.setHasBeenVisited(true);

		if(this.getActionTileStatus() == ActionTileStatus.Active){

			//get the current deck
			Deck deck = currentGame.getDeck();

			//get the current card from the top of the deck
			ActionCard currentCard = deck.getCurrentCard();

			Game.Mode mode;

			//if the type of the card is roll die, get the mode and set it in the current game
			if(currentCard instanceof RollDieActionCard){
				RollDieActionCard rollDieActionCard = (RollDieActionCard) currentCard;
				mode = rollDieActionCard.getActionCardGameMode();
				currentGame.setMode(mode);
			}
			//if the type of the card is connect tiles, get the mode and set it in the current game
			else if(currentCard instanceof ConnectTilesActionCard){
				ConnectTilesActionCard connectTilesActionCard = (ConnectTilesActionCard) currentCard;
				mode = connectTilesActionCard.getActionCardGameMode();
				currentGame.setMode(mode);
			}
			//if the type of the card is remove connection, get the mode and set it in the current game
			else if(currentCard instanceof RemoveConnectionActionCard){
				RemoveConnectionActionCard removeConnectionActionCard = (RemoveConnectionActionCard) currentCard;
				mode = removeConnectionActionCard.getActionCardGameMode();
				currentGame.setMode(mode);
			}
			//if the type of the card is lost turn, get the mode and set it in the current game
			else if(currentCard instanceof LoseTurnActionCard){
				LoseTurnActionCard loseTurnActionCard = (LoseTurnActionCard) currentCard;
				mode = loseTurnActionCard.getActionCardGameMode();
				currentGame.setMode(mode);
			}else if(currentCard instanceof TeleportActionCard){
				TeleportActionCard loseTurnActionCard = (TeleportActionCard) currentCard;
				mode = loseTurnActionCard.getActionCardGameMode();
				currentGame.setMode(mode);
			}
			this.deactivate();
		}else{
			//determining the next player
			currentGame.determineNextPlayer();
			
			//decrement the inactivity of action tiles
			currentGame.updateTileStatus();
			
			//set the mode of game of GAME
			currentGame.setMode(Game.Mode.GAME);
			
		}
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "inactivityPeriod" + ":" + getInactivityPeriod()+ "," +
            "turnsUntilActive" + ":" + getTurnsUntilActive()+ "]"
     + outputString;
  }
}