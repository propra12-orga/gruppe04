package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Game.C;
import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class WallField extends Field {

	WallField(boolean blocked, boolean destroyable, boolean explodable,
			Item item, String pic) {
		super(blocked, destroyable, explodable, item, pic);
	}

	WallField(boolean blocked, boolean destroyable, boolean explodable,
			Item item) {
		super(blocked, destroyable, explodable, item, C.WALLFIELD_DEFAULT_PIC);
	}

	WallField() {
		super(C.WALLFIELD_DEFAULT_BLOCKED, C.WALLFIELD_DEAFULT_DESTROYABLE,
				C.WALLFIELD_DEFAULT_EXPLODABLE, C.WALLFIELD_DEFAULT_ITEM,
				C.WALLFIELD_DEFAULT_PIC);
	}
}
