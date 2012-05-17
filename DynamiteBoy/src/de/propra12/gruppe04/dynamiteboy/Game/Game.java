package de.propra12.gruppe04.dynamiteboy.Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class Game extends JPanel implements ActionListener {
	private Timer timer;
	private Player player;
	private Map map = new Map(640, 480);
	private GridLayout MapLayout = new GridLayout(0, 20);

	public Game() {
		addKeyListener(new KAdapter());
		setFocusable(true);
		setLayout(MapLayout);
		loadFields();
		player = new Player();
		timer = new Timer(5, this);
		timer.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		player.move();
		repaint();
	}

	private void loadFields() {
		for (int y = 0; y < 480 / 32; y++) {
			for (int x = 0; x < 640 / 32; x++) {
				JLabel pField = new JLabel(map.getFieldGrid(x, y).getImage());
				add(pField);
			}
		}
	}

	private class KAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
	}
}
