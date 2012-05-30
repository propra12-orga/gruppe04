package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class DestroyableField extends Field {
	private static final String DESTROYABLEFIELD_DEFAULT_PIC = "../images/db_field_destroyable.png";
	private static final boolean DESTROYABLEFIELD_DEFAULT_BLOCKED = true;
	private static final boolean DESTROYABLEFIELD_DEFAULT_DESTROYABLE = true;
	private static final Item DESTROYABLEFIELD_DEFAULT_ITEM = null;

	DestroyableField(boolean blocked, boolean destroyable, Item item, String pic) {
		super(blocked, destroyable, item, pic);
	}

	DestroyableField() {
		super(DESTROYABLEFIELD_DEFAULT_BLOCKED,
				DESTROYABLEFIELD_DEFAULT_DESTROYABLE,
				DESTROYABLEFIELD_DEFAULT_ITEM, DESTROYABLEFIELD_DEFAULT_PIC);
	}

}
