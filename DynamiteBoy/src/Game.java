public class Game {
	private int[][] FieldGrid;

	public Game(Gui g) {
		generateFieldGrid(g);

	}

	/**
	 * Generates the grid and sets up values for "blocked" and "unblocked".
	 * Currently only border and left side is set up.
	 * 
	 * @param g
	 *            Field grid calculated with width and height
	 */
	private void generateFieldGrid(Gui g) {
		int gridWidth = g.getWidth() / 32;
		int gridHeight = g.getHeigt() / 32;
		FieldGrid = new int[gridWidth][gridHeight];

		// Sets 0 for unblocked and 1 for blocked
		for (int i = 0; i < gridHeight; i++) {
			FieldGrid[0][i] = 1;
			FieldGrid[gridWidth - 1][i] = 1;
		}
		for (int i = 0; i < gridWidth; i++) {
			FieldGrid[i][0] = 1;
			FieldGrid[i][gridHeight - 1] = 1;
		}
		for (int i = 2; i < (gridWidth / 2) - 2; i += 2) {
			for (int j = 2; j < gridHeight - 2; j += 2)
				FieldGrid[i][j] = 1;
		}
		for (int i = ((gridWidth / 2) - 1); i < gridWidth - 2; i += 2) {
			for (int j = 2; j < gridHeight - 2; j += 2)
				FieldGrid[i][j] = 1;
		}

	}

	/**
	 * (0,0) at top-left
	 * 
	 * @param posx
	 *            x-coordinate for block-check
	 * @param posy
	 *            y-coordinate for block-check
	 * @return Returns "true" when position is blocked
	 */
	public boolean isBlocked(int posx, int posy) {
		if (FieldGrid[posx][posy] == 1) {
			return true;
		} else {
			return false;
		}
	}

}
