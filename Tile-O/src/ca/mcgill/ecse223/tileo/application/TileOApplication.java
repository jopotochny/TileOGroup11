package ca.mcgill.ecse223.tileo.application;

import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.view.TileOPage;

public class TileOApplication {
	
	private static TileO tileO;
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
}
