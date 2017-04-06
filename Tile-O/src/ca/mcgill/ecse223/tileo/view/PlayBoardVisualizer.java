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

import ca.mcgill.ecse223.tileo.controller.PlayController;
import ca.mcgill.ecse223.tileo.model.ActionTile;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;

public class PlayBoardVisualizer extends JPanel {

	private static final long serialVersionUID = 5765666411683246454L;


	//UI elements
	private List<Rectangle2D> rectangles = new ArrayList<Rectangle2D>();
	private static final int RECTWIDTH = 30;
	private static final int RECTHEIGHT = 30;
	private static final int SPACING = 10;

	//data elements
	private TileO tileO;
	private int numberOfTiles;
	private int numberOfConnections;
	private HashMap<Rectangle2D, Tile> tiles;
	private Tile selectedTile;
	private Tile selectedTile1;
	private Tile selectedTile2;
	private Tile previouslySelectedTile;
	private Tile previouslySelectedTile1;
	private Tile previouslySelectedTile2;
    private PlayController controller;
	private List<Tile> possibleMoves = null;
	private int flag = 0;

	//board dimensions
	private static final int WIDTH_BOARD_VISUALIZATION = 800;
	private static final int HEIGHT_BOARD_VISUALIZATION = 400;

	public PlayBoardVisualizer(TileO tileO){
		super();
		init(tileO);
	}

	private void init(TileO tileO){
		this.tileO = tileO;
		controller = new PlayController(tileO);
		tiles = new HashMap<Rectangle2D, Tile>();
		selectedTile = null;
		selectedTile1 = null;
		selectedTile2 = null;
		previouslySelectedTile = null;
		previouslySelectedTile1 = null;
		previouslySelectedTile2 = null;
		rectangles = new ArrayList<Rectangle2D>();


		//add a mouse listener to detect if a rectangle was clicked; if yes, save the corresponding tile in selectedTile

		addMouseListener(new MouseAdapter(){

			public void mousePressed(MouseEvent e){
				int x = e.getX();
				int y = e.getY();
				//check all the rectangles to determine if one was clicked; if yes
				//save the corresponding tile in selectedTile
				selectedTile = null;
				for(Rectangle2D rect : rectangles){
					//if we clicked on a rectangle enter the if
					if(rect.contains(x, y)){
						if(flag == 0){
							//getting the first tile
							selectedTile1 = tiles.get(rect);
							/*
							 * if we have previously selected a tile, and is the same as the tile selected now
							 * enter the if, and set everything to null
							 */
							if((previouslySelectedTile2!=null) &&( previouslySelectedTile2.getX() == selectedTile1.getX() &&  previouslySelectedTile2.getY() == selectedTile1.getY() )){
								selectedTile2 = null;
								selectedTile1 = null;
								previouslySelectedTile2 = null;
								flag = 0;
							}else{
								//else leaving the selectedTile as is, so it will colored in yellow afterwards
								flag = 1;
								previouslySelectedTile1 = selectedTile1;
							}

						}else{
							//same logic as above, but for the second tile selected
							selectedTile2 = tiles.get(rect);
							if((previouslySelectedTile1!=null) &&( previouslySelectedTile1.getX() == selectedTile2.getX() &&  previouslySelectedTile1.getY() == selectedTile2.getY() )){
								selectedTile1 = null;
								selectedTile2 = null;
								previouslySelectedTile1 = null;
								flag = 1;
							}else{
								previouslySelectedTile2 = selectedTile2;
								flag = 0;
							}
						}
						break;
					}
				}
				if(isWinTileHintMode()){
					playWinTileHint();
				}
				else if(isMoveOtherPlayerMode()){
					playMoveOtherPlayer();
				}
				else if(isRevealTileMode()){
					playRevealTile();
				}
				else if(isSwapPlayerPositionMode()){
					playSwapPlayerPosition();
				}
				repaint();
			}

		});

		//addMouseListeners();
	}

	private void doDrawing(Graphics g){

		if(tileO != null){
			Game game = tileO.getCurrentGame();

			BasicStroke thinStroke = new BasicStroke(2);
			BasicStroke thickStroke = new BasicStroke(3);
			Graphics2D g2d = (Graphics2D) g.create();

			numberOfTiles = game.getTiles().size();
			List<Tile> currentTiles = game.getTiles();
			List<Player> listOfPlayers = game.getPlayers();
            List<Tile> inactiveActionTiles = controller.getInactiveActionTiles();

			//loop through all the tiles, and for each one draw a rectangle with a specific color
			for(Tile tile : currentTiles){

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



				//if the player has to move, color in light gray the possible moves the player can take
				if(possibleMoves != null){
					for(Tile blueTile : possibleMoves){
						if( (blueTile.getX() == tile.getX()) && (blueTile.getY() == tile.getY()) ){
							g2d.setColor(Color.LIGHT_GRAY);
							g2d.fill(rectangle);
							break;
						}
					}
				}

				//setting the position of each player
				for(Player player : listOfPlayers){
					Tile playerTile = player.getCurrentTile();
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


				//if the player clicks on any tile, color it in Cyan
				//first if, for a selection of 1 tile. and the else is for a selection of two tiles
				if((selectedTile != null) && (tile.getX() == selectedTile.getX() && tile.getY() == selectedTile.getY())){
					g2d.setColor(Color.CYAN);
					g2d.fill(rectangle);
				}else{
					if((selectedTile1 != null) && (tile.getX() == selectedTile1.getX() && tile.getY() == selectedTile1.getY())){
						g2d.setColor(Color.CYAN);
						g2d.fill(rectangle);
					}else if((selectedTile2 != null) && (tile.getX() == selectedTile2.getX() && tile.getY() == selectedTile2.getY())){
						g2d.setColor(Color.CYAN);
						g2d.fill(rectangle);
					}
				}

				g2d.setColor(Color.BLACK);
				g2d.draw(rectangle);

				if(inactiveActionTiles.contains(tile)){
                ActionTile actionTile = (ActionTile) tile;
                String inactivity = Integer.toString(actionTile.getTurnsUntilActive());
                g2d.setColor(Color.MAGENTA);
                g2d.drawString(inactivity, tile.getX() + RECTWIDTH/2 , tile.getY() + RECTHEIGHT/2);
                }
			}


			//adding the connections to the board
			for(Connection connection : game.getConnections()){
				Tile tile1 = connection.getTiles().get(0);
				Tile tile2 = connection.getTiles().get(1);

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


	public void setPossibleMoves(List<Tile> listOfTiles){
		possibleMoves = listOfTiles;
		repaint();
	}

	public List<Tile> getPossibleMoves(){
		return possibleMoves;
	}

	//return the tile selected by the player
	public Tile getSelectedTile(){
		if(selectedTile1 != null && selectedTile2 != null){
			return null;
		}else if(selectedTile1 != null){
			return selectedTile1;
		}else{
			return selectedTile2;
		}

	}

	public void setSelectedTileToNull(){
		selectedTile1 = null;
		selectedTile2 = null;
		previouslySelectedTile2 = null;
		flag = 0;
		redraw();
	}

	//return the two tiles selected by the player
	public List<Tile> getSelectedTiles(){
		if(selectedTile1 != null && selectedTile2 != null){
			ArrayList<Tile> selectedTiles = new ArrayList<Tile>();
			selectedTiles.add(selectedTile1);
			selectedTiles.add(selectedTile2);
			return selectedTiles;
		}else{
			return null;
		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawing(g);
	}

	private boolean isWinTileHintMode(){

		if(PlayTileOPage.getControllerMode().equals(PlayController.Mode.ActionCard) && PlayTileOPage.getGameMode().equals(Game.Mode.GAME_WINTILEHINTACTIONCARD)){
			return true;
		}
		return false;
	}

	private boolean isRevealTileMode(){

		if(PlayTileOPage.getControllerMode().equals(PlayController.Mode.ActionCard) && PlayTileOPage.getGameMode().equals(Game.Mode.GAME_REVEALTILEACTIONCARD)){
			return true;
		}
		return false;
	}

	private boolean isSwapPlayerPositionMode(){

		if(PlayTileOPage.getControllerMode().equals(PlayController.Mode.ActionCard) && PlayTileOPage.getGameMode().equals(Game.Mode.GAME_SWAPPLAYERPOSITIONACTIONCARD)){
			return true;
		}
		return false;
	}

	private void playRevealTile(){
		if(selectedTile1 != null){
			PlayTileOPage.playRevealTileActionCard(selectedTile1);
		} else if (selectedTile2 != null){
			PlayTileOPage.playRevealTileActionCard(selectedTile2);
		}
	}

	private void playWinTileHint(){

		if(selectedTile1 != null){
			PlayTileOPage.playWinTileHintActionCard(selectedTile1);
		}else if(selectedTile2 != null){
			PlayTileOPage.playWinTileHintActionCard(selectedTile2);
		}
	}

	private void playSwapPlayerPosition(){


		if(selectedTile2 != null){
			for (Player p : this.tileO.getCurrentGame().getPlayers()){
				if(p.getCurrentTile() == selectedTile2 && p != this.tileO.getCurrentGame().getCurrentPlayer()){
					Player thePlayer = p;
					if(selectedTile1 != null){
						PlayTileOPage.playSwapPlayerPositionActionCard(thePlayer);
					break;
					}
				}
			}
		}
	}

	private boolean isMoveOtherPlayerMode(){
		if(PlayTileOPage.getControllerMode().equals(PlayController.Mode.ActionCard) && PlayTileOPage.getGameMode().equals(Game.Mode.GAME_MOVEOTHERPLAYERACTIONCARD)){
			return true;
		}
		return false;
	}
	private void playMoveOtherPlayer(){
		if(selectedTile1 != null){
			for (Player p : this.tileO.getCurrentGame().getPlayers()){
				if(p.getCurrentTile() == selectedTile1 && p != this.tileO.getCurrentGame().getCurrentPlayer()){
					Player thePlayer = p;
					if(selectedTile2 != null){
						PlayTileOPage.playMoveOtherPlayerActionCard(thePlayer, selectedTile2);
					}
					break;
				}
			}

		}
		else if(selectedTile2 != null){
			for (Player p : this.tileO.getCurrentGame().getPlayers()){
				if(p.getCurrentTile() == selectedTile2 && p != this.tileO.getCurrentGame().getCurrentPlayer()){
					Player thePlayer = p;
					if(selectedTile1 != null){
						PlayTileOPage.playMoveOtherPlayerActionCard(thePlayer, selectedTile1);
					}
					break;
				}
			}

		}
	}
	public void redraw(){
		repaint();
	}



}



