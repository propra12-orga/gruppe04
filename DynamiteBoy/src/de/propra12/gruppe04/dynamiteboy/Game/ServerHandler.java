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

	public class HandleClient implements Runnable {
		BufferedReader reader;
		Socket socket;

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
					System.out.println("Server got message: " + message);

				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}// close HandleClient

}
