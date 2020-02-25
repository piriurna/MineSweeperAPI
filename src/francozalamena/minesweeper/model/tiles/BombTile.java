package francozalamena.minesweeper.model.tiles;

import org.newdawn.slick.Graphics;

public class BombTile extends Tile {

	public BombTile(double sideSize, double posX, double posY) {
		super(sideSize, posX, posY);
	}

	@Override
	public void render(Graphics g) {
		drawTile(g);
		if (isFlaged()) {
			drawFlag(g);
			return;
		}

		if (!isClicked())
			return;
	}

	@Override
	public void onMouseClicked() {
		this.setClicked(true);
	}

	public void explode() {

	}
}
