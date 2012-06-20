package de.propra12.gruppe04.dynamiteboy.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler {
	PrintWriter writer;
	ServerSocket serverSocket;
	Socket clientSocket;

	public ServerHandler() {
		setUpConnection();
		sendHello();
	}

	private void setUpConnection() {
		try {
			serverSocket = new ServerSocket(4242);
			// wait until a client connects
			while (clientSocket == null) {
				clientSocket = serverSocket.accept();

			}
			writer = new PrintWriter(clientSocket.getOutputStream());
			Thread t = new Thread(new HandleClient(clientSocket));
			t.start();
			System.out.println("Server: connection to client established");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void sendHello() {
		try {
			writer.println("Server says Hello");
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

	public class HandleClient implements Runnable {
		BufferedReader reader;
		Socket socket;
		private int dx;
		private int dy;

		public HandleClient(Socket clientSocket) {
			try {
				socket = clientSocket;
				InputStreamReader inputReader = new InputStreamReader(
						socket.getInputStream());
				reader = new BufferedReader(inputReader);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

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

	}// close HandleClient

}
