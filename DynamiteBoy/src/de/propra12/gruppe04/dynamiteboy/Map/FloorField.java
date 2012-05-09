package de.propra12.gruppe04.dynamiteboy.Map;

public class FloorField extends Field {
	private static final String FLOORFIELD_DEFAULT_PIC = "";
	private static final boolean FLOORFIELD_DEFAULT_BLOCKED = false;
	private static final boolean FLOORFIELD_DEAFULT_DESTROYABLE = false;
	private static final int FLOORFIELD_DEFAULT_ITEM = 0;

	FloorField(boolean blocked, boolean destroyable, int item, String pic) {
		super(blocked, destroyable, item, pic);

	}

	FloorField() {
		super(FLOORFIELD_DEFAULT_BLOCKED, FLOORFIELD_DEAFULT_DESTROYABLE,
				FLOORFIELD_DEFAULT_ITEM, FLOORFIELD_DEFAULT_PIC);
	}

}
