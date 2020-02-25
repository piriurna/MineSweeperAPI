package francozalamena.minesweeper.model;

import org.newdawn.slick.Graphics;

import francozalamena.minesweeper.Game;
import francozalamena.minesweeper.model.tiles.BombTile;
import francozalamena.minesweeper.model.tiles.Tile;

public class Grid {

	private Tile[][] tiles;

	private int bombCount;

	private int x, y, rowNumber, columnNumber;

	private double tileSize;

	public Grid(int x, int y, int rowNumber, int columnNumber, double tileSize, Difficulty difficulty) {
		this.x = x;
		this.y = y;
		this.tileSize = tileSize;
		this.setRowNumber(rowNumber);
		this.setColumnNumber(columnNumber);
		tiles = new Tile[rowNumber][columnNumber];

		for (int i = 0; i < rowNumber; i++) {
			for (int j = 0; j < columnNumber; j++) {
				if (Math.random() < difficulty.getPercentageOfBombs()) {
					tiles[i][j] = new BombTile(tileSize, j * tileSize + x, i * tileSize + y);
					bombCount++;
				} else
					tiles[i][j] = new Tile(tileSize, j * tileSize + x, i * tileSize + y);
			}
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < tiles[0].length; i++) {
			for (int j = 0; j < tiles[1].length; j++) {
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

	public int countFlaggedTiles(int row, int col) {
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
				if (getTile(i, j).isFlagged()) {
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

				Tile t = getTile(i, j);
				
				if (getTile(i, j).isClicked() || getTile(i, j).isFlagged())
					continue;
				
				getTile(i, j).setClicked(true);
				if (t instanceof BombTile) {
					gameOver();
					break;
				}
				if (getTile(i, j).getBombs() == 0)
					openBlankTiles(i, j);
			}
		}
	}

	public void flagTile(Tile t) {
		if (!t.isFlagged()) {
			if (!t.isClicked()) {
				if ((getBombCount() > 0))
					t.setFlagged(true);
				setBombCount((getBombCount() > 0) ? (getBombCount() - 1) : 0);
			}
		} else {
			t.setFlagged(false);
			setBombCount(getBombCount() + 1);
		}
	}

	public boolean isBomb(Tile t) {
		return (t instanceof BombTile);
	}

	public void gameOver() {
		Game.setGameOver(true);
		for (int i = 0; i < getTiles()[0].length; i++) {
			for (int j = 0; j < getTiles()[1].length; j++) {
				Tile t = getTile(i, j);
				if (t instanceof BombTile) {
					t.setClicked(true);
				}
			}
		}
	}

	// GETTERS AND SETTERS

	public Tile[][] getTiles() {
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		for (int i = 0; i < getTiles()[0].length; i++) {
			for (int j = 0; j < getTiles()[1].length; j++) {
				getTile(i, j).setPosX(j * this.tileSize + x);
			}
		}
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		for (int i = 0; i < getTiles()[0].length; i++) {
			for (int j = 0; j < getTiles()[1].length; j++) {
				getTile(i, j).setPosY(i * this.tileSize + y);
			}
		}
		this.y = y;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
}
