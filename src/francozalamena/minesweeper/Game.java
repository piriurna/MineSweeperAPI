package francozalamena.minesweeper;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import francozalamena.minesweeper.states.Menu;
import francozalamena.minesweeper.states.Play;


public class Game extends StateBasedGame{

	private static final int MENU = 0;
	private static final int PLAY = 0;
	
	public Game(String name) {
		super(name);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY));
	}
	
	public static void main(String[] args) {
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Game("MineSweeper"));
			appgc.setDisplayMode(1366, 720, false);
			appgc.start();
		}catch(SlickException e) {
			e.printStackTrace();;
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException { 
		this.getState(MENU).init(container, this);
		this.getState(PLAY).init(container, this);
		this.enterState(MENU);
	}

}
