package de.propra12.gruppe04.dynamiteboy.Map;

public class WallField extends Field {
	private static final String WALLFIELD_DEFAULT_PIC = "";
	private static final boolean WALLFIELD_DEFAULT_BLOCKED = true;
	private static final boolean WALLFIELD_DEAFULT_DESTROYABLE = false;
	private static final int WALLFIELD_DEFAULT_ITEM = 0;

	WallField(boolean blocked, boolean destroyable, int item, String pic) {
		super(blocked, destroyable, item, pic);
	}

	WallField(boolean blocked, boolean destroyable, int item) {
		super(blocked, destroyable, item, WALLFIELD_DEFAULT_PIC);
		// TODO Auto-generated constructor stub
	}

	WallField() {
		super(WALLFIELD_DEFAULT_BLOCKED, WALLFIELD_DEAFULT_DESTROYABLE,
				WALLFIELD_DEFAULT_ITEM, WALLFIELD_DEFAULT_PIC);
	}
}
