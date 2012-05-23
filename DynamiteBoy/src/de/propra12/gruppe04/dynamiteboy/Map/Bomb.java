package de.propra12.gruppe04.dynamiteboy.Map;


public class Bomb extends Item implements Runnable {
	private int xpos;
	private int ypos;
	private static final String BOMB_PIC = "";
	private static final int BOMB_DELAY = 3000;
	private static final int BOMB_TYPE = 1;

	public Bomb(int x, int y, boolean collectable) {
		// Type '1' for BOMB
		super(BOMB_TYPE, BOMB_PIC, collectable);
		this.xpos = x;
		this.ypos = y;

	}

	@Override
	public void run() {
		try {
			Thread.sleep(BOMB_DELAY);
		} catch (InterruptedException e) {
			detonate();
		}
	}

	/**
	 * Detonation handling
	 * 
	 */
	private void detonate() {

	}

}
