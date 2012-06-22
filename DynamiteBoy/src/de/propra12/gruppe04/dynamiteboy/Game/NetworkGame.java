package de.propra12.gruppe04.dynamiteboy.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Map.ExitField;
import de.propra12.gruppe04.dynamiteboy.Map.Field;
import de.propra12.gruppe04.dynamiteboy.Map.Map;
import de.propra12.gruppe04.dynamiteboy.Menu.ScoreMenu;

public class NetworkGame extends JPanel {
	private final int SERVER = 1;
	private final int CLIENT = 0;
	private String serverIP;
	private JFrame frame;
	private Map map;
	private InputHandler input;
	private boolean running = true;
	final double GAME_FREQUENCY = 30.0;
	final double MAX_FPS = 60;
	final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_FREQUENCY;
	final int MAX_UPDATES_BEFORE_RENDER = 5;
	final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / MAX_FPS;
	private float interpolation;
	private final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
	double startTime = System.currentTimeMillis();
	double currentGameTime;
	private int gameType;
	private Player[] player = new Player[2];
	private int lastp1x;
	private int lastp2x;
	private int lastp1y;
	private int lastp2y;
	private String winnerName;
	private String loserName;
	ServerHandler server;
	ClientHandler client;

	public NetworkGame(final JFrame frame, String ip, int type, String mapName) {
		this.frame = frame;
		frame.setTitle("DynamiteBoy");
		this.serverIP = ip;
		this.map = new Map(640, 480, mapName);
		this.gameType = type;
		createPlayers();

		// Be client or Server
		if (type == SERVER) {
			frame.setTitle("Server is waiting for client");
			server = new ServerHandler();
			frame.setTitle("DynamiteBoy - Server");
		} else if (type == CLIENT) {
			frame.setTitle("Client is waiting for server");
			client = new ClientHandler(ip);
			frame.setTitle("DynamiteBoy - Client");
		}

		setFocusable(true);
		this.input = new InputHandler();
		this.addKeyListener(input);
		if (running) {
			runGameLoop();
			System.out.println("Gameloop started");
		}
	}

	private void gameLoop() {
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		while (running) {
			double now = System.nanoTime();
			int updateCount = 0;
			while (now - lastUpdateTime > TIME_BETWEEN_UPDATES
					&& updateCount < MAX_UPDATES_BEFORE_RENDER) {
				updateGame();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}
			this.interpolation = Math.min(1.0f,
					(float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
			repaint();
			currentGameTime = (System.currentTimeMillis() - startTime) / 1000;
			lastRenderTime = now;

			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime) {
				lastSecondTime = thisSecond;
			}
			while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS
					&& now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				Thread.yield();
				try {
					Thread.sleep(1);
				} catch (Exception e) {
				}
				now = System.nanoTime();
			}
		}
	}

	/**
	 * Creates a thread to run the game loop
	 */
	public void runGameLoop() {
		Thread loop = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		loop.start();
	}

	/**
	 * makes changes to the game objects (gets called within each gameLoop-step)
	 */
	private void updateGame() {
		movePlayer();
		moveNetworkPlayer();
		itemHandling(SERVER);
		itemHandling(CLIENT);
	}

	private void moveNetworkPlayer() {
		if (gameType == SERVER) {
			if (server.getDirection() != -1) {
				System.out.println(server.getDirection());
				player[CLIENT].move(server.getDirection());
			}
		} else if (gameType == CLIENT) {
			if (client.getDirection() != -1) {
				System.out.println(client.getDirection());
				player[SERVER].move(client.getDirection());
			}
		}
	}

	/**
	 * creates players and sets starting positions
	 * 
	 * @param numberOfPlayers
	 *            to create
	 */
	public void createPlayers() {
		this.player[SERVER] = new Player(SERVER, 32, 32, map);
		this.player[CLIENT] = new Player(CLIENT, 581, 416, map);

	}

	public void itemHandling(int player) {
		int x = this.player[player].getxPos();
		int y = this.player[player].getyPos();
		Field f = map.getFieldByPixel(x + 16, y + 16);
		if (f instanceof ExitField) {
			this.winnerName = this.player[player].getPlayerName();
			ScoreMenu m = new ScoreMenu(frame, this);
			this.setVisible(false);
			running = false;
		}
	}

	// Key handling
	/**
	 * Moves Player 1 when keys are pressed
	 */
	public void movePlayer() {
		if (input.isKeyDown(KeyEvent.VK_LEFT)) {
			player[gameType].move(LEFT);
			if (gameType == SERVER) {
				server.movePlayer(LEFT);
			} else if (gameType == CLIENT) {
				client.movePlayer(LEFT);
			}

		}
		if (input.isKeyDown(KeyEvent.VK_RIGHT)) {
			player[gameType].move(RIGHT);
			if (gameType == SERVER) {
				server.movePlayer(RIGHT);
			} else if (gameType == CLIENT) {
				client.movePlayer(RIGHT);
			}
		}
		if (input.isKeyDown(KeyEvent.VK_UP)) {
			player[gameType].move(UP);
			if (gameType == SERVER) {
				server.movePlayer(UP);
			} else if (gameType == CLIENT) {
				client.movePlayer(UP);
			}
		}
		if (input.isKeyDown(KeyEvent.VK_DOWN)) {
			player[gameType].move(DOWN);
			if (gameType == SERVER) {
				server.movePlayer(DOWN);
			} else if (gameType == CLIENT) {
				client.movePlayer(DOWN);
			}
		}
		if (input.isKeyDown(KeyEvent.VK_ENTER)) {
			player[gameType].plantBomb();
		}
		if (input.isKeyUp(KeyEvent.VK_LEFT)) {
			player[gameType].setDx(0);
		}
		if (input.isKeyUp(KeyEvent.VK_RIGHT)) {
			player[gameType].setDx(0);
		}
		if (input.isKeyUp(KeyEvent.VK_UP)) {
			player[gameType].setDy(0);
		}
		if (input.isKeyUp(KeyEvent.VK_DOWN)) {
			player[gameType].setDy(0);
		}
		if (input.isKeyUp(KeyEvent.VK_ENTER)) {
			// DO NOTHING
		}
	}

	// PAINT METHODS

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
	 * paints the hub on the bottom of the game window
	 * 
	 */
	public void paintHud(Graphics g2d) {
		ImageIcon hudbg = new ImageIcon(this.getClass().getResource(
				"../images/db_hud_bg.png"));
		g2d.drawImage(hudbg.getImage(), 0, 480, this);
		g2d.setColor(Color.black);
		Font font = new Font("Ubuntu", Font.BOLD, 12);
		g2d.setFont(font);
		g2d.drawString("Time: " + (int) currentGameTime / 60 + ":"
				+ (int) currentGameTime % 60, 5, 492);
		g2d.drawString("Player #1", 100, 492);
		g2d.drawString("Bombs left: " + player[CLIENT].getBombCount(), 100, 505);
		g2d.drawString("Player #2", 200, 492);
		g2d.drawString("Bombs left: " + player[SERVER].getBombCount(), 200, 505);
	}

	/**
	 * draws the player
	 * 
	 * @param g2d
	 *            g2d Graphics object (painter)
	 * @param pIndex
	 *            index of player to be drawn
	 * @param interpolation
	 *            number to generate smooth drawing (is calculated in GameLoop)
	 */
	public void paintPlayer(Graphics g2d, int pIndex) {
		int x = player[pIndex].getxPos();
		int y = player[pIndex].getyPos();
		if (pIndex == 0) {
			x = (int) ((player[pIndex].getxPos() - lastp1x)
					* this.interpolation + lastp1x);
			y = (int) ((player[pIndex].getyPos() - lastp1y)
					* this.interpolation + lastp1y);
			lastp1x = player[pIndex].getxPos();
			lastp1y = player[pIndex].getyPos();
		} else if (pIndex == 1) {
			x = (int) ((player[pIndex].getxPos() - lastp2x)
					* this.interpolation + lastp2x);
			y = (int) ((player[pIndex].getyPos() - lastp2y)
					* this.interpolation + lastp2y);
			lastp2x = player[pIndex].getxPos();
			lastp2y = player[pIndex].getyPos();
		}
		g2d.drawImage(player[pIndex].getImage(), x, y, this);
	}

	// Everything to paint goes in here
	public void paint(Graphics g) {
		super.paint(g);
		Graphics g2d = (Graphics2D) g;
		paintField(g2d);
		paintHud(g2d);
		paintPlayer(g2d, CLIENT);
		paintPlayer(g2d, SERVER);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public String getWinnerName() {
		return winnerName;
	}

	public String getLoserName() {
		return loserName;
	}

	public Map getMap() {
		return map;
	}

	public double getCurrentGameTime() {

		return currentGameTime;
	}

	public Player getPlayer(int playerIndex) {
		return player[playerIndex];
	}

}
