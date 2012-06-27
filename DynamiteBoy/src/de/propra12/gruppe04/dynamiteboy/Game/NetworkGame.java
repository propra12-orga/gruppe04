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

import de.propra12.gruppe04.dynamiteboy.Item.Exit;
import de.propra12.gruppe04.dynamiteboy.Map.Field;
import de.propra12.gruppe04.dynamiteboy.Map.Map;
import de.propra12.gruppe04.dynamiteboy.Menu.MainMenu;
import de.propra12.gruppe04.dynamiteboy.Menu.ScoreMenu;

/**
 * Handles everything related to the network-game. Extends JPanel and must be
 * added to the passed frame.
 * 
 * 
 */
public class NetworkGame extends JPanel {
	private String serverIP;
	private JFrame frame;
	private Map map;
	private InputHandler input;
	private boolean running = true;
	double startTime;
	private int numberOfPlayers = 2;

	private float interpolation;
	double currentGameTime;
	private int gameType;
	private int netType;
	private Player[] player = new Player[2];
	private int lastp1x;
	private int lastp2x;
	private int lastp1y;
	private int lastp2y;
	private String winnerName;
	private String loserName;
	NetworkHandler net;

	/**
	 * Creates a Game instance with passed number of players on passed frame.
	 * The map is created from specified map-name
	 * 
	 * @param frame
	 *            Frame the game must be displayed on.
	 * @param ip
	 *            If NetworkGame instance is created with type CLIENT(0) this is
	 *            the ip it will connect to
	 * @param type
	 *            Pass 0 to be client or 1 to be server
	 * @param mapName
	 *            XML file for the map parser e.g. "Map1.xml". Stored in the map
	 *            package.
	 */
	public NetworkGame(final JFrame frame, String ip, int type, String mapName) {
		this.frame = frame;
		frame.setTitle("DynamiteBoy");
		this.serverIP = ip;
		this.map = new Map(640, 480, mapName);
		this.gameType = type;
		createPlayers();

		// Be client or Server
		if (type == C.SERVER) {
			netType = C.CLIENT;
			frame.setTitle("Server is waiting for client");
			net = new NetworkHandler(ip, C.SERVER);
			frame.setTitle("DynamiteBoy - Server");
		} else if (type == C.CLIENT) {
			netType = C.SERVER;
			frame.setTitle("Client is waiting for server");
			net = new NetworkHandler(ip, C.CLIENT);
			frame.setTitle("DynamiteBoy - Client");
		}

		setFocusable(true);
		this.input = new InputHandler();
		this.addKeyListener(input);
		if (running) {
			runGameLoop();
		}
	}

	/**
	 * The actual game-loop. Repaints up to MAX_FPS (specified in C class)
	 */
	private void gameLoop() {
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		while (running) {
			double now = System.nanoTime();
			int updateCount = 0;
			while (now - lastUpdateTime > C.TIME_BETWEEN_UPDATES
					&& updateCount < C.MAX_UPDATES_BEFORE_RENDER) {
				updateGame();
				lastUpdateTime += C.TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			if (now - lastUpdateTime > C.TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - C.TIME_BETWEEN_UPDATES;
			}
			this.interpolation = Math.min(1.0f,
					(float) ((now - lastUpdateTime) / C.TIME_BETWEEN_UPDATES));
			repaint();
			currentGameTime = (System.currentTimeMillis() - startTime) / 1000;
			lastRenderTime = now;

			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime) {
				lastSecondTime = thisSecond;
			}
			while (now - lastRenderTime < C.TARGET_TIME_BETWEEN_RENDERS
					&& now - lastUpdateTime < C.TIME_BETWEEN_UPDATES) {
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
		// set starttime at this point so everyone has the same time
		startTime = System.currentTimeMillis();
	}

	/**
	 * makes changes to the game objects (gets called within each gameLoop-step)
	 * 
	 * 
	 */
	private void updateGame() {
		movePlayer();
		sendPlayerData();
		updateNetworkPlayer();
		itemHandling(C.SERVER);
		itemHandling(C.CLIENT);
		if (net.isRunning == false) {
			System.out.println("Game stopped");
			MainMenu m = new MainMenu(frame);
			frame.add(m);
			this.setVisible(false);
			m.setVisible(true);
			running = false;
		}
	}

	/**
	 * Sends the playerdata through the network that will be extracted from the
	 * playerobject of this NetworkGame instance
	 */
	private void sendPlayerData() {
		net.sendPlayerdata(this.getPlayer(gameType));
	}

	/**
	 * creates players and sets starting positions
	 */
	public void createPlayers() {
		this.player[C.SERVER] = new Player(C.SERVER, 32, 32, map);
		this.player[C.CLIENT] = new Player(C.CLIENT, 581, 416, map);

	}

	/**
	 * Decides what to do on this field
	 * 
	 * First: Checks if Field is ExitField and exits if it is. <br>
	 * Second: Checks if Field is currently deadly
	 * 
	 * @param player
	 *            Player that is affected by the items
	 */
	public void itemHandling(int player) {
		int x = this.player[player].getxPos();
		int y = this.player[player].getyPos();
		Field f = map.getFieldByPixel(x + 16, y + 16);
		if (f.getItem() instanceof Exit) {
			this.winnerName = this.player[player].getPlayerName();
			ScoreMenu m = new ScoreMenu(frame, this);
			this.setVisible(false);
			running = false;
		}
		if (f.isDeadly() == true) {
			if (player == C.CLIENT) {
				this.loserName = "Client";
				this.winnerName = "Server";
			} else {
				this.loserName = "Server";
				this.winnerName = "Client";
			}
			ScoreMenu m = new ScoreMenu(frame, this);
			this.setVisible(false);
			running = false;
		}
	}

	// Key handling / Player movement

	/**
	 * Updates everything referring to the networkplayer such as position and
	 * bombs
	 * 
	 */
	private void updateNetworkPlayer() {
		player[netType].setxPos(net.getPlayerXPos());
		player[netType].setyPos(net.getPlayerYPos());
		player[netType].setBombCount(net.getPlayerBombcount());
		if (net.playerBomb == true) {
			net.setPlayerBomb(false);
			player[netType].plantBomb();
		}
	}

	/**
	 * Moves Player and plants bomb when keys are pressed<br>
	 * Also sends BombData when a bomb is planted <br>
	 * Left: move player to the left <br>
	 * Right: move player to the right<br>
	 * Down: move player down<br>
	 * Up: move player up<br>
	 * Enter: plant bomb<br>
	 * 
	 */
	public void movePlayer() {
		if (input.isKeyDown(KeyEvent.VK_LEFT)) {
			player[gameType].move(C.LEFT);
		}
		if (input.isKeyDown(KeyEvent.VK_RIGHT)) {
			player[gameType].move(C.RIGHT);
		}
		if (input.isKeyDown(KeyEvent.VK_UP)) {
			player[gameType].move(C.UP);
		}
		if (input.isKeyDown(KeyEvent.VK_DOWN)) {
			player[gameType].move(C.DOWN);
		}
		if (input.isKeyDown(KeyEvent.VK_ENTER)) {
			player[gameType].plantBomb();
			net.sendBombData(true);
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
	 * Paints the hud on the bottom of the game window.
	 * 
	 * @param g2d
	 *            Graphics object (painter)
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
		g2d.drawString("Player #1 (Client)", 100, 492);
		g2d.drawString("Bombs left: " + player[C.CLIENT].getBombCount(), 100,
				505);
		g2d.drawString("Player #2 (Server)", 250, 492);
		g2d.drawString("Bombs left: " + player[C.SERVER].getBombCount(), 250,
				505);
	}

	/**
	 * Draws the player
	 * 
	 * @param g2d
	 *            g2d Graphics object (painter)
	 * @param pIndex
	 *            index of player to be drawn
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
		paintPlayer(g2d, C.CLIENT);
		paintPlayer(g2d, C.SERVER);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	/**
	 * 
	 * @return Winnername of current game <br>
	 *         <b>null</b> while the game running
	 */
	public String getWinnerName() {
		return winnerName;
	}

	/**
	 * 
	 * @return Losername of current game <br>
	 *         <b>null</b> while the game running
	 */
	public String getLoserName() {
		return loserName;
	}

	/**
	 * 
	 * @return Map that was created from the XML file specified on creating the
	 *         game
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * 
	 * @return The current gametime in milliseconds
	 */
	public double getCurrentGameTime() {

		return currentGameTime;
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

}
