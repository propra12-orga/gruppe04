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
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import de.propra12.gruppe04.dynamiteboy.Game.InputHandler;

/**
 * This class represents the Map Editor
 * 
 * @author ekki
 * 
 */
public class Editor extends JPanel implements MouseListener {
	private JFrame frame;
	private boolean running = true;
	private Map map;
	private String mapname;
	private String authorname;
	private String mapType;
	private InputHandler input;
	private JButton buttonChangeFieldType;
	private JButton buttonResetSelection;
	private JButton buttonSaveAndExit;
	private JPanel panelEdit = new JPanel(new GridLayout(1, 3));
	private Boolean selection = false;
	private String oldpicture;
	private Field f = null;
	private ButtonGroup buttonGroupFieldType;
	private JRadioButton radioButtonFloorField;
	private JRadioButton radioButtonWallField;
	private JRadioButton radioButtonDestroyableField;
	private ButtonGroup buttonGroupItemType;
	private JRadioButton radioButtonExitItem;

	// Add new items here to the editor
	// TODO find a way to handle added items dynamically (build a resource
	// manager that supplies the editor and the game with new items, maybe via
	// some sort of "entity model")

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
		// TODO: generate map-object and change mapname-element,author-element
		// and maptype-element
		// TODO map needs saveMap-method that saves dom into xml-file, name
		// based on mapname-element
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
				if (fieldType == "destroyable") {
					String itemType = getItemTypeDecision();
				}
				// TODO Save FieldType and ItemType to DOM-Object (has to get
				// coordinates from somewhere, too)
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
			}
		});
		buttonChangeFieldType.setPreferredSize(new Dimension(60, 32));
		buttonResetSelection.setPreferredSize(new Dimension(60, 32));
		buttonSaveAndExit.setPreferredSize(new Dimension(60, 32));
		frame.getContentPane().add(BorderLayout.SOUTH, panelEdit);
	}

	public String getSelectedButton(ButtonGroup group) {
		Enumeration<AbstractButton> e = group.getElements();
		while (e.hasMoreElements()) {
			AbstractButton b = e.nextElement();
			if (b.isSelected())
				return b.getText();
		}
		return null;
	}

	/**
	 * returns
	 * 
	 * Field Types: (1) "": empty string represents FloorField (2) "wall":
	 * WallField (3) "destroyable": DestroyableField
	 * 
	 * @return
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
		// TODO write method that checks whick radiobutton for item type of
		// wallfield has been chosen
		// and assigns it to the dom-object
		// TODO change return
		return authorname;
	}

	public boolean isMapValid(Map map) {
		// TODO write method that checks map vor validity
		return true;
	}

	public void resetSelectedField() {
		if (f != null) {
			f.setImage(oldpicture);
			repaint();
			selection = false;
		}
	}

	public void selectField(int x, int y) {
		if (x <= 640 && y <= 480 && !selection) {
			f = map.getFieldByPixel(x, y);
			oldpicture = f.fieldpic;
			f.setImage("../images/dbedit_field_selected.png");
			repaint();
			selection = true;
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