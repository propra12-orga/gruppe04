import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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

		assertEquals(true, g.getFieldGrid(0, 0).isBlocked());
		assertEquals(true, g.getFieldGrid(19, 0).isBlocked());
		assertEquals(true, g.getFieldGrid(0, 14).isBlocked());
		assertEquals(true, g.getFieldGrid(19, 14).isBlocked());
		assertEquals(true, g.getFieldGrid(19, 14).isBlocked());
		assertEquals(true, g.getFieldGrid(2, 2).isBlocked());
		assertEquals(true, g.getFieldGrid(2, 12).isBlocked());
		assertEquals(true, g.getFieldGrid(17, 2).isBlocked());
		assertEquals(true, g.getFieldGrid(17, 2).isBlocked());

	}
}
