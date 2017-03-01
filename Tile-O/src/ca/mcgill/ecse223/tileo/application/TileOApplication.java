package ca.mcgill.ecse223.tileo.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.persistence.PersistenceXStream;
import ca.mcgill.ecse223.tileo.view.TileOPage;

public class TileOApplication {
	
	private static TileO tileO;
	private static Game game;
	private static String filename = "data.xml";
	
	
	public static void main(String[] args) {
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	
                new TileOPage().setVisible(true);
            }
        });
        
	}
	
	public static TileO getTileO(){
		tileO = new TileO();
		return tileO;
	}
	
	public static Game getGame() {
		if (game == null) {
			// load model
			game = load();
		}
 		return game;
	}
	
	public static Game getGame(int index){
		if (game == null) {
			// load model
			game = load();
		}
 		return tileO.getGame(index);
	}
	
	public static void save() {
		PersistenceXStream.saveToXMLwithXStream(game);
	}
	
	public static Game load() {
		PersistenceXStream.setFilename(filename);
		game = (Game) PersistenceXStream.loadFromXMLwithXStream();
		// model cannot be loaded - create empty Game
		if (game == null) {
			game = new Game(0, null, null, null);
		}
		else {
			reinitializeUniqueNumber(game.getPlayers());
		}
		
		return game;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}
	
	public static  void reinitializeUniqueNumber(List<Player> players){
		Map<Integer, Player> playersByNumber = new HashMap<Integer, Player>();
	    for (Player player : players) {
	      playersByNumber.put(player.getNumber(), player);
	    }
	}
}
