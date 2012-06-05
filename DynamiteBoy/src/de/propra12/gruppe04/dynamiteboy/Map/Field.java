package de.propra12.gruppe04.dynamiteboy.Map;

import java.awt.Image;

import javax.swing.ImageIcon;

import de.propra12.gruppe04.dynamiteboy.Item.Item;

public abstract class Field {
	private boolean blocked; // true=moving over this field not allowed,
								// false=moving is allowed
	private boolean destroyable; // true=this field can be destroyed by bombs,
									// false=this field can not be destroyed
	private boolean explodable; // true=explosions are possible on this field,
								// false=explosions are impossible
	private boolean deadly = false; // true=this field kills players when on it,
									// false=this field does not kill players
	private Item item; // item that this field holds
	private ImageIcon image; // image of this field
	String fieldpic; // path to the iamge of this field

	/**
	 * creates a new Field
	 * 
	 * @param blocked
	 *            determines if moving over this field allowed
	 * @param destroyable
	 *            determines if this field can be destroyed by bombs
	 * @param explodable
	 *            determines if an explosion is displayed on this field
	 * @param item
	 *            sets the item that this field holds
	 * @param fieldpic
	 *            path to the image of this field
	 */
	Field(boolean blocked, boolean destroyable, boolean explodable, Item item,
			String fieldpic) {
		this.blocked = blocked;
		this.destroyable = destroyable;
		this.explodable = explodable;
		this.item = item;
		this.fieldpic = fieldpic;
		setImage(fieldpic);
	}

	/**
	 * makes this field an "exploding" (therefore deadly) field
	 * 
	 * @param deadly
	 */
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

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isDestroyable() {
		return destroyable;
	}

	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}

	public Item getItem() {
		return item;
	}

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
