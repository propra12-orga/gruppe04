package de.propra12.gruppe04.dynamiteboy.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

public class ClientHandler {
	String serverIP;
	BufferedReader reader;
	PrintWriter writer;
	Socket socket;
	private final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
	public int direction;

	public ClientHandler(String ip) {
		this.serverIP = ip;
		setUpConnection();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();

	}

	private void setUpConnection() {
		try {
			while (socket == null) {
				socket = new Socket(serverIP, 4242);
			}
			InputStreamReader streamReader = new InputStreamReader(
					socket.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(socket.getOutputStream());
			// TODO REMOVE DEBUG
			System.out.println("Client: connection to server established");

		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Server existiert nicht!");
		}

	}

	public void movePlayer(int direction) {
		// LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
		try {
			switch (direction) {
			case LEFT:
				writer.println("moveleft");
				writer.flush();

				break;
			case DOWN:
				writer.println("movedown");
				writer.flush();

				break;
			case RIGHT:
				writer.println("moveright");
				writer.flush();

				break;
			case UP:
				writer.println("moveup");
				writer.flush();
				break;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public class IncomingReader implements Runnable {

		@Override
		public void run() {
			String message = "0";
			try {
				while (true) {
					if (message.equals("moveleft")) {
						direction = LEFT;
					}
					if (message.equals("moveright")) {
						direction = RIGHT;
					}
					if (message.equals("movedown")) {
						direction = DOWN;
					}
					if (message.equals("moveup")) {
						direction = UP;
					}
					if (message.equals("0")) {
						direction = -1;
						TimeUnit.MILLISECONDS.sleep(100);
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	} // close IncomingReader

	public int getDirection() {
		return direction;
	}

}
