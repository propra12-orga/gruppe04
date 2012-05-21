package de.propra12.gruppe04.dynamiteboy.Map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Game.Player;

public class Map extends JPanel {
	private static Field[][] FieldGrid;
	private Player player;
	private GridLayout MapLayout = new GridLayout(0, 20);

	/**
	 * 
	 * @param width
	 *            Map-width in px
	 * @param height
	 *            Map-height in px
	 */
	public Map(int width, int height, Player player) {
		generateFieldGrid(width, height);
		setFocusable(true);
		this.player = player;
		this.addKeyListener(new KAdapter());
		setLayout(MapLayout);
		paintFields();
	}

	/**
	 * Generates the grid and sets up values for "blocked" and "unblocked".
	 * 
	 * @param width
	 *            Map-width in px
	 * @param height
	 *            Map-height in px
	 */
	private void generateFieldGrid(int width, int height) {
		int gridWidth = width / 32;
		int gridHeight = height / 32;
		FieldGrid = new Field[gridWidth][gridHeight];
		WallField blocked = new WallField(true, false, 0);
		FloorField unblocked = new FloorField();
		// Set unblocked-references
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				FieldGrid[i][j] = unblocked;
			}
		}
		// Set blocked-references
		for (int i = 0; i < gridHeight; i++) {
			FieldGrid[0][i] = blocked;
			FieldGrid[gridWidth - 1][i] = blocked;
		}
		for (int i = 0; i < gridWidth; i++) {
			FieldGrid[i][0] = blocked;
			FieldGrid[i][gridHeight - 1] = blocked;
		}
		for (int i = 2; i < (gridWidth / 2) - 2; i += 2) {
			for (int j = 2; j < gridHeight - 2; j += 2)
				FieldGrid[i][j] = blocked;
		}
		for (int i = ((gridWidth / 2) - 1); i < gridWidth - 2; i += 2) {
			for (int j = 2; j < gridHeight - 2; j += 2)
				FieldGrid[i][j] = blocked;
		}

	}

	/**
	 * 
	 * @param x
	 *            x-coordinate of fieldposition
	 * @param y
	 *            y-coordinate of fieldposition
	 * @return Field object
	 */
	public Field getFieldGrid(int x, int y) {
		Field f = FieldGrid[x][y];
		return f;
	}

	public static Field getFieldGridByPixel(int x, int y) {
		Field f = FieldGrid[(x / 32)][(y / 32)];
		return f;
	}

	public void paintFields() {
		for (int y = 0; y < 480 / 32; y++) {
			for (int x = 0; x < 640 / 32; x++) {
				JLabel pField = new JLabel(this.getFieldGrid(x, y)
						.getImageIcon());
				add(pField);
			}
		}

	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	private class KAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
			player.move();
			repaint();
		}

		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
			player.move();
			repaint();
		}
	}

}
