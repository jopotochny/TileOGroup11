package getNeighboursTest;
import java.util.ArrayList;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.NormalTile;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;

public class TestNeightbours {
	
	public static void main (String[] args){
		
		TileO tileO = TileOApplication.getTileO();
		Game game = new Game ( 32 , tileO);
		
		tileO.setCurrentGame(game);
		
		
		
		NormalTile A = new NormalTile(0,0,game);
		NormalTile B = new NormalTile(1,0,game);
		NormalTile C = new NormalTile(2,0,game);
		NormalTile D = new NormalTile(3,0,game);
		NormalTile E = new NormalTile(0,1,game);
		NormalTile F = new NormalTile(1,1,game);
		NormalTile G = new NormalTile(2,1,game);
		NormalTile H = new NormalTile(3,1,game);
		NormalTile I = new NormalTile(0,2,game);
		NormalTile K = new NormalTile(1,2,game);
		NormalTile L = new NormalTile(2,2,game);
		NormalTile M = new NormalTile(3,2,game);
		NormalTile N = new NormalTile(0,3,game);
		NormalTile O = new NormalTile(1,3,game);
		NormalTile P = new NormalTile(2,3,game);
		NormalTile Q = new NormalTile(3,3,game);
		
		

		Connection a = new Connection(game);
		Connection b = new Connection(game);
		Connection c = new Connection(game);
		Connection d = new Connection(game);
		Connection e = new Connection(game);
		Connection f = new Connection(game);
		Connection g = new Connection(game);
		Connection h = new Connection(game);
		Connection i = new Connection(game);
		Connection j = new Connection(game);
		Connection k = new Connection(game);
		Connection l = new Connection(game);
		Connection m = new Connection(game);
		
		
		
		K.addConnection(a);
		L.addConnection(a); // (1 , 2) -- (2 , 2)
		
		L.addConnection(b);
		G.addConnection(b); // (2 , 2) -- (2 , 1)
		
		L.addConnection(c);
		M.addConnection(c); // (2 , 2) -- (3 , 2)
		
		M.addConnection(d); // (3 , 2) -- ( 3 , 3)
		Q.addConnection(d);
		
		K.addConnection(e);// ( 1 , 2) -- (1 , 1)
		F.addConnection(e);
		
		
		B.addConnection(f);// (1 , 0) -- (1 , 1)
		F.addConnection(f);
		
		P.addConnection(g); // ( 2, 3 ) -- ( 3 , 3 )
		Q.addConnection(g);
		
		H.addConnection(h);
		M.addConnection(h); // (3 , 2) -- ( 3 , 1)
		
		H.addConnection(i);
		D.addConnection(i);// (3 , 1 ) -- ( 3 , 0)
		
		D.addConnection(j);
		C.addConnection(j);// ( 3 , 0 ) -- ( 2 , 0)
		
		L.addConnection(k);
		P.addConnection(k);//(( 2, 2) -- (2 , 3)
		
		B.addConnection(l);
		A.addConnection(l);//(0,0) -- ( 0, 1)
		
		B.addConnection(m);
		C.addConnection(m);//(0,1) -- (0,2)
		
		
		
		ArrayList<Tile> testingShit = (ArrayList<Tile>) L.getNeighbours(3);
	/*
		 A==B==C==D 
			|	  |	
		 E  F  G  H
			|  |  |	
		 I  K==L==M
			   |  | 
		 N  O  P==Q
				*/
		
		System.out.println("Starting on  L at Coordinates "+ L.getX() +" "+L.getY());
		 System.out.println("FINAL CHILDREN");

		
		for(Tile tile : testingShit){
			System.out.print(tile.getX()+" ");
			System.out.println(tile.getY());
			System.out.println("----------------");
			
		}
		
		
		
		
		
		
		
	}

}
