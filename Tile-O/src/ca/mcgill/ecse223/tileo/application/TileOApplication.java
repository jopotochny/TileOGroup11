package ca.mcgill.ecse223.tileo.application;


import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.persistence.PersistenceXStream;
import ca.mcgill.ecse223.tileo.view.HomePage;

public class TileOApplication {
	
	private static String filename = "output/tileO.xml";
	private static Game game;
	private static TileO tileO;
	
	public static void main(String[] args) {
		// load model
		final TileO tileO = PersistenceXStream.initializeModelManager(filename);
		
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	//tileO.getCurrentGame().setMode(Mode.GAME);
                new HomePage(tileO).setVisible(true);
            }
        });
        
	}
	
	public static TileO getTileO(){
		tileO = new TileO();
		return tileO;
	}
	// Get Current Game
	public static Game getGame() {
		tileO.setCurrentGame(game);
 		return tileO.getCurrentGame();
	}
	// Get Specific Game
	public static Game getGame(int index){
		game = load().getGame(index);
		tileO.setCurrentGame(game);
 		return game;
	}
	// Save file
	public static void save() {
		setFilename(filename);
	    PersistenceXStream.initializeModelManager(filename);
	}
	// Load file
	public static TileO load() {
		tileO = (TileO) PersistenceXStream.loadFromXMLwithXStream();
		return tileO;
	}
	// Set Filename
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}
	
}
