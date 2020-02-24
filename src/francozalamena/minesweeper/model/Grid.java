package francozalamena.minesweeper.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import francozalamena.minesweeper.model.tiles.BombTile;
import francozalamena.minesweeper.model.tiles.Tile;

public class Grid {

	private Tile[][] tiles;
	
	public Grid(int x, int y, int rowNumber, int columnNumber, double tileSize, Difficulty difficulty) throws SlickException {
		tiles = new Tile[rowNumber][columnNumber];
		int bombs = 0;
		for(int i = 0; i<rowNumber; i++) {
			for(int j = 0; j<columnNumber; j++) {
				if(Math.random() < difficulty.getPercentageOfBombs()) {
					tiles[i][j] = new BombTile(tileSize, j * tileSize + x, i * tileSize + y);
					bombs++;
				}else
				tiles[i][j] = new Tile(tileSize, j * tileSize + x, i * tileSize + y);
			}
		}
		System.out.println("Number of bombs ingame: " + bombs); 
	}
	
	public void render(Graphics g) {
		for(int i = 0; i<tiles[0].length; i++) {
			for(int j = 0; j<tiles[1].length; j++) {
				tiles[i][j].render(g);
			}
		}
	}
	
	public Tile[][] getTiles(){
		return tiles;
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
}
