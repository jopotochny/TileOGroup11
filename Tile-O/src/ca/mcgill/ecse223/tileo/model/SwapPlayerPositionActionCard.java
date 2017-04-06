package ca.mcgill.ecse223.tileo.model;

import java.io.Serializable;


public class SwapPlayerPositionActionCard extends ActionCard implements Serializable{


	public SwapPlayerPositionActionCard(String aInstructions, Deck aDeck) {
		super(aInstructions, aDeck);
		// TODO Auto-generated constructor stub
	}

	public void play (Player swappedPlayer){
		Player currentPlayer = getDeck().getGame().getCurrentPlayer();

		Tile oldTile = currentPlayer.getCurrentTile();
		Tile newTile = swappedPlayer.getCurrentTile();

		currentPlayer.setCurrentTile(newTile);
		swappedPlayer.setCurrentTile(oldTile);
		
		/*
		currentPlayer = getDeck().getGame().getCurrentPlayer();
		Player tempPlayer = new Player(4,currentPlayer.getGame());

		Tile oldTile = currentPlayer.getCurrentTile();
		Tile newTile = swappedPlayer.getCurrentTile();
		Tile tempTile = currentPlayer.getGame().getTile(currentPlayer.getCurrentTile().getX()+1);

		tempPlayer.setCurrentTile(oldTile);
		currentPlayer.setCurrentTile(newTile);
		swappedPlayer.setCurrentTile(tempTile);*/
	}

	public Game.Mode getActionCardGameMode(){
	    return Game.Mode.GAME_SWAPPLAYERPOSITIONACTIONCARD;
	  }
	
	private static final long serialVersionUID = 8194616193887934963L;
}
