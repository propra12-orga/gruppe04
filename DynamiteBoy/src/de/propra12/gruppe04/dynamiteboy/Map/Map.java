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
	private static Field[][] FieldGrid; // Array that contains all fields of the
										// map
	private static int gridWidth; // width of the map in fields
	private static int gridHeight; // height of the map in fields

	/**
	 * Creates a Map from an XML file
	 * 
	 * @param width
	 *            map width in px
	 * @param height
	 *            map height in px
	 * @param fileLocation
	 *            path of the XML file containing the map data
	 */
	public Map(int width, int height, String fileLocation) {
		this.gridWidth = width / 32;
		this.gridHeight = height / 32;
		generateFieldGrid(fileLocation);
	}

	/**
	 * Generates the grid and creates Field objects in FieldGrid[x][y] based on
	 * data from given XML-file
	 * 
	 * @param fileLocation
	 *            name of the XML-file containing the map data
	 */

	private void generateFieldGrid(String fileName) {
		FieldGrid = new Field[gridWidth][gridHeight];
		try {
			String path = "src/de/propra12/gruppe04/dynamiteboy/Map/";
			File mapData = new File(path + fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(mapData);
			NodeList mapRows = doc.getElementsByTagName("row");
			// FieldGrid gets created here
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

	/**
	 * 
	 * @param element
	 *            XML-representation of the Field object
	 * @return x-coordinate of current field
	 */
	private int xmlFieldxPos(Element element) {
		String stringPos = element.getAttribute("xPos");
		int xPos = Integer.parseInt(stringPos);
		return xPos;
	}

	/**
	 * 
	 * @param element
	 *            XML-representation of the Field object
	 * @return y-coordinate of current field
	 */
	private int xmlFieldyPos(Element element) {
		String stringPos = element.getAttribute("yPos");
		int yPos = Integer.parseInt(stringPos);
		return yPos;
	}

	/**
	 * 
	 * @param element
	 *            XML-representation of the Field object
	 * @return field object
	 */
	private Field xmlCreateField(Element element) {
		Field f = null;
		Exit exit = new Exit(false);
		String type = element.getTextContent();
		if (type.equals("")) {
			f = new FloorField();
			if (element.hasAttribute("exit")) {
				f = new ExitField();
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

	/**
	 * checks if field exists at given coordinates and returns Field-object if
	 * it does, otherwise returns null
	 * 
	 * @param x
	 *            x-gridposition
	 * @param y
	 *            y-gridposition
	 * @return Field
	 */
	public Field getField(int x, int y) {
		Field f = null;
		if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight) {
			f = FieldGrid[x][y];
		}
		return f;
	}

	public Field getFieldByPixel(int x, int y) {
		Field f = FieldGrid[(x / 32)][(y / 32)];
		return f;
	}

	public void setFloorField(int x, int y) {
		FieldGrid[x][y] = new FloorField();
	}

	public void setExitField(int x, int y) {
		FieldGrid[x][y] = new ExitField();
	}

	public static int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	public static int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

}
