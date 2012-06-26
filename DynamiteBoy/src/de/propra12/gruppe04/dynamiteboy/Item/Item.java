package de.propra12.gruppe04.dynamiteboy.Item;

/**
 * Abstract Superclass for items
 * 
 */
public abstract class Item {
	private boolean collectable;
	String picture;

	public Item(boolean collectable) {
		this.collectable = collectable;
	}

}
