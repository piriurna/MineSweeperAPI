package francozalamena.minesweeper.model.tiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Tile {
	
	protected double sideSize;
	protected Image sprite;
	protected double posX, posY;
	protected boolean isClicked;
	protected boolean isFlaged;
	private int bombs;
	
	public Tile(double sideSize, Image sprite, double posX, double posY) {
		this.setSideSize(sideSize);
		this.setSprite(sprite);
		this.setPosX(posX);
		this.setPosY(posY);
		this.setClicked(false);
	}
	public Tile(double sideSize, double posX, double posY) {
		this.setSideSize(sideSize);
		this.setPosX(posX);
		this.setPosY(posY);
		this.setClicked(false);
	}
	public Tile() {}

	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((float) posX, (float) posY, (float) sideSize, (float) sideSize);
		g.setColor(Color.black);
		g.drawRect((float) posX, (float) posY, (float) sideSize, (float) sideSize);
		if(isFlaged) {
			g.drawString("+", (float) (posX + sideSize/2), (float) (posY + sideSize/2));
			return;
		}
		
		if(!isClicked) return;
		
		g.drawString(String.valueOf(getBombs()), (float) (posX + sideSize/2), (float) (posY + sideSize/2));	
	}
	
	public void onMouseClicked() {
		this.setClicked(true);
	}
	
	public void onRightMouseClicked() {
		
	}
	
	//GETTERS AND SETTERS
	
	public double getSideSize() {
		return sideSize;
	}

	public void setSideSize(double sideSize) {
		this.sideSize = sideSize;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
	
	public int getBombs() {
		return bombs;
	}
	public void setBombs(int bombs) {
		this.bombs = bombs;
	}
	public boolean isClicked() {
		return isClicked;
	}
	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}
	public boolean isFlaged() {
		return isFlaged;
	}
	public void setFlaged(boolean isFlaged) {
		this.isFlaged = isFlaged;
	}
	
}
