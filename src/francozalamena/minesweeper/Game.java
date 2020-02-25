package francozalamena.minesweeper;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import francozalamena.minesweeper.states.Menu;
import francozalamena.minesweeper.states.Play;


public class Game extends StateBasedGame{

	private static final int MENU = 0;
	private static final int PLAY = 0;
	
	public static final int SCREEN_WIDTH = 1366, SCREEN_HEIGHT = 720;
	
	private static boolean gameOver;
	
	public Game(String name) {
		super(name);
		this.addState(new Menu(MENU));
		this.addState(new Play(PLAY));
	}
	
	public static void main(String[] args) {
		try {
			Display.create();
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		}
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Game("MineSweeper"));
			appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			appgc.start();
		}catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException { 
		this.getState(MENU).init(container, this);
		this.getState(PLAY).init(container, this);
		this.enterState(PLAY);
	}
	
	public static void gameOver() {
		setGameOver(true);
	}

	public static boolean isOver() {
		return gameOver;
	}

	public static void setGameOver(boolean gameOver) {
		Game.gameOver = gameOver;
	}

}
