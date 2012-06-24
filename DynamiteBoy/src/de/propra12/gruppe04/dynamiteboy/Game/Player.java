package de.propra12.gruppe04.dynamiteboy.Game;

import java.awt.Image;

import javax.swing.ImageIcon;

import de.propra12.gruppe04.dynamiteboy.Item.Bomb;
import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class Player {
	private int dx;
	private int dy;
	private int xPos;
	private int yPos;
	private Image image;
	private ImageIcon img;
	private String name = "";
	private int playerIndex;
	private String playerPicture[] = { "../images/player1.png",
			"../images/player2.png" };
	private String playerName[] = { "Player 1", "Player 2" };
	private Map map;
	private int bombcount;
	// CONSTANTS
	private final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
	private final int COLLISION_X_OFFSET = 5;
	private final int COLLISION_Y_OFFSET = 3;
	public final int BOMBS = 10;

	Player(int playerIndex, int startxPos, int startyPos, Map map) {
		this.img = new ImageIcon(this.getClass().getResource(
				playerPicture[playerIndex]));
		this.image = img.getImage();
		this.xPos = startxPos;
		this.yPos = startyPos;
		this.map = map;
		this.playerIndex = playerIndex;
		this.name = playerName[playerIndex];
		this.setBombCount(BOMBS);
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
			if (map.getFieldByPixel(getxPos() + COLLISION_X_OFFSET,
					getyPos() + COLLISION_Y_OFFSET).isBlocked() == false
					&& map.getFieldByPixel(getxPos() + COLLISION_X_OFFSET,
							getyPos() + 32 - COLLISION_Y_OFFSET).isBlocked() == false) {
				// MOVE LEFT
				setDx(-4);
				setDy(0);
				xPos += dx;
			}
			break;
		case DOWN:
			if (map.getFieldByPixel(getxPos() + COLLISION_X_OFFSET,
					getyPos() + 32 - COLLISION_Y_OFFSET).isBlocked() == false
					&& map.getFieldByPixel(getxPos() + 32 - COLLISION_X_OFFSET,
							getyPos() + 32 - COLLISION_Y_OFFSET).isBlocked() == false) {
				// MOVE DOWN
				setDy(4);
				setDx(0);
				yPos += dy;
			}
			break;
		case RIGHT:
			if (map.getFieldByPixel(getxPos() + 32 - COLLISION_X_OFFSET,
					getyPos() + COLLISION_Y_OFFSET).isBlocked() == false
					&& map.getFieldByPixel(getxPos() + 32 - COLLISION_X_OFFSET,
							getyPos() + 32 - COLLISION_Y_OFFSET).isBlocked() == false) {
				// MOVE RIGHT
				setDx(4);
				setDy(0);
				xPos += dx;
			}
			break;
		case UP:
			if (map.getFieldByPixel(getxPos() + COLLISION_X_OFFSET,
					getyPos() + COLLISION_Y_OFFSET).isBlocked() == false
					&& map.getFieldByPixel(getxPos() + 32 - COLLISION_X_OFFSET,
							getyPos() + COLLISION_Y_OFFSET).isBlocked() == false) {
				// MOVE UP
				setDx(0);
				setDy(-4);
				yPos += dy;
			}
			break;
		}
	}

	/**
	 * Plants a bomb on current grid-position
	 * 
	 * @param pIndex
	 *            player to plant the bomb
	 */

	public void plantBomb() {
		if (map.getField(getGridfieldXByMiddle(), getGridfieldYByMiddle())
				.getItem() instanceof Bomb) {
			// DO NOTHING
		} else if (map.getField(getGridfieldXByMiddle(),
				getGridfieldYByMiddle()).getItem() == null
				&& getBombCount() > 0) {
			Bomb bomb = new Bomb(getGridfieldXByMiddle(),
					getGridfieldYByMiddle(), false, map);
			Thread bombThread = new Thread(bomb);
			bombThread.start();
			setBombCount(getBombCount() - 1);
		}
	}

	/**
	 * 
	 * @return Player-position in pixel
	 */
	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * 
	 * @return Player-position in pixel
	 */
	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	/**
	 * 
	 * @return player-image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return y-position of current Gridfield
	 */
	public int getGridfieldX(int x) {
		x = x / 32;
		return x;
	}

	/**
	 * @return y-position of current Gridfield
	 */
	public int getGridfieldY(int y) {
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

	/**
	 * 
	 * * @return x position of current Gridfield based on player middle
	 */
	public int getGridfieldXByMiddle() {
		return getGridfieldX(getxPos() + 16);

	}

	/**
	 * 
	 * * @return y position of current Gridfield based on player middle
	 */
	public int getGridfieldYByMiddle() {
		return getGridfieldY(getyPos() + 16);

	}

	public String getPlayerName() {
		return name;
	}

	public int getBombCount() {
		return bombcount;
	}

	public void setBombCount(int bombs) {
		this.bombcount = bombs;
	}

}
