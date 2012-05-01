import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GameTest {
	private Game g;

	@Before
	public void setUp() {
		Gui gui = new Gui(640, 480);
		Game g = new Game(gui);
	}

	@Ignore
	public void GuiTest() {

	}

	@Test
	public void blockedTest() {

		assertEquals(true, g.isBlocked(0, 0));
		assertEquals(true, g.isBlocked(19, 0));
		assertEquals(true, g.isBlocked(0, 14));
		assertEquals(true, g.isBlocked(19, 14));
		assertEquals(true, g.isBlocked(19, 14));
		assertEquals(true, g.isBlocked(2, 2));
		assertEquals(true, g.isBlocked(2, 12));
		assertEquals(true, g.isBlocked(17, 2));
		assertEquals(true, g.isBlocked(17, 2));

	}
}
