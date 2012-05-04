import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player {
	private int dx;
	private int dy;
	private int x;
	private int y;
	private Image image;

	Player() {
		ImageIcon img = new ImageIcon(this.getClass().getResource(
				"images/db_char_placeholder.png"));
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
	 * 
	 * @param e
	 *            takes key event (pressed) and changes player-position
	 *            accordingly
	 */

	// TODO don't let the player move into two directions at once
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
		}

		if (key == KeyEvent.VK_UP) {
			dy = -1;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 1;
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
	}
}
