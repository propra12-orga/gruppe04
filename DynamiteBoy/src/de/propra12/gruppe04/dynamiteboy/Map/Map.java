package de.propra12.gruppe04.dynamiteboy.Map;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.propra12.gruppe04.dynamiteboy.Item.Exit;

public class Map {
	private static Field[][] FieldGrid;
	private int gridWidth;
	private int gridHeight;

	/**
	 * Creates the default Map (no XML file needed)
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

	/**
	 * Creates a Map from
	 * 
	 * @param width
	 *            Map-width in px
	 * @param height
	 *            Map-height in px
	 * @param fileLocation
	 *            path of the XML file containing the map data
	 */
	public Map(int width, int height, String fileLocation) {
		this.gridWidth = width / 32;
		this.gridHeight = height / 32;
		generateFieldGrid(fileLocation);
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
		// Set unblocked-references
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				FieldGrid[i][j] = new FloorField();
			}
		}
		// Set blocked-references
		for (int i = 0; i < gridHeight; i++) {
			FieldGrid[0][i] = new WallField(true, false, null);
			FieldGrid[gridWidth - 1][i] = new WallField(true, false, null);
		}
		for (int i = 0; i < gridWidth; i++) {
			FieldGrid[i][0] = new WallField(true, false, null);
			FieldGrid[i][gridHeight - 1] = new WallField(true, false, null);
		}
		for (int i = 2; i < (gridWidth / 2) - 1; i += 2) {
			for (int j = 2; j < gridHeight - 2; j += 2)
				FieldGrid[i][j] = new WallField(true, false, null);
		}
		for (int i = ((gridWidth / 2) + 1); i < gridWidth - 2; i += 2) {
			for (int j = 2; j < gridHeight - 2; j += 2)
				FieldGrid[i][j] = new WallField(true, false, null);
		}
		// TODO Remove debug
		FieldGrid[1][5] = new DestroyableField();

		// Set Exit-field
		FieldGrid[1][10] = new ExitField();

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

	private void generateFieldGrid(String fileLocation) {
		FieldGrid = new Field[gridWidth][gridHeight];
		try {
			File mapData = new File(fileLocation);

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(mapData);
			NodeList mapRows = doc.getElementsByTagName("row");
			// FieldGrid gets created here
			// TODO Currently only FloorFields are created
			for (int i = 0; i < mapRows.getLength(); i++) {
				Node node = mapRows.item(i);
				Element element = (Element) node;
				NodeList mapFields = element.getElementsByTagName("Field");
				for (int j = 0; j < mapFields.getLength(); j++) {
					Node fnode = mapFields.item(j);
					Element felement = (Element) fnode;
					FieldGrid[xmlFieldxPos(felement)][xmlFieldyPos(felement)] = xmlCreateField(felement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int xmlFieldxPos(Element element) {
		String stringPos = element.getAttribute("xPos");
		int xPos = Integer.parseInt(stringPos);
		return xPos;
	}

	private int xmlFieldyPos(Element element) {
		String stringPos = element.getAttribute("yPos");
		int yPos = Integer.parseInt(stringPos);
		return yPos;
	}

	private Field xmlCreateField(Element element) {
		Field f = null;
		Exit exit = new Exit(false);
		String type = element.getTextContent();
		if (type.equals("")) {
			f = new FloorField();
			if (element.hasAttribute("exit")) {
				f.setItem(exit);
			}
		} else if (type.equals("wall")) {
			f = new WallField();
		} else if (type.equals("destroyable")) {
			f = new DestroyableField();
			if (element.hasAttribute("exit")) {
				f.setItem(exit);
			}
		}
		return f;
	}
}
