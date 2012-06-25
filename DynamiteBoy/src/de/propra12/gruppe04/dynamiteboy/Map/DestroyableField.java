package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Game.C;
import de.propra12.gruppe04.dynamiteboy.Item.Item;

/**
 * 
 * Represents a destroyable field
 * 
 */
public class DestroyableField extends Field {

	DestroyableField(boolean blocked, boolean destroyable, boolean explodable,
			Item item, String pic) {
		super(blocked, destroyable, explodable, item, pic);
	}

	DestroyableField() {
		super(C.DESTROYABLEFIELD_DEFAULT_BLOCKED,
				C.DESTROYABLEFIELD_DEFAULT_DESTROYABLE,
				C.DESTROYABLEFIELD_DEFAULT_EXPLODABLE,
				C.DESTROYABLEFIELD_DEFAULT_ITEM, C.DESTROYABLEFIELD_DEFAULT_PIC);
	}

}
