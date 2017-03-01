package ca.mcgill.ecse223.tileo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.DesignController;
import ca.mcgill.ecse223.tileo.controller.PlayController;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.TileO;

public class TileOPage extends JFrame{

	private static final long serialVersionUID = -4426310869335015542L;

	//UI elements
	private JLabel errorMessage;
	private Game.Mode mode;

	//save and load game
	private JButton saveGame;
	private JComboBox<Integer> loadGame;
	private JLabel player;
	private Integer selectedGame;

	//actions
	private JTextField deck;
	private JButton rollDie;
	private JButton connectTiles;
	private JButton MoveTo;
	private JButton removeConnection;
	private JButton teleportTo;

	private JLabel remainingPieces;

	//board
	private JLabel boardTitle;
	private BoardVisualizer boardVisualizer;
	private static final int WIDTH_BOARD_VISUALIZATION = 600;
	private static final int HEIGHT_BOARD_VISUALIZATION = 300;

	//console
	private JLabel consoleTitle;
	private JTextField console;

	//data elements
	private String error = null;

	//load game visualization
	private HashMap<Integer, Game> availableGames;

	
	
	public TileOPage() {
		initComponents();
		refreshData();
	}

	public void initComponents(){		
		//set the size of the applet
		this.setSize(1400, 700);
		
		//set the initial mode of the game
		mode = Game.Mode.GAME;
		
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		//save and load game
		saveGame = new JButton();
		loadGame = new JComboBox<Integer>();
		player = new JLabel();
		player.setText("Player 1");
		
		//board TODO
		boardVisualizer = new BoardVisualizer(null);
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
		
		
		// TODO
		saveGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        //PlayController.saveGame();
			}
		});
		
				
		// TODO
		loadGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//PlayController.loadGame(selectedGame);
			}
		});
		
		
		//actions
		
		deck = new JTextField();
		deck.setText("connect two tiles");
		deck.setEditable(false);
		deck.setPreferredSize(new Dimension(60, 30));
		deck.setHorizontalAlignment(JLabel.CENTER);
		
		rollDie = new JButton();
		rollDie.setText("Roll Dice");
		
		connectTiles = new JButton();
		connectTiles.setText("Connect tiles");
		
		MoveTo =  new JButton();
		MoveTo.setText(" Move to");
		
		removeConnection = new JButton();
		removeConnection.setText("Remove connection");
		
		teleportTo = new JButton();
		teleportTo.setText("Teleport to");
		
		deck.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        
			}
		});
		
		rollDie.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        
			}
		});
		connectTiles.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        if(mode.equals(Game.Mode.GAME_CONNECTTILESACTIONCARD)){
		        	
		        }
			}
		});
		MoveTo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        
			}
		});
		removeConnection.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        
			}
		});
		
		// TODO get the tile here or 2D?
		teleportTo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        
			}
		});
		
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
											.addComponent(MoveTo)
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
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {MoveTo, removeConnection});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {saveGame, loadGame});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {rollDie, MoveTo, teleportTo, remainingPieces});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {connectTiles, removeConnection});
			
		layout.setVerticalGroup(
				layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
							.addComponent(saveGame)
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
								.addComponent(MoveTo)
								.addComponent(removeConnection)
								)
							.addComponent(teleportTo)
							.addComponent(remainingPieces)
							)

		);

		pack();
	}
	
	private void refreshData(){
		errorMessage.setText(error);
		
		// TODO change the tileo controller
		TileO tileO = TileOApplication.getTileO();
		PlayController pc = new PlayController(tileO);
		
		if( error== null || error.length() == 0){
			// populate page with data
			
			
			
			
			
			// load game
			Integer index = 0;
			availableGames = new HashMap<Integer, Game>();
			loadGame.removeAllItems();
			for(Game game : pc.getGames()){
				availableGames.put(index, game);
				loadGame.addItem(game.getTileO().indexOfGame(game));
				index++;
			}
			selectedGame = -1;
			loadGame.setSelectedIndex(selectedGame);
			//console
			console.setText("");
			
			//player index TODO
			/*String indexOfPlayer ="Player: " + pc.getCurrentPlayer().getNumber();
			player.setText(indexOfPlayer);*/
			
			
		}
		
		
	}
	
}
