package de.propra12.gruppe04.dynamiteboy.Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Map.Map;

public class Game extends JPanel {
	private Player player;
	private Map map;
	private GridLayout MapLayout = new GridLayout(0, 20);

	public Game() {
		// SET UP
		this.map = new Map(640, 480);
		setPlayer(new Player(map));
		setFocusable(true);
		this.addKeyListener(new KAdapter());
		setLayout(MapLayout);
		paintFields();

	}

	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void paintFields() {
		for (int y = 0; y < 480 / 32; y++) {
			for (int x = 0; x < 640 / 32; x++) {
				JLabel pField = new JLabel(map.getField(x, y).getImageIcon());
				add(pField);
			}
		}

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

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

}
