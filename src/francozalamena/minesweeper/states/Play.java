package francozalamena.minesweeper.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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

	public Play(int state) {
		this.state = state;
		try {
			this.playField = new Grid(Game.SCREEN_WIDTH / 2 - (35 * 10) / 2, Game.SCREEN_HEIGHT / 2 - (35 * 10) / 2, 10,
					10, 35, Difficulty.MEDIUM);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		for (int i = 0; i < playField.getTiles()[1].length; i++) {
			for (int j = 0; j < playField.getTiles()[0].length; j++) {
				Tile t = playField.getTiles()[i][j];
				t.setBombs(playField.countBombs(i, j));
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.clear();
		playField.render(g);
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(Game.isOver()) return;
		handleLeftMouseButton();
		handleRightMouseButton();
		handleMiddleMouseButton();
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void handleLeftMouseButton() {
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
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
	}

	private boolean rightMouseButton;
	private void handleRightMouseButton() {
		if (Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON) && !rightMouseButton) {
			for (int i = 0; i < playField.getTiles()[0].length; i++) {
				for (int j = 0; j < playField.getTiles()[1].length; j++) {
					Tile t = playField.getTile(i, j);
					if (mouseHitBounds(t)) {
						int bombCount = (playField.getBombCount() > 0)?(playField.getBombCount() - 1):0;
						playField.setBombCount(bombCount);
						if(bombCount > 0 && !t.isFlaged())
							t.setFlaged(true);
						else
							t.setFlaged(false);
					}
				}
			}
		}
		rightMouseButton = Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON);
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


	private boolean mouseHitBounds(Tile t) {
		return ((getMouseX() > t.getPosX()) && (getMouseX() < t.getPosX() + t.getSideSize()))
				&& ((getMouseY() > t.getPosY()) && (getMouseY() < t.getPosY() + t.getSideSize()));
	}

	@Override
	public int getID() {
		return state;
	}

	private double getMouseX() {
		return Mouse.getX();
	}

	private double getMouseY() {
		return Game.SCREEN_HEIGHT - Mouse.getY();
	}
}
