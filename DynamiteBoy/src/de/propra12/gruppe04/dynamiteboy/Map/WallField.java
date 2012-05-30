package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class WallField extends Field {
	private static final String WALLFIELD_DEFAULT_PIC = "../images/db_field_wall.png";
	private static final boolean WALLFIELD_DEFAULT_BLOCKED = true;
	private static final boolean WALLFIELD_DEAFULT_DESTROYABLE = false;
	private static final Item WALLFIELD_DEFAULT_ITEM = null;

	WallField(boolean blocked, boolean destroyable, Item item, String pic) {
		super(blocked, destroyable, item, pic);
	}

	WallField(boolean blocked, boolean destroyable, Item item) {
		super(blocked, destroyable, item, WALLFIELD_DEFAULT_PIC);
	}

	WallField() {
		super(WALLFIELD_DEFAULT_BLOCKED, WALLFIELD_DEAFULT_DESTROYABLE,
				WALLFIELD_DEFAULT_ITEM, WALLFIELD_DEFAULT_PIC);
	}
}
