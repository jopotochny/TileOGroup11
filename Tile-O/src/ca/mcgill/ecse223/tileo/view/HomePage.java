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
	private JButton gotoLoadedGameDesign;
	private int selectedGame;
	private int selectedGameDesign;
	private JComboBox<Integer> listOfGames;
	private JComboBox<Integer> listOfGamesDesign;
	private HashMap<Integer, Game> availableGames;
	private HashMap<Integer, Game> availableGamesDesign;
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
		gotoLoadedGame.setText("Load Playmode game");

		gotoLoadedGameDesign = new JButton();
		gotoLoadedGameDesign.setText("Load Designmode game");

		listOfGames = new JComboBox<Integer>();
		listOfGamesDesign = new JComboBox<Integer>();

		availableGames = new HashMap<Integer, Game>();
		availableGamesDesign = new HashMap<Integer, Game>();

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

		gotoLoadedGameDesign.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				loadGameDesign();
			}
		});

		listOfGames.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				if(cb.getSelectedItem() != null){
					selectedGame = (int) cb.getSelectedItem();
				}
			}
		});

		listOfGamesDesign.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<Integer> cb = (JComboBox<Integer>) evt.getSource();
				if(cb.getSelectedItem() != null){
					selectedGameDesign = (int) cb.getSelectedItem();
				}
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
						.addGroup(layout.createParallelGroup()
								.addComponent(listOfGamesDesign)
								.addComponent(gotoLoadedGameDesign)
								)



						)

				);


		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {gotoLoadedGame, listOfGames , listOfGamesDesign , gotoLoadedGameDesign});
		//layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {MoveTo, removeConnection});
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {gotoLoadedGame, listOfGames , listOfGamesDesign , gotoLoadedGameDesign});
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
						.addGroup(layout.createSequentialGroup()
								.addComponent(listOfGamesDesign)
								.addComponent(gotoLoadedGameDesign)




								)


						)



				);

		pack();

	}

	private void refreshData(){
		errorLabel.setText(error);

		PlayController pc = new PlayController(tileO);
		int index = 0;
		int indextwo = 0;
		listOfGames.removeAllItems();
		for(Game game : pc.getGames()){
			if(game.getMode()!= null){
				if(!(game.getMode().equals(Game.Mode.DESIGN))){
					availableGames.put(index, game);
					listOfGames.addItem(game.getTileO().indexOfGame(game));

				}
			}
			index++;
		}

		for(Game game : pc.getGames()){
			if(game.getMode()!= null){
				if((game.getMode().equals(Game.Mode.DESIGN))){
					availableGamesDesign.put(indextwo, game);
					listOfGamesDesign.addItem(game.getTileO().indexOfGame(game));
					indextwo++;
				}
			}
		}
		selectedGame = -1;
		selectedGameDesign =-1;
		listOfGames.setSelectedIndex(selectedGame);
		listOfGamesDesign.setSelectedIndex(selectedGameDesign);
		error = "";
	}

	public void creatNewGame(){
		//redirect to the create game page
		dispose();
		new DesignTileOPage(tileO,false).setVisible(true);
	}


	public void loadGame(){
		if(selectedGame == -1){
			return;
		} else{
			System.out.println(selectedGame);
			PlayController pc = new PlayController(tileO);
			Game game = TileOApplication.getGame(selectedGame);
			tileO.setCurrentGame(game);
			try {
				pc.startGame(game);
				dispose();
				new PlayTileOPage(tileO, pc).setVisible(true);
			} catch (InvalidInputException e) {
				error=e.getMessage();
			}
		}
	}

	public void loadGameDesign(){
		if(selectedGameDesign == -1){
			return;
		} else{
			Game game = TileOApplication.getGame(selectedGameDesign);
			tileO.setCurrentGame(game);
			dispose();
			new DesignTileOPage(game , tileO, true).setVisible(true);
		}

	}


}