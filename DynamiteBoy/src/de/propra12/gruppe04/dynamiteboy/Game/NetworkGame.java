package de.propra12.gruppe04.dynamiteboy.Game;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class NetworkGame extends JPanel {
	private final int SERVER = 1;
	private final int CLIENT = 0;
	private String ipAddress;
	private JFrame frame;
	private Map map;
	private InputHandler input;

	public NetworkGame(final JFrame frame, String ip, int type, String mapName) {
		// TODO REMOVE DEBUG
		System.out.println("A network-game will now be set up");
		this.add(new JLabel("UNDER CONSTRUCTION... COME BACK LATER"));
		this.frame = frame;
		frame.setTitle("DynamiteBoy");
		this.ipAddress = ip;
		this.map = new Map(640, 480, mapName);

		// Be client or Server
		if (type == SERVER) {
			ServerHandler server = new ServerHandler(ip);
		} else if (type == CLIENT) {
			ClientHandler client = new ClientHandler();
		}

		this.addKeyListener(input);
		this.input = new InputHandler();
		setFocusable(true);
	}

}
