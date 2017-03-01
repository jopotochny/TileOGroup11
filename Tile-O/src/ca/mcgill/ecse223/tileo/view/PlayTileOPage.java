package ca.mcgill.ecse223.tileo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.controller.PlayController;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.persistence.PersistenceXStream;

public class PlayTileOPage extends JFrame{

	private static final long serialVersionUID = -4426310869335015542L;

	//UI elements
	private JLabel errorMessage;
	private Game.Mode mode;

	//save and load game
	private JButton saveGame;
	private JButton loadGame;
	private JComboBox<Integer> loadGameComboBox;
	private JLabel player;
	private Integer selectedGame;

	//actions
	private JTextField deck;
	private JButton rollDie;
	private JButton connectTiles;
	private JButton moveTo;
	private JButton removeConnection;
	private JButton teleportTo;

	private JLabel remainingPieces;

	//board
	private JLabel boardTitle;
	private PlayBoardVisualizer boardVisualizer;
	private static final int WIDTH_BOARD_VISUALIZATION = 800;
	private static final int HEIGHT_BOARD_VISUALIZATION = 400;

	//console
	private JLabel consoleTitle;
	private JTextField console;

	//data elements
	private String error = null;

	//load game visualization
	private HashMap<Integer, Game> availableGames;

	private TileO tileO;

	public PlayTileOPage(TileO tileO) {
		this.tileO = tileO;
		initComponents();
		refreshData();
	}

	public void initComponents(){	
		//set the size of the window
		this.setPreferredSize(new Dimension(1400, 700));

		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		//save and load game
		saveGame = new JButton();
		loadGame = new JButton();
		loadGameComboBox = new JComboBox<Integer>();
		player = new JLabel();

		//board 
		boardVisualizer = new PlayBoardVisualizer(tileO);
		boardTitle = new JLabel();
		boardVisualizer.setMinimumSize(new Dimension(WIDTH_BOARD_VISUALIZATION, HEIGHT_BOARD_VISUALIZATION));
		boardVisualizer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		boardTitle.setText("Board:");

		//console
		console = new JTextField();
		consoleTitle = new JLabel();
		console.setText("Player 1, please roll the die.");
		console.setEditable(false);
		consoleTitle.setText("Console:");

		//remaining pieces
		remainingPieces = new JLabel();
		remainingPieces.setText("Remaining pieces: 32");

		//global settings and listeners
		saveGame.setText("Save");
		loadGame.setText("Load Game");

		//player
		player.setText("Player 1");

		//actions
		deck = new JTextField();
		deck.setText("Action card instructions will be shown here");
		deck.setEditable(false);
		deck.setPreferredSize(new Dimension(60, 30));
		deck.setHorizontalAlignment(JLabel.CENTER);

		rollDie = new JButton();
		rollDie.setText("Roll Dice");

		connectTiles = new JButton();
		connectTiles.setText("Connect tiles");

		moveTo =  new JButton();
		moveTo.setText(" Move to");

		removeConnection = new JButton();
		removeConnection.setText("Remove connection");

		teleportTo = new JButton();
		teleportTo.setText("Teleport to");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(
				layout.createSequentialGroup()

				.addGroup(layout.createParallelGroup()
						.addComponent(saveGame)
						.addComponent(loadGameComboBox)
						.addComponent(loadGame)
						.addComponent(player)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(boardTitle)
						.addComponent(boardVisualizer)
						.addComponent(consoleTitle)
						.addComponent(console)

						)
				.addGroup(layout.createParallelGroup()
						.addComponent(deck)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(rollDie)
										.addComponent(moveTo)
										.addComponent(teleportTo)
										.addComponent(remainingPieces)
										)
								.addGroup(layout.createParallelGroup()
										.addComponent(connectTiles)
										.addComponent(removeConnection)
										)
								)
						)
				);

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {rollDie, connectTiles});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {moveTo, removeConnection});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {saveGame, loadGameComboBox});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {rollDie, moveTo, teleportTo, remainingPieces});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {connectTiles, removeConnection});

		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(saveGame)
						.addComponent(loadGameComboBox)
						.addComponent(loadGame)
						.addComponent(player)
						)
				.addGroup(layout.createSequentialGroup()
						.addComponent(boardTitle)
						.addComponent(boardVisualizer)
						.addComponent(consoleTitle)
						.addComponent(console)
						)
				.addGroup(layout.createSequentialGroup()
						.addComponent(deck, 200, 200, 300)
						.addGroup(layout.createParallelGroup()
								.addComponent(rollDie)
								.addComponent(connectTiles)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(moveTo)
								.addComponent(removeConnection)
								)
						.addComponent(teleportTo)
						.addComponent(remainingPieces)
						)

				);

		// saving the current game 
		saveGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PlayController pc = new PlayController(tileO);
				pc.saveGame();
			}
		});

		loadGameComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				selectedGame = cb.getSelectedIndex();
			}
		});

		//load the game selected by the player
		loadGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Game game =  tileO.getGame(selectedGame);
				tileO.setCurrentGame(game);
			}
		});

		//setting action listeners for die
		rollDie.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rollDieGetPossibleMoves();
			}
		});

		//setting the action listener for move to button
		moveTo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveToTile();
			}
		});

		//setting action listeners for connecting tiles
		connectTiles.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addConnectionBetweenTiles();
			}
		});


		//setting the actions listeners for remove connection action card
		removeConnection.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeConnectionBetweenTiles();
			}
		});

		//setting the action listeners for teleport action card
		teleportTo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				teleportTo();
			}
		});
		
		//get the mode of the game
		PlayController pc = new PlayController(tileO);
		mode = pc.getGameMode();
		setButtonActivity();

		pack();
	}

	private void refreshData(){
		//output the error in the console
		console.setText(error);
		PlayController pc = new PlayController(tileO);

		if( error== null || error.length() == 0){
			
			//redraw the board 
			boardVisualizer.redraw();

			// load game
			Integer index = 0;
			availableGames = new HashMap<Integer, Game>();
			loadGameComboBox.removeAllItems();
			for(Game game : pc.getGames()){
				availableGames.put(index, game);
				loadGameComboBox.addItem(game.getTileO().indexOfGame(game));
				index++;
			}

			selectedGame = -1;
			loadGameComboBox.setSelectedIndex(selectedGame);
			//console
			console.setText("");

			//get the player index in order to update the player JLabel
			int playerIndex = pc.getCurrentPlayerIndex() + 1;
			String playerLabel = "Player " + playerIndex;

			//update the mode of the game
			mode = pc.getGameMode();

		}
		
		//set the inactivity of the button depending on the game mode
		setButtonActivity();
		
		error = "";
	}

	private void setButtonActivity(){
		
		rollDie.setEnabled(false);
		moveTo.setEnabled(false);
		connectTiles.setEnabled(false);
		removeConnection.setEnabled(false);
		teleportTo.setEnabled(false);

		System.out.println("game mode in activity: " + tileO.getCurrentGame().getMode());
		if(mode.equals(Game.Mode.GAME)){
			rollDie.setEnabled(true);
			moveTo.setEnabled(true);
			deck.setText("");
		}else if(mode.equals(Game.Mode.GAME_CONNECTTILESACTIONCARD)){
			connectTiles.setEnabled(true);
			deck.setText("Connect Two Tiles");
		}else if(mode.equals(Game.Mode.GAME_LOSETURNACTIONCARD)){
			rollDie.setEnabled(true);
			moveTo.setEnabled(true);
			deck.setText("Sorry, you lost your turn");
		}else if(mode.equals(Game.Mode.GAME_REMOVECONNECTIONACTIONCARD)){
			removeConnection.setEnabled(true);
			deck.setText("Please remove a connection from the board");
		}else if(mode.equals(Game.Mode.GAME_ROLLDIEACTIONCARD)){
			rollDie.setEnabled(true);
			moveTo.setEnabled(true);
		}else if(mode.equals(Game.Mode.GAME_TELEPORTACTIONCARD)){
			teleportTo.setEnabled(true);
			deck.setText("Teleport to any tile");
		}else if(mode.equals(Game.Mode.GAME_WON)){
			PlayController pc = new PlayController(tileO);
			rollDie.setEnabled(false);
			moveTo.setEnabled(false);
			teleportTo.setEnabled(false);
			removeConnection.setEnabled(false);
			connectTiles.setEnabled(false);
			console.setText("Congratulation!!! Player " + pc.getCurrentPlayerIndex() + " won the game!");
		}

	}

	private void rollDieGetPossibleMoves(){

		//update the mode of the game
		mode = tileO.getCurrentGame().getMode();
		PlayController pc = new PlayController(tileO);
		List<Tile> possibleMoves = new ArrayList<Tile>();

		if(mode.equals(Mode.GAME)){
			possibleMoves = pc.rollDie();
		}else if(mode.equals(Mode.GAME_ROLLDIEACTIONCARD)){
			try {
				possibleMoves = pc.playRollDieActionCard();
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}

		boardVisualizer.setPossibleMoves(possibleMoves);
		if(possibleMoves.size() == 0){
			pc.noMoves();
			refreshData();
		}else{
			// TODO is it good to put it here?
			//setting the roll die to inactive, so that the player can't roll die more than 1 time
			rollDie.setEnabled(false);
		}		
	}


	private void moveToTile(){
		PlayController pc = new PlayController(tileO);
		
		Tile tile = boardVisualizer.getSelectedTile();
		
		List<Tile> possibleMoves = boardVisualizer.getPossibleMoves();
		
		/*
		 * at first, we check if the list is empty, if so, the user didn't roll the die 
		 * second, we check if the user chose a valid tile to move on
		 * third we check if the user chose a tile, if so call the controller and move
		 * else, the user either chose more than 1 tile, or no tile at all
		 */
		
		System.out.println("possibleMoves: " + possibleMoves);
		System.out.println("tile: " + tile);
		
		if(possibleMoves == null){
			error = "Please roll the die before moving to a new tile";
		}else if( tile == null){
			error = "Please select 1 tile from the board.";
		}else if(! (pc.isValidMove(possibleMoves, tile)) ){
			error = "Please select one of the highlighted tiles.";
		}else{
			try{
				pc.land(tile);
				boardVisualizer.setPossibleMoves(null);
			} catch(InvalidInputException e){
				error = e.getMessage();
			}
		}

		refreshData();
	}

	private void addConnectionBetweenTiles(){

		List<Tile> tilesToConnect = boardVisualizer.getSelectedTiles();

		if(tilesToConnect != null){
			Tile tile1 = tilesToConnect.get(0);
			Tile tile2 = tilesToConnect.get(1);

			PlayController pc = new PlayController(tileO);
			try{
				pc.playConnectTilesActionCard(tile1, tile2);
			} catch (InvalidInputException e){
				error = e.getMessage();
			}
		}else{
			error = "Please select two tiles form the board";
		}
		refreshData();
	}


	private void removeConnectionBetweenTiles(){

		List<Tile> tilesToConnect = boardVisualizer.getSelectedTiles();

		if(tilesToConnect != null){
			Tile tile1 = tilesToConnect.get(0);
			Tile tile2 = tilesToConnect.get(1);
			
			Connection connectionToBeRemoved = null;
			
			// remove the connection the two tiles share
			for(Connection connection1 : tile1.getConnections()){
				for(Tile tile : connection1.getTiles()){
					if(tile.getX() == tile2.getX() && tile.getY() == tile2.getY()){
						connectionToBeRemoved = connection1;
					}
				}
			}
			
			System.out.println(tile1.indexOfConnection(connectionToBeRemoved));
			System.out.println(tile2.indexOfConnection(connectionToBeRemoved));
			
			System.out.println("connection to be removed: " + connectionToBeRemoved);
			
			PlayController pc = new PlayController(tileO);
			try{
			pc.playRemoveConnectionActionCard(connectionToBeRemoved);
		} catch (InvalidInputException e){
			error = e.getMessage();
		}
		}else{
			error = "Please selected two tiles from the board";
		}

		refreshData();
	}


	public void teleportTo(){
		System.out.println("mode before teleport to: " + tileO.getCurrentGame().getMode());
		Tile tile = boardVisualizer.getSelectedTile();
		if(tile != null){
			PlayController pc = new PlayController(tileO);
			try{
				pc.playTeleportActionCard(tile);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}else{
			error = "Please select 1 tile from the board";
		}
		System.out.println("mode after teleport to: " + tileO.getCurrentGame().getMode());
		refreshData();
	}


}
