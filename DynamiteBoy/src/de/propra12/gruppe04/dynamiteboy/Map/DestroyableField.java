package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class DestroyableField extends Field {
	private static final String DEFAULT_PIC = "../images/db_field_destroyable.png";
	private static final boolean DEFAULT_BLOCKED = true;
	private static final boolean DEFAULT_DESTROYABLE = true;
	private static final boolean DEFAULT_EXPLODABLE = true;
	private static final Item DEFAULT_ITEM = null;

	DestroyableField(boolean blocked, boolean destroyable, boolean explodable,
			Item item, String pic) {
		super(blocked, destroyable, explodable, item, pic);
	}

	DestroyableField() {
		super(DEFAULT_BLOCKED, DEFAULT_DESTROYABLE, DEFAULT_EXPLODABLE,
				DEFAULT_ITEM, DEFAULT_PIC);
	}

}
