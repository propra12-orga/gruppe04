package de.propra12.gruppe04.dynamiteboy.Map;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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
import de.propra12.gruppe04.dynamiteboy.Item.FunnyPill;
import de.propra12.gruppe04.dynamiteboy.Item.P1Starter;
import de.propra12.gruppe04.dynamiteboy.Item.P2Starter;
import de.propra12.gruppe04.dynamiteboy.Item.Teleporter;

public class Map {
	private static Field[][] FieldGrid; // Array that contains all fields of the
										// map
	private static int gridWidth; // width of the map in fields
	private static int gridHeight; // height of the map in fields
	private String mapName;
	private String mapType;
	private String mapAuthor;
	// The starting positions for the players and the exit (fieldgrid-positions)
	private int p1startx;
	private int p2startx;
	private int p1starty;
	private int p2starty;
	private int exitx;
	private int exity;
	private String[] score = new String[5];
	private String[] names = new String[5];
	private String path = "src/de/propra12/gruppe04/dynamiteboy/Map/";
	private String filename;

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
		loadMapFromXML(filename);
		int singleScore = 900;
		for (int i = 0; i < 5; i++) {
			score[i] = "" + singleScore;
			names[i] = "DynamiteBoy";
			singleScore -= 100;
		}
	}

	/**
	 * Generates the grid and creates Field objects in FieldGrid[x][y] based on
	 * data from given XML-file
	 * 
	 * @param fileLocation
	 *            name of the XML-file containing the map data
	 */
	public void loadMapFromXML(String fileName) {
		FieldGrid = new Field[gridWidth][gridHeight];
		try {
			File mapData = new File(path + fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(mapData);
			// load scores
			NodeList scores = doc.getElementsByTagName("score");
			for (int i = 0; i < scores.getLength(); i++) {
				Element currentScore = (Element) scores.item(i);
				this.names[i] = currentScore.getAttribute("name");
				this.score[i] = currentScore.getTextContent();
			}
			// set map attributes
			Element mapNode = (Element) doc.getFirstChild();
			this.mapAuthor = mapNode.getAttribute("author");
			this.mapType = mapNode.getAttribute("mode");
			// TODO Remove Debug
			this.mapName = mapNode.getAttribute("name");
			// FieldGrid gets created here
			NodeList mapRows = doc.getElementsByTagName("row");
			for (int i = 0; i < mapRows.getLength(); i++) {
				Node node = mapRows.item(i);
				Element element = (Element) node;
				NodeList mapFields = element.getElementsByTagName("Field");
				for (int j = 0; j < mapFields.getLength(); j++) {
					Node fnode = mapFields.item(j);
					Element felement = (Element) fnode;
					FieldGrid[getFieldXPosFromXML(felement)][getFieldYPosFromXML(felement)] = createFieldFromXML(felement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * saves the current state of the field grid to an xml file also generates
	 * scores and everything else
	 */
	public void saveMapToXML() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// create root element (<map>)
			Document doc = docBuilder.newDocument();
			Element map = doc.createElement("map");
			doc.appendChild(map);
			// set maps attributes
			map.setAttribute("mode", mapType);
			map.setAttribute("name", mapName);
			map.setAttribute("author", mapAuthor);
			// create scores
			int singleScore = 900;
			for (int i = 0; i < 5; i++) {
				Element xmlscore = doc.createElement("score");
				map.appendChild(xmlscore);
				xmlscore.setAttribute("id", "" + (i + 1));
				xmlscore.setAttribute("name", names[i]);
				xmlscore.appendChild(doc.createTextNode(score[i]));
				singleScore -= 100;
			}
			// create rows, and then in each row the fields
			for (int y = 0; y < gridHeight; y++) {
				String yString = "" + y;
				Element row = doc.createElement("row");
				map.appendChild(row);
				// create field
				for (int x = 0; x < gridWidth; x++) {
					Element field = doc.createElement("Field");
					row.appendChild(field);
					String xString = "" + x;
					field.setAttribute("xPos", xString);
					field.setAttribute("yPos", yString);
					// Set the fields attributes and the fieldtype
					Field f = this.getField(x, y);
					if (f.hasItem()) {
						String item = f.getItemType();
						field.setAttribute(item, "1");
					}
					String fieldType = f.getFieldType();
					field.appendChild(doc.createTextNode(fieldType));
				}
			}

			// write the contents of DOM-Object into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			transformerFactory.setAttribute("indent-number", new Integer(2));
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
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
	private int getFieldXPosFromXML(Element element) {
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
	private int getFieldYPosFromXML(Element element) {
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
	private Field createFieldFromXML(Element element) {
		Field f = null;
		Exit exit = new Exit(false);
		String type = element.getTextContent();
		if (type.equals("")) {
			f = new FloorField();
			if (element.hasAttribute("exit")) {
				f.setItem(new Exit(false));
			} else if (element.hasAttribute("teleporter")) {
				f.setItem(new Teleporter());
			} else if (element.hasAttribute("funnypill")) {
				f.setItem(new FunnyPill());
			} else if (element.hasAttribute("p1starter")) {
				this.setP1startx(getFieldXPosFromXML(element));
				this.setP1starty(getFieldYPosFromXML(element));
				f.setItem(new P1Starter(false));
			} else if (element.hasAttribute("p2starter")) {
				this.setP2startx(getFieldXPosFromXML(element));
				this.setP2starty(getFieldYPosFromXML(element));
				f.setItem(new P2Starter(false));
			}
		} else if (type.equals("wall")) {
			f = new WallField();
		} else if (type.equals("destroyable")) {
			f = new DestroyableField();
			if (element.hasAttribute("exit")) {
				f.setItem(new Exit(false));
			} else if (element.hasAttribute("teleporter")) {
				f.setItem(new Teleporter());
			} else if (element.hasAttribute("funnypill")) {
				f.setItem(new FunnyPill());
			} else if (element.hasAttribute("p1starter")) {
				this.setP1startx(getFieldXPosFromXML(element));
				this.setP1starty(getFieldYPosFromXML(element));
			} else if (element.hasAttribute("p2starter")) {
				this.setP2startx(getFieldXPosFromXML(element));
				this.setP2starty(getFieldYPosFromXML(element));
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

	public boolean hasExit() {
		for (int y = 0; y < getGridHeight(); y++) {
			for (int x = 0; x < getGridWidth(); x++) {
				if (getField(x, y).getItem() instanceof Exit) {
					// TODO Remove Debug
					System.out.println("Exit at " + y + "/" + x);
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasStartPoints() {
		boolean p1 = false;
		boolean p2 = false;
		for (int y = 0; y < getGridHeight(); y++) {
			for (int x = 0; x < getGridWidth(); x++) {
				if (getField(x, y).getItem() instanceof P1Starter) {
					p1 = true;
				}
				if (getField(x, y).getItem() instanceof P2Starter) {
					p2 = true;
				}
			}
		}
		if (this.mapType == "singleplayer" && p1) {
			return true;
		} else if (this.mapType == "multiplayer" && p1 && p2) {
			return true;
		} else {
			return false;
		}
	}

	public Field getFieldByPixel(int x, int y) {
		Field f = FieldGrid[(x / 32)][(y / 32)];
		return f;
	}

	public void setFloorField(int x, int y) {
		FieldGrid[x][y] = new FloorField();
	}

	public void setExitField(int x, int y) {
		FieldGrid[x][y] = new FloorField();
		FieldGrid[x][y].setItem(new Exit(false));
	}

	public void setFunnyPillField(int x, int y) {
		FieldGrid[x][y] = new FloorField();
		FieldGrid[x][y].setItem(new FunnyPill());
	}

	public void setTeleportField(int x, int y) {
		FieldGrid[x][y] = new FloorField();
		FieldGrid[x][y].setItem(new Teleporter());
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

	public Field getP1StartField() {
		if (this.hasStartPoints()) {
			for (int y = 0; y < getGridHeight(); y++) {
				for (int x = 0; x < getGridWidth(); x++) {
					if (getField(x, y).getItem() instanceof P1Starter) {
						Field f = getField(x, y);
						return f;
					}
				}
			}
		}
		return null;
	}

	public Field getP2StartField() {
		if (this.hasStartPoints()) {
			for (int y = 0; y < getGridHeight(); y++) {
				for (int x = 0; x < getGridWidth(); x++) {
					if (getField(x, y).getItem() instanceof P2Starter) {
						Field f = getField(x, y);
						return f;
					}
				}
			}
		}
		return null;
	}

	public Field getExitField() {
		if (this.hasExit()) {
			for (int y = 0; y < getGridHeight(); y++) {
				for (int x = 0; x < getGridWidth(); x++) {
					if (getField(x, y).getItem() instanceof Exit) {
						Field f = getField(x, y);
						return f;
					}
				}
			}
		}
		return null;
	}

	public int getP1startx() {
		return p1startx;
	}

	public void setP1startx(int p1startx) {
		this.p1startx = p1startx;
	}

	public int getP2startx() {
		return p2startx;
	}

	public void setP2startx(int p2startx) {
		this.p2startx = p2startx;
	}

	public int getP1starty() {
		return p1starty;
	}

	public void setP1starty(int p1starty) {
		this.p1starty = p1starty;
	}

	public int getP2starty() {
		return p2starty;
	}

	public void setP2starty(int p2starty) {
		this.p2starty = p2starty;
	}

	public int getExitx() {
		return exitx;
	}

	public void setExitx(int exitx) {
		this.exitx = exitx;
	}

	public int getExity() {
		return exity;
	}

	public void setExity(int exity) {
		this.exity = exity;
	}

	public String[] getScore() {
		return score;
	}

	public void setScore(String[] score) {
		this.score = score;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

}
