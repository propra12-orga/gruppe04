package de.propra12.gruppe04.dynamiteboy.Test;

import org.junit.Test;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class MapTest {
	private Map m;

	// Checks if map can be created with xmlFile containing the map data
	@Test
	public void mapXmlTest() {
		m = new Map(640, 480,
				"src/de/propra12/gruppe04/dynamiteboy/Map/Map1.xml");
	}

}