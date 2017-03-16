package ca.mcgill.ecse223.tileo.view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.controller.PlayController;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.TileO;

public class HomePage extends JFrame {
	
	//UI elements
	private JLabel welcomeMessage;
	private JButton gotoCreateGame;
	private JButton gotoLoadedGame;
	private int selectedGame;
	private JComboBox<Integer> listOfGames;
	private HashMap<Integer, Game> availableGames;
	private JLabel errorLabel;	
	private String error;
	private TileO tileO;

	
	
	public HomePage(TileO tileO){
		this.tileO = tileO;
		initComponents();
		refreshData();
	}

	public void initComponents(){
		
	//set the size of the applet
	this.setPreferredSize(new Dimension(1400, 700) );
		
	//initialize all the elements
	welcomeMessage = new JLabel();
	
	welcomeMessage.setText("Welcome to Group11's Tile-O !");
	
	welcomeMessage.setFont(new Font("Serif", Font.BOLD, 100));
	
	
	
	gotoCreateGame = new JButton();
	gotoCreateGame.setPreferredSize(new Dimension(200 , 700));
	gotoCreateGame.setText("Create a new game");
	
	gotoLoadedGame = new JButton();
	gotoLoadedGame.setText("Load game");
	
	listOfGames = new JComboBox<Integer>();
	
	availableGames = new HashMap<Integer, Game>();
	
	errorLabel = new JLabel();
	errorLabel.setText("");
	
	gotoCreateGame.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			creatNewGame();
		}
	});
	
	gotoLoadedGame.addActionListener(new java.awt.event.ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt){
			loadGame();
		}
	});
	
	listOfGames.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
			selectedGame = cb.getSelectedIndex();
		}
	});

	
	GroupLayout layout = new GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setAutoCreateGaps(true);
	layout.setAutoCreateContainerGaps(true);
	
	layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addComponent(welcomeMessage)
				.addComponent(errorLabel)
				.addGroup(layout.createSequentialGroup()
						.addComponent(gotoCreateGame)
						.addGroup(layout.createParallelGroup()
								.addComponent(listOfGames)
								.addComponent(gotoLoadedGame)
								)
			
	
						)
		
				);
	 
	
	layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {gotoLoadedGame, listOfGames});
	//layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {MoveTo, removeConnection});
	layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {gotoLoadedGame, listOfGames});
	//layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {connectTiles, removeConnection});
		
	layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addComponent(welcomeMessage)
				.addComponent(errorLabel)
				.addGroup(layout.createParallelGroup()
						.addComponent(gotoCreateGame)
						.addGroup(layout.createSequentialGroup()
								.addComponent(listOfGames)
								.addComponent(gotoLoadedGame)
							
								
								
								
								)
						
						
						)
			
			
			
			);
	
	pack();
		
	}
	
	private void refreshData(){
		errorLabel.setText(error);
		
		PlayController pc = new PlayController(tileO);
		int index = 0;
		listOfGames.removeAllItems();
		for(Game game : pc.getGames()){
			availableGames.put(index, game);
			listOfGames.addItem(game.getTileO().indexOfGame(game));
			index++;
		}

		selectedGame = -1;
		listOfGames.setSelectedIndex(selectedGame);
		error = "";
	}
	
	public void creatNewGame(){
		//redirect to the create game page
		dispose();
		new DesignTileOPage(tileO).setVisible(true);
	}
	
	
	public void loadGame(){
		PlayController pc = new PlayController(tileO);
		Game game = TileOApplication.getGame(selectedGame);
		tileO.setCurrentGame(game);
		try {
			pc.startGame();
			dispose();
			new PlayTileOPage(tileO, pc).setVisible(true);
		} catch (InvalidInputException e) {
			error=e.getMessage();
		}
	}
	
}