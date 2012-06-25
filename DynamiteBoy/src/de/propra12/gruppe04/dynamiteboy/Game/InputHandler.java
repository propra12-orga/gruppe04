package de.propra12.gruppe04.dynamiteboy.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * Handles key events
 * 
 */
public class InputHandler implements KeyListener {
	private int[] keys = new int[256];

	private boolean[] is_key_up = new boolean[256]; // true if pressed
	private boolean[] is_key_down = new boolean[256]; // true if not pressed
	private boolean keyPressed = false;
	private boolean keyReleased = false;

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < 256) {
			keys[e.getKeyCode()] = (int) System.currentTimeMillis();
			is_key_down[e.getKeyCode()] = true;
			is_key_up[e.getKeyCode()] = false;
			keyPressed = true;
			keyReleased = false;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < 256) {
			keys[e.getKeyCode()] = 0;
			is_key_up[e.getKeyCode()] = true;
			is_key_down[e.getKeyCode()] = false;
			keyPressed = false;
			keyReleased = true;
		}
	}

	/**
	 * @param key
	 *            Keys are referred by the constants in
	 *            <i>java.awt.event.KeyEvent</i>
	 * @return true if specified key is pressed
	 */
	public boolean isKeyDown(int key) {
		return is_key_down[key];
	}

	/**
	 * @param key
	 *            Keys are referred by the constants in
	 *            <i>java.awt.event.KeyEvent</i>
	 * @return true if specified key is not pressed
	 */
	public boolean isKeyUp(int key) {
		return is_key_up[key];
	}

	public void update() {
		is_key_down = new boolean[256];
		keyReleased = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}