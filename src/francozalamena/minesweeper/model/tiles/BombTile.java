package francozalamena.minesweeper.model.tiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

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
		if (isFlagged) {
			drawFlag(g);
			return;
		}
	}

	public void explode() {
	}
	
	private void drawClicked(Graphics g) {
		String text = (isFlagged())? "X": "B";
		g.setColor(Color.blue);
		g.drawString(text, (float) (posX + sideSize / 2), (float) (posY + sideSize / 2));
		g.setColor(Color.black);
	}
	
	@Override
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}
}
