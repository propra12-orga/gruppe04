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
import de.propra12.gruppe04.dynamiteboy.Item.FunnyPill;
import de.propra12.gruppe04.dynamiteboy.Item.Teleporter;
import de.propra12.gruppe04.dynamiteboy.Map.Field;
import de.propra12.gruppe04.dynamiteboy.Map.FloorField;
import de.propra12.gruppe04.dynamiteboy.Map.Map;
import de.propra12.gruppe04.dynamiteboy.Menu.ScoreMenu;

/**
 * Handles everything related to the game. Extends JPanel and must be added to
 * the passed frame.
 * 
 * 
 */
public class Game extends JPanel {
	private int playerStartPos[][] = new int[2][2];
	private Player player[] = new Player[2];
	private Map map;
	private JFrame frame;
	private String winnerName;
	private String loserName;
	private int numberOfPlayers;
	private InputHandler input;
	private boolean running = true;
	private float interpolation;
	// TODO find a nicer way to handle lastplayer positions (maybe store them in
	// the player object)
	private int lastp1x;
	private int lastp2x;
	private int lastp1y;
	private int lastp2y;
	double startTime = System.currentTimeMillis();
	double currentGameTime;
	ImageIcon hudbg;

	private boolean tutorial = false;
	private int tutorialStep = 0;

	/**
	 * Creates a Game instance with passed number of players. The map is created
	 * from specified mapname
	 * 
	 * @param frame
	 *            Frame the game must be displayed on.
	 * @param numberOfPlayers
	 *            Number of players to be created. Note that currently only two
	 *            players are supported
	 * @param mapName
	 *            XML file for the map parser e.g. "Map1.xml". Stored in the map
	 *            package.
	 */
	public Game(JFrame frame, int numberOfPlayers, String mapName) {
		// SET UP
		frame.setTitle("DynamiteBoy");
		this.numberOfPlayers = numberOfPlayers;
		this.map = new Map(640, 480, mapName);
		this.frame = frame;
		createPlayers(numberOfPlayers);
		setFocusable(true);
		if (mapName.equals("Tutorial.xml")) {
			this.tutorial = true;
		}
		this.input = new InputHandler();
		this.addKeyListener(input);
		if (running) {
			runGameLoop();
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
	 * The actual gameloop. Repaints up to MAX_FPS (specified in C class)
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
	 * makes changes to the game objects (gets called within each gameLoop-step)
	 */
	private void updateGame() {
		if (tutorial) {
			updateTutorial();
		}
		if (numberOfPlayers == 1) {
			movePlayer1();
			itemHandling(player[C.PLAYER1]);
		} else if (numberOfPlayers == 2) {
			movePlayer1();
			itemHandling(player[C.PLAYER1]);
			movePlayer2();
			itemHandling(player[C.PLAYER2]);
		}
	}

	/**
	 * Tutorialsteps go in here! Note that painting operations go into
	 * paintTut()
	 */
	private void updateTutorial() {
		switch (tutorialStep) {
		case 0:
			map.getField(12, 12)
					.setImage("../images/dbedit_field_selected.png");
			if (player[C.PLAYER1].getGridfieldXByMiddle() == 12
					&& player[C.PLAYER1].getGridfieldYByMiddle() == 12) {
				((FloorField) map.getField(12, 12)).setRandImage();
				map.setDestroyableField(13, 13);
				Exit exit = new Exit(false);
				map.getField(13, 13).setItem(exit);
				tutorialStep = 1;
			}
			break;
		case 1:
			if (map.getField(13, 13) instanceof FloorField) {
				tutorialStep = 2;
			}
			break;
		}
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
	 * Decides what to do on this field
	 * 
	 * First: Checks if Field is ExitField and exits if it is. Second: Checks if
	 * Field is currently deadly
	 * 
	 * @param player
	 *            Player that is affected by the items
	 */
	public void itemHandling(Player player) {
		int x = player.getxPos();
		int y = player.getyPos();
		Field f = map.getFieldByPixel(x + 16, y + 16);
		// f is Exitfield
		if (f.getItem() instanceof Exit) {
			this.winnerName = player.getPlayerName();
			ScoreMenu m = new ScoreMenu(frame, this);
			this.setVisible(false);
			running = false;
		}
		// f is deadly
		if (f.isDeadly() == true) {
			if (player == this.player[C.PLAYER1]) {
				if (numberOfPlayers == 1) {
					this.loserName = player.getPlayerName();
				} else {
					this.loserName = player.getPlayerName();
					this.winnerName = this.player[C.PLAYER2].getPlayerName();
				}
			} else {
				this.loserName = player.getPlayerName();
				this.winnerName = this.player[C.PLAYER1].getPlayerName();
			}
			ScoreMenu m = new ScoreMenu(frame, this);
			this.setVisible(false);
			running = false;
		}
		// f has Teleporter
		if (f.getItem() instanceof Teleporter) {
			((Teleporter) f.getItem()).teleport(player, this.getMap());
		}
		// f has lsd
		if (f.getItem() instanceof FunnyPill) {
			f.setImage(C.FLOORFIELD_DEFAULT_PIC);
			((FunnyPill) f.getItem()).beFunny(this.getMap(), player);
			// f.setItem(null);
		}

	}

	// KEY HANDLING AND PAINT METHODS

	/**
	 * Paints the hud on the bottom of the game window.
	 * 
	 * @param g2d
	 *            Graphics object (painter)
	 */
	public void paintHud(Graphics g2d) {
		if (C.funny == true) {
			hudbg = new ImageIcon(this.getClass().getResource(C.FUNNYPILL_HUD));
			g2d.setColor(Color.yellow);
		} else {
			hudbg = new ImageIcon(this.getClass().getResource(
					"../images/db_hud_bg.png"));
			g2d.setColor(Color.black);
		}
		g2d.drawImage(hudbg.getImage(), 0, 480, this);

		Font font = new Font("Ubuntu", Font.BOLD, 12);
		g2d.setFont(font);
		g2d.drawString("Time: " + (int) currentGameTime / 60 + ":"
				+ (int) currentGameTime % 60, 5, 492);
		g2d.drawString("Player #1", 100, 492);
		g2d.drawString("Bombs left: " + player[C.PLAYER1].getBombCount(), 100,
				505);
		if (numberOfPlayers > 1) {
			g2d.drawString("Player #2", 200, 492);
			g2d.drawString("Bombs left: " + player[C.PLAYER2].getBombCount(),
					200, 505);
		}
		if (C.funny == true && numberOfPlayers == 1) {
			g2d.setColor(Color.orange);
			Font font2 = new Font("Ubuntu", Font.BOLD, 30);
			g2d.setFont(font2);
			g2d.drawString("FUNNYPILLLL", 215, 505);
		}

	}

	private void paintTut(Graphics g2d, int tutstep) {
		switch (tutstep) {
		case 0:
			g2d.drawImage(
					new ImageIcon(this.getClass().getResource(
							"../images/db_tutorial_0.png")).getImage(), 192, 0,
					this);
			break;
		case 1:
			g2d.drawImage(
					new ImageIcon(this.getClass().getResource(
							"../images/db_tutorial_1.png")).getImage(), 192, 0,
					this);
			break;
		case 2:
			g2d.drawImage(
					new ImageIcon(this.getClass().getResource(
							"../images/db_tutorial_2.png")).getImage(), 192, 0,
					this);
			break;
		case 3:
			g2d.drawImage(
					new ImageIcon(this.getClass().getResource(
							"../images/db_tutorial_3.png")).getImage(), 192, 0,
					this);
			break;
		}

	}

	/**
	 * Draws the map
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

	/**
	 * Paint methods
	 * 
	 * @param g
	 *            Current graphics object
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics g2d = (Graphics2D) g;
		paintField(g2d);
		if (tutorial) {
			paintTut(g2d, tutorialStep);
		}
		for (int i = 0; i < numberOfPlayers; i++) {
			paintPlayer(g2d, i);
		}
		paintHud(g2d);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	/**
	 * Moves Player 1 and plants bomb when keys are pressed<br>
	 * Left: move player to the left <br>
	 * Right: move player to the right<br>
	 * Down: move player down<br>
	 * Up: move player up<br>
	 * Enter: plant bomb<br>
	 * 
	 */
	public void movePlayer1() {
		// Player 1 Movement
		if (input.isKeyDown(KeyEvent.VK_LEFT)) {
			player[C.PLAYER1].move(C.LEFT);
		}
		if (input.isKeyDown(KeyEvent.VK_RIGHT)) {
			player[C.PLAYER1].move(C.RIGHT);
		}
		if (input.isKeyDown(KeyEvent.VK_UP)) {
			player[C.PLAYER1].move(C.UP);
		}
		if (input.isKeyDown(KeyEvent.VK_DOWN)) {
			player[C.PLAYER1].move(C.DOWN);
		}
		if (input.isKeyDown(KeyEvent.VK_ENTER)) {
			player[C.PLAYER1].plantBomb();
		}
		if (input.isKeyUp(KeyEvent.VK_LEFT)) {
			player[C.PLAYER1].setDx(0);
		}
		if (input.isKeyUp(KeyEvent.VK_RIGHT)) {
			player[C.PLAYER1].setDx(0);
		}
		if (input.isKeyUp(KeyEvent.VK_UP)) {
			player[C.PLAYER1].setDy(0);
		}
		if (input.isKeyUp(KeyEvent.VK_DOWN)) {
			player[C.PLAYER1].setDy(0);
		}
		if (input.isKeyUp(KeyEvent.VK_ENTER)) {
			// DO NOTHING
		}
	}

	/**
	 * Moves Player 2 and plants bomb when keys are pressed<br>
	 * A: move player to the left<br>
	 * D: move player to the right<br>
	 * S: move player down<br>
	 * W: move player up<br>
	 * Space: plant bomb<br>
	 * 
	 */
	public void movePlayer2() {
		if (input.isKeyDown(KeyEvent.VK_A)) {
			player[C.PLAYER2].move(C.LEFT);
		}
		if (input.isKeyDown(KeyEvent.VK_D)) {
			player[C.PLAYER2].move(C.RIGHT);
		}
		if (input.isKeyDown(KeyEvent.VK_W)) {
			player[C.PLAYER2].move(C.UP);
		}
		if (input.isKeyDown(KeyEvent.VK_S)) {
			player[C.PLAYER2].move(C.DOWN);
		}
		if (input.isKeyDown(KeyEvent.VK_SPACE)) {
			player[C.PLAYER2].plantBomb();
		}
		if (input.isKeyUp(KeyEvent.VK_A)) {
			player[C.PLAYER2].setDx(0);
		}
		if (input.isKeyUp(KeyEvent.VK_D)) {
			player[C.PLAYER2].setDx(0);
		}
		if (input.isKeyUp(KeyEvent.VK_W)) {
			player[C.PLAYER2].setDy(0);
		}
		if (input.isKeyUp(KeyEvent.VK_S)) {
			player[C.PLAYER2].setDy(0);
		}
		if (input.isKeyUp(KeyEvent.VK_SPACE)) {
			// DO NOTHING
		}
	}

	// GETTERS AND SETTERS

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
	 * @param playerIndex
	 *            index of player to return
	 * @return player object
	 */
	public Player getPlayer(int playerIndex) {
		return player[playerIndex];
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
	 * @return Returns the current number of players
	 */
	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}

}
