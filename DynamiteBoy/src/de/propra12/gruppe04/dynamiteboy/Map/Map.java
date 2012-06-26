package de.propra12.gruppe04.dynamiteboy.Map;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.propra12.gruppe04.dynamiteboy.Item.Exit;
import de.propra12.gruppe04.dynamiteboy.Item.Lsd;
import de.propra12.gruppe04.dynamiteboy.Item.Teleporter;

public class Map {
	private static Field[][] FieldGrid; // Array that contains all fields of the
										// map
	private static int gridWidth; // width of the map in fields
	private static int gridHeight; // height of the map in fields
	private String mapName;
	private String mapType;
	private String mapAuthor;
	String path = "src/de/propra12/gruppe04/dynamiteboy/Map/";

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
	public Map(int width, int height, String filename) {
		this.gridWidth = width / 32;
		this.gridHeight = height / 32;
		generateFieldGrid(filename);
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

	public void saveFieldGridToXML() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// create root element (<map>)
			Document doc = docBuilder.newDocument();
			Element map = doc.createElement("map");
			doc.appendChild(map);
			// set maps attributes
			map.setAttribute("type", mapType);
			map.setAttribute("name", mapName);
			map.setAttribute("author", mapAuthor);
			// create rows, and then in each row the fields
			for (int i = 0; i < gridHeight; i++) {
				String yString = "" + i;
				Element row = doc.createElement("row");
				map.appendChild(row);
				for (int j = 0; j < gridWidth; j++) {
					Element field = doc.createElement("Field");
					row.appendChild(field);
					String xString = "" + j;
					field.setAttribute("xPos", xString);
					field.setAttribute("yPos", yString);
					String fieldType = FieldGrid[j][i].getFieldType();
					field.appendChild(doc.createTextNode(fieldType));
				}
			}

			// write the contents of DOM-Object into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path + mapName
					+ ".xml"));
			transformer.transform(source, result);
			// TODO Remove Debug
			System.out.println("Gespeichert");
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
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
			} else if (element.hasAttribute("teleporter")) {
				f.setItem(new Teleporter());
			} else if (element.hasAttribute("lsd")) {
				f.setItem(new Lsd());
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

	public void setDestroyableField(int x, int y) {
		FieldGrid[x][y] = new DestroyableField();
	}

	public void setWallField(int x, int y) {
		FieldGrid[x][y] = new WallField();
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

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getMapType() {
		return mapType;
	}

	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

	public String getMapAuthor() {
		return mapAuthor;
	}

	public void setMapAuthor(String mapAuthor) {
		this.mapAuthor = mapAuthor;
	}

}
