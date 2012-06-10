package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class WallField extends Field {
	// CONSTANTS
	private static final String DEFAULT_PIC = "../images/db_field_wall.png";
	private static final boolean DEFAULT_BLOCKED = true;
	private static final boolean DEAFULT_DESTROYABLE = false;
	private static final boolean DEFAULT_EXPLODABLE = false;
	private static final Item DEFAULT_ITEM = null;
	private static final boolean DEFAULT_DEADLY = false;

	WallField(boolean blocked, boolean destroyable, boolean explodable,
			Item item, String pic) {
		super(blocked, destroyable, explodable, item, pic);
	}

	WallField(boolean blocked, boolean destroyable, boolean explodable,
			Item item) {
		super(blocked, destroyable, explodable, item, DEFAULT_PIC);
	}

	WallField() {
		super(DEFAULT_BLOCKED, DEAFULT_DESTROYABLE, DEFAULT_EXPLODABLE,
				DEFAULT_ITEM, DEFAULT_PIC);
	}
}
