
public class Map {
	private Field[][] FieldGrid;

	/**
	 * 
	 * @param width
	 *            Map-width in px
	 * @param height
	 *            Map-height in px
	 */
	public Map(int width, int height) {
		generateFieldGrid(width, height);
	}

	/**
	 * Generates the grid and sets up values for "blocked" and "unblocked".
	 * 
	 * @param width
	 *            Map-width in px
	 * @param height
	 *            Map-height in px
	 */
	private void generateFieldGrid(int width, int height) {
		int gridWidth = width / 32;
		int gridHeight = height / 32;
		FieldGrid = new Field[gridWidth][gridHeight];

		WallField block = new WallField(true, false, 0);

		// Sets 0 for unblocked and 1 for blocked
		for (int i = 0; i < gridHeight; i++) {
			FieldGrid[0][i] = block;
			FieldGrid[gridWidth - 1][i] = block;
		}
		for (int i = 0; i < gridWidth; i++) {
			FieldGrid[i][0] = block;
			FieldGrid[i][gridHeight - 1] = block;
		}
		for (int i = 2; i < (gridWidth / 2) - 2; i += 2) {
			for (int j = 2; j < gridHeight - 2; j += 2)
				FieldGrid[i][j] = block;
		}
		for (int i = ((gridWidth / 2) - 1); i < gridWidth - 2; i += 2) {
			for (int j = 2; j < gridHeight - 2; j += 2)
				FieldGrid[i][j] = block;
		}

	}

	/**
	 * 
	 * @param x
	 *            x-coordinate of fieldposition
	 * @param y
	 *            y-coordinate of fieldposition
	 * @return Field object
	 */
	public Field getFieldGrid(int x, int y) {
		Field f = FieldGrid[x][y];
		return f;
	}

}
