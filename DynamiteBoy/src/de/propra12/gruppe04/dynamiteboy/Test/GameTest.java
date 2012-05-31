package de.propra12.gruppe04.dynamiteboy.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class GameTest {
	private Map g;

	@Before
	public void setUp() {
		g = new Map(640, 480);
	}

	@Ignore
	public void GuiTest() {

	}

	@Test
	public void blockedTest() {

		assertEquals(true, g.getField(0, 0).isBlocked());
		assertEquals(true, g.getField(19, 0).isBlocked());
		assertEquals(true, g.getField(0, 14).isBlocked());
		assertEquals(true, g.getField(19, 14).isBlocked());
		assertEquals(true, g.getField(19, 14).isBlocked());
		assertEquals(true, g.getField(2, 2).isBlocked());
		assertEquals(true, g.getField(2, 12).isBlocked());
		assertEquals(true, g.getField(17, 2).isBlocked());
		assertEquals(true, g.getField(17, 2).isBlocked());

	}

	@Test
	public void unblockedTest() {
		assertEquals(false, g.getField(1, 1).isBlocked());
		assertEquals(false, g.getField(18, 13).isBlocked());

	}

	@Test
	public void pixelBlockTest() {
		assertEquals(true, g.getFieldByPixel(630, 9).isBlocked());
		assertEquals(true, g.getFieldByPixel(608, 32).isBlocked());
		assertEquals(false, g.getFieldByPixel(607, 32).isBlocked());

	}

	@Test
	public void destroyableTest() {
		assertEquals(false, g.getField(1, 1).isDestroyable());
	}
}
