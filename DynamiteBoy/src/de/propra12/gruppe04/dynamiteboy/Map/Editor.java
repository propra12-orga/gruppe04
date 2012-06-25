package de.propra12.gruppe04.dynamiteboy.Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Game.InputHandler;

public class Editor extends JPanel implements MouseListener {
	JFrame frame;
	private boolean running = true;
	Map map;
	InputHandler input;
	private JButton buttonChangeFieldType;
	private JButton buttonResetSelection;
	private JButton buttonSaveAndExit;
	private JPanel panelEdit = new JPanel(new GridLayout(1, 3));
	private Boolean selection = false;
	private String oldpicture;
	private Field f = null;

	public Editor(JFrame frame) {
		this.map = new Map(640, 480, "MapTemplate.xml");
		this.frame = frame;
		setFocusable(true);
		addMouseListener(this);
		initGUI();
		repaint();
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
		g2d.setColor(Color.black);
		Font font = new Font("Ubuntu", Font.BOLD, 12);
		g2d.setFont(font);
		g2d.drawString("Time: ", 5, 492);
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