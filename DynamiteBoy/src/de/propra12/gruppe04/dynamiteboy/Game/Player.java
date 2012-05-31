package de.propra12.gruppe04.dynamiteboy.Game;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player {
	private int dx;
	private int dy;
	private int xPos;
	private int yPos;
	private Image image;
	private String playerPicture[] = { "../images/player1.png",
			"../images/player2.png" };

	// playerPicture[0] = "../images/db_char_placeholder.png";

	Player(int playerPictureIndex, int startxPos, int startyPos) {
		ImageIcon img = new ImageIcon(this.getClass().getResource(
				playerPicture[playerPictureIndex]));
		image = img.getImage();
		xPos = startxPos;
		yPos = startyPos;
	}

	/**
	 * Updates player coordinates
	 */
	public void move() {
		xPos += dx;
		yPos += dy;
	}

	/**
	 * 
	 * @return current x-coordinate of player
	 */
	public int getxPos() {
		return xPos;
	}

	/**
	 * 
	 * @return current y-coordinate of player
	 */
	public int getyPos() {
		return yPos;
	}

	/**
	 * 
	 * @return player-image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return x-position in current map
	 */
	public int getGridX(int x) {
		x = x / 32;
		return x;
	}

	/**
	 * @return x-position in current map
	 */
	public int getGridY(int y) {
		y = y / 32;
		return y;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

}
