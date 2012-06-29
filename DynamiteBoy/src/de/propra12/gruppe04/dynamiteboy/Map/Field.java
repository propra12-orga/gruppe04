package de.propra12.gruppe04.dynamiteboy.Map;

import java.awt.Image;

import javax.swing.ImageIcon;

import de.propra12.gruppe04.dynamiteboy.Game.C;
import de.propra12.gruppe04.dynamiteboy.Item.Exit;
import de.propra12.gruppe04.dynamiteboy.Item.FunnyPill;
import de.propra12.gruppe04.dynamiteboy.Item.Item;
import de.propra12.gruppe04.dynamiteboy.Item.P1Starter;
import de.propra12.gruppe04.dynamiteboy.Item.P2Starter;
import de.propra12.gruppe04.dynamiteboy.Item.Teleporter;

/**
 * 
 * Abstract superclass for field objects
 * 
 */
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
	protected ImageIcon image; // image of this field
	private boolean selected = false; // is this field currently selected in the
										// editor?
	String fieldpic; // path to the image of this field

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
	 *            true if field should be deadly
	 */
	public void beDeadly(boolean deadly) {
		if (deadly) {
			this.deadly = true;
			this.setImage("../images/db_field_explosion.png");
		} else if (!deadly) {
			this.deadly = false;
			if (C.funny == true) {
				if (this.getItem() == null) {
					this.setImage(C.FUNNYPILL_EXPLODED);
				} else if (this.getItem() instanceof Teleporter) {
					this.setImage(C.FUNNYPILL_TELEPORTER);
				} else if (this.getItem() instanceof Exit) {
					this.setImage(C.FUNNYPILL_EXIT);
				}
				// if not funnypill
				// TODO clear up where item changing happens
			} else {
				if (this.getItem() instanceof Exit) {
					this.setImage(C.EXITFIELD_DEFAULT_PIC);
				} else if (this.getItem() instanceof FunnyPill) {
					this.setImage(C.FUNNPILL_DEFAULT_ITEM);
				} else if (this.getItem() instanceof Teleporter) {
					this.setImage(C.TELEPORTFIELD_DEFAULT_PIC);
				} else {
					this.setImage(fieldpic);
				}
			}
		}
	}

	/**
	 * returns a string representing the type of this field, ready for saving
	 * into xml-file
	 * 
	 * @return
	 */
	public String getFieldType() {
		// DEFAULT FIELD TYPE: FLOORFIELD
		String type = "";
		if (this instanceof FloorField) {
			type = "";
			return type;
		}
		if (this instanceof WallField) {
			type = "wall";
			return type;
		}
		if (this instanceof DestroyableField) {
			type = "destroyable";
			return type;
		}
		return type;
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

	public boolean hasItem() {
		if (this.item != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * returns type of this fields item
	 * 
	 * @return
	 */
	public String getItemType() {
		String itemName = "";
		if (hasItem()) {
			if (this.item instanceof Exit) {
				itemName = "exit";
			}
			if (this.item instanceof FunnyPill) {
				itemName = "funnypill";
			}
			if (this.item instanceof Teleporter) {
				itemName = "teleporter";
			}
			if (this.item instanceof P1Starter) {
				itemName = "p1starter";
			}
			if (this.item instanceof P2Starter) {
				itemName = "p2starter";
			}
		}
		return itemName;
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
