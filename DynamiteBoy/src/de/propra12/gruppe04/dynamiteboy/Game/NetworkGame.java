package de.propra12.gruppe04.dynamiteboy.Game;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

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

	public NetworkGame(final JFrame frame, String ip, int type, String mapName) {
		// TODO REMOVE DEBUG
		System.out.println("A network-game will now be set up");
		this.frame = frame;
		frame.setTitle("DynamiteBoy");
		this.serverIP = ip;
		this.map = new Map(640, 480, mapName);

		// Be client or Server
		if (type == SERVER) {
			frame.setTitle("Server is waiting for client");
			ServerHandler server = new ServerHandler();
			frame.setTitle("Connection to client established");
		} else if (type == CLIENT) {
			frame.setTitle("Client is waiting for server");
			ClientHandler client = new ClientHandler(ip);
			frame.setTitle("Connection to server established");
		}
		this.add(new JLabel("UNDER CONSTRUCTION... COME BACK LATER"));
		frame.setTitle("DynamiteBoy");
		this.addKeyListener(input);
		this.input = new InputHandler();
		setFocusable(true);
		this.setVisible(true);
		if (running) {
			runGameLoop();
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
		// move player

		// item handling
	}

}
