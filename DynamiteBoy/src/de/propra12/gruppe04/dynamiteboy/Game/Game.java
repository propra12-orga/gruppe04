package de.propra12.gruppe04.dynamiteboy.Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Item.Bomb;
import de.propra12.gruppe04.dynamiteboy.Item.Exit;
import de.propra12.gruppe04.dynamiteboy.Item.Item;
import de.propra12.gruppe04.dynamiteboy.Map.Map;
import de.propra12.gruppe04.dynamiteboy.Menu.ScoreMenu;

public class Game extends JPanel {
	private Player player1;
	private Map map;
	private int counter;
	private JFrame frame;

	public Game(JFrame frame) {
		// SET UP
		this.map = new Map(640, 480);
		this.frame = frame;
		setPlayer(new Player(map));
		setFocusable(true);
		this.addKeyListener(new KAdapter());
	}

	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player1;
	}

	public void setPlayer(Player player) {
		this.player1 = player;
	}

	/**
	 * Plants a bomb on current grid-position
	 */
	public void plantBomb() {
		Bomb bomb = new Bomb(player1.getGridX(player1.getX() + 16),
				player1.getGridY(player1.getY() + 16), false, map);
		Thread bombThread = new Thread(bomb);
		bombThread.start();

	}

	// KEY HANDLING AND PAINT METHODS DOWN FROM HERE

	private class KAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			player1KeyReleased(e);
			player1.move();
			repaint();
		}

		public void keyPressed(KeyEvent e) {
			player1KeyPressed(e);
			player1.move();
			repaint();
		}
	}

	public void paintField(Graphics g2d) {
		for (int y = 0; y < 480; y += 32) {
			for (int x = 0; x < 640; x += 32) {
				g2d.drawImage(map.getFieldByPixel(x, y).getImageIcon()
						.getImage(), x, y, this);

			}
		}
	}

	public void paintPlayer(Graphics g2d) {
		g2d.drawImage(player1.getImage(), player1.getX(), player1.getY(), this);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics g2d = (Graphics2D) g;
		paintField(g2d);
		paintPlayer(g2d);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
		// Redraw with 30fps
		try {
			TimeUnit.SECONDS.sleep(1 / 30);
			repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param e
	 *            takes key event (pressed) and changes player-position
	 *            accordingly
	 */

	public void player1KeyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			if (map.getFieldByPixel(player1.getX(), player1.getY()).isBlocked() == false
					&& map.getFieldByPixel(player1.getX(), player1.getY() + 30)
							.isBlocked() == false) {
				this.itemHandling(player1.getX(), player1.getY());
				player1.setDx(-4);
				player1.setDy(0);
			}
		}

		if (key == KeyEvent.VK_RIGHT) {
			if (map.getFieldByPixel(player1.getX() + 28, player1.getY())
					.isBlocked() == false
					&& map.getFieldByPixel(player1.getX() + 28,
							player1.getY() + 28).isBlocked() == false) {
				this.itemHandling(player1.getX(), player1.getY());
				player1.setDx(4);
				player1.setDy(0);
			}
		}

		if (key == KeyEvent.VK_UP) {
			if (map.getFieldByPixel(player1.getX(), player1.getY()).isBlocked() == false
					&& map.getFieldByPixel(player1.getX() + 22, player1.getY())
							.isBlocked() == false) {
				this.itemHandling(player1.getX(), player1.getY());
				player1.setDy(-4);
				player1.setDx(0);
			}
		}

		if (key == KeyEvent.VK_DOWN) {
			if (map.getFieldByPixel(player1.getX(), player1.getY() + 30)
					.isBlocked() == false
					&& map.getFieldByPixel(player1.getX() + 22,
							player1.getY() + 32).isBlocked() == false) {
				this.itemHandling(player1.getX(), player1.getY());
				player1.setDy(4);
				player1.setDx(0);
			}
		}
		if (key == KeyEvent.VK_SPACE) {
			plantBomb();
		}
	}

	/**
	 * 
	 * @param e
	 *            takes key event (released) and stops changing player position
	 */
	public void player1KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			player1.setDx(0);
		}

		if (key == KeyEvent.VK_RIGHT) {
			player1.setDx(0);
		}

		if (key == KeyEvent.VK_UP) {
			player1.setDy(0);
		}

		if (key == KeyEvent.VK_DOWN) {
			player1.setDy(0);
		}
	}

	/**
	 * Checks if item is at field with pixel-coordinates x and y and handles it.
	 * 
	 * @param x
	 * @param y
	 */
	public void itemHandling(int x, int y) {
		Item item = map.getFieldByPixel(x, y).getItem();
		if (item instanceof Exit) {
			this.setVisible(false);
			ScoreMenu m = new ScoreMenu(frame);
		}
	}

}
