package de.propra12.gruppe04.dynamiteboy.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ServerHandler {
	PrintWriter writer;
	ServerSocket serverSocket;
	Socket clientSocket;
	private final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
	public int direction;

	public ServerHandler() {
		setUpConnection();
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
		Socket socket;;

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

	}// close HandleClient

	public int getDirection() {
		return direction;
	}

}
