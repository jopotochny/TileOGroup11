/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.*;

// line 21 "../../../../../TileOStates.ump"
// line 25 "../../../../../TileO.ump"
public class Player
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Player> playersByNumber = new HashMap<Integer, Player>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private int number;
  private int turnsUntilActive;

  //Player State Machines
  public enum PlayerStatus { Active, Inactive }
  private PlayerStatus playerStatus;
  public enum Color { RED, BLUE, GREEN, YELLOW }
  private Color color;

  //Player Associations
  private Tile startingTile;
  private Tile currentTile;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(int aNumber, Game aGame)
  {
    turnsUntilActive = 0;
    if (!setNumber(aNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate number");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
    setPlayerStatus(PlayerStatus.Active);
    setColor(Color.RED);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumber(int aNumber)
  {
    boolean wasSet = false;
    Integer anOldNumber = getNumber();
    if (hasWithNumber(aNumber)) {
      return wasSet;
    }
    number = aNumber;
    wasSet = true;
    if (anOldNumber != null) {
      playersByNumber.remove(anOldNumber);
    }
    playersByNumber.put(aNumber, this);
    return wasSet;
  }

  public boolean setTurnsUntilActive(int aTurnsUntilActive)
  {
    boolean wasSet = false;
    turnsUntilActive = aTurnsUntilActive;
    wasSet = true;
    return wasSet;
  }

  public int getNumber()
  {
    return number;
  }

  public static Player getWithNumber(int aNumber)
  {
    return playersByNumber.get(aNumber);
  }

  public static boolean hasWithNumber(int aNumber)
  {
    return getWithNumber(aNumber) != null;
  }

  public int getTurnsUntilActive()
  {
    return turnsUntilActive;
  }

  public String getPlayerStatusFullName()
  {
    String answer = playerStatus.toString();
    return answer;
  }

  public String getColorFullName()
  {
    String answer = color.toString();
    return answer;
  }

  public PlayerStatus getPlayerStatus()
  {
    return playerStatus;
  }

  public Color getColor()
  {
    return color;
  }

  public boolean loseTurns(int n)
  {
    boolean wasEventProcessed = false;
    
    PlayerStatus aPlayerStatus = playerStatus;
    switch (aPlayerStatus)
    {
      case Active:
        if (n>0)
        {
        // line 24 "../../../../../TileOStates.ump"
          setTurnsUntilActive(getTurnsUntilActive() + n);
          setPlayerStatus(PlayerStatus.Inactive);
          wasEventProcessed = true;
          break;
        }
        break;
      case Inactive:
        if (n>0)
        {
        // line 35 "../../../../../TileOStates.ump"
          setTurnsUntilActive(getTurnsUntilActive() + n);
          setPlayerStatus(PlayerStatus.Inactive);
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
    
    PlayerStatus aPlayerStatus = playerStatus;
    switch (aPlayerStatus)
    {
      case Inactive:
        if (getTurnsUntilActive()>1)
        {
        // line 29 "../../../../../TileOStates.ump"
          setTurnsUntilActive(getTurnsUntilActive() - 1);
          setPlayerStatus(PlayerStatus.Inactive);
          wasEventProcessed = true;
          break;
        }
        if (getTurnsUntilActive()<=1)
        {
        // line 32 "../../../../../TileOStates.ump"
          setTurnsUntilActive(0);
          setPlayerStatus(PlayerStatus.Active);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setPlayerStatus(PlayerStatus aPlayerStatus)
  {
    playerStatus = aPlayerStatus;
  }

  public boolean setColor(Color aColor)
  {
    color = aColor;
    return true;
  }

  public Tile getStartingTile()
  {
    return startingTile;
  }

  public boolean hasStartingTile()
  {
    boolean has = startingTile != null;
    return has;
  }

  public Tile getCurrentTile()
  {
    return currentTile;
  }

  public boolean hasCurrentTile()
  {
    boolean has = currentTile != null;
    return has;
  }

  public Game getGame()
  {
    return game;
  }

  public boolean setStartingTile(Tile aNewStartingTile)
  {
    boolean wasSet = false;
    startingTile = aNewStartingTile;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentTile(Tile aNewCurrentTile)
  {
    boolean wasSet = false;
    currentTile = aNewCurrentTile;
    wasSet = true;
    return wasSet;
  }

  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    playersByNumber.remove(getNumber());
    startingTile = null;
    currentTile = null;
    Game placeholderGame = game;
    this.game = null;
    placeholderGame.removePlayer(this);
  }

  // line 34 "../../../../../TileO.ump"
   public List<Tile> getPossibleMoves(Tile startTile, int moves){
    ArrayList<ArrayList<Tile>> pastVisits = new ArrayList<ArrayList<Tile>>();
	  	  
	  boolean flag = true;
	
	  
	  ArrayList<ArrayList<Tile>> children = new ArrayList<ArrayList<Tile>>();
	  
	  // at first , add the first tile to the list of visited tiles
	 
	  // while the number of moves hasn't been reached
	  while (moves > 0){
		  
		  ArrayList<ArrayList<Tile>> childrenTwo = new ArrayList<ArrayList<Tile>>();
		  ArrayList<ArrayList<Tile>> duplicates = new ArrayList<ArrayList<Tile>>();

		  
		  ArrayList<Tile> visitedTiles = new ArrayList<Tile>();
		  if(flag == true){
			  
		  ArrayList<Tile> visitedTilesBaseCase = new ArrayList<Tile>();
		  visitedTilesBaseCase.add(startTile);
		  
		  pastVisits.add(visitedTilesBaseCase);
		  flag = false;
		  }
		  
		  // loop through every previously visited Tile
		  for (Tile tile : pastVisits.get(pastVisits.size()-1)){
			  
			  
			  
			  ArrayList<Tile> tempChildren = new ArrayList<Tile>();
			  // get the neighbors of every child
			  
			  for( Tile tiles : tile.getNeighbours()){	
				 
				  	tempChildren.add(tiles);	  
				 }
			  

			  children.add(tempChildren);
			  
		  }
		  
		  
		  //we passed over every visited tile , we can clear the list
		  //now we have every neighbor , we want to check if they are
		  //contained in the last array of the pastVisited array of arrays
		  
		  
		  //but first we have to check for duplicate sets
		  //if there are any , take of them and put it in the list of children
		  
		  
		  //checking for duplicate sets , adding them directly to our final list of next children
		  int checkOne = 0;
		 
		  for(ArrayList<Tile> tileList : children){
			  int checkTwo = 0;
			  
			  for(ArrayList<Tile> tileListTwo : children){
				  
				  if((tileList.containsAll(tileListTwo))&&(tileListTwo.containsAll(tileList)) ){

					 if( checkOne != checkTwo){

						  for(Tile tilesTest : tileList){

						if(!duplicates.contains(tileList)){
					  duplicates.add(tileList);
						}
					  }
					 }
				  }
				  
				  
				  checkTwo++;
			  }		  
			  checkOne++;
		  }
		  
		  //for every list of child , only keep the ones that do not appear in the last visited set

		  int lastElementIndex = pastVisits.size();
		  if(lastElementIndex >= 2){
			 

			  ArrayList<Tile> checker = pastVisits.get(pastVisits.size()-2);

			 
		  for(ArrayList<Tile> tileListAgain : children){
			  ArrayList<Tile> temp = new ArrayList<Tile>();
			  for(Tile tile : tileListAgain){
				  
				  if(!checker.contains(tile)){
					  
					  temp.add(tile);
					  
				  }
			  }
			  
			  childrenTwo.add(temp);
			  
		  }
		  
		  //for the base case since there is no previous visited tile
		  }else{


			  for(ArrayList<Tile> tileListAgainTwo : children){
				  ArrayList<Tile> tempTwo = new ArrayList<Tile>();

				  for(Tile tile : tileListAgainTwo){	
					  

						  tempTwo.add(tile);
						  			  
			  }

			  childrenTwo.add(tempTwo);
		  }
			  
			  
		  }	  
		children.clear();
		visitedTiles.clear();
		
		for(ArrayList<Tile> moreLists : childrenTwo){
			for(Tile tilesOfChildrenTwo : moreLists){

				visitedTiles.add(tilesOfChildrenTwo);
			}
		}
		for(ArrayList<Tile> moreListsTwo : duplicates){
			for(Tile tilesOfDuplicates : moreListsTwo){

				visitedTiles.add(tilesOfDuplicates);
			}
		}
			
		  // once we finish with the visited tiles
		  // we add them to the hashset of previously visited
		  // and clear the list of visited tiles
		  pastVisits.add(visitedTiles);

		  	  
		  moves-- ;
		  
  }
	  

	  return pastVisits.get(pastVisits.size()-1);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "," +
            "turnsUntilActive" + ":" + getTurnsUntilActive()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startingTile = "+(getStartingTile()!=null?Integer.toHexString(System.identityHashCode(getStartingTile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentTile = "+(getCurrentTile()!=null?Integer.toHexString(System.identityHashCode(getCurrentTile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null")
     + outputString;
  }
}