package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Game.C;
import de.propra12.gruppe04.dynamiteboy.Item.Item;

public class ExitField extends Field {

	ExitField(boolean blocked, boolean destroyable, boolean explodable,
			Item item, String pic) {
		super(blocked, destroyable, explodable, item, pic);
	}

	public ExitField() {
		super(C.EXITFIELD_DEFAULT_BLOCKED, C.EXITFIELD_DEAFULT_DESTROYABLE,
				C.EXITFIELD_DEFAULT_EXPLODABLE, C.EXITFIELD_DEFAULT_ITEM,
				C.EXITFIELD_DEFAULT_PIC);
	}

}
