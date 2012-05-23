package de.propra12.gruppe04.dynamiteboy.Game;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import de.propra12.gruppe04.dynamiteboy.Map.Bomb;
import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class Player implements Runnable {
	private int dx;
	private int dy;
	private int x;
	private int y;
	private Image image;
	private String playerPicture = "../images/db_char_placeholder.png";

	Player() {
		ImageIcon img = new ImageIcon(this.getClass()
				.getResource(playerPicture));
		image = img.getImage();
		x = 32;
		y = 32;
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

	/**
	 * 
	 * @param e
	 *            takes key event (pressed) and changes player-position
	 *            accordingly
	 */

	// TODO don't let the player move into two directions at once
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			if (Map.getFieldGridByPixel(x - 1, y).isBlocked() == false) {
				dx = -4;
			}
		}

		if (key == KeyEvent.VK_RIGHT) {
			if (Map.getFieldGridByPixel(x + 1, y).isBlocked() == false) {
				dx = 4;
			}
		}

		if (key == KeyEvent.VK_UP) {
			if (Map.getFieldGridByPixel(x, y - 1).isBlocked() == false) {
				dy = -4;
			}
		}

		if (key == KeyEvent.VK_DOWN) {
			if (Map.getFieldGridByPixel(x, y + 1).isBlocked() == false) {
				dy = 4;
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            takes key event (released) and stops changing player position
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
		if (key == KeyEvent.VK_BACK_SPACE) {
			// plantBomb();
		}
	}

	private void plantBomb() {
		Bomb bomb = new Bomb(getGridX(this.x), getGridY(this.y), false);
		Thread bombThread = new Thread(bomb);
		bombThread.start();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
