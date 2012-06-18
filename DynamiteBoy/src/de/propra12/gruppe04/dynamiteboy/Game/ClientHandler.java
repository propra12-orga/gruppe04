package de.propra12.gruppe04.dynamiteboy.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

	public class IncomingReader implements Runnable {
		@Override
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Client got message: " + message);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	} // close IncomingReader
}
