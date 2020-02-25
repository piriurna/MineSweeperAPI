package francozalamena.minesweeper.model.tiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import francozalamena.minesweeper.Game;

public class BombTile extends Tile {

	public BombTile(double sideSize, double posX, double posY) {
		super(sideSize, posX, posY);
	}

	@Override
	public void render(Graphics g) {
		drawTile(g);
		if (isClicked) {
			drawClicked(g);
			return;
		}
		if (isFlaged) {
			drawFlag(g);
			return;
		}
	}

	public void explode() {
		Game.setGameOver(true);
	}
	
	private void drawClicked(Graphics g) {
		g.setColor(Color.blue);
		g.drawString("B", (float) (posX + sideSize / 2), (float) (posY + sideSize / 2));
		g.setColor(Color.black);
	}
	
	@Override
	public void setFlaged(boolean isFlaged) {
		this.isFlaged = isFlaged;
	}
}
