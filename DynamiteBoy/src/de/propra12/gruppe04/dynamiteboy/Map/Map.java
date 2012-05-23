package de.propra12.gruppe04.dynamiteboy.Map;

public class Map {
	private static Field[][] FieldGrid;
	private int gridWidth;
	private int gridHeight;

	/**
	 * 
	 * @param width
	 *            Map-width in px
	 * @param height
	 *            Map-height in px
	 */
	public Map(int width, int height) {
		this.gridWidth = width / 32;
		this.gridHeight = height / 32;
		generateFieldGrid();

	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	/**
	 * Generates the grid and sets up values for "blocked" and "unblocked".
	 * 
	 * @param width
	 *            Map-width in px
	 * @param height
	 *            Map-height in px
	 */
	private void generateFieldGrid() {
		FieldGrid = new Field[gridWidth][gridHeight];
		WallField blocked = new WallField(true, false, 0);
		FloorField unblocked = new FloorField();
		// Set unblocked-references
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				FieldGrid[i][j] = unblocked;
			}
		}
		// Set blocked-references
		for (int i = 0; i < gridHeight; i++) {
			FieldGrid[0][i] = blocked;
			FieldGrid[gridWidth - 1][i] = blocked;
		}
		for (int i = 0; i < gridWidth; i++) {
			FieldGrid[i][0] = blocked;
			FieldGrid[i][gridHeight - 1] = blocked;
		}
		for (int i = 2; i < (gridWidth / 2) - 2; i += 2) {
			for (int j = 2; j < gridHeight - 2; j += 2)
				FieldGrid[i][j] = blocked;
		}
		for (int i = ((gridWidth / 2) - 1); i < gridWidth - 2; i += 2) {
			for (int j = 2; j < gridHeight - 2; j += 2)
				FieldGrid[i][j] = blocked;
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
	public Field getField(int x, int y) {
		Field f = FieldGrid[x][y];
		return f;
	}

	public Field getFieldByPixel(int x, int y) {
		Field f = FieldGrid[(x / 32)][(y / 32)];
		return f;
	}

	public void setFloorField(int x, int y) {
		FieldGrid[x][y] = new FloorField();

	}

}
