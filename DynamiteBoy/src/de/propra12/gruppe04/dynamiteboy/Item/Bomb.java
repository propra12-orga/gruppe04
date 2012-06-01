package de.propra12.gruppe04.dynamiteboy.Item;

import java.util.concurrent.TimeUnit;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class Bomb extends Item implements Runnable {
	private static final int BOMB_DELAY = 3;
	private static final int BOMB_RADIUS = 3;
	private Map map;
	private int xPos;
	private int yPos;

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
		this.map = map;
		this.xPos = x;
		this.yPos = y;
	}

	/**
	 * Bombthread method
	 */
	@Override
	public void run() {
		try {
			map.getField(this.getxPos(), this.getyPos()).setItem(this);
			TimeUnit.SECONDS.sleep(BOMB_DELAY);
			detonate(this.getxPos(), this.getyPos());
			// TODO Remove debug
			System.out.println("BOOM!");
			map.getField(this.getxPos(), this.getyPos()).setItem(null);
		} catch (InterruptedException e) {

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
		for (int i = 1; i <= BOMB_RADIUS; i++) {
			// right
			if (isDestroyable(x + (1 * i), y)) {
				destroy(x + (1 * i), y);
			}
			// left
			if (isDestroyable(x - (1 * i), y)) {
				destroy(x - (1 * i), y);
			}
			// bottom
			if (isDestroyable(x, y + (1 * i))) {
				destroy(x, y + (1 * i));
			}
			// top
			if (isDestroyable(x, y - (1 * i))) {
				destroy(x, y - (1 * i));
			}
		}
	}

	/**
	 * Checks if field is within the map and if it is destroyable
	 * 
	 * @param x
	 * @param y
	 * @return true if field is destroyable
	 */
	private boolean isDestroyable(int x, int y) {
		if (x >= 0 && x < map.getGridWidth() && y >= 0
				&& y < map.getGridHeight()) {
			if (map.getField(x, y).isDestroyable()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
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
		} else if (map.getField(x, y).getItem() == null) {
			map.setFloorField(x, y);
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
