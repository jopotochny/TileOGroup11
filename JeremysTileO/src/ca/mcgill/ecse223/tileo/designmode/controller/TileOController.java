package ca.mcgill.ecse223.tileo.designmode.controller;

import java.util.List;

import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.LoseTurnActionCard;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.RemoveConnectionActionCard;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;

public class TileOController {

	private TileO tileo;

	public TileOController(TileO tileo) {
		this.tileo = tileo;
	}

	public void identifyWinTile(int x, int y) throws InvalidInputException {
		Game game = tileo.getCurrentGame();
		if (game.hasWinTile() == true) {
			throw new InvalidInputException("The win tile has already been set");
		} else {
			WinTile winTile = new WinTile(x, y, game);
			game.setWinTile(winTile);
		}

	}

	public void identifyActionTile(int x, int y, int inactivityPeriod) throws InvalidInputException {
		Game game = tileo.getCurrentGame();

		List<Tile> tiles = game.getTiles();

		for (Tile t : tiles) {
			if (t.getX() == x && t.getY() == y) {
				throw new InvalidInputException("Action tile already exists");
			}
			ActionTile actionT = new ActionTile(x, y, game, inactivityPeriod);
		}

	}

	public void identifyStartTile(NormalTile aStart, int player) throws InvalidInputException {
		Game game = tileo.getCurrentGame();
		//System.out.println("the x is " + game.getPlayer(0).getStartingTile().getY());
		for (int i = 0 ; i<game.getPlayers().size();i++) {
			//if(p == null) continue;
			if(game.getPlayers().size() > 1){
				final Tile startingTile = game.getPlayer(i).getStartingTile();
				//if(startingTile == null) continue;
				if (startingTile.getX() == aStart.getX() && startingTile.getY() == aStart.getY()) {
					throw new InvalidInputException("There is already a player on this starting tile");
				}
			}else {
				game.getPlayer(player).setStartingTile(aStart);
			}
		}
	}

	public void selectCards(int loseTurn, int connect, int rollDie, int remove, int teleport)
			throws InvalidInputException {

		Game game = tileo.getCurrentGame();

		Deck deck = game.getDeck();

		if ((loseTurn + connect + rollDie + remove + teleport) != 32) {
			throw new InvalidInputException("The amount of cards chosen is not 32");
		}
		for (int i = 1; i <= loseTurn; i++) {
			LoseTurnActionCard loseCard = new LoseTurnActionCard("Lose your turn", deck);
		}

		for (int i = 1; i <= connect; i++) {
			ConnectTilesActionCard connectCard = new ConnectTilesActionCard("Connect tiles", deck);
		}

		for (int i = 1; i <= rollDie; i++) {
			RollDieActionCard rollCard = new RollDieActionCard("Roll again", deck);
		}

		for (int i = 1; i <= remove; i++) {
			RemoveConnectionActionCard removeCard = new RemoveConnectionActionCard("Remove connection", deck);
		}

		for (int i = 1; i <= teleport; i++) {
			TeleportActionCard teleportCard = new TeleportActionCard("Move to any tile", deck);
		}
	}
}
