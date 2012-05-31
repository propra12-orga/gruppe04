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
	private int playerStartPos[][] = new int[2][2];
	private Player player[] = new Player[2];
	private Map map;
	private JFrame frame;
	private int numberOfPlayers;

	public Game(JFrame frame, int numberOfPlayers) {
		// SET UP
		this.numberOfPlayers = numberOfPlayers;
		this.map = new Map(640, 480,
				"src/de/propra12/gruppe04/dynamiteboy/Map/Map1.xml");
		// this.map = new Map(640, 480);
		this.frame = frame;
		this.playerStartPos[0][0] = 32;
		this.playerStartPos[0][1] = 32;
		this.playerStartPos[1][0] = 581;
		this.playerStartPos[1][1] = 416;

		System.err.println(this.numberOfPlayers);
		createPlayers(this.numberOfPlayers);
		setFocusable(true);
		this.addKeyListener(new KAdapter());
	}

	public Map getMap() {
		return map;
	}

	public Player getPlayer(int playerIndex) {
		return this.player[playerIndex];
	}

	public void createPlayers(int numberOfPlayers) {
		for (int i = 0; i < numberOfPlayers; i++) {
			this.player[i] = new Player(i, this.playerStartPos[i][0],
					this.playerStartPos[i][1]);
		}
	}

	/**
	 * Plants a bomb on current grid-position
	 */

	public void plantBomb(int playerIndex) {
		Bomb bomb = new Bomb(
				this.player[playerIndex].getGridX(this.player[playerIndex]
						.getxPos() + 16),
				this.player[playerIndex].getGridY(this.player[playerIndex]
						.getyPos() + 16), false, map);
		Thread bombThread = new Thread(bomb);
		bombThread.start();

	}

	// KEY HANDLING AND PAINT METHODS DOWN FROM HERE

	private class KAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			player1KeyReleased(e);
			if (numberOfPlayers > 1)
				player2KeyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			int playerSum = player.length;
			player1KeyPressed(e);
			if (numberOfPlayers > 1)
				player2KeyPressed(e);
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

	public void paintPlayer(Graphics g2d, int playerIndex) {
		g2d.drawImage(this.player[playerIndex].getImage(),
				this.player[playerIndex].getxPos(),
				this.player[playerIndex].getyPos(), this);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics g2d = (Graphics2D) g;
		paintField(g2d);

		for (int i = 0; i < this.numberOfPlayers; i++) {
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

	/**
	 * 
	 * @param e
	 *            takes key event (pressed) and changes player-position
	 *            accordingly
	 */
	public void playerMoveLeft(int playerIndex) {
		if (map.getFieldByPixel(this.player[playerIndex].getxPos(),
				this.player[playerIndex].getyPos()).isBlocked() == false
				&& map.getFieldByPixel(this.player[playerIndex].getxPos(),
						this.player[playerIndex].getyPos() + 30).isBlocked() == false) {
			this.itemHandling(this.player[playerIndex].getxPos(),
					this.player[playerIndex].getyPos());
			this.player[playerIndex].setDx(-4);
			this.player[playerIndex].setDy(0);
		}
	}

	public void playerMoveRight(int playerIndex) {
		if (map.getFieldByPixel(this.player[playerIndex].getxPos() + 28,
				this.player[playerIndex].getyPos()).isBlocked() == false
				&& map.getFieldByPixel(this.player[playerIndex].getxPos() + 28,
						this.player[playerIndex].getyPos() + 28).isBlocked() == false) {
			this.itemHandling(this.player[playerIndex].getxPos(),
					this.player[playerIndex].getyPos());
			this.player[playerIndex].setDx(4);
			this.player[playerIndex].setDy(0);
		}
	}

	public void playerMoveUp(int playerIndex) {

		if (map.getFieldByPixel(this.player[playerIndex].getxPos(),
				this.player[playerIndex].getyPos()).isBlocked() == false
				&& map.getFieldByPixel(this.player[playerIndex].getxPos() + 22,
						this.player[playerIndex].getyPos()).isBlocked() == false) {
			this.itemHandling(this.player[playerIndex].getxPos(),
					this.player[playerIndex].getyPos());
			this.player[playerIndex].setDy(-4);
			this.player[playerIndex].setDx(0);
		}
	}

	public void playerMoveDown(int playerIndex) {
		if (map.getFieldByPixel(this.player[playerIndex].getxPos(),
				this.player[playerIndex].getyPos() + 30).isBlocked() == false
				&& map.getFieldByPixel(this.player[playerIndex].getxPos() + 22,
						this.player[playerIndex].getyPos() + 32).isBlocked() == false) {
			this.itemHandling(this.player[playerIndex].getxPos(),
					this.player[playerIndex].getyPos());
			this.player[playerIndex].setDy(4);
			this.player[playerIndex].setDx(0);
		}
	}

	public void player1KeyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			playerMoveLeft(0);
		}

		if (key == KeyEvent.VK_RIGHT) {
			playerMoveRight(0);
		}

		if (key == KeyEvent.VK_UP) {
			playerMoveUp(0);
		}

		if (key == KeyEvent.VK_DOWN) {
			playerMoveDown(0);
		}

		if (key == KeyEvent.VK_ENTER) {
			plantBomb(0);
		}

		this.player[0].move();

	}

	public void player2KeyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			playerMoveLeft(1);
		}

		if (key == KeyEvent.VK_D) {
			playerMoveRight(1);
		}

		if (key == KeyEvent.VK_W) {
			playerMoveUp(1);
		}

		if (key == KeyEvent.VK_S) {
			playerMoveDown(1);
		}

		if (key == KeyEvent.VK_SPACE) {
			plantBomb(1);
		}

		this.player[1].move();

	}

	/**
	 * 
	 * @param e
	 *            takes key event (released) and stops changing player position
	 */
	public void player1KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			this.player[0].setDx(0);
		}

		if (key == KeyEvent.VK_RIGHT) {
			this.player[0].setDx(0);
		}

		if (key == KeyEvent.VK_UP) {
			this.player[0].setDy(0);
		}

		if (key == KeyEvent.VK_DOWN) {
			this.player[0].setDy(0);
		}

	}

	public void player2KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			this.player[1].setDx(0);
		}

		if (key == KeyEvent.VK_D) {
			this.player[1].setDx(0);
		}

		if (key == KeyEvent.VK_W) {
			this.player[1].setDy(0);
		}

		if (key == KeyEvent.VK_S) {
			this.player[1].setDy(0);
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
