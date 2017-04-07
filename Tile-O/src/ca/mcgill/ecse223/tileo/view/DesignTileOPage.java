package ca.mcgill.ecse223.tileo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.tileo.controller.DesignController;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.controller.PlayController;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.TileO;

public class DesignTileOPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;

	// UI elements
	private JLabel errorMessage;
	private Game.Mode mode;
	private Game game;
	private boolean fromSave = false;
	// save and load game
	private JButton saveGame;
	private JLabel player;
	private Integer selectedGame;

	// counters for button press
	// inactivity is number in jtextfield for actiontile

	private int counter = 0;
	private int counter1 = 0;
	protected int inactivity;

	// actions
	private JButton createDeck;
	private JLabel teleportLabel;
	private JLabel removeConnectionLabel;
	private JLabel connectionLabel;
	private JLabel loseLabel;
	private JLabel rollLabel;
	private JLabel inactivityLabel;
	private JLabel wintTileHintLabel;
	private JLabel setInactiveCardLabel;
	private JLabel nextPlayerLabel;
	private JLabel moveOtherPlayerLabel;
	private JLabel revealLabel;
	protected static JTextField connectionPiecesLeft;
	private JTextField revealCard;
	private JTextField inactivityText;
	private JTextField winTileHintCard;
	private JTextField setInactiveCard;
	private JTextField nextPlayerCard;
	private JTextField teleportCard;
	private JTextField removeConnectionCard;
	private JTextField connectionCard;
	private JTextField loseTurnCard;
	private JTextField rollCard;
	private JTextField moveOtherPlayerCard;
	private JButton submitDeck;
	private JButton addConnection;
	private JButton addTile;
	private JButton removeConnection;
	private JButton removeTile;
	private JButton setStart;
	private JButton addWinTile;
	private JButton addActionTile;
	private JButton startGame;
	private JLabel swapPlayerLabel;
	private JTextField swapPlayer;

	private DesignController cont;
	private TileO tileo;

	// board
	private JLabel boardTitle;
	private DesignBoardVisualizer boardVisualizer;
	private static final int WIDTH_BOARD_VISUALIZATION = 800;
	private static final int HEIGHT_BOARD_VISUALIZATION = 400;

	// console
	private JLabel consoleTitle;
	protected static JTextField console;

	// data elements
	private String error = null;

	// load game visualization
	private HashMap<Integer, Game> availableGames;

	public DesignTileOPage(TileO tileO, boolean save) {
		this.fromSave = save;
		this.tileo = tileO;
		cont = new DesignController(tileo);
		game = cont.createGame();
		game.setMode(Game.Mode.DESIGN);
		initComponents();
		refreshData();
	}
	
	public DesignTileOPage(Game aGame, TileO tileO, boolean save){
		this.fromSave = save;
		this.tileo = tileO;
		game = aGame;
		//tileO.setCurrentGame(aGame);
		initComponents();
		refreshData();
	}
	
	public static boolean LoadPageDesign(TileO tileo){
		return true;
	}

	public void initComponents() {
		// set the size of the applet
		this.setPreferredSize(new Dimension(1400, 700));

		// set the initial mode of the game
		mode = Game.Mode.GAME;

		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		// save and load game
		saveGame = new JButton();
		player = new JLabel();
		player.setText("Player 1");

		// board TODO
		boardVisualizer = new DesignBoardVisualizer(tileo, fromSave);
		boardTitle = new JLabel();
		boardVisualizer.setMinimumSize(new Dimension(WIDTH_BOARD_VISUALIZATION, HEIGHT_BOARD_VISUALIZATION));
		boardVisualizer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		boardTitle.setText("Board:");

		// console
		console = new JTextField();
		consoleTitle = new JLabel();
		console.setText("Player 1, please roll the die.");
		console.setEditable(false);
		consoleTitle.setText("Console:");

		// global settings and listeners
		saveGame.setText("Save");

		startGame = new JButton();
		startGame.setText("Start Game");

		// actions and initialize design ui elements

		swapPlayerLabel = new JLabel("Swap Player Action Card");
		swapPlayer = new JTextField();
		
		createDeck = new JButton();
		createDeck.setText("Create Deck");
		
		moveOtherPlayerLabel = new JLabel("Move Other Player Card");
		moveOtherPlayerCard = new JTextField();
		
		revealLabel = new JLabel("Reveal Tile Action Card");
		nextPlayerLabel = new JLabel("Next Player Rolls One Card");
		wintTileHintLabel = new JLabel("Win Tile Hint Card");
		rollLabel = new JLabel("Roll Again Card");
		teleportLabel = new JLabel("Teleport Card");
		connectionLabel = new JLabel("Add Connection Card");
		loseLabel = new JLabel("Lose Turn Card");
		removeConnectionLabel = new JLabel("Remove Connection Card");
		setInactiveCardLabel = new JLabel("Set Inactivity Card");
		
		revealCard = new JTextField();
		nextPlayerCard = new JTextField();
		winTileHintCard = new JTextField();
		setInactiveCard = new JTextField();
		rollCard = new JTextField();
		teleportCard = new JTextField();
		connectionCard = new JTextField();
		loseTurnCard = new JTextField();
		removeConnectionCard = new JTextField();

		connectionPiecesLeft = new JTextField("32 left");
		connectionPiecesLeft.setEditable(false);

		setStart = new JButton("Set Starting Tile");
		addConnection = new JButton();
		addConnection.setText("Add Connection");

		addActionTile = new JButton("Add Action Tile");
		addWinTile = new JButton("Add Win Tile");

		addTile = new JButton("Add Tile");
		removeTile = new JButton("Remove Tile");

		submitDeck = new JButton("Submit Deck");

		removeConnection = new JButton();
		removeConnection.setText("Remove Connection");

		inactivityLabel = new JLabel("Amount of turns inactive");
		inactivityText = new JTextField();

		addTile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// check if its been clicked once or twice
				// set boolean accordingly
				if (counter % 2 == 0) {
					// set boolean to true in board visualizer to run action
					// when board is clicked
					boardVisualizer.setEnable(true);

					// disable other buttons and change clicked button text
					addTile.setText("Stop Adding");
					removeTile.setEnabled(false);
					addWinTile.setEnabled(false);
					addConnection.setEnabled(false);
					setStart.setEnabled(false);
					addActionTile.setEnabled(false);
					removeConnection.setEnabled(false);

					counter++;

				} else {
					boardVisualizer.setEnable(false);

					addTile.setText("Add Tile");
					removeTile.setEnabled(true);
					addWinTile.setEnabled(true);
					addConnection.setEnabled(true);
					setStart.setEnabled(true);
					addActionTile.setEnabled(true);
					removeConnection.setEnabled(true);

					counter++;
					refreshData();
				}
			}
		});

		saveGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//game = tileo.getCurrentGame();
				//TileOApplication.setFilename(String.valueOf(selectedGame));
				cont.saveGame();

			}
		});
		

		removeConnection.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// check if its been clicked once or twice
				// set boolean accordingly
				if (counter % 2 == 0) {
					// change boolean variable to allow action to occur in board
					// visualizer
					boardVisualizer.setEnable4(true);

					// disable buttons and change text
					removeConnection.setText("Stop Removing");
					removeTile.setEnabled(false);
					addWinTile.setEnabled(false);
					addConnection.setEnabled(false);
					setStart.setEnabled(false);
					addActionTile.setEnabled(false);
					addTile.setEnabled(false);
					counter++;
				} else {
					boardVisualizer.setEnable4(false);
					removeConnection.setText("Remove Connection");

					removeTile.setEnabled(true);
					addWinTile.setEnabled(true);
					addConnection.setEnabled(true);
					setStart.setEnabled(true);
					addTile.setEnabled(true);
					addActionTile.setEnabled(true);
					removeConnection.setEnabled(true);

					counter++;
					refreshData();
				}

			}
		});

		/*saveGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TileOApplication.save();

			}
		});*/

		submitDeck.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				// create deck with numbers from field
				boolean error = true;
				int numRoll=0;
				int numTeleport=0;
				int numLose=0;
				int numConnect=0;
				int numExtra=0;
				int numHint = 0;
				int numNext = 0;
				int numInactive = 0;
				int numMoveOtherPlayer=0;
				int numReveal = 0;
				int numSwap = 0;

					try{
						numReveal = Integer.parseInt(revealCard.getText());
						numRoll = Integer.parseInt(rollCard.getText());
						numTeleport = Integer.parseInt(teleportCard.getText());
						numLose = Integer.parseInt(loseTurnCard.getText());
						numConnect = Integer.parseInt(connectionCard.getText());
						numExtra = Integer.parseInt(removeConnectionCard.getText());
						numHint = Integer.parseInt(winTileHintCard.getText());
						numNext = Integer.parseInt(nextPlayerCard.getText());
						numInactive = Integer.parseInt(setInactiveCard.getText());
						numMoveOtherPlayer = Integer.parseInt(moveOtherPlayerCard.getText());
						numSwap = Integer.parseInt(swapPlayer.getText());
						
						
					}catch(NumberFormatException e){
						console.setText("One of the cards has an invalid value");
						error = false;
						return;
					}

					try {
						cont.selectCards(numConnect, numRoll, numExtra, numTeleport, numLose, numHint , numNext, numInactive, numMoveOtherPlayer, numReveal, numSwap);
					} catch (Exception e) {
						// e.printStackTrace();
						console.setText(e.getMessage().trim());
						error = false;
					}
//				} else {
//					console.setText("One of the cards has an invalid value");
//				}
				if(error)
					refreshData();
			}

		});

		addConnection.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// check if its been clicked once or twice
				// set boolean accordingly
				if (counter % 2 == 0) {
					boardVisualizer.setEnable3(true);

					addConnection.setText("Stop Connecting");

					removeTile.setEnabled(false);
					addWinTile.setEnabled(false);
					addTile.setEnabled(false);
					setStart.setEnabled(false);
					addActionTile.setEnabled(false);
					removeConnection.setEnabled(false);
					counter++;
				} else {
					boardVisualizer.setEnable3(false);
					// connectionPiecesLeft.setText(String.boardVisualizer.getConnections());
					addConnection.setText("Connect Tiles");
					removeTile.setEnabled(true);
					addWinTile.setEnabled(true);
					addTile.setEnabled(true);
					setStart.setEnabled(true);
					addActionTile.setEnabled(true);
					removeConnection.setEnabled(true);
					counter++;
					refreshData();
				}
			}
		});

		removeTile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// check if its been clicked once or twice
				// set boolean accordingly
				if (counter1 % 2 == 0) {
					boardVisualizer.setEnable1(true);

					addTile.setEnabled(false);
					addConnection.setEnabled(false);
					setStart.setEnabled(false);
					addWinTile.setEnabled(false);
					addActionTile.setEnabled(false);
					removeConnection.setEnabled(false);
					removeTile.setText("Stop Removing");
					counter1++;

				} else {
					boardVisualizer.setEnable1(false);

					addTile.setEnabled(true);
					addTile.setEnabled(true);
					addConnection.setEnabled(true);
					setStart.setEnabled(true);
					addActionTile.setEnabled(true);
					addWinTile.setEnabled(true);
					removeConnection.setEnabled(true);
					removeTile.setText("Remove Tile");
					counter1++;
					refreshData();

				}

			}
		});

		setStart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// check if its been clicked once or twice
				// set boolean accordingly
				if (counter1 % 2 == 0) {
					boardVisualizer.setEnable5(true);

					addTile.setEnabled(false);
					addConnection.setEnabled(false);
					removeTile.setEnabled(false);
					addWinTile.setEnabled(false);
					addActionTile.setEnabled(false);
					removeConnection.setEnabled(false);
					setStart.setText("Stop Setting");
					counter1++;

				} else {
					boardVisualizer.setEnable5(false);

					addTile.setEnabled(true);
					addTile.setEnabled(true);
					addConnection.setEnabled(true);
					removeTile.setEnabled(true);
					addActionTile.setEnabled(true);
					addWinTile.setEnabled(true);
					removeConnection.setEnabled(true);
					setStart.setText("Set Starting Tile");
					counter1++;
					refreshData();
				}
			}
		});

		addActionTile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				// check if its been clicked once or twice
				// set boolean accordingly
				if (counter1 % 2 == 0) {
					if (!inactivityText.getText().isEmpty() && inactivityText.getText().matches("[0-9]+"))
						try{
							inactivity = Integer.parseInt(inactivityText.getText());
						} catch(NumberFormatException e){
							e.getMessage();
							console.setText("The number entered is not in range");
							return;
						}

					else {
						console.setText("There is no inactivity value");
						return;
					}
					boardVisualizer.inactivity = inactivity;

					boardVisualizer.setEnable6(true);
					inactivityText.setEditable(false);
					addActionTile.setText("--Stop Adding--");
					addTile.setEnabled(false);
					addConnection.setEnabled(false);
					removeTile.setEnabled(false);
					addWinTile.setEnabled(false);
					removeConnection.setEnabled(false);
					setStart.setEnabled(false);

					counter1++;
				} else {
					boardVisualizer.setEnable6(false);
					
					inactivityText.setEditable(true);
					addActionTile.setText("Add Action Tile");
					addTile.setEnabled(true);
					addConnection.setEnabled(true);
					removeTile.setEnabled(true);
					addWinTile.setEnabled(true);
					addActionTile.setEnabled(true);
					removeConnection.setEnabled(true);
					setStart.setEnabled(true);
					counter1++;
					refreshData();
				}
			}
		});

		startGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startGame();
			}
		});

		addWinTile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// check if its been clicked once or twice
				// set boolean accordingly
				if (counter1 % 2 == 0) {
					boardVisualizer.setEnable2(true);

					removeTile.setEnabled(false);
					addTile.setEnabled(false);
					addConnection.setEnabled(false);
					setStart.setEnabled(false);
					addActionTile.setEnabled(false);
					removeConnection.setEnabled(false);
					counter1++;

				} else {
					boardVisualizer.setEnable2(false);

					addTile.setEnabled(true);
					removeTile.setEnabled(true);
					addTile.setEnabled(true);
					addConnection.setEnabled(true);
					setStart.setEnabled(true);
					addActionTile.setEnabled(true);
					removeConnection.setEnabled(true);
					counter1++;
					refreshData();

				}
			}
		});

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// layout design
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(saveGame)
						.addComponent(player).addComponent(startGame))
				.addGroup(layout.createParallelGroup().addComponent(boardTitle).addComponent(boardVisualizer)
						.addComponent(consoleTitle).addComponent(console)

				).addGroup(layout.createParallelGroup()
				 .addGroup(layout.createSequentialGroup()
				 .addGroup(layout.createParallelGroup()
				 .addComponent(setStart)
				 .addComponent(addTile)
				 .addComponent(removeTile)
				 .addComponent(addConnection)
				 .addComponent(removeConnection)
				 .addComponent(addWinTile)
				 .addComponent(inactivityLabel)
				 .addComponent(connectionLabel)
				 .addComponent(removeConnectionLabel)
				 .addComponent(loseLabel)
				 .addComponent(teleportLabel)
				 .addComponent(rollLabel)
				 .addComponent(wintTileHintLabel)
				 .addComponent(moveOtherPlayerLabel)
				 .addComponent(swapPlayerLabel)
				 .addComponent(revealLabel)
				 .addComponent(nextPlayerLabel)
				 .addComponent(setInactiveCardLabel))
				 .addGroup(layout.createParallelGroup()
						 .addComponent(inactivityText)
						 .addComponent(connectionPiecesLeft)
						 .addComponent(addActionTile)
						 .addComponent(connectionCard)
						 .addComponent(removeConnectionCard)
						 .addComponent(loseTurnCard)
						 .addComponent(teleportCard)
						 .addComponent(rollCard)
						 .addComponent(winTileHintCard)
						 .addComponent(moveOtherPlayerCard)
						 .addComponent(swapPlayer)
						 .addComponent(revealCard)
						 .addComponent(nextPlayerCard)
						 .addComponent(setInactiveCard)
						 .addComponent(submitDeck)))));

		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] { saveGame });
		layout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { rollCard, removeConnectionCard, teleportCard,winTileHintCard, connectionCard, submitDeck,
						addActionTile, inactivityText, removeTile, loseTurnCard, nextPlayerCard ,  connectionPiecesLeft, setInactiveCard, moveOtherPlayerCard, revealCard, swapPlayer });

		layout.setVerticalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup().addComponent(saveGame)
						.addComponent(player).addComponent(startGame))
				.addGroup(layout.createSequentialGroup().addComponent(boardTitle).addComponent(boardVisualizer)
						.addComponent(consoleTitle).addComponent(console))
				.addGroup(layout.createSequentialGroup().addComponent(inactivityLabel).addComponent(inactivityText)
						.addComponent(addActionTile).addComponent(addWinTile).addComponent(addTile)
						.addComponent(removeTile).addComponent(removeConnection)

						.addComponent(setStart)
						.addGroup(layout.createParallelGroup().addComponent(addConnection)
								.addComponent(connectionPiecesLeft))
						.addGroup(layout.createParallelGroup().addComponent(rollLabel).addComponent(rollCard))
						.addGroup(layout.createParallelGroup().addComponent(wintTileHintLabel).addComponent(winTileHintCard))
						.addGroup(layout.createParallelGroup().addComponent(setInactiveCardLabel).addComponent(setInactiveCard))
						.addGroup(layout.createParallelGroup().addComponent(removeConnectionCard)
								.addComponent(removeConnectionLabel))
						.addGroup(layout.createParallelGroup().addComponent(loseLabel).addComponent(loseTurnCard)

						).addGroup(layout.createParallelGroup()
								.addComponent(teleportCard)
								.addComponent(teleportLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(nextPlayerCard)
								.addComponent(nextPlayerLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(connectionLabel)
								.addComponent(connectionCard))
						.addGroup(layout.createParallelGroup()
								.addComponent(moveOtherPlayerLabel)
								.addComponent(moveOtherPlayerCard))
						.addGroup(layout.createParallelGroup()
								.addComponent(swapPlayerLabel)
								.addComponent(swapPlayer)
								)
						.addGroup(layout.createParallelGroup()
								.addComponent(revealLabel)
								.addComponent(revealCard)
								)
						.addComponent(submitDeck)));
		pack();
	}

	private void refreshData() {
		console.setText("");
		// load game
		
		DesignController dc = new DesignController(tileo);
		Integer index = 0;
		availableGames = new HashMap<Integer, Game>();

		// console set text to null
		// console.setText("");

		// set jtextfield to null
		moveOtherPlayerCard.setText("");
		revealCard.setText("");
		nextPlayerCard.setText("");
		winTileHintCard.setText("");
		loseTurnCard.setText("");
		rollCard.setText("");
		swapPlayer.setText("");
		removeConnectionCard.setText("");
		connectionCard.setText("");
		teleportCard.setText("");
		setInactiveCard.setText("");
		inactivityText.setText("");
		error = "";
	}

	public void startGame() {

		PlayController pc = new PlayController(tileo);

		try {
			pc.startGame(game);
			dispose();
			new PlayTileOPage(tileo, pc).setVisible(true);
		} catch (InvalidInputException e) {
			error = e.getMessage();
			console.setText(error);
		}
		
	}
	
	public void loadGame() {
		/*DesignController cont = new DesignController(tileo);
		cont.loadDesign(selectedGame);
		//Game game = TileOApplication.getGame(selectedGame);
		//tileo.setCurrentGame(game);
		//cont.createGame();
		dispose();
		new DesignTileOPage(tileo).setVisible(true);*/
		
//		PlayController pc = new PlayController(tileo);
//		
//		if (loadGame.getItemCount() == 0) {
//			error = "there are no games to play";
//		}
//		else if (loadGame.getItemCount()>0){
//			try {
//				int gameIndex = loadGame.getSelectedIndex();
//				Game selectedGame = availableGames.get(gameIndex);
//				TileOApplication.getTileO().setCurrentGame(selectedGame);
//				pc.startGame(TileOApplication.getTileO().getCurrentGame());
//				dispose();
//				new DesignTileOPage(tileo).setVisible(true);
//			}
//			catch(InvalidInputException e) {
//				error = e.getMessage();
//			}
//		}
//		
		//PlayController pc = new PlayController(tileo);
		//Game game = TileOApplication.getGame(loadGame.getSelectedIndex()-1);
		
		
		tileo.setCurrentGame(game);
		//LoadPageDesign(tileo);
		dispose();
		new DesignTileOPage(game,tileo,fromSave).setVisible(true);
	}

}
