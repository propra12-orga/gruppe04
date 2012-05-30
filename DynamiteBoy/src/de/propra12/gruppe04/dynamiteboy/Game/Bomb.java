package de.propra12.gruppe04.dynamiteboy.Game;

import java.util.concurrent.TimeUnit;

import de.propra12.gruppe04.dynamiteboy.Map.Item;
import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class Bomb extends Item implements Runnable {
	private static final String BOMB_PIC = "../images/db_item_bomb.png";
	private static final int BOMB_DELAY = 3;
	private static final int BOMB_TYPE = 1;
	private static final int BOMB_RADIUS = 3;
	private Map map;

	public Bomb(int x, int y, boolean collectable, Map map) {
		super(x, y, BOMB_TYPE, BOMB_PIC, collectable);
		// TODO Remove debug
		System.out.println("Bomb planted at " + x + "/" + y);
		this.map = map;
	}

	@Override
	public void run() {
		try {
			map.getField(this.getXpos(), this.getYpos()).setItem(1);
			TimeUnit.SECONDS.sleep(BOMB_DELAY);
			detonate(this.getXpos(), this.getYpos());
			// TODO Remove debug
			System.out.println("BOOM!");
			map.getField(this.getXpos(), this.getYpos()).setItem(0);
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
			if (destroyable(x + (1 * i), y)) {
				destroy(x + (1 * i), y);
			}
			if (destroyable(x - (1 * i), y)) {
				destroy(x + (1 * i), y);
			}
			if (destroyable(x, y + (1 * i))) {
				destroy(x, y + (1 * i));
			}
			if (destroyable(x, y - (1 * i))) {
				destroy(x, y - (1 * i));
			}
		}
	}

	private boolean destroyable(int x, int y) {
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

	private void destroy(int x, int y) {
		map.setFloorField(x, y);
	}
}
