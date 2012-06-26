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
	private Boolean fieldIsSelected = false;
	private Field f = null;
	private int fieldXPos;
	private int fieldYPos;
	private String oldpicture;

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
		buttonChangeFieldType = new JButton("Feld Ändern");
		buttonResetSelection = new JButton("Auswahl aufheben");
		buttonSaveAndExit = new JButton("Speichern und verlassen");
		panelEdit.add(buttonChangeFieldType);
		panelEdit.add(buttonResetSelection);
		panelEdit.add(buttonSaveAndExit);
		buttonChangeFieldType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fieldType = getFieldTypeDecision();
				if (fieldType == "") {
					map.setFloorField(fieldXPos, fieldYPos);
					repaint();
					fieldIsSelected = false;
				}
				if (fieldType == "wall") {
					map.setWallField(fieldXPos, fieldYPos);
					repaint();
					fieldIsSelected = false;
				}
				if (fieldType == "destroyable") {
					String itemType = getItemTypeDecision();
					map.setDestroyableField(fieldXPos, fieldYPos);
					f = map.getField(fieldXPos, fieldYPos);
					if (itemType == "exit") {
						Exit exit = new Exit(false);
						f.setItem(exit);
					}
					repaint();
					fieldIsSelected = false;

				}
			}
		});
		buttonResetSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetSelectedField();
			}
		});
		buttonSaveAndExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Set Main Settings
				map.setMapAuthor(authorname);
				map.setMapType(mapType);
				map.setMapName(mapname);
				map.saveFieldGridToXML();
			}
		});
		buttonChangeFieldType.setPreferredSize(new Dimension(60, 32));
		buttonResetSelection.setPreferredSize(new Dimension(60, 32));
		buttonSaveAndExit.setPreferredSize(new Dimension(60, 32));
		frame.getContentPane().add(BorderLayout.SOUTH, panelEdit);
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

	public String getItemTypeDecision() {
		// DEFAULT ITEM TYPE GETS SET HERE
		// TODO create constant
		String itemType = "No Item";
		Object[] itemTypes = { "No Item", "Exit" };
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
		}
		return itemType;
	}

	public boolean isMapValid(Map map) {
		// TODO write method that checks if map is valid
		// TODO singleplayer -> check if exit item is set
		// TODO singleplayer -> check if exit is reachable
		// TODO multiplayer -> check if 2 startpoints are set
		// TODO multiplayer -> check if startpoints are reachable
		return true;
	}

	public void resetSelectedField() {
		if (f != null) {
			f.setImage(oldpicture);
			repaint();
			fieldIsSelected = false;
		}
	}

	public void selectField(int x, int y) {
		if (x <= 640 && y <= 480 && !fieldIsSelected) {
			f = map.getFieldByPixel(x, y);
			fieldXPos = (x / 32);
			fieldYPos = (y / 32);
			System.out.println(fieldXPos);
			System.out.println(fieldYPos);
			oldpicture = f.fieldpic;
			f.setImage("../images/dbedit_field_selected.png");
			repaint();
			fieldIsSelected = true;
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