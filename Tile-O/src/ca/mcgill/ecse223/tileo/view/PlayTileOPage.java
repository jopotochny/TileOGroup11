package ca.mcgill.ecse223.tileo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
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
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;

public class PlayTileOPage extends JFrame{

	private static final long serialVersionUID = -4426310869335015542L;

	//UI elements
	private JLabel errorMessage;
	private Game.Mode gameMode;
	private PlayController.Mode controllerMode;

	//save and load game
	private JButton saveGame;
	private JLabel player;

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
	private String deckText = "";

	private TileO tileO;
	private PlayController pc;

	public PlayTileOPage(TileO tileO, PlayController pc) {
		this.tileO = tileO;
		this.pc = pc;
		initComponents();
		refreshData();
	}

	public void initComponents(){	
		//set the size of the window
		this.setPreferredSize(new Dimension(1400, 700));

		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		//save game
		saveGame = new JButton();
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
		remainingPieces.setText("");

		//global settings and listeners
		saveGame.setText("Save");

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
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {saveGame});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {rollDie, moveTo, teleportTo, remainingPieces});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {connectTiles, removeConnection});

		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(saveGame)
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
				pc.saveGame();
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
		gameMode = pc.getGameMode();
		controllerMode = pc.getMode();
		setButtonActivity();

		pack();
	}

	private void refreshData(){
		//output the error in the console
		console.setText(error);

		if( error== null || error.length() == 0){

			//redraw the board 
			boardVisualizer.redraw();

			//console
			console.setText("");

			//get the player index in order to update the player JLabel
			int playerIndex = pc.getCurrentPlayerIndex() + 1;
			String playerLabel = "Player " + playerIndex;
			player.setText(playerLabel);

			//setting the remaining connection pieces
			int nbOfConnections = pc.getNumberRemainingPieces();
			String connectionPieces = "Remaining pieces: " + nbOfConnections;
			remainingPieces.setText(connectionPieces);

			//update the mode of the game and the controller
			gameMode = pc.getGameMode();
			controllerMode = pc.getMode();			

		}

		//set the inactivity of the button depending on the game mode
		setButtonActivity();

		error = "";
		deckText = "";
	}

	private void setButtonActivity(){

		rollDie.setEnabled(false);
		moveTo.setEnabled(false);
		connectTiles.setEnabled(false);
		removeConnection.setEnabled(false);
		teleportTo.setEnabled(false);

		if(controllerMode.equals(PlayController.Mode.Roll)){
			rollDie.setEnabled(true);
			if(deckText.equals("")){
				deckText = " Roll the die";
			}
			deck.setText(deckText);
		}else if(controllerMode.equals(PlayController.Mode.Ready)){
			rollDie.setEnabled(true);
			if(deckText.equals("")){
				deckText = " Roll the die";
			}
			deck.setText(deckText);
		}else if(controllerMode.equals(PlayController.Mode.Move)){
			moveTo.setEnabled(true);
			deck.setText("Move to a highlighted tile");
		}else if(gameMode.equals(Game.Mode.GAME_CONNECTTILESACTIONCARD)){
			connectTiles.setEnabled(true);
			deckText = "Connect Two Tiles";
			deck.setText(deckText);
		}else if(gameMode.equals(Game.Mode.GAME_LOSETURNACTIONCARD)){
			rollDie.setEnabled(true);
			moveTo.setEnabled(true);
			deckText = "Sorry, you lost your turn. Please next player roll a die.";
			deck.setText(deckText);
			loseTurn();
		}else if(gameMode.equals(Game.Mode.GAME_REMOVECONNECTIONACTIONCARD)){
			removeConnection.setEnabled(true);
			deckText = "Please remove a connection from the board";
			deck.setText(deckText);
		}else if(gameMode.equals(Game.Mode.GAME_ROLLDIEACTIONCARD)){
			rollDie.setEnabled(true);
			deckText = "You got another turn! please roll the die";
			deck.setText(deckText);
		}else if(gameMode.equals(Game.Mode.GAME_TELEPORTACTIONCARD)){
			teleportTo.setEnabled(true);
			deckText = "Teleport to any tile";
			deck.setText(deckText);
		}else if(gameMode.equals(Game.Mode.GAME_WON) || controllerMode.equals(PlayController.Mode.Won)){
			rollDie.setEnabled(false);
			moveTo.setEnabled(false);
			teleportTo.setEnabled(false);
			removeConnection.setEnabled(false);
			connectTiles.setEnabled(false);
			console.setText("Congratulation!!! Player " + (pc.getCurrentPlayerIndex()+1) + " won the game!");
		}else if(gameMode.equals(Game.Mode.GAME) && controllerMode.equals(PlayController.Mode.ActionCard)){
			rollDie.setEnabled(true);
		}

	}

	private void rollDieGetPossibleMoves(){	

		//update the mode of the game
		gameMode = pc.getGameMode();
		
		List<Tile> possibleMoves = new ArrayList<Tile>();

		if(gameMode.equals(Mode.GAME)){
			pc.rollDie();
			possibleMoves = pc.getPossibleMoves();
		}else if(gameMode.equals(Mode.GAME_ROLLDIEACTIONCARD)){
			try {
				pc.playRollDieActionCard();
				possibleMoves = pc.getPossibleMoves();
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}

		boardVisualizer.setPossibleMoves(possibleMoves);
		if(possibleMoves.size() == 0){
			pc.noMoves();
		}else{
			// TODO remove it
			//setting the roll die to inactive, so that the player can't roll die more than 1 time
			rollDie.setEnabled(false);
		}		
		refreshData();
	}


	private void moveToTile(){

		Tile tile = boardVisualizer.getSelectedTile();

		List<Tile> possibleMoves = boardVisualizer.getPossibleMoves();

		/*
		 * at first, we check if the list is empty, if so, the user didn't roll the die 
		 * second, we check if the user chose a valid tile to move on
		 * third we check if the user chose a tile, if so call the controller and move
		 * else, the user either chose more than 1 tile, or no tile at all
		 */

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

	private void loseTurn(){
		try {
			pc.playLoseTurnActionCard();
		} catch (InvalidInputException e) {

			error = e.getMessage();
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

		Tile tile = boardVisualizer.getSelectedTile();
		if(tile != null){
			try{
				pc.playTeleportActionCard(tile);
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}else{
			error = "Please select 1 tile from the board";
		}

		refreshData();
	}


}
