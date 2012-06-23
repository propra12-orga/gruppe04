package de.propra12.gruppe04.dynamiteboy.Menu;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Map.Editor;
import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class EditorMenu extends JPanel {
	private JFrame frame;
	private Map map;
	private JButton buttonStartEditor;
	private JPanel panelButton = new JPanel(new GridLayout(1, 3));

	public EditorMenu(final JFrame frame) {
		this.frame = frame;
		frame.setTitle("DynamiteBoy - Karteneditor");
		buttonStartEditor = new JButton("Starte Editor");
		panelButton.add(buttonStartEditor);

		buttonStartEditor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panelButton.setVisible(false);
				Editor editor = new Editor(frame);
			}
		});
		frame.getContentPane().add(BorderLayout.SOUTH, panelButton);
	}

}
