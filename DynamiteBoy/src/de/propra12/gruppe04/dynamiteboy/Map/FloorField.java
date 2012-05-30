package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Item.Bomb;
import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class FloorField extends Field {
	private static final String FLOORFIELD_DEFAULT_PIC = "../images/db_field_floor.png";
	private static final boolean FLOORFIELD_DEFAULT_BLOCKED = false;
	private static final boolean FLOORFIELD_DEAFULT_DESTROYABLE = false;
	private static final Item FLOORFIELD_DEFAULT_ITEM = null;

	FloorField(boolean blocked, boolean destroyable, Item item, String pic) {
		super(blocked, destroyable, item, pic);
	}

	FloorField(boolean blocked, boolean destroyable, Item item) {
		super(blocked, destroyable, item, FLOORFIELD_DEFAULT_PIC);
	}

	FloorField() {
		super(FLOORFIELD_DEFAULT_BLOCKED, FLOORFIELD_DEAFULT_DESTROYABLE,
				FLOORFIELD_DEFAULT_ITEM, FLOORFIELD_DEFAULT_PIC);
	}

	public void setItem(Item item) {
		super.setItem(item);
		if (item == null) {
			super.setImage(FLOORFIELD_DEFAULT_PIC);
		}
		if (item instanceof Bomb) {
			super.setImage("../images/db_field_floorbomb.png");
		}
	}
}