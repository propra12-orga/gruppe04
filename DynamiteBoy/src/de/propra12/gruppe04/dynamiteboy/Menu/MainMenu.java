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
	private JButton ButtonNetworkPlay;
	private JFrame frame;
	private TitlePanel title = new TitlePanel();

	private JPanel panelButton = new JPanel(new GridLayout(1, 3));

	/**
	 * Constructor Sets up MainMenu with a Start Button to start the game
	 * 
	 */

	public MainMenu(final JFrame frame) {
		this.frame = frame;
		frame.setTitle("DynamiteBoy - Hauptmenü");
		ButtonStart1p = new JButton("1 Spieler");
		ButtonStart2p = new JButton("2 Spieler");
		ButtonNetworkPlay = new JButton("Netzwerkspiel");
		panelButton.add(ButtonStart1p);
		panelButton.add(ButtonStart2p);
		panelButton.add(ButtonNetworkPlay);

		/**
		 * Sets up a single player game
		 */
		ButtonStart1p.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ButtonStart1p.setText("Spiel wird gestartet...");
				loadGame(1, "Map1.xml");
			}

		});
		/**
		 * Sets up a two player game
		 */
		ButtonStart2p.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ButtonStart2p.setText("Spiel wird gestartet...");
				loadGame(2, "Maze.xml");
			}

		});

		/**
		 * Go to Network Menu for further setup
		 * 
		 */
		ButtonNetworkPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				title.setVisible(false);
				panelButton.setVisible(false);
				NetworkMenu nMenu = new NetworkMenu(frame);
			}

		});

		frame.getContentPane().add(BorderLayout.CENTER, this.title);
		ButtonStart1p.setPreferredSize(new Dimension(100, 80));
		ButtonStart2p.setPreferredSize(new Dimension(100, 80));
		frame.getContentPane().add(BorderLayout.SOUTH, panelButton);
	}

	public void loadGame(int numberOfPlayers, String mapName) {
		Game game = new Game(frame, numberOfPlayers, mapName);
		frame.getContentPane().add(game);
		title.setVisible(false);
		panelButton.setVisible(false);
		game.setVisible(true);
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
