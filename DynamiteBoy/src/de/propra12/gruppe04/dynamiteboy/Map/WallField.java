package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class WallField extends Field {
	private static final String DEFAULT_PIC = "../images/db_field_wall.png";
	private static final boolean DEFAULT_BLOCKED = true;
	private static final boolean DEAFULT_DESTROYABLE = false;
	private static final Item DEFAULT_ITEM = null;

	WallField(boolean blocked, boolean destroyable, Item item, String pic) {
		super(blocked, destroyable, item, pic);
	}

	WallField(boolean blocked, boolean destroyable, Item item) {
		super(blocked, destroyable, item, DEFAULT_PIC);
	}

	WallField() {
		super(DEFAULT_BLOCKED, DEAFULT_DESTROYABLE,
				DEFAULT_ITEM, DEFAULT_PIC);
	}
}
