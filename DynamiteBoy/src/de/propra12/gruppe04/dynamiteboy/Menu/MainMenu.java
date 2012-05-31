package de.propra12.gruppe04.dynamiteboy.Menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Game.Game;

public class MainMenu extends JPanel {
	private JButton ButtonStart1p;
	private JButton ButtonStart2p;
	private JFrame frame;
	private TitlePanel title = new TitlePanel();

	private JPanel panelButton = new JPanel(new GridLayout(1, 2)); // panelButton
																	// das
																	// GridLayout

	/**
	 * Constructor Sets up MainMenu with a Start Button to start the game
	 * 
	 * @param width
	 *            Window-width
	 * @param height
	 *            Window-height
	 */

	public void loadGame(int numberOfPlayers) {
		Game game = new Game(frame, numberOfPlayers);
		frame.getContentPane().add(game);
		title.setVisible(false);
		panelButton.setVisible(false);
		game.setVisible(true);
	}

	public MainMenu(final JFrame frame) {
		this.frame = frame;
		// final JFrame frame = new JFrame();
		ButtonStart1p = new JButton("1 Spieler");
		ButtonStart2p = new JButton("2 Spieler");
		panelButton.add(ButtonStart1p);
		panelButton.add(ButtonStart2p);

		ButtonStart1p.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ButtonStart1p.setText("Spiel wird gestartet...");
				loadGame(1);
			}

		});

		ButtonStart2p.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ButtonStart2p.setText("Spiel wird gestartet...");
				loadGame(2);
			}

		});

		frame.getContentPane().add(BorderLayout.CENTER, this.title);
		ButtonStart1p.setPreferredSize(new Dimension(100, 80));
		ButtonStart2p.setPreferredSize(new Dimension(100, 80));
		frame.getContentPane().add(BorderLayout.SOUTH, panelButton);
	}

	static class TitlePanel extends JPanel {
		private String titleScreenImage = "../images/db_menu_titlescreen.png";
		ImageIcon img = new ImageIcon(this.getClass().getResource(
				titleScreenImage));

		public void paintComponent(Graphics g) {
			g.drawImage(img.getImage(), 0, 0, this);

		}

	}

}
