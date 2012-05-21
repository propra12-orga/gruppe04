package de.propra12.gruppe04.dynamiteboy.Game;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class Game {
	private Player player;
	private Map map;

	public Game() {
		setPlayer(new Player());
		this.map = new Map(640, 480, player);

	}

	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
