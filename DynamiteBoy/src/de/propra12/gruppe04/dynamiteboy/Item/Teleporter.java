package de.propra12.gruppe04.dynamiteboy.Item;

import de.propra12.gruppe04.dynamiteboy.Game.Player;
import de.propra12.gruppe04.dynamiteboy.Map.FloorField;
import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class Teleporter extends Item {

	public Teleporter() {
		super(false);
	}

	/**
	 * Teleports the player to a new Position on the map
	 * 
	 * @param player
	 * @param map
	 */
	public void teleport(Player player, Map map) {
		int teleX = (int) Math.random() * 20;
		int teleY = (int) Math.random() * 15;
		System.out.println("Teleport to " + teleX + "/" + teleY);
		if (map.getField(teleX, teleY) instanceof FloorField) {
			player.setxPos(teleX * 32);
			player.setyPos(teleY * 32);
		} else {

		}

	}
}
