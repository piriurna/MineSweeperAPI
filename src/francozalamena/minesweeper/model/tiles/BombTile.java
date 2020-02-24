package francozalamena.minesweeper.model.tiles;

import org.newdawn.slick.Graphics;

public class BombTile extends Tile{
	
	public BombTile(double sideSize, double posX, double posY) {
		super(sideSize, posX, posY);
	}
	
	@Override
	public void render(Graphics g) {
		g.drawRect((float) posX, (float) posY, (float) sideSize, (float) sideSize);
		
		if(!isClicked()) return;
		
		g.drawString(String.valueOf(getBombs()), (float) (posX + sideSize/2), (float) (posY + sideSize/2));	
	}
	
	@Override
	public void onMouseClicked() {
		this.setClicked(true);
	}
	
	public void explode() {
		
	}
}
