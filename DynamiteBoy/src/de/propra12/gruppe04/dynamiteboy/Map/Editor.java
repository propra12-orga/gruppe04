package de.propra12.gruppe04.dynamiteboy.Map;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Game.InputHandler;
import de.propra12.gruppe04.dynamiteboy.Item.Exit;
import de.propra12.gruppe04.dynamiteboy.Item.FunnyPill;
import de.propra12.gruppe04.dynamiteboy.Item.P1Starter;
import de.propra12.gruppe04.dynamiteboy.Item.P2Starter;
import de.propra12.gruppe04.dynamiteboy.Item.Teleporter;
import de.propra12.gruppe04.dynamiteboy.Menu.MainMenu;

/**
 * This class represents the Map Editor
 * 
 * @author ekki
 * 
 */
public class Editor extends JPanel implements MouseListener {
	private JFrame frame;
	private Map map;
	private String mapname;
	private String authorname;
	private String mapType;
	private InputHandler input;
	private JButton buttonChangeFieldType;
	private JButton buttonResetSelection;
	private JButton buttonSaveAndExit;
	private JPanel panelEdit = new JPanel(new GridLayout(1, 3));
	private int p1startx;
	private int p1starty;

	/**
	 * Starts a new Editor with the given parameters
	 * 
	 * @param frame
	 *            The frame the editor should run in
	 * @param mapname
	 *            name of the map to generate
	 * @param authorname
	 *            map creator name
	 * @param mapType
	 *            choose between singleplayer or multiplayer map
	 */
	public Editor(JFrame frame, String mapname, String authorname,
			String mapType) {
		this.map = new Map(640, 480, "MapTemplate.xml");
		this.mapname = mapname;
		this.authorname = authorname;
		this.mapType = mapType;
		this.frame = frame;
		this.setFocusable(true);
		addMouseListener(this);
		initGUI();
		repaint();
		// TODO REMOVE DEBUG
		System.out.println("Started editor with the following settings: \n"
				+ mapname + ", " + authorname + ", " + mapType);
	}

	public void initGUI() {
		buttonChangeFieldType = new JButton("Felder Ändern");
		buttonResetSelection = new JButton("Auswahl aufheben");
		buttonSaveAndExit = new JButton("Speichern und verlassen");
		panelEdit.add(buttonChangeFieldType);
		panelEdit.add(buttonResetSelection);
		panelEdit.add(buttonSaveAndExit);
		buttonChangeFieldType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeFieldTypes();
			}
		});
		buttonResetSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetSelection();
			}
		});
		buttonSaveAndExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Set Main Settings
				map.setMapAuthor(authorname);
				map.setMapType(mapType);
				map.setMapName(mapname);
				if (isMapValid(map)) {
					// Save
					map.saveFieldGridToXML();
					// Exit
					quitEditor();
					MainMenu m = new MainMenu(frame);

				}

			}
		});
		buttonChangeFieldType.setPreferredSize(new Dimension(60, 32));
		buttonResetSelection.setPreferredSize(new Dimension(60, 32));
		buttonSaveAndExit.setPreferredSize(new Dimension(60, 32));
		frame.getContentPane().add(BorderLayout.SOUTH, panelEdit);
	}

	/**
	 * goes back to main menu
	 */
	public void quitEditor() {
		panelEdit.setVisible(false);
		this.setVisible(false);
		this.setFocusable(false);
	}

	/**
	 * shows the user a dialog to choose the currently selected fields type
	 * 
	 * Field Types: (1) "": empty string represents FloorField (2) "wall":
	 * WallField (3) "destroyable": DestroyableField
	 * 
	 * @return chosen FieldType
	 */
	public String getFieldTypeDecision() {
		// DEFAULT FIELD TYPE GETS SET HERE
		// TODO create constant
		String fieldType = "";
		Object[] fieldTypes = { "Boden", "Wand", "Zerstörbare Wand" };
		fieldType = (String) JOptionPane.showInputDialog(frame,
				"Bitte Feldtyp wählen: \n", "Feldtyp",
				JOptionPane.PLAIN_MESSAGE, null, fieldTypes, "");
		if ((fieldType != null) && (fieldType.length() > 0)) {
			if (fieldType.equals(fieldTypes[0])) {
				fieldType = "";
				return fieldType;
			}
			if (fieldType.equals(fieldTypes[1])) {
				fieldType = "wall";
				return fieldType;
			}
			if (fieldType.equals(fieldTypes[2])) {
				fieldType = "destroyable";
				return fieldType;
			}
		}
		return fieldType;
	}

	/**
	 * shows a dialogue that asks the user if he wants the selected destroyable
	 * field to have an item
	 * 
	 * @return
	 */
	public String getItemTypeDecision() {
		// DEFAULT ITEM TYPE GETS SET HERE
		// TODO create constant
		String itemType = "No Item";
		Object[] itemTypes = { "No Item", "Exit", "FunnyPill", "Teleporter" };
		itemType = (String) JOptionPane.showInputDialog(frame,
				"Bitte Feldtyp wählen: \n", "Feldtyp",
				JOptionPane.PLAIN_MESSAGE, null, itemTypes, "");
		if ((itemType != null) && (itemType.length() > 0)) {
			if (itemType.equals(itemTypes[0])) {
				itemType = "";
				return itemType;
			}
			if (itemType.equals(itemTypes[1])) {
				itemType = "exit";
				return itemType;
			}
			if (itemType.equals(itemTypes[2])) {
				itemType = "funnypill";
				return itemType;
			}
			if (itemType.equals(itemTypes[3])) {
				itemType = "teleporter";
				return itemType;
			}
		}
		return itemType;
	}

	/**
	 * shows a dialogue that asks the user if he wants the selected floorfield
	 * to be a startpoint
	 * 
	 * @return
	 */
	public String getStartPointDecision() {
		String startPoint = "No Item";
		Object[] startPoints = { "No Startpoint", "Start P1", "Start P2" };
		startPoint = (String) JOptionPane.showInputDialog(frame,
				"Soll hier ein Spieler starten? \n", "Startpunkt",
				JOptionPane.PLAIN_MESSAGE, null, startPoints, "");
		if ((startPoint != null) && (startPoint.length() > 0)) {
			if (startPoint.equals(startPoints[0])) {
				startPoint = "";
				return startPoint;
			}
			if (startPoint.equals(startPoints[1])) {
				startPoint = "p1starter";
				return startPoint;
			}
			if (startPoint.equals(startPoints[2])) {
				startPoint = "p2starter";
				return startPoint;
			}
		}
		return startPoint;
	}

	/**
	 * This method changes the selected fields of the map based on the users
	 * choice in the called method FieldTypeDecsion() and sets items and
	 * starting points based on the decisions from getItemTypeDecision() and
	 * getStartPointDecision()
	 */
	public void changeFieldTypes() {
		String fieldType = getFieldTypeDecision();
		for (int y = 0; y < map.getGridHeight(); y++) {
			for (int x = 0; x < map.getGridWidth(); x++) {
				Field f = map.getField(x, y);
				if (f.isSelected()) {
					if (fieldType == "") {
						// Floor Field
						String startpoint = getStartPointDecision();
						map.setFloorField(x, y);
						f = map.getField(x, y);
						if (startpoint == "p1starter") {
							P1Starter p1 = new P1Starter(false);
							f.setItem(p1);
							map.setP1startx(x);
							map.setP1starty(y);
						}
						if (startpoint == "p2starter") {
							P2Starter p2 = new P2Starter(false);
							f.setItem(p2);
							map.setP2startx(x);
							map.setP2starty(y);
						}
						repaint();
					}
					if (fieldType == "wall") {
						map.setWallField(x, y);
						repaint();
					}
					if (fieldType == "destroyable") {
						String itemType = getItemTypeDecision();
						map.setDestroyableField(x, y);
						f = map.getField(x, y);
						if (itemType == "exit") {
							Exit exit = new Exit(false);
							f.setItem(exit);
							map.setExitx(x);
							map.setExity(y);
						}
						if (itemType == "funnypill") {
							FunnyPill pill = new FunnyPill();
							f.setItem(pill);
						}
						if (itemType == "teleporter") {
							Teleporter tele = new Teleporter();
							f.setItem(tele);
						}
					}
					repaint();
				}
			}
		}
	}

	/**
	 * Checks if the given map is valid (WIP)
	 * 
	 * @param map
	 * @return true=valid, false=not valid
	 */
	public boolean isMapValid(Map map) {
		String type = map.getMapType();
		if (type.equals("singleplayer")) {
			if (!map.hasExit()) {
				// TODO Remove Debug (add JDialog, or something)
				System.out.println("Map is Singleplayer and has no Exit!");
				return false;
			}
		}
		if (type.equals("multiplayer")) {
			if (map.hasExit()) {
				// TODO Remove Debug (add JDialog, or something)
				System.out.println("Map is multiplayer and has an Exit!");
				return false;
			}
		}
		if (!map.hasStartPoints()) {
			// TODO Remove Debug (add JDialog, or something)
			System.out.println("A Startpoint is missing!");
			return false;
		}
		// if (!isExitReachable()) {
		// System.out.println("Exit is not reachable!");
		// return false;
		// }
		// TODO singleplayer -> check if exit is reachable (not fully working)
		// TODO multiplayer -> check if 2 startpoints are set
		return true;
	}

	/**
	 * removes the selection from the currently selected fields
	 */
	public void resetSelection() {
		for (int y = 0; y < map.getGridHeight(); y++) {
			for (int x = 0; x < map.getGridWidth(); x++) {
				Field f = map.getField(x, y);
				if (f.isSelected()) {
					f.setSelected(false);
					f.setImage(f.fieldpic);
					repaint();
				}
			}
		}
	}

	/**
	 * This method checks if the player can reach the exit from his starting
	 * point
	 * 
	 * @return
	 */
	public boolean isExitReachable() {
		// Player starting point coordinates are set
		p1startx = map.getP1startx();
		p1starty = map.getP1starty();
		// Exit coordinates are set
		int ex = map.getExitx();
		int ey = map.getExity();
		checkReachableFields(p1startx, p1starty);
		return false;
	}

	/**
	 * This method checks if the player is able to move from the current field
	 * and returns all reachable fields in an array (WIP)
	 * 
	 * @return
	 */
	public boolean checkReachableFields(int startx, int starty) {
		// check if field to check is within map range
		if (startx > 0 && startx < map.getGridWidth() && starty > 0
				&& starty < map.getGridHeight()) {
			if (map.getField(startx + 1, starty) instanceof FloorField
					|| map.getField(startx + 1, starty) instanceof DestroyableField) {
				if (map.getField(startx + 1, starty).getItem() instanceof Exit) {
					return true;
				}
				// next check
				checkReachableFields(startx + 1, starty);
				// if we get here-> x+1 is through so we need to set back the
				// position to
				// the initial starting point
				startx = p1startx;
				starty = p1starty;
			} else if (map.getField(startx - 1, starty) instanceof FloorField
					|| map.getField(startx - 1, starty) instanceof DestroyableField) {
				if (map.getField(startx - 1, starty).getItem() instanceof Exit) {
					return true;
				}
				// next check
				checkReachableFields(startx - 1, starty);
				// x-1 is through: setback!
				startx = p1startx;
				starty = p1starty;
			} else if (map.getField(startx, starty + 1) instanceof FloorField
					|| map.getField(startx, starty + 1) instanceof DestroyableField) {
				if (map.getField(startx, starty + 1).getItem() instanceof Exit) {
					return true;
				}
				// next check
				checkReachableFields(startx, starty + 1);
				// y+1 is through: setback!
				startx = p1startx;
				starty = p1starty;
			} else if (map.getField(startx, starty - 1) instanceof FloorField
					|| map.getField(startx, starty - 1) instanceof DestroyableField) {
				if (map.getField(startx, starty - 1).getItem() instanceof Exit) {
					return true;
				}
				// next check
				checkReachableFields(startx, starty - 1);
				// y-1 is through: setback!
				startx = p1startx;
				starty = p1starty;
			}
		}
		return false;
	}

	/**
	 * selects a field at (x,y), coordinates in pixels
	 * 
	 * @param x
	 * @param y
	 */
	public void selectField(int x, int y) {
		// check if field to select is within borders
		if (x >= 35 && x <= 605 && y >= 35 && y <= 445) {
			Field f = map.getFieldByPixel(x, y);
			f.setImage("../images/dbedit_field_selected.png");
			repaint();
			f.setSelected(true);
		}
	}

	/**
	 * paints everything
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics g2d = (Graphics2D) g;
		for (int y = 0; y < 480; y += 32) {
			for (int x = 0; x < 640; x += 32) {
				g2d.drawImage(map.getFieldByPixel(x, y).getImageIcon()
						.getImage(), x, y, this);
			}
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		selectField(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}