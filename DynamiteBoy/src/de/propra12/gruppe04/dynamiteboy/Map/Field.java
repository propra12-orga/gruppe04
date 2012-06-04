package de.propra12.gruppe04.dynamiteboy.Map;

import java.awt.Image;

import javax.swing.ImageIcon;

import de.propra12.gruppe04.dynamiteboy.Item.Item;

public abstract class Field {

	Field(boolean blocked, boolean destroyable, boolean explodable, Item item,
			String fieldpic) {
		this.blocked = blocked;
		this.destroyable = destroyable;
		this.explodable = explodable;
		this.item = item;
		this.fieldpic = fieldpic;
		setImage(fieldpic);
	}

	private boolean blocked;
	private boolean destroyable;
	private boolean explodable;
	private Item item;
	private ImageIcon image;
	private boolean deadly = false;
	String fieldpic;

	public void beDeadly(boolean deadly) {
		if (deadly) {
			this.deadly = true;
			this.setImage("../images/db_field_explosion.png");
		} else if (!deadly) {
			this.deadly = false;
			this.setImage(fieldpic);
		}

	}

	public boolean isDeadly() {
		return deadly;

	}

	/**
	 * 
	 * @return true if field is blocked
	 */
	public boolean isBlocked() {
		return blocked;
	}

	/**
	 * Sets blocked state of field
	 * 
	 * 
	 * @param blocked
	 *            set true for the field to be blocked
	 */
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	/**
	 * 
	 * @return true if block is destroyable
	 */
	public boolean isDestroyable() {
		return destroyable;
	}

	/**
	 * 
	 * @param destroyable
	 *            set true for the field to be destroyable
	 */
	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}

	/**
	 * 
	 * @return item type of field
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * 
	 * @param item
	 *            set item type for field
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	public void setImage(String pic) {
		this.image = new ImageIcon(this.getClass().getResource(pic));
	}

	public Image getImage() {
		return this.image.getImage();
	}

	public ImageIcon getImageIcon() {
		return this.image;
	}

	public boolean isExplodable() {
		return explodable;
	}

	public void setExplodable(boolean explodable) {
		this.explodable = explodable;
	}

}
