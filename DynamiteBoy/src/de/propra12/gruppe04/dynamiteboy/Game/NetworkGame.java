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
	}

}
