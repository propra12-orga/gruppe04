package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Item.Exit;
import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class ExitField extends Field {
	private static final String DEFAULT_PIC = "../images/db_field_exit.gif";
	private static final boolean DEFAULT_BLOCKED = false;
	private static final boolean DEAFULT_DESTROYABLE = false;
	private static final Item DEFAULT_ITEM = new Exit(false);

	ExitField(boolean blocked, boolean destroyable, Item item, String pic) {
		super(blocked, destroyable, item, pic);
	}

	public ExitField() {
		super(DEFAULT_BLOCKED, DEAFULT_DESTROYABLE, DEFAULT_ITEM, DEFAULT_PIC);
	}

}
