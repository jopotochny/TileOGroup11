package ca.mcgill.ecse223.tileo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.designmode.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.designmode.controller.TileOController;
import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.TileO;

public class TestController{
	private TileO tileo;

	@Before
	public void setUp() throws Exception {
		tileo = TileOApplication.getTileO();
		tileo.delete();

	}

	public static void tearDown() throws Exception {

	}

	@Test
	public void testIdentifyWin() {
		TileOController controller = new TileOController(tileo);
		Game game = new Game(32, tileo);
		tileo.addGame(game);
		tileo.setCurrentGame(game);

		try {
			controller.identifyWinTile(2, 5);
		} catch (InvalidInputException e) {
			fail();
		}

		assertEquals(true, game.hasWinTile());
	}

	@Test
	public void testWinTileExists() {
		TileOController controller = new TileOController(tileo);
		Game game = new Game(32, tileo);
		tileo.addGame(game);
		tileo.setCurrentGame(game);
		try {
			controller.identifyWinTile(2, 3);
		} catch (InvalidInputException e1) {
			// TODO Auto-generated catch block
			fail();
		}
		String error = "";

		try {
			controller.identifyWinTile(2, 5);

		} catch (InvalidInputException e) {
			error += e.getMessage();
		}

		assertEquals("The win tile has already been set", error);
	}

	@Test
	public void testStartTile() {
		TileOController controller = new TileOController(tileo);
		Game game = new Game(32, tileo);

		Player player = new Player(0, game);

		NormalTile tile = new NormalTile(1, 2, game);

		tileo.addGame(game);
		tileo.setCurrentGame(game);
		try {
			controller.identifyStartTile(tile, 0);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(true, player.hasStartingTile());
	}

	@Test
	public void testConflictingStart() {
		TileOController controller = new TileOController(tileo);
		Game game = new Game(32, tileo);
		tileo.addGame(game);
		tileo.setCurrentGame(game);

		String error = "";
		NormalTile tile = new NormalTile(1, 1, game);
		Player player1 = new Player(1, game);
		player1.setStartingTile(tile);
		Player player2 = new Player(2, game);

		try {
			controller.identifyStartTile(tile, 2);
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}

		assertEquals("There is already a player on this starting tile", error);
	}

	@Test
	public void testConflictingActionTile() {
		String error = "";
		TileOController controller = new TileOController(tileo);
		Game game = new Game(32, tileo);
		tileo.addGame(game);
		tileo.setCurrentGame(game);

		ActionTile action = new ActionTile(1, 2, game, 3);

		try {
			controller.identifyActionTile(1, 2, 2);
		} catch (InvalidInputException e) {
			error += e.getMessage();
		}
		assertEquals("Action tile already exists", error);
	}

	@Test
	public void testCreateActionTile() {
		TileOController controller = new TileOController(tileo);
		Game game = new Game(32, tileo);
		tileo.addGame(game);
		tileo.setCurrentGame(game);

		try {
			controller.identifyActionTile(1, 4, 2);
		} catch (InvalidInputException e) {
			fail();
		}

	}

	@Test
	public void testCreateCards() {
		TileOController controller = new TileOController(tileo);
		Game game = new Game(32, tileo);
		tileo.addGame(game);
		tileo.setCurrentGame(game);

		int remove, connect, teleport, roll, lose;
		remove = 5;
		connect = 5;
		teleport = 5;
		roll = 9;
		lose = 8;

		try {
			controller.selectCards(remove, connect, teleport, roll, lose);
		} catch (InvalidInputException e) {
			fail();
		}

		assertEquals(game.getDeck().getCards().size(), 32);
	}

	@Test
	public void testTooManyCards() {
		String error = "";
		TileOController controller = new TileOController(tileo);
		Game game = new Game(32, tileo);
		tileo.addGame(game);
		tileo.setCurrentGame(game);
		int remove, connect, teleport, roll, lose;
		remove = 5;
		connect = 5;
		teleport = 5;
		roll = 12;
		lose = 7;

		try {
			controller.selectCards(remove, connect, teleport, roll, lose);
		} catch (InvalidInputException e) {
			error += e.getMessage();

		}

		assertEquals("The amount of cards chosen is not 32", error);
	}
}
