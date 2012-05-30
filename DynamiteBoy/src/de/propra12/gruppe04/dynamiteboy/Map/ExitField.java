package de.propra12.gruppe04.dynamiteboy.Map;

public class ExitField extends Field {
	private static final String EXITFIELD_DEFAULT_PIC = "../images/db_field_exit.png";
	private static final boolean EXITFIELD_DEFAULT_BLOCKED = false;
	private static final boolean EXITFIELD_DEAFULT_DESTROYABLE = false;
	private static final int EXITFIELD_DEFAULT_ITEM = 0;

	ExitField(boolean blocked, boolean destroyable, int item, String pic) {
		super(blocked, destroyable, item, pic);
	}

	public ExitField() {
		super(blocked, destroyable, item, pic);
	}

}
