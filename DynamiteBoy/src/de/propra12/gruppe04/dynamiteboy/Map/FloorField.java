package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Game.C;
import de.propra12.gruppe04.dynamiteboy.Item.Bomb;
import de.propra12.gruppe04.dynamiteboy.Item.FunnyPill;
import de.propra12.gruppe04.dynamiteboy.Item.Item;
import de.propra12.gruppe04.dynamiteboy.Item.Teleporter;

/**
 * 
 * Represents a walkable floor field
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
		setRandImage();
	}

	public void setItem(Item item) {
		super.setItem(item);
		if (item == null) {
			if (C.funny == true) {
				super.setImage(C.FUNNYPILL_EXPLODED);
			} else {
				super.setImage(C.FLOORFIELD_DEFAULT_PIC);
			}
		}
		if (item instanceof Bomb) {
			if (C.funny == true) {
				super.setImage(C.FUNNYPILL_BOMB);
			} else {
				super.setImage("../images/db_field_floorbomb.png");
			}
		}
		if (item instanceof FunnyPill) {
			super.setImage("../images/funnypill/db_item_funnypill.png");
		}
		if (item instanceof Teleporter) {
			super.setImage("../images/db_field_teleporter.png");
		}

	}

	public void setRandImage() {
		int i = (int) (Math.random() * 100);
		if (i < 20) {
			// grass & flowers
			super.setImage("../images/db_field_floor.png");
		} else if (i >= 20 && i < 60) {
			// empty floor
			super.setImage("../images/db_field_floor2.png");
		} else if (i >= 60)
			// grass
			super.setImage("../images/db_field_floor3.png");
	}
}