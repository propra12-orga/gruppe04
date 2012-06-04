package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Item.Bomb;
import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class FloorField extends Field {
	private static final String DEFAULT_PIC = "../images/db_field_floor.png";
	private static final boolean DEFAULT_BLOCKED = false;
	private static final boolean DEAFULT_DESTROYABLE = false;
	private static final boolean DEFAULT_EXPLODABLE = true;
	private static final Item DEFAULT_ITEM = null;

	FloorField(boolean blocked, boolean destroyable, boolean explodable,
			Item item, String pic) {
		super(blocked, destroyable, explodable, item, pic);
	}

	FloorField(boolean blocked, boolean destroyable, boolean explodable,
			Item item) {
		super(blocked, destroyable, explodable, item, DEFAULT_PIC);
	}

	FloorField() {
		super(DEFAULT_BLOCKED, DEAFULT_DESTROYABLE, DEFAULT_EXPLODABLE,
				DEFAULT_ITEM, DEFAULT_PIC);
	}

	public void setItem(Item item) {
		super.setItem(item);
		if (item == null) {
			super.setImage(DEFAULT_PIC);
		}
		if (item instanceof Bomb) {
			super.setImage("../images/db_field_floorbomb.png");
		}
	}
}