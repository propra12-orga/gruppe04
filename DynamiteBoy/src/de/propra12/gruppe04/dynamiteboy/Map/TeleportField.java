package de.propra12.gruppe04.dynamiteboy.Map;

import de.propra12.gruppe04.dynamiteboy.Game.C;

public class TeleportField extends Field {

	public TeleportField() {
		super(C.TELEPORTFIELD_DEFAULT_BLOCKED,
				C.TELEPORTFIELD_DEAFULT_DESTROYABLE,
				C.TELEPORTFIELD_DEFAULT_EXPLODABLE,
				C.TELEPORTFIELD_DEFAULT_ITEM, C.TELEPORTFIELD_DEFAULT_PIC);
	}

}
