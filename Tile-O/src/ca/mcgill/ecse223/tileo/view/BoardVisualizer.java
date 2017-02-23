package ca.mcgill.ecse223.tileo.view;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;

public class BoardVisualizer extends JPanel {
	
	private static final long serialVersionUID = 5765666411683246454L;
	
	
	//UI elements
	private List<Rectangle2D> rectangles = new ArrayList<Rectangle2D>();
	private static final int RECTWIDTH = 30;
	private static final int RECHEIGHT = 30;
	private static final int SPACING = 10;
	private static final int MAXIMUMNUMBEROFTILESSHOWN  = 196;
	
	//data elements
	private TileO tileO;
	private HashMap<Rectangle2D, Tile> tiles;
	private Tile selectedTile;
	private Tile selectedTile1;
	private Tile selectedTile2;
	
	
	
	public BoardVisualizer(TileO tileO){
		super();
		init(tileO);
	}
	
	private void init(TileO tileO){
		this.tileO = tileO;
		
		tiles = new HashMap<Rectangle2D, Tile>();
		selectedTile = null;
		selectedTile1 = null;
		selectedTile2 = null;
		
		//add a mouse listener to detect if a rectangle was clicked; if yes, save the corresponding tile in selectedTile
		
		addMouseListener(new MouseAdapter(){
			
			public void mousePressed(MouseEvent e){
				int x = e.getX();
				int y = e.getY();
				
				//check all the rectangles to determine if one was clicked; if yes
				//save the corresponding tile in selectedTile
				
				for(Rectangle2D rect : rectangles){
					if(rect.contains(x, y)){
						selectedTile = tiles.get(rect);
						break;
					}
				}
				repaint();
			}
			
		});
		
	}
	
	private void doDrawing(Graphics g){
		
		if(tileO != null){
			Game currentGame = tileO.getCurrentGame();
			int numberOfTiles = currentGame.numberOfTiles() - 1;
			
			Graphics2D g2d = (Graphics2D) g.create();
			BasicStroke thickStroke = new BasicStroke(4);
			g2d.setStroke(thickStroke);
			
			rectangles.clear();
			tiles.clear();
			
			
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawing(g);
	}
	
	
}
