package ca.mcgill.ecse223.tileo.application;


import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.tileo.view.HomePage;

public class TileOApplication {
	
	private static String filename = "output.txt";
	private static Game game;
	private static TileO tileO;
	
	public static void main(String[] args) {
		// load model
		//final TileO tileO = PersistenceXStream.initializeModelManager(filename);
		final TileO tileO = load();
		
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
	
	public static void save(){
		PersistenceObjectStream.serialize(tileO);
	}
	
	public static TileO load(){
		PersistenceObjectStream.setFilename(filename);
		tileO = (TileO) PersistenceObjectStream.deserialize();
		if (tileO == null) {
			tileO = new TileO();
		}
		return tileO;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}
	
}
