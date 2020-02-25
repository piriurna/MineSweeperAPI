package francozalamena.minesweeper.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import francozalamena.minesweeper.Game;
import francozalamena.minesweeper.model.Difficulty;
import francozalamena.minesweeper.model.Grid;
import francozalamena.minesweeper.model.tiles.BombTile;
import francozalamena.minesweeper.model.tiles.Tile;

public class Play extends BasicGameState {

	private int state;
	private Grid playField;
	private static final int numOfRows = 10, numOfCols = 10;
	private static int sideSize = (Game.SCREEN_HEIGHT - 100) / numOfRows,
			gridX = Game.SCREEN_WIDTH / 2 - (sideSize * numOfRows) / 2,
			gridY = Game.SCREEN_HEIGHT / 2 - (sideSize * numOfCols) / 2;

	public Play(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		createNewGame();
	}

	private static int counterX = gridX + (sideSize * numOfRows) / 2 - 25, counterY = gridY - 20;

	private void drawBombCount(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Bombs: " + playField.getBombCount(), counterX, counterY);
	}

	private static int resetButtonX = gridX + (sideSize * numOfRows) + 50, resetButtonY = gridY;

	private void drawGameOver(Graphics g) throws SlickException {
		g.setColor(Color.black);
		g.drawImage(new Image("res/reset.png"), resetButtonX, resetButtonY);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.clear();
		playField.render(g);
		drawBombCount(g);
		if (Game.isOver()) {
			drawGameOver(g);
		}
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (Game.isOver()) {
			handlePostGame();
		} else {
			handleLeftMouseButton();
			handleRightMouseButton();
			handleMiddleMouseButton();
		}
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	boolean firstTime = false;

	private void handlePostGame() {
		playField.setX(15);
		gridX = 15;
		counterX = gridX + (sideSize * numOfRows) / 2 - 25;
		resetButtonX = gridX + (sideSize * numOfRows) + 50;
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON))
			if (resetClicked()) {
				createNewGame();
			}
	}

	private void createNewGame() {
		sideSize = (Game.SCREEN_HEIGHT - 100) / numOfRows;
		gridX = Game.SCREEN_WIDTH / 2 - (sideSize * numOfRows) / 2;
		gridY = Game.SCREEN_HEIGHT / 2 - (sideSize * numOfCols) / 2;
		counterX = gridX + (sideSize * numOfRows) / 2 - 25;
		this.playField = new Grid(gridX, gridY, numOfRows, numOfCols, sideSize, Difficulty.MEDIUM);
		for (int i = 0; i < playField.getTiles()[1].length; i++) {
			for (int j = 0; j < playField.getTiles()[0].length; j++) {
				Tile t = playField.getTiles()[i][j];
				t.setBombs(playField.countBombs(i, j));
			}
		}
		Game.setGameOver(false);
	}

	private void verifyClickedTile() {
		for (int i = 0; i < playField.getTiles()[0].length; i++) {
			for (int j = 0; j < playField.getTiles()[1].length; j++) {
				Tile t = playField.getTile(i, j);
				if (mouseHitBounds(t)) {
					if (playField.isBomb(t)) {
						((BombTile) t).explode();
						playField.gameOver();
					} else {
						if (t.getBombs() == 0)
							playField.openBlankTiles(i, j);
					}
					t.onMouseClicked();
				}
			}
		}
	}

	private boolean leftMouseButton;

	private void handleLeftMouseButton() {
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON) && !leftMouseButton) {
			verifyClickedTile();
		}
		leftMouseButton = Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON);
	}

	private boolean rightMouseButton;

	private void handleRightMouseButton() {
		if (Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON) && !rightMouseButton) {
			for (int i = 0; i < playField.getTiles()[0].length; i++) {
				for (int j = 0; j < playField.getTiles()[1].length; j++) {
					Tile t = playField.getTile(i, j);
					if (mouseHitBounds(t)) {
						flagTile(t);
					}
				}
			}
		}
		rightMouseButton = Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON);
	}

	private void flagTile(Tile t) {
		if (!t.isFlagged()) {
			if (!t.isClicked()) {
				if((playField.getBombCount() > 0)) t.setFlagged(true);
				playField.setBombCount((playField.getBombCount() > 0) ? (playField.getBombCount() - 1) : 0);	
			}
		} else {
			t.setFlagged(false);
			playField.setBombCount(playField.getBombCount() + 1);
		}
	}

	private boolean middleMouseButton;

	private void handleMiddleMouseButton() {
		if (Mouse.isButtonDown(Input.MOUSE_MIDDLE_BUTTON) && !middleMouseButton) {
			for (int i = 0; i < playField.getTiles()[0].length; i++) {
				for (int j = 0; j < playField.getTiles()[1].length; j++) {
					Tile t = playField.getTile(i, j);
					if (mouseHitBounds(t))
						if (playField.countFlaggedBombs(i, j) == t.getBombs() && t.isClicked())
							playField.openBlankTiles(i, j);
				}
			}
		}
		middleMouseButton = Mouse.isButtonDown(Input.MOUSE_MIDDLE_BUTTON);
	}

	private boolean mouseHitBounds(Object o) {
		if (o instanceof Tile)
			return ((getMouseX() > ((Tile) o).getPosX())
					&& (getMouseX() < ((Tile) o).getPosX() + ((Tile) o).getSideSize()))
					&& ((getMouseY() > ((Tile) o).getPosY())
							&& (getMouseY() < ((Tile) o).getPosY() + ((Tile) o).getSideSize()));
		return false;
	}

	private boolean resetClicked() {
		return (getMouseX() > resetButtonX && getMouseX() < resetButtonX + 300 && getMouseY() > resetButtonY
				&& getMouseY() < resetButtonY + 118);
	}

	private double getMouseX() {
		return Mouse.getX();
	}

	private double getMouseY() {
		return Game.SCREEN_HEIGHT - Mouse.getY();
	}

	@Override
	public int getID() {
		return state;
	}
}
