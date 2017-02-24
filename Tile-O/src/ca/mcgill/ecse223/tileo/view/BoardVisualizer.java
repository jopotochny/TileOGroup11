package ca.mcgill.ecse223.tileo.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
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
	private int numberOfTiles;
	private HashMap<Rectangle2D, Tile> tiles;
	private Tile selectedTile;
	private Tile selectedTile1;
	private Tile selectedTile2;
	
	//boad dimensions
	private static final int WIDTH_BOARD_VISUALIZATION = 800;
	private static final int HEIGHT_BOARD_VISUALIZATION = 400;
	
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
		rectangles = new ArrayList<Rectangle2D>();
		
		//numberOfRectangle = tileO.getCurrentGame().getTiles().size();
		
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
	
	private void createTiles(Graphics g){
		tileO = TileOApplication.getTileO();
		Game game = new Game(32, tileO);
		tileO.setCurrentGame(game);
				
		BasicStroke thinStroke = new BasicStroke(2);
		Graphics2D g2d = (Graphics2D) g.create();
		
		
		numberOfTiles =30;
		int tracker = numberOfTiles;
		
		//TODO get all the information from the tileo
		int i=0, x=0, y=0;
		while(tracker >=0 ){
			for(x=0; x<WIDTH_BOARD_VISUALIZATION && tracker>=0; ){
				NormalTile tile = new NormalTile(x, y, game);
				g2d.setStroke(thinStroke);
				Rectangle2D rectangle = new Rectangle2D.Float(x, y, RECTWIDTH, RECHEIGHT);
				rectangles.add(rectangle);
				tiles.put(rectangle, tile);
				g2d.setColor(Color.WHITE);
				g2d.fill(rectangle);
				g2d.setColor(Color.BLACK);
				g2d.draw(rectangle);
				tracker--;
				x+=RECTWIDTH + SPACING;
			}
			y+=RECHEIGHT + SPACING;
		}
		
		
		
				
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
		//doDrawing(g);
		createTiles(g);
	}
}
