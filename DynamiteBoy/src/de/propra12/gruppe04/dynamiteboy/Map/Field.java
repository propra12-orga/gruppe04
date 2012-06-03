package de.propra12.gruppe04.dynamiteboy.Map;

import java.awt.Image;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

import de.propra12.gruppe04.dynamiteboy.Item.Item;

public abstract class Field {

	Field(boolean blocked, boolean destroyable, Item item, String fieldpic) {
		this.blocked = blocked;
		this.destroyable = destroyable;
		this.item = item;
		this.fieldpic = fieldpic;
		setImage(fieldpic);
	}

	private boolean blocked;
	private boolean destroyable;
	private Item item;
	private ImageIcon image;
	private boolean deadly = false;
	String fieldpic;

	public void beDeadly(int x, int y) {
		if (x >= 0 && x < Map.getGridWidth() && y >= 0
				&& y < Map.getGridHeight()) {
			this.deadly = true;
			System.out.println("I am deadly! x=" + x + " y=" + y);
			String oldpic = this.fieldpic;
			this.setImage("../images/db_field_explosion.png");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.deadly = false;
			this.setImage(oldpic);
			System.out.println("I am no more deadly! x=" + x + " y=" + y);
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

}
