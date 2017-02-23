package ca.mcgill.ecse223.tileo.controller;

import java.util.List;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Die;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;


public class PlayController {

	private TileO tileO;

	public PlayController(TileO tileO){
		this.tileO = tileO;
	}

	public void startGame(Game selectedGame) throws InvalidInputException{

		/*
		 * validation check:
		 * there need to be actionCards in the deck
		 * there need to be a winTile
		 * the starting tile of each player needs to be defined
		 */
		if(selectedGame.getDeck().hasCards() == false){
			throw new InvalidInputException("The game has no action cards.");
		}
		if(selectedGame.hasWinTile() == false){
			throw new InvalidInputException("The game has no win tile.");
		}

		List<Player> players = selectedGame.getPlayers();

		for(Player player: players){
			if(player.hasStartingTile() == false){
				throw new InvalidInputException("The starting tile of each player is not defined.");
			}
		}

		//set the current game to the selected game
		TileO tileOApplication = selectedGame.getTileO();
		tileOApplication.setCurrentGame(selectedGame);

		//get the deck of the current game

		Deck currentDeck = selectedGame.getDeck();
		currentDeck.shuffle();

		//set all the tiles in the current game to not visited
		List<Tile> tiles = selectedGame.getTiles();
		for(Tile tile: tiles){
			tile.setHasBeenVisited(false);
		}

		//get the starting tile for each player, set it the current tile of each one and set it to visited
		for(Player player: players){
			Tile startingTile = player.getStartingTile();
			player.setCurrentTile(startingTile);
			startingTile.setHasBeenVisited(true);
		}

		//setting player 1
		if(selectedGame.setCurrentPlayer(selectedGame.getPlayers().get(0))){

		}

		//set the current connection pieces to the spare connection pieces
		selectedGame.setCurrentConnectionPieces(selectedGame.SpareConnectionPieces);

		//set the mode of the current game to GAME
		selectedGame.setMode(Game.Mode.GAME);	

	}

	public List<Tile> rollDie(){
		//get the current game from the TileO application
		Game currentGame = tileO.getCurrentGame();

		//get die from the current game
		Die die = currentGame.getDie();

		//roll the die to generate a number

		int number = die.roll();

		//getting the current player from the current game
		Player currentPlayer = currentGame.getCurrentPlayer();

		//get the possible moves of the current player, depending on the generated number
		// TODO implement the get possible moves method
		List<Tile> tiles = currentPlayer.getPossibleMoves(number);

		return tiles;
	}

	public void land(Tile tile) throws InvalidInputException{
		//getting the current game
		Game currentGame = tileO.getCurrentGame();

		/*
		 * getting the list of tiles, followed by checking if the 
		 * input tile is in the tiles of the current game
		 */
		List<Tile> tiles = currentGame.getTiles();
		if(tiles.contains(tile) == false){
			throw new InvalidInputException("The given tile is not found in the current game.");
		}
		//if the tile is of type NormalTile
		if(tile instanceof NormalTile){
			//setting pointer of type normalTile pointing tile
			NormalTile normalTile = (NormalTile) tile;
			normalTile.land();
		}
		//if the tile is of type WinTile
		else if(tile instanceof WinTile){
			WinTile winTile = (WinTile) tile;
			winTile.land();
		}
		//if the tile is of type ActionTile
		else if(tile instanceof ActionTile){
			ActionTile actionTile = (ActionTile) tile;
			actionTile.land();
		}

	}

	public List<Tile> playRollDieActionCard() throws InvalidInputException{
		return null;
	}

	public void playConnectTilesActionCard(Tile tile1, Tile tile2) throws InvalidInputException{

	}

	public void playRemoveConnectionActionCard(Connection connection) throws InvalidInputException{

	}

	public void playTeleportActionCard(Tile tile) throws InvalidInputException{

	}
	
	//get games from the model
	public List<Game> getGames(){
		return TileOApplication.getTileO().getGames();
	}
	
	
	//get the current player from the current game
	public Player getCurrentPlayer(){
		return TileOApplication.getTileO().getCurrentGame().getCurrentPlayer();
	}
	

}
