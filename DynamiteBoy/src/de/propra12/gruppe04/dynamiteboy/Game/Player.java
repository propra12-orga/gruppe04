package de.propra12.gruppe04.dynamiteboy.Game;

import java.awt.Image;

import javax.swing.ImageIcon;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class Player {
	private int dx;
	private int dy;
	private int xPos;
	private int yPos;
	private Image image;
	private String playerPicture[] = { "../images/player1.png",
			"../images/player2.png" };
	private Map map;
	private final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
	private final int COLLISION_OFFSET = 4;

	// playerPicture[0] = "../images/db_char_placeholder.png";

	Player(int playerPictureIndex, int startxPos, int startyPos, Map map) {
		ImageIcon img = new ImageIcon(this.getClass().getResource(
				playerPicture[playerPictureIndex]));
		image = img.getImage();
		xPos = startxPos;
		yPos = startyPos;
		this.map = map;
	}

	/**
	 * Updates player coordinates
	 * 
	 * @param direction
	 *            to move. LEFT = 0, DOWN= 1, RIGHT =2, UP = 3
	 */
	public void move(int direction) {
		switch (direction) {
		case LEFT:
			if (map.getFieldByPixel(getxPos() + COLLISION_OFFSET,
					getyPos() + COLLISION_OFFSET).isBlocked() == false
					&& map.getFieldByPixel(getxPos() + COLLISION_OFFSET,
							getyPos() + 32 - COLLISION_OFFSET).isBlocked() == false) {
				// MOVE LEFT
				setDx(-4);
				setDy(0);
				xPos += dx;
			}
			break;
		case DOWN:
			if (map.getFieldByPixel(getxPos() + COLLISION_OFFSET,
					getyPos() + 32 - COLLISION_OFFSET).isBlocked() == false
					&& map.getFieldByPixel(getxPos() + 32 - COLLISION_OFFSET,
							getyPos() + 32 - COLLISION_OFFSET).isBlocked() == false) {
				// MOVE DOWN
				setDy(4);
				setDx(0);
				yPos += dy;
			}
			break;
		case RIGHT:
			if (map.getFieldByPixel(getxPos() + 32 - COLLISION_OFFSET,
					getyPos() + COLLISION_OFFSET).isBlocked() == false
					&& map.getFieldByPixel(getxPos() + 32 - COLLISION_OFFSET,
							getyPos() + 32 - COLLISION_OFFSET).isBlocked() == false) {
				// MOVE RIGHT
				setDx(4);
				setDy(0);
				xPos += dx;
			}
			break;
		case UP:
			if (map.getFieldByPixel(getxPos() + COLLISION_OFFSET,
					getyPos() + COLLISION_OFFSET).isBlocked() == false
					&& map.getFieldByPixel(getxPos() + 32 - COLLISION_OFFSET,
							getyPos() + COLLISION_OFFSET).isBlocked() == false) {
				// MOVE UP
				setDx(0);
				setDy(-4);
				yPos += dy;
			}
			break;
		}
	}

	/**
	 * 
	 * @return Player-position in pixel
	 */
	public int getxPos() {
		return xPos;
	}

	/**
	 * 
	 * @return Player-position in pixel
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

	/**
	 * 
	 * @return current x-movement of player
	 */
	public int getDx() {
		return dx;
	}

	/**
	 * 
	 * @param set
	 *            x-movement of player
	 */
	public void setDx(int dx) {
		this.dx = dx;
	}

	/**
	 * 
	 * @return current y-movement of player
	 */
	public int getDy() {
		return dy;
	}

	/**
	 * 
	 * @param set
	 *            y-movement of player
	 */
	public void setDy(int dy) {
		this.dy = dy;
	}

}
