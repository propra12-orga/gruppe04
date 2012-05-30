package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Item.Exit;
import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class ExitField extends Field {
	private static final String EXITFIELD_DEFAULT_PIC = "../images/db_field_exit.png";
	private static final boolean EXITFIELD_DEFAULT_BLOCKED = false;
	private static final boolean EXITFIELD_DEAFULT_DESTROYABLE = false;
	private static final Item EXITFIELD_DEFAULT_ITEM = new Exit(false);

	ExitField(boolean blocked, boolean destroyable, Item item, String pic) {
		super(blocked, destroyable, item, pic);
	}

	public ExitField() {
		super(EXITFIELD_DEFAULT_BLOCKED, EXITFIELD_DEAFULT_DESTROYABLE,
				EXITFIELD_DEFAULT_ITEM, EXITFIELD_DEFAULT_PIC);
	}

}
