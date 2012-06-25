package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Game.C;
import de.propra12.gruppe04.dynamiteboy.Item.Bomb;
import de.propra12.gruppe04.dynamiteboy.Item.Item;

/**
 * 
 * Represents a floor field
 * 
 */
public class FloorField extends Field {

	FloorField(boolean blocked, boolean destroyable, boolean explodable,
			Item item, String pic) {
		super(blocked, destroyable, explodable, item, pic);
	}

	FloorField(boolean blocked, boolean destroyable, boolean explodable,
			Item item) {
		super(blocked, destroyable, explodable, item, C.FLOORFIELD_DEFAULT_PIC);
	}

	FloorField() {
		super(C.FLOORFIELD_DEFAULT_BLOCKED, C.FLOORFIELD_DEAFULT_DESTROYABLE,
				C.FLOORFIELD_DEFAULT_EXPLODABLE, C.FLOORFIELD_DEFAULT_ITEM,
				C.FLOORFIELD_DEFAULT_PIC);
	}

	public void setItem(Item item) {
		super.setItem(item);
		if (item == null) {
			super.setImage(C.FLOORFIELD_DEFAULT_PIC);
		}
		if (item instanceof Bomb) {
			super.setImage("../images/db_field_floorbomb.png");
		}
	}
}