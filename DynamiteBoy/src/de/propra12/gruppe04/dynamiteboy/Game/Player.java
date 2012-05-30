package de.propra12.gruppe04.dynamiteboy.Game;

import java.awt.Image;

import javax.swing.ImageIcon;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class Player {
	private int dx;
	private int dy;
	private int x;
	private int y;
	private Image image;
	private String playerPicture = "../images/db_char_placeholder.png";
	private Map map;

	Player(Map map) {
		ImageIcon img = new ImageIcon(this.getClass()
				.getResource(playerPicture));
		image = img.getImage();
		x = 32;
		y = 32;
		this.map = map;
	}

	/**
	 * Updates player coordinates
	 */
	public void move() {
		x += dx;
		y += dy;
	}

	/**
	 * 
	 * @return current x-coordinate of player
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return current y-coordinate of player
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return player-image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return x-position in current map
	 */
	public int getGridX(int x) {
		x = x / 32;
		return x;
	}

	/**
	 * @return x-position in current map
	 */
	public int getGridY(int y) {
		y = y / 32;
		return y;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

}
