package ca.mcgill.ecse223.tileo.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

import ca.mcgill.ecse223.tileo.model.ActionCard;
import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Die;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.LoseTurnActionCard;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.RemoveConnectionActionCard;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.model.WinTile;

public class PersistenceXStream {

	private static XStream xstream = new XStream();
	private static String filename = "tileo.txt";
	
	public static TileO initializeModelManager(String fileName){
		//Initialization for persistence
		TileO tileo;
		setFilename(fileName);
		setAlias("ActionCard", ActionCard.class);
		setAlias("ActionTile", ActionTile.class);
		setAlias("Connection", Connection.class);
		setAlias("ConnectTilesActionCard", ConnectTilesActionCard.class);
		setAlias("Deck", Deck.class);
		setAlias("Die", Die.class);
		setAlias("Game", Game.class);
		setAlias("LoseTurnActionCard", LoseTurnActionCard.class);
		setAlias("NormalTile", NormalTile.class);
		setAlias("Player", Player.class);
		setAlias("RemoveConnectionActionCard", RemoveConnectionActionCard.class);
		setAlias("RollDieActionCard", RollDieActionCard.class);
		setAlias("TeleportActionCard", TeleportActionCard.class);
		setAlias("Tile", Tile.class);
		setAlias("TileO", TileO.class);
		setAlias("WinTile", WinTile.class);
		
		// load model if exists, create otherwise
        File file = new File(fileName);
        if (file.exists()) {
            tileo = (TileO) loadFromXMLwithXStream();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            tileo = new TileO();
            saveToXMLwithXStream(tileo);
        }
        return tileo;
	}
	
	public static boolean saveToXMLwithXStream(Object obj) {
        xstream.setMode(XStream.ID_REFERENCES);
        String xml = xstream.toXML(obj); // save our xml file

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(xml);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

	public static Object loadFromXMLwithXStream() {
        xstream.setMode(XStream.ID_REFERENCES);
        try {
            FileReader fileReader = new FileReader(filename); // load our xml file
            return xstream.fromXML(fileReader);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static void setAlias(String xmlTagName, Class<?> className){
		xstream.alias(xmlTagName, className);
	}
	
	public static void setFilename(String fn) {
		filename = fn;
	}

}
