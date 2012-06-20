package de.propra12.gruppe04.dynamiteboy.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ClientHandler {
	String serverIP;
	BufferedReader reader;
	PrintWriter writer;
	Socket socket;

	public ClientHandler(String ip) {
		this.serverIP = ip;
		setUpConnection();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
		sendHello();

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

	private void sendHello() {
		try {
			writer.println("Client says Hello");
			writer.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void movePlayer(int direction) {
		// LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
		try {
			switch (direction) {
			case 0:
				writer.println("moveleft");
				writer.flush();

				break;
			case 1:
				writer.println("movedown");
				writer.flush();

				break;
			case 2:
				writer.println("moveright");
				writer.flush();

				break;
			case 3:
				writer.println("moveup");
				writer.flush();
				break;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public class IncomingReader implements Runnable {
		private int dx;
		private int dy;

		@Override
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					// TODO REMOVE DEBUG
					System.out.println("Client got message: " + message);
					if (message.equals("moveleft")) {
						dx = -4;
						dy = 0;
					}
					if (message.equals("moveright")) {
						dx = 4;
						dy = 0;
					}
					if (message.equals("movedown")) {
						dy = 4;
						dx = 0;
					}
					if (message.equals("moveup")) {
						dy = -4;
						dx = 0;
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	} // close IncomingReader
}
