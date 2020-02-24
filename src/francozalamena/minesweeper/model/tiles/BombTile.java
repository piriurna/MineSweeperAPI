package francozalamena.minesweeper.model.tiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class BombTile extends Tile{
	
	public BombTile(double sideSize, double posX, double posY) {
		super(sideSize, posX, posY);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((float) posX, (float) posY, (float) sideSize, (float) sideSize);
		g.setColor(Color.black);
		g.drawRect((float) posX, (float) posY, (float) sideSize, (float) sideSize);
		if(isFlaged()) {
			g.drawString("+", (float) (posX + sideSize/2), (float) (posY + sideSize/2));
			return;
		}
			
		if(!isClicked()) return;
	}
	
	@Override
	public void onMouseClicked() {
		this.setClicked(true);
	}
	
	public void explode() {
		
	}
}
