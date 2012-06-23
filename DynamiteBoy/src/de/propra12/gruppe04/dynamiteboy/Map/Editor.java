package de.propra12.gruppe04.dynamiteboy.Map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Editor extends JPanel {
	JFrame frame;
	private boolean running = true;
	Map map;

	public Editor(JFrame frame) {
		this.map = new Map(640, 480, "Map1.xml");
		this.frame = frame;
		setFocusable(true);
		repaint();
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
}
