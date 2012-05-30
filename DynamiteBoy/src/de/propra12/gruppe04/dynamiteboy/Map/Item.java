package de.propra12.gruppe04.dynamiteboy.Map;

public abstract class Item {
	private String picture;
	private int type;
	private boolean collectable;
	private int xpos;
	private int ypos;

	/*
	 * ITEMS REFERRED BY TYPE: 0 = NO ITEM / 1 = BOMB
	 */

	public Item(int x, int y, int type, String picture, boolean collectable) {
		this.type = type;
		this.picture = picture;
		this.collectable = collectable;
		setXpos(x);
		setYpos(y);
	}

	/**
	 * @return x-position of the item
	 */
	public int getXpos() {
		return xpos;
	}

	/**
	 * sets x-position of the item
	 * 
	 * @param xpos
	 */
	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	/**
	 * 
	 * @return y-position of the item
	 */
	public int getYpos() {
		return ypos;
	}

	/**
	 * sets y-position of the item
	 * 
	 * @param ypos
	 */
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

}
