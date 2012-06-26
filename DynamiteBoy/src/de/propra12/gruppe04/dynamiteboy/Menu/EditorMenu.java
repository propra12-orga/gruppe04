package de.propra12.gruppe04.dynamiteboy.Menu;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import de.propra12.gruppe04.dynamiteboy.Map.Editor;
import de.propra12.gruppe04.dynamiteboy.Map.Map;

/**
 * Takes the required settings for creating a map from the user and starts the
 * editor with the given parameters
 * 
 * @author ekki
 * 
 */
public class EditorMenu extends JPanel {
	/**
	 * for informations about the fields check initGUI()-method
	 */
	private JFrame frame;
	private Map map;
	private JPanel panelInput;
	private JPanel panelButton;
	private JLabel labelMapname;
	private JFormattedTextField textFieldMapname;
	private String mapname;
	private JLabel labelAuthorname;
	private JFormattedTextField textFieldAuthorname;
	private String authorname;
	private JLabel labelMaptype;
	private ButtonGroup buttonGroupMapType;
	private JRadioButton radioButtonSingleplayer;
	private JRadioButton radioButtonMultiplayer;
	private String mapType;
	private JButton buttonStartEditor;

	public EditorMenu(final JFrame frame) {
		// Setup frame
		this.frame = frame;
		frame.setTitle("DynamiteBoy - Karteneditor");
		initGUI();
		// Add Elements to frame
		frame.getContentPane().add(BorderLayout.NORTH, panelInput);
		frame.getContentPane().add(BorderLayout.SOUTH, panelButton);
	}

	public void initGUI() {
		// The panels (same order as displayed)
		panelInput = new JPanel(new GridLayout(0, 2));
		panelButton = new JPanel(new GridLayout(1, 1));
		// Now the GUI Elements for the different Settings:
		// mapname
		labelMapname = new JLabel("Name der Karte:", JLabel.LEFT);
		textFieldMapname = new JFormattedTextField();
		mapname = "default";
		// author name
		labelAuthorname = new JLabel("Name des Autors:", JLabel.LEFT);
		textFieldAuthorname = new JFormattedTextField();
		authorname = "default";
		// map type
		labelMaptype = new JLabel("Maptyp:", JLabel.LEFT);
		buttonGroupMapType = new ButtonGroup();
		radioButtonSingleplayer = new JRadioButton("Singleplayer");
		radioButtonSingleplayer.setSelected(true);
		radioButtonMultiplayer = new JRadioButton("Multiplayer");
		buttonGroupMapType.add(radioButtonSingleplayer);
		buttonGroupMapType.add(radioButtonMultiplayer);
		// Buttons and Dialogs
		buttonStartEditor = new JButton("Starte Editor");
		// Add the GUI-Elements to their JPanels
		panelInput.add(labelMapname);
		panelInput.add(textFieldMapname);
		panelInput.add(labelAuthorname);
		panelInput.add(textFieldAuthorname);
		panelInput.add(labelMaptype);
		panelInput.add(radioButtonSingleplayer);
		panelInput.add(radioButtonMultiplayer);
		panelButton.add(buttonStartEditor);
		buttonStartEditor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// process user input
				if (inputIsValid()) {
					// get user input
					mapname = textFieldMapname.getText();
					authorname = textFieldAuthorname.getText();
					mapType = getMapTypeDecision();
					// open Editor
					panelButton.setVisible(false);
					panelInput.setVisible(false);
					Editor editor = new Editor(frame, mapname, authorname,
							mapType);
					frame.add(editor);
					editor.setVisible(true);
				}
			}
		});
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

	public String getMapTypeDecision() {
		// DEFAULT MAP TYPE GETS SET HERE
		// TODO create constant
		String mapType = "singleplayer";
		if (getSelectedButton(buttonGroupMapType) == radioButtonSingleplayer
				.getText()) {
			mapType = "singleplayer";
			return mapType;
		} else if (getSelectedButton(buttonGroupMapType) == radioButtonMultiplayer
				.getText()) {
			mapType = "multiplayer";
			return mapType;
		}
		return mapType;
	}

	/**
	 * checks if all given user input is correct (even for windows filenames)
	 * 
	 * @return true/false
	 */
	public boolean inputIsValid() {
		if (!textFieldMapname
				.getText()
				.matches(
						"^(?!^(PRN|AUX|CLOCK\\$|NUL|CON|COM\\d|LPT\\d|\\..*)(\\..+)?$)[^\\x00-\\x1f\\\\?*:\\\";|/]+$")) {
			JOptionPane.showMessageDialog(frame, "Mapname ungültig");
			return false;
		}
		if (!textFieldAuthorname
				.getText()
				.matches(
						"^(?!^(PRN|AUX|CLOCK\\$|NUL|CON|COM\\d|LPT\\d|\\..*)(\\..+)?$)[^\\x00-\\x1f\\\\?*:\\\";|/]+$")) {
			JOptionPane.showMessageDialog(frame, "Name des Autors ungültig");
			return false;
		}
		// mapType has the default value "singleplayer" -> no need to check
		return true;
	}
}