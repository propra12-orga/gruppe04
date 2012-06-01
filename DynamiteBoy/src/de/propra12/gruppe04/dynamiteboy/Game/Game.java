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
import de.propra12.gruppe04.dynamiteboy.Item.Item;
import de.propra12.gruppe04.dynamiteboy.Map.ExitField;
import de.propra12.gruppe04.dynamiteboy.Map.Field;
import de.propra12.gruppe04.dynamiteboy.Map.Map;
import de.propra12.gruppe04.dynamiteboy.Menu.ScoreMenu;

public class Game extends JPanel {
	private int playerStartPos[][] = new int[2][2];
	private Player player[] = new Player[2];
	private Map map;
	private JFrame frame;
	private int numberOfPlayers;
	// Player constants
	private final int PLAYER1 = 0, PLAYER2 = 1;
	// Movement constants
	private final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;

	public Game(JFrame frame, int numberOfPlayers, String mapName) {
		// SET UP
		this.numberOfPlayers = numberOfPlayers;
		this.map = new Map(640, 480, mapName);
		this.frame = frame;
		createPlayers(numberOfPlayers);
		setFocusable(true);
		this.addKeyListener(new KAdapter());
	}

	/**
	 * 
	 * @return current map-object
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * 
	 * @param playerIndex
	 *            index of player to return
	 * @return player object
	 */
	public Player getPlayer(int playerIndex) {
		return player[playerIndex];
	}

	/**
	 * creates players and sets starting positions
	 * 
	 * @param numberOfPlayers
	 *            to create
	 */
	public void createPlayers(int numberOfPlayers) {
		playerStartPos[0][0] = 32;
		playerStartPos[0][1] = 32;
		playerStartPos[1][0] = 581;
		playerStartPos[1][1] = 416;
		for (int i = 0; i < numberOfPlayers; i++) {
			player[i] = new Player(i, playerStartPos[i][0],
					playerStartPos[i][1], map);

		}
	}

	/**
	 * Plants a bomb on current grid-position
	 * 
	 * @param playerIndex
	 *            player to plant the bomb
	 */

	public void plantBomb(int playerIndex) {
		Bomb bomb = new Bomb(
				player[playerIndex]
						.getGridX(player[playerIndex].getxPos() + 16),
				player[playerIndex].getGridY(player[playerIndex].getyPos() + 16),
				false, map);
		Thread bombThread = new Thread(bomb);
		bombThread.start();
	}

	/**
	 * Checks if item is at field with pixel-coordinates x and y and handles it
	 * 
	 * First: Checks if Field is ExitField (has nothing to do with items but is
	 * easier to implement here atm than elsewhere) and exits if it is
	 * 
	 * Then: Other items are handled
	 * 
	 * @param x
	 *            x-coordinate of player (in px)
	 * @param y
	 *            y-coordinate of player (in px)
	 */
	public void itemHandling(int x, int y) {
		Field f = map.getFieldByPixel(x + 16, y + 16);
		Item item = map.getFieldByPixel(x + 16, y + 16).getItem();
		if (f instanceof ExitField) {
			this.setVisible(false);
			ScoreMenu m = new ScoreMenu(frame);
		}
	}

	// KEY HANDLING AND PAINT METHODS DOWN FROM HERE

	// Adapter to handle KeyEvents
	private class KAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			player1KeyReleased(e);
			if (numberOfPlayers > 1) {
				player2KeyReleased(e);
			}
		}

		public void keyPressed(KeyEvent e) {
			player1KeyPressed(e);
			if (numberOfPlayers > 1) {
				player2KeyPressed(e);
			}
		}
	}

	/**
	 * draws the map
	 * 
	 * @param g2d
	 *            Graphics object (painter)
	 */
	public void paintField(Graphics g2d) {
		for (int y = 0; y < 480; y += 32) {
			for (int x = 0; x < 640; x += 32) {
				g2d.drawImage(map.getFieldByPixel(x, y).getImageIcon()
						.getImage(), x, y, this);
			}
		}
	}

	/**
	 * draws the player
	 * 
	 * @param g2d
	 *            g2d Graphics object (painter)
	 * @param playerIndex
	 *            index of player to be drawn
	 */
	public void paintPlayer(Graphics g2d, int playerIndex) {
		g2d.drawImage(player[playerIndex].getImage(),
				player[playerIndex].getxPos(), player[playerIndex].getyPos(),
				this);
	}

	/**
	 * paints everything and repaints at 30fps
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics g2d = (Graphics2D) g;
		paintField(g2d);
		for (int i = 0; i < numberOfPlayers; i++) {
			paintPlayer(g2d, i);
		}
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

	public void player1KeyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			player[PLAYER1].move(LEFT);
			itemHandling(player[PLAYER1].getxPos(), player[PLAYER1].getyPos());
		}

		if (key == KeyEvent.VK_RIGHT) {
			player[PLAYER1].move(RIGHT);
			itemHandling(player[PLAYER1].getxPos(), player[PLAYER1].getyPos());
		}

		if (key == KeyEvent.VK_UP) {
			player[PLAYER1].move(UP);
			itemHandling(player[PLAYER1].getxPos(), player[PLAYER1].getyPos());
		}

		if (key == KeyEvent.VK_DOWN) {
			player[PLAYER1].move(DOWN);
			itemHandling(player[PLAYER1].getxPos(), player[PLAYER1].getyPos());
		}

		if (key == KeyEvent.VK_ENTER) {
			plantBomb(PLAYER1);
		}

	}

	public void player2KeyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			player[PLAYER2].move(LEFT);
			itemHandling(player[PLAYER2].getxPos(), player[PLAYER2].getyPos());
			;
		}

		if (key == KeyEvent.VK_D) {
			player[PLAYER2].move(RIGHT);
			itemHandling(player[PLAYER2].getxPos(), player[PLAYER2].getyPos());
		}

		if (key == KeyEvent.VK_W) {
			player[PLAYER2].move(UP);
			itemHandling(player[PLAYER2].getxPos(), player[PLAYER2].getyPos());
		}

		if (key == KeyEvent.VK_S) {
			player[PLAYER2].move(DOWN);
			itemHandling(player[PLAYER2].getxPos(), player[PLAYER2].getyPos());
		}

		if (key == KeyEvent.VK_SPACE) {
			plantBomb(PLAYER2);
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
			player[PLAYER1].setDx(0);
		}

		if (key == KeyEvent.VK_RIGHT) {
			player[PLAYER1].setDx(0);
		}

		if (key == KeyEvent.VK_UP) {
			player[PLAYER1].setDy(0);
		}

		if (key == KeyEvent.VK_DOWN) {
			player[PLAYER1].setDy(0);
		}

	}

	public void player2KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			player[PLAYER2].setDx(0);
		}

		if (key == KeyEvent.VK_D) {
			player[PLAYER2].setDx(0);
		}

		if (key == KeyEvent.VK_W) {
			player[PLAYER2].setDy(0);
		}

		if (key == KeyEvent.VK_S) {
			player[PLAYER2].setDy(0);
		}

	}

}
