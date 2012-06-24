package de.propra12.gruppe04.dynamiteboy.Item;

import java.util.concurrent.TimeUnit;

import de.propra12.gruppe04.dynamiteboy.Game.C;
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
	private int map_gridwidth;
	private int map_gridheight;

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
		this.map_gridwidth = Map.getGridWidth();
		this.map_gridheight = Map.getGridHeight();
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
			boolean wait = true;
			int timecounter = 0;
			map.getField(xPos, yPos).setItem(this);
			// wait until detonation
			while (wait) {
				TimeUnit.MILLISECONDS.sleep(1);
				timecounter++;
				if (map.getField(getxPos(), getyPos()).isDeadly()
						|| timecounter >= C.BOMB_DELAY) {
					detonate(xPos, yPos);
					wait = false;
				}
			}

			// TODO Remove debug
			System.out.println("BOOM!");
			TimeUnit.MILLISECONDS.sleep(C.EXPLOSION_DURATION);
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
		for (int i = 1; i <= calcExploRange(C.RIGHT); i++) {
			x = startx + (1 * i);
			y = starty;
			map.getField(x, y).beDeadly(true);
		}
		// left
		for (int i = 1; i <= calcExploRange(C.LEFT); i++) {
			x = startx - (1 * i);
			y = starty;
			map.getField(x, y).beDeadly(true);
		}
		// DOWN
		for (int i = 1; i <= calcExploRange(C.DOWN); i++) {
			x = startx;
			y = starty + (1 * i);
			map.getField(x, y).beDeadly(true);
		}
		// UP
		for (int i = 1; i <= calcExploRange(C.UP); i++) {
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
		for (int i = 1; i <= calcExploRange(C.RIGHT); i++) {
			x = startx + (1 * i);
			y = starty;
			map.getField(x, y).beDeadly(false);
			if (isDestroyable(x, y)) {
				destroy(x, y);
			}
		}
		// left
		for (int i = 1; i <= calcExploRange(C.LEFT); i++) {
			x = startx - (1 * i);
			y = starty;
			map.getField(x, y).beDeadly(false);
			if (isDestroyable(x, y)) {
				destroy(x, y);
			}
		}
		// DOWN
		for (int i = 1; i <= calcExploRange(C.DOWN); i++) {
			x = startx;
			y = starty + (1 * i);
			map.getField(x, y).beDeadly(false);
			if (isDestroyable(x, y)) {
				destroy(x, y);
			}
		}
		// UP
		for (int i = 1; i <= calcExploRange(C.UP); i++) {
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
		case C.LEFT:
			for (int i = 1; i <= C.BOMB_RADIUS; i++) {
				if (isExplodable(xPos - i, yPos) && gotonext) {
					range++;
				} else {
					gotonext = false;
				}
			}
			break;
		case C.DOWN:
			for (int i = 1; i <= C.BOMB_RADIUS; i++) {
				if (isExplodable(xPos, yPos + i) && gotonext) {
					range++;
				} else {
					gotonext = false;
				}
			}
			break;
		case C.RIGHT:
			for (int i = 1; i <= C.BOMB_RADIUS; i++) {
				if (isExplodable(xPos + i, yPos) && gotonext) {
					range++;
				} else {
					gotonext = false;
				}
			}
			break;
		case C.UP:
			for (int i = 1; i <= C.BOMB_RADIUS; i++) {
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
		if (x >= 0 && x < map_gridwidth && y >= 0 && y < map_gridheight) {
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
