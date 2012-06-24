package de.propra12.gruppe04.dynamiteboy.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * Handles networking by setting up a client or server. Data is passed through a
 * PlayerData Object.
 * 
 * @author peter
 * 
 */
public class NetworkHandler {
	protected ObjectOutputStream out;
	protected ServerSocket serverSocket;
	protected Socket clientSocket;
	protected ObjectInputStream in;
	private String ip;
	protected Socket socket;
	protected BufferedReader reader;
	protected boolean isRunning = true;

	// Variables for Playerhandling
	protected int playerXPos;
	protected int playerYPos;
	protected boolean playerBomb;
	protected int playerBombcount;

	/**
	 * Creates a client/server for a network game
	 * 
	 * @param ip
	 *            only relevant if a client is created
	 * @param type
	 *            SERVER = 1, CLIENT = 0
	 */
	public NetworkHandler(String ip, int type) {
		this.ip = ip;
		switch (type) {
		case C.SERVER:
			setUpServer();
			break;
		case C.CLIENT:
			setUpClient(ip);
			break;
		}
	}

	/**
	 * Set up a client and connect to passed ip
	 * 
	 * @param ip
	 */
	private void setUpClient(String ip) {
		try {
			while (socket == null) {
				socket = new Socket(ip, 4242);
			}
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

			Thread readerThread = new Thread(new IncomingReader());
			readerThread.start();
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Server nicht gefunden!");
		}

	} // close setUpClient

	/**
	 * Set up a Server
	 */
	private void setUpServer() {
		try {
			serverSocket = new ServerSocket(4242);
			// wait until a client connects
			while (clientSocket == null) {
				clientSocket = serverSocket.accept();
			}
			out = new ObjectOutputStream(clientSocket.getOutputStream());

			// handle client stuff
			socket = clientSocket;
			in = new ObjectInputStream(socket.getInputStream());

			Thread readerThread = new Thread(new IncomingReader());
			readerThread.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	} // close setUpServer

	// GETTERS AND SETTERS FOR NETWORKHANDLER

	public int getPlayerXPos() {
		return playerXPos;
	}

	public void setPlayerXPos(int playerXPos) {
		this.playerXPos = playerXPos;
	}

	public int getPlayerYPos() {
		return playerYPos;
	}

	public void setPlayerYPos(int playerYPos) {
		this.playerYPos = playerYPos;
	}

	public boolean isPlayerBomb() {
		return playerBomb;
	}

	public void setPlayerBomb(boolean playerBomb) {
		this.playerBomb = playerBomb;
	}

	public int getPlayerBombcount() {

		return playerBombcount;
	}

	public void setPlayerBombcount(int count) {

		this.playerBombcount = count;
	}

	// end of getters and setters

	/**
	 * 
	 * Handles incoming messages
	 * 
	 * 
	 */
	public class IncomingReader implements Runnable {

		@Override
		public void run() {
			Object obj = null;

			while (isRunning) {
				try {
					obj = in.readObject();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if (obj instanceof PlayerData) {
					PlayerData data = (PlayerData) obj;
					setPlayerXPos(data.getxPos());
					setPlayerYPos(data.getyPos());
					setPlayerBombcount(data.getBombcount());
				}
				if (obj instanceof BombData) {
					BombData data = (BombData) obj;
					setPlayerBomb(data.isBomb());
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	} // close IncomingReader

	// OBJECTS THAT CAN BE SENT AND METHODS TO SEND THEM

	/**
	 * Creates a serialized object with playerdata to send through the networks
	 * 
	 */
	public static class PlayerData implements Serializable {
		private int xPos;
		private int yPos;
		private int bombcount;

		public PlayerData(int xPos, int yPos, int bombcount) {
			this.xPos = xPos;
			this.yPos = yPos;
			this.bombcount = bombcount;
		}

		public int getxPos() {
			return xPos;
		}

		public int getyPos() {
			return yPos;
		}

		public int getBombcount() {
			return bombcount;
		}

		public void setBombcount(int bombcount) {
			this.bombcount = bombcount;
		}

	} // close PlayerData

	public void sendPlayerdata(Player player) {
		PlayerData pd = new PlayerData(player.getxPos(), player.getyPos(),
				player.getBombCount());
		try {
			out.writeObject(pd);
			out.flush();
		} catch (IOException e) {
			System.out.println("Failed to send playerdata");
			e.printStackTrace();
		}

	}

	public static class BombData implements Serializable {
		private boolean bomb;

		public BombData(boolean bomb) {
			this.bomb = bomb;
		}

		public boolean isBomb() {
			return bomb;
		}

	}

	public void sendBombData(boolean bomb) {
		BombData bd = new BombData(bomb);
		try {
			out.writeObject(bd);
			out.flush();
		} catch (IOException e) {
			System.out.println("Failed to send bombdata");
			e.printStackTrace();
		}
	}

}
