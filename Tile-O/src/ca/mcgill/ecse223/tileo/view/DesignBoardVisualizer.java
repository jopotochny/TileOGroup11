package ca.mcgill.ecse223.tileo.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import ca.mcgill.ecse223.tileo.controller.DesignController;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;

public class DesignBoardVisualizer extends JPanel {
	
	private static final long serialVersionUID = 5765666411683246454L;
	
	//UI elements
	private List<Rectangle2D> blackRectangles = new ArrayList<Rectangle2D>();
	private static final int RECTWIDTH = 30;
	private static final int RECTHEIGHT = 30;
	private static final int SPACING = 10;
	private static final int MAXIMUMNUMBEROFTILESSHOWN  = 196;
	
	//data elements
	private List<Tile[]> connectionList;
	private List<Rectangle2D> connectableList;
	private TileO tileO;
	private int numberOfTiles;
	private HashMap<Rectangle2D, Tile> tiles;
	private List<Rectangle2D> whiteTilesList;
	private List<Rectangle2D> startTiles;
	private List <Rectangle2D> actionTiles;
	private Rectangle2D whiteTile;
	private Rectangle2D winTile = null;
	private Tile tile;
	private Tile[] connectArray = new Tile[2];
	private Tile[] disconnectArray = new Tile[2];
	private List<Rectangle2D> rectangles = new ArrayList<Rectangle2D>();

	//game and controller
	//private DesignTileOPage page;
	private DesignController descont;
	private Game game;
	
	protected volatile int inactivity;
	protected volatile String errorMsg = "";

	private int drawCounter = 0;
	//mouse listener boolean controllers
	private boolean enabled = false;
	private boolean enabled1 = false;
	private boolean enabled2 = false;
	private boolean enabled3 = false;
	private boolean enabled4 = false;
	private boolean enabled5 = false;
	protected volatile boolean enabled6 = false;
	
	//count makes sure that connect has 2 elements
	private int count = 0;
	private int counter = 0;
	private int disCount = 0;
	private Point actionTile;
	
	private boolean save = false;
	
	//boad dimensions
	private static final int WIDTH_BOARD_VISUALIZATION = 800;
	private static final int HEIGHT_BOARD_VISUALIZATION = 400;
	
	public DesignBoardVisualizer(TileO tileO, boolean fromSave){
		super();
		this.save = fromSave;
		this.tileO=tileO;
		inactivity = 0;
		game = this.tileO.getCurrentGame();
		descont = new DesignController(this.tileO);
		init(tileO);
	}
	
	private void init(TileO tileO){
		this.tileO = tileO;
		
		tile = null;
		tiles = new HashMap<Rectangle2D, Tile>();
		blackRectangles = new ArrayList<Rectangle2D>();
		whiteTilesList = new ArrayList<Rectangle2D>();
		actionTiles = new ArrayList<Rectangle2D>();
		startTiles = new ArrayList<Rectangle2D>();
		connectableList = new ArrayList<Rectangle2D>();
		connectionList = new ArrayList<Tile[]>();
		
		//add a mouse listener to detect if a rectangle was clicked
		addMouseListener(new MouseAdapter(){
			
			public void mousePressed(MouseEvent e){
			//methods use mouse click to run controller method
					clickTile(e);
					clickStartTile(e);
					clickDisconnect(e);
					clickWinTile(e);
					clickConnect(e);
					clickRemoveTile(e);
					clickActionTile(e);
				
				repaint();
			}
			
		});
	}
	private void doDrawing(Graphics g){
		
		if(tileO != null){
			Game game = tileO.getCurrentGame();
			if(game.hasWinTile()){
				winTile = new Rectangle2D.Float(game.getWinTile().getX(), game.getWinTile().getY(), RECTWIDTH, RECTHEIGHT);
			}
			
			BasicStroke thinStroke = new BasicStroke(2);
			BasicStroke thickStroke = new BasicStroke(3);
			Graphics2D g2d = (Graphics2D) g.create();

			numberOfTiles = game.getTiles().size();
			List<Tile> currentTiles = game.getTiles();
			List<Player> listOfPlayers = game.getPlayers();

			//loop through all the tiles, and for each one draw a rectangle with a specific color
			for(Tile tile : currentTiles){
				if(tile instanceof NormalTile){
					whiteTilesList.add(new Rectangle2D.Float(tile.getX(), tile.getY(), RECTWIDTH, RECTHEIGHT));
				} else if (tile instanceof ActionTile){
					actionTiles.add(new Rectangle2D.Float(tile.getX(), tile.getY(), RECTWIDTH, RECTHEIGHT));
				}
				Rectangle2D rect = new Rectangle2D.Float(tile.getX(), tile.getY(), RECTWIDTH, RECTHEIGHT);
				connectableList.add(rect);
				tiles.put(rect, tile);
				g2d.setStroke(thinStroke);
				Rectangle2D rectangle = new Rectangle2D.Float(tile.getX(), tile.getY(), RECTWIDTH, RECTHEIGHT);
				rectangles.add(rectangle);
				tiles.put(rectangle, tile);

				//setting the color of the tile to black if marked visited, otherwise to white
				if(tile.isHasBeenVisited()){
					g2d.setColor(Color.BLACK);
					g2d.fill(rectangle);
				}else{	
					g2d.setColor(Color.WHITE);
					g2d.fill(rectangle);
				}



				if(counter == 0 && game.hasPlayers()){
					for(Player player : listOfPlayers){
						Tile playerTile = player.getStartingTile();
						startTiles.add(new Rectangle2D.Float(player.getStartingTile().getX(), player.getStartingTile().getY(), RECTWIDTH, RECTHEIGHT));
						
						if(playerTile.getX() == tile.getX() && playerTile.getY() == tile.getY()){
							Player.Color playerColor = player.getColor();
							if(playerColor.equals(Player.Color.BLUE)){
								g2d.setColor(Color.BLUE);
								g2d.fill(rectangle);
							}else if(playerColor.equals(Player.Color.YELLOW)){
								g2d.setColor(Color.YELLOW);
								g2d.fill(rectangle);
							}else if(playerColor.equals(Player.Color.RED)){
								g2d.setColor(Color.RED);
								g2d.fill(rectangle);
							}else if(playerColor.equals(Player.Color.GREEN)){
								g2d.setColor(Color.GREEN);
								g2d.fill(rectangle);
							}

							break;
						}
					}
				}
				//setting the position of each player				
			


				//if the player clicks on any tile, color it in Cyan
				//first if, for a selection of 1 tile. and the else is for a selection of two tiles

				g2d.setColor(Color.BLACK);
				g2d.draw(rectangle);
				counter++;
			}


			//adding the connections to the board
			if(game.hasConnections()){
			for(Connection connection : game.getConnections()){
				Tile tile1 = connection.getTiles().get(0);
				Tile tile2 = connection.getTiles().get(1);
				Tile[] tiles = new Tile[2];
				tiles[0] = tile1;
				tiles[1] = tile2;
				connectionList.add(tiles);
				if(tile1.getY() == tile2.getY()){
					//tile1 and tile2 are adjacent and have a horizontal connection
					if(tile1.getX() < tile2.getX()){
						g2d.setStroke(thickStroke);
						g2d.drawLine(tile1.getX() + RECTWIDTH, tile1.getY() + RECTHEIGHT/2, tile2.getX(), tile2.getY() + RECTHEIGHT/2);

					}else{
						g2d.setStroke(thickStroke);
						g2d.drawLine(tile2.getX() + RECTWIDTH, tile2.getY() + RECTHEIGHT/2, tile1.getX(), tile2.getY() + RECTWIDTH/2);
					}
					//tile1 and tile2 are adjacent and have a vertical connection
				}else{
					if(tile1.getY() < tile2.getY()){
						g2d.setStroke(thickStroke);
						g2d.drawLine(tile1.getX() + RECTWIDTH/2, tile1.getY() + RECTHEIGHT, tile2.getX() + RECTWIDTH/2, tile2.getY());
					}else{
						g2d.setStroke(thickStroke);
						g2d.drawLine(tile1.getX() + RECTWIDTH/2, tile1.getY(), tile2.getX() + RECTWIDTH/2, tile2.getY() + RECTHEIGHT);
					}
				}
			}
			}
		}
	}
	
	private void createTiles(Graphics g){
		BasicStroke thinStroke = new BasicStroke(2);
		Graphics2D g2d = (Graphics2D) g.create();
		
		//TODO figure out how many tiles
		numberOfTiles =14*14+3;
		int tracker = numberOfTiles;
		
		Color c = null;
		
		int i=0, x=0, y=0;
		
		while(tracker >=0 ){
			for(x=0; x<WIDTH_BOARD_VISUALIZATION && tracker>=0; ){		
				Rectangle2D rectangle = new Rectangle2D.Float(x, y, RECTWIDTH, RECTHEIGHT);
				for(Rectangle2D rect : whiteTilesList){
					//check if rectangle is in list of white tiles; if so change color to white
					if(rect.getX()== rectangle.getX() && rect.getY() == rectangle.getY()){
						
						c = Color.WHITE;
						break;
					}else{
						c = Color.BLACK;
					}
				}
				
				//set color of wintike to yellow
				if(winTile != null){
					g2d.setStroke(thinStroke);
					g2d.setColor(Color.YELLOW);
					Rectangle2D win = new Rectangle2D.Float((int)Math.round(winTile.getX()),(int)Math.round(winTile.getY()),RECTWIDTH, RECTHEIGHT);
					g2d.fill(win);
					g2d.setColor(Color.BLACK);
					g2d.draw(win);
				}
				
				if(actionTiles.size() > 0){
					for(Rectangle2D rec : actionTiles){
						g2d.setStroke(thinStroke);
						g2d.setColor(Color.GREEN);
						Rectangle2D action = new Rectangle2D.Float((int)Math.round(rec.getX()),(int)Math.round(rec.getY()),RECTWIDTH, RECTHEIGHT);
						g2d.fill(action);
						g2d.setColor(Color.BLACK);
						g2d.draw(action);
					}
				}
				
				//set color of starting tiles to blue
				if(startTiles.size() > 0){
					Color color;
					
					for(int j = 0; j<startTiles.size(); j++){
						Rectangle2D rec = startTiles.get(j);
						if(j == 0)
							color = Color.RED;
						else if(j == 1)
							color = Color.BLUE;
						else if (j == 2)
							color = Color.ORANGE;
						else
							color = Color.PINK;
						
						g2d.setStroke(thinStroke);
						g2d.setColor(color);
						Rectangle2D start = new Rectangle2D.Float((int)Math.round(rec.getX()),(int)Math.round(rec.getY()),RECTWIDTH, RECTHEIGHT);
						g2d.fill(start);
						g2d.setColor(Color.BLACK);
						g2d.draw(start);
					}
				}
				
				if(!connectableList.contains(rectangle) )
					blackRectangles.add(rectangle);
				
				g2d.setStroke(thinStroke);
				g2d.setColor(c);
				g2d.fill(rectangle);
				g2d.setColor(Color.BLACK);
				g2d.draw(rectangle);
				tracker--;
				x+=RECTWIDTH + SPACING;
				
			}
			y+=RECTHEIGHT + SPACING;
		}
		
	}
	
	private void drawConnection(Graphics g){
		//draws connection from list of arrays 
		BasicStroke thinStroke = new BasicStroke(4);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setStroke(thinStroke);
		g2d.setColor(Color.BLACK);
		
		for(Tile[] connected : connectionList){
			if(connected[1] != null)
				//checks for position of the 2 tiles in connected
				if((connected[0].getX() - connected[1].getX() == 0) && (connected[0].getY()+40 == connected[1].getY())){
					Line2D line = new Line2D.Float(connected[0].getX() +15, connected[0].getY()+30, connected[1].getX()+15, connected[1].getY());
					g2d.draw(line);
				}else if((connected[0].getX() - connected[1].getX() == 0) && (connected[0].getY()-40 == connected[1].getY())){
					Line2D line = new Line2D.Float(connected[0].getX()+15, connected[0].getY(), connected[1].getX()+15, connected[1].getY()+30);
					g2d.draw(line);
				}else if((connected[0].getY() - connected[1].getY() == 0) && (connected[0].getX() + 40 == connected[1].getX())){
					Line2D line = new Line2D.Float(connected[0].getX()+30, connected[0].getY()+15, connected[1].getX(), connected[1].getY()+15);
					g2d.draw(line);
				} else if((connected[0].getY() - connected[1].getY() == 0) && (connected[0].getX() - 40 == connected[1].getX())) {
					Line2D line = new Line2D.Float(connected[0].getX(), connected[0].getY()+15, connected[1].getX()+30, connected[1].getY()+15);
					g2d.draw(line);
				}
		}
			
	}
	
	private void clickTile(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		
		//check enabled to see if button is active or not
		if(!enabled){
			return;
		}else if(enabled){
			errorMsg ="";
			for(Rectangle2D rect : blackRectangles){
				//check if clicked tile is in blackrectangles
				//if so add it to whitetilelist
				if(rect.contains(x, y)){
					whiteTilesList.add(rect);
					connectableList.add(rect);
					
					try {
						descont.addTileToBoard((int)Math.round(rect.getX()), (int)Math.round(rect.getY()));
					} catch (InvalidInputException e1) {
						// TODO Auto-generated catch block
						errorMsg = e1.getMessage();
					}
					tiles.put(rect, game.getTile(game.getTiles().size() - 1));
					blackRectangles.remove(rect);
					break;
				}
			}
			DesignTileOPage.console.setText(errorMsg);

		}
	}
	
	private void clickRemoveTile(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		
		//check enabled1 to see if button is active
		if(!enabled1){
			return;
		} else if(enabled1){
			errorMsg = "";
			Iterator<Rectangle2D> iter = connectableList.iterator();
			
			Rectangle2D rect = null;
			while(iter.hasNext()){
				Rectangle2D next = iter.next();
				if(next.contains(x,y)){
					rect = next;
					tile = tiles.get(rect);
					whiteTilesList.remove(rect);
					actionTiles.remove(rect);
					startTiles.remove(rect);
					iter.remove();
					tiles.remove(rect);
					
					
				}
				
			}

			Iterator<Tile[]> iter1 = connectionList.iterator();
			
			while(iter1.hasNext()){
				Tile[] t = iter1.next();
				if((t[0].getX() == Math.round(rect.getX()) && t[0].getY() == Math.round(rect.getY())) || (t[1].getX() == Math.round(rect.getX()) && t[1].getY() == Math.round(rect.getY()))){
					iter1.remove();
				}
			}
				
			if(winTile != null){
				if(rect.getX() == winTile.getX() && rect.getY() == winTile.getY()){
					winTile = null;
					tile = game.getWinTile();
				}
			}
			
			try{
				descont.removeTileFromBoard(tile);
			}catch(InvalidInputException e1){
				errorMsg = e1.getMessage();
				
			}
			DesignTileOPage.connectionPiecesLeft.setText((32-connectionList.size()) + " left");

			DesignTileOPage.console.setText(errorMsg);
			
		}
	}
	
	private void clickWinTile(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		//check enabled2 to see if button is active
		if(!enabled2){	
			return;
			
		} else if(enabled2){
			errorMsg = "";
			
			Iterator<Rectangle2D> iter = blackRectangles.iterator();
			Rectangle2D rect = null;
			
			while(iter.hasNext()){
				Rectangle2D next = iter.next();
				if(next.contains(x,y)  ){
					if(!connectableList.contains(next)){
						rect = next;
					} else {
						DesignTileOPage.console.setText("Please select a black tile");
						return;
					}
				}
			}
		
			try{
				descont.identifyWinTile((int)Math.round(rect.getX()), (int)Math.round(rect.getY()));
			} catch(InvalidInputException e1){
				errorMsg = e1.getMessage();			
			}
			
			tiles.put(rect, game.getTile(game.getTiles().size()-1));
			
			if(winTile == null)
				winTile = rect;
			
			connectableList.add(winTile);
			DesignTileOPage.console.setText(errorMsg);
		}
		
	}
	
	private void clickConnect(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		//wasConnected allows s to make sure that the games connectionpieces list 
		//is equal to connectList
		int wasConnected = 0;
		//check to see if button was clicked on and off
		
		
		if(!enabled3){
			return;
		} else if(enabled3){
			errorMsg = "";
			Iterator<Rectangle2D> iter = connectableList.iterator();
			while(iter.hasNext()){
				Rectangle2D r = iter.next();
				if(r.contains(x,y)){
					if(count == 2)
						count = 0;
					connectArray[count] = tiles.get(r);
					count++;
				}
			}
			
		}
		if(count == 2){
			
			try {
				descont.addConnectionDuringDesign(connectArray[0], connectArray[1]);
			} catch (InvalidInputException e2) {
				// TODO: handle exception
				wasConnected++;
				errorMsg = e2.getMessage();				
			}
			
			
			if(wasConnected == 0)
				connectionList.add(connectArray);
			
			DesignTileOPage.connectionPiecesLeft.setText((32-connectionList.size()) + " left");
			
			connectArray = new Tile[2];
				
		}
		DesignTileOPage.console.setText(errorMsg);
		
	}
	
	private void clickDisconnect(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		
		//wasConnected allows s to make sure that the games connectionpieces list 
		//is equal to connectList
		int wasConnected = 0;
		
		//check to see if button was clicked on and off
		if(disCount == 2)
			disCount = 0;
		
		if(!enabled4){
			return;
		} else if(enabled4){
			errorMsg="";
			for(Rectangle2D r : whiteTilesList){
				if(r.contains(x,y)){
					disconnectArray[disCount] = tiles.get(r);
					disCount++;
				}
			}
			
		}
		if(disCount == 2){
			Tile[] remove = null;
			for(Tile[] connections : connectionList){
				if((connections[0] == disconnectArray[0] && connections[1] == disconnectArray[1]) || (connections[0] == disconnectArray[1] && connections[1] == disconnectArray[0])){
					remove = connections;
				} 
			}
			if(remove == null){
				remove = new Tile[2];
				remove[0] = new NormalTile(-40,0, game);
			
				remove[1] = new NormalTile(-80,0, game);
			}

			try {
				descont.removeConnectionDuringDesign(remove[0], remove[1]);;
			} catch (InvalidInputException e2) {
				// TODO: handle exception
				wasConnected++;
				errorMsg = e2.getMessage();
				
			}
			
			if(wasConnected == 0)
				connectionList.remove(remove);
			
			DesignTileOPage.connectionPiecesLeft.setText((32-connectionList.size()) + " left");

			disconnectArray = new Tile[2];
			
		}
		
		DesignTileOPage.console.setText(errorMsg);
		
	}
	
	private void clickStartTile(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		
		//check enabled to see if button is active or not
		if(!enabled5){
			return;
		}else if(enabled5){
			errorMsg = "";
			for(Rectangle2D rect : whiteTilesList){
				//check if clicked tile is in blackrectangles
				//if so add it to whitetilelist
				if(rect.contains(x, y)){
					
					Tile start = tiles.get(rect);
					
					try {
						descont.identifyStartTile(start);
					} catch (InvalidInputException e1) {
						// TODO Auto-generated catch block
						errorMsg = e1.getMessage();
						DesignTileOPage.console.setText(errorMsg);
						break;
					}
					startTiles.add(rect);
					
				}
			}
		}
		
	}
	
	private void clickActionTile(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		
		if(!enabled6){
			return;
		} else{
			errorMsg = "";
			for(Rectangle2D rect : blackRectangles){
				//check if clicked tile is in blackrectangles
				//if so add it to whitetilelist
				if(rect.contains(x, y)){
					if(!connectableList.contains(rect)){
						
						try{
							descont.identifyActionTile((int)Math.round(rect.getX()), (int)Math.round(rect.getY()), inactivity);
						}catch(InvalidInputException e1){
							errorMsg = e1.getMessage();
						}
						
						tiles.put(rect, game.getTile(game.getTiles().size()-1));
						actionTiles.add(rect);
						connectableList.add(rect);
				//		enabled6=false;
						
					}
				}
			}
		}
		
		DesignTileOPage.console.setText(errorMsg);
		
	}
	
	
	//setters so when button clicked on tileopage, enabled(1-3)
	public void setEnable(boolean b ){
		enabled = b;
	}
	
	public void setEnable1(boolean b ){
		enabled1 = b;
	}
	
	public void setEnable2(boolean b ){
		enabled2 = b;
	}
	
	public void setEnable3(boolean b ){
		enabled3 = b;
	}
	
	public void setEnable4(boolean b ){
		enabled4 = b;
	}
	
	public void setEnable5(boolean b ){
		enabled5 = b;
	}
	
	public void setEnable6(boolean b ){
		enabled6 = b;
	}
	
	public Point getActionTile(){
		return actionTile;
	}
	

	
	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		
		if(save){
			if(drawCounter == 0){
				doDrawing(g);
				drawCounter++;
			}
			createTiles(g);
			drawConnection(g);
		}	
		else{	
			createTiles(g);
			drawConnection(g);
		}
				
		
	}
}
