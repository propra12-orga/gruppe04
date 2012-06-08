package de.propra12.gruppe04.dynamiteboy.Item;

import java.util.concurrent.TimeUnit;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

/**
 * The bomb class is used to instantiate bomb objects that represent the bombs
 * dropped by the players
 * 
 * 
 */
public class Bomb extends Item implements Runnable {
	/**
	 * TODO clarify checking for existence of exploding fields (currently
	 * happening in isExplodable())
	 */
	private Map map;
	private int xPos;
	private int yPos;
	// BOMB CONSTANTS
	private static final int BOMB_DELAY = 3000;
	private static final int EXPLOSION_DURATION = 400;
	private static final int BOMB_RADIUS = 3;
	// MAP CONSTANTS
	private final int MAP_GRIDWIDTH;
	private final int MAP_GRIDHEIGHT;
	// MOVEMENT CONSTANTS
	private final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;

	/**
	 * Creats bomb instance
	 * 
	 * @param x
	 * @param y
	 * @param collectable
	 * @param map
	 */
	public Bomb(int x, int y, boolean collectable, Map map) {
		super(collectable);
		// TODO Remove debug
		System.out.println("Bomb planted at " + x + "/" + y);
		this.MAP_GRIDWIDTH = Map.getGridWidth();
		this.MAP_GRIDHEIGHT = Map.getGridHeight();
		this.map = map;
		this.xPos = x;
		this.yPos = y;

	}

	/**
	 * Handles bomb behavior on threadstart
	 */
	@Override
	public void run() {
		try {
			map.getField(xPos, yPos).setItem(this);
			TimeUnit.MILLISECONDS.sleep(BOMB_DELAY);
			detonate(xPos, yPos);
			// TODO Remove debug
			System.out.println("BOOM!");
			TimeUnit.MILLISECONDS.sleep(EXPLOSION_DURATION);
			stopDetonating(xPos, yPos);
			map.getField(xPos, yPos).setItem(null);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles bomb detonation depending on position in grid
	 * 
	 * @param x
	 *            x-position
	 * @param y
	 *            y-position
	 */
	private void detonate(int x, int y) {
		int startx = x;
		int starty = y;
		// middle
		map.getField(x, y).beDeadly(true);
		// right
		for (int i = 1; i <= calcExploRange(RIGHT); i++) {
			x = startx + (1 * i);
			y = starty;
			map.getField(x, y).beDeadly(true);
		}
		// left
		for (int i = 1; i <= calcExploRange(LEFT); i++) {
			x = startx - (1 * i);
			y = starty;
			map.getField(x, y).beDeadly(true);
		}
		// DOWN
		for (int i = 1; i <= calcExploRange(DOWN); i++) {
			x = startx;
			y = starty + (1 * i);
			map.getField(x, y).beDeadly(true);
		}
		// UP
		for (int i = 1; i <= calcExploRange(UP); i++) {
			x = startx;
			y = starty - (1 * i);
			map.getField(x, y).beDeadly(true);
		}
	}

	/**
	 * Sets all fields back to undeadly
	 * 
	 * @param x
	 * @param y
	 */
	private void stopDetonating(int x, int y) {
		int startx = x;
		int starty = y;
		// middle
		map.getField(x, y).beDeadly(false);
		// right
		for (int i = 1; i <= calcExploRange(RIGHT); i++) {
			x = startx + (1 * i);
			y = starty;
			map.getField(x, y).beDeadly(false);
			if (isDestroyable(x, y)) {
				destroy(x, y);
			}
		}
		// left
		for (int i = 1; i <= calcExploRange(LEFT); i++) {
			x = startx - (1 * i);
			y = starty;
			map.getField(x, y).beDeadly(false);
			if (isDestroyable(x, y)) {
				destroy(x, y);
			}
		}
		// DOWN
		for (int i = 1; i <= calcExploRange(DOWN); i++) {
			x = startx;
			y = starty + (1 * i);
			map.getField(x, y).beDeadly(false);
			if (isDestroyable(x, y)) {
				destroy(x, y);
			}
		}
		// UP
		for (int i = 1; i <= calcExploRange(UP); i++) {
			x = startx;
			y = starty - (1 * i);
			map.getField(x, y).beDeadly(false);
			if (isDestroyable(x, y)) {
				destroy(x, y);
			}
		}
	}

	private boolean isDestroyable(int x, int y) {
		if (map.getField(x, y).isDestroyable()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculates range for each direction
	 * 
	 * @param direction
	 * @return explosion radius
	 */
	public int calcExploRange(int direction) {
		boolean gotonext = true;
		int range = 0;
		switch (direction) {
		case LEFT:
			for (int i = 1; i <= BOMB_RADIUS; i++) {
				if (isExplodable(xPos - i, yPos) && gotonext) {
					range++;
				} else {
					gotonext = false;
				}
			}
			break;
		case DOWN:
			for (int i = 1; i <= BOMB_RADIUS; i++) {
				if (isExplodable(xPos, yPos + i) && gotonext) {
					range++;
				} else {
					gotonext = false;
				}
			}
			break;
		case RIGHT:
			for (int i = 1; i <= BOMB_RADIUS; i++) {
				if (isExplodable(xPos + i, yPos) && gotonext) {
					range++;
				} else {
					gotonext = false;
				}
			}
			break;
		case UP:
			for (int i = 1; i <= BOMB_RADIUS; i++) {
				if (isExplodable(xPos, yPos - i) && gotonext) {
					range++;
				} else {
					gotonext = false;
				}
			}
			break;
		}
		return range;
	}

	/**
	 * Handles field-change on destroy on passed fieldposition
	 * 
	 * @param x
	 * @param y
	 */
	private void destroy(int x, int y) {
		if (map.getField(x, y).getItem() instanceof Exit) {
			map.setExitField(x, y);
		}
		if (map.getField(x, y).getItem() == null) {
			map.setFloorField(x, y);
		}
	}

	/**
	 * Checks if field is within the map and if it is explodable
	 * 
	 * @param x
	 * @param y
	 * @return true if explodable
	 */
	private boolean isExplodable(int x, int y) {
		// is field within map range?
		if (x >= 0 && x < MAP_GRIDWIDTH && y >= 0 && y < MAP_GRIDHEIGHT) {
			if (map.getField(x, y).isExplodable()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// GETTERS AND SETTERS

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
}
