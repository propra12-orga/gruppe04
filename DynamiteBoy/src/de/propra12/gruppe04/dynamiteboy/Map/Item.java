package de.propra12.gruppe04.dynamiteboy.Map;

public abstract class Item {
	private String picture;
	private int type;
	private boolean collectable;

	/*
	 * ITEM TYPES REFERRED BY TYPE: 0 = NO ITEM / 1 = BOMB
	 */

	public Item(int type, String picture, boolean collectable) {
		this.type = type;
		this.picture = picture;
		this.collectable = collectable;
	}

}
