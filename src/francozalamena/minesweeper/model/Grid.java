package francozalamena.minesweeper.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import francozalamena.minesweeper.Game;
import francozalamena.minesweeper.model.tiles.BombTile;
import francozalamena.minesweeper.model.tiles.Tile;

public class Grid {

	private Tile[][] tiles;
	
	private int bombCount;
	
	public Grid(int x, int y, int rowNumber, int columnNumber, double tileSize, Difficulty difficulty) {
		tiles = new Tile[rowNumber][columnNumber];

		for(int i = 0; i<rowNumber; i++) {
			for(int j = 0; j<columnNumber; j++) {
				if(Math.random() < difficulty.getPercentageOfBombs()) {
					tiles[i][j] = new BombTile(tileSize, j * tileSize + x, i * tileSize + y);
					bombCount++;
				}else
				tiles[i][j] = new Tile(tileSize, j * tileSize + x, i * tileSize + y);
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i<tiles[0].length; i++) {
			for(int j = 0; j<tiles[1].length; j++) {
				tiles[i][j].render(g);
			}
		}
	}
	
	public int countBombs(int row, int col) {
		int bombs = 0;
		for (int i = (row - 1); i <= (row + 1); i++) {
			if (i > getTiles()[0].length - 1)
				break;
			if (i < 0)
				continue;
			for (int j = (col - 1); j <= col + 1; j++) {
				if (row == i && col == j)
					continue;
				if (j > getTiles()[1].length - 1)
					break;
				if (j < 0)
					continue;
				if (isBomb(getTile(i, j))) {
					bombs++;
				}
			}
		}
		return bombs;
	}
	
	public int countFlaggedBombs(int row, int col) {
		int bombs = 0;
		for (int i = (row - 1); i <= (row + 1); i++) {
			if (i > getTiles()[0].length - 1)
				break;
			if (i < 0)
				continue;
			for (int j = (col - 1); j <= col + 1; j++) {
				if (row == i && col == j)
					continue;
				if (j > getTiles()[1].length - 1)
					break;
				if (j < 0)
					continue;
				if (isBomb(getTile(i, j)) && getTile(i, j).isFlaged()) {
					bombs++;
				}
			}
		}
		return bombs;
	}
	
	public void openBlankTiles(int row, int col) {
		for (int i = (row - 1); i <= (row + 1); i++) {
			if (i > getTiles()[0].length - 1)
				break;
			if (i < 0)
				continue;
			for (int j = (col - 1); j <= col + 1; j++) {
				if (row == i && col == j)
					continue;
				if (j > getTiles()[1].length - 1)
					break;
				if (j < 0)
					continue;
				if (getTile(i, j).isClicked())
					continue;
				if (!(getTile(i, j) instanceof BombTile)) {
					getTile(i, j).setClicked(true);
					if (getTile(i, j).getBombs() == 0)
						openBlankTiles(i, j);
				}
			}
		}
	}
	
	public boolean isBomb(Tile t) {
		return (t instanceof BombTile);
	}
	
	public void gameOver() {
		Game.setGameOver(true);
		for(int i = 0; i < getTiles()[0].length; i++) {
			for(int j = 0; j < getTiles()[1].length; j++) {
				Tile t = getTile(i,j);
				if(t instanceof BombTile) {
					t.setClicked(true);
				}
			}
		}
	}
	
	//GETTERS AND SETTERS
	
	public Tile[][] getTiles(){
		return tiles;
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public int getBombCount() {
		return bombCount;
	}

	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}
}
