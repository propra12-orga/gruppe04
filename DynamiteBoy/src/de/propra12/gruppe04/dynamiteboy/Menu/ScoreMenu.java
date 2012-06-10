package de.propra12.gruppe04.dynamiteboy.Menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.propra12.gruppe04.dynamiteboy.Game.Game;

public class ScoreMenu extends JPanel {
	private JButton buttonBackToMain;
	private TitlePanel title = new TitlePanel();
	private static String winnerName;
	private static String loserName;
	private JFrame frame;
	private static int bombcount;

	/**
	 * Constructor Sets up MainMenu with a Start Button to start the game
	 * 
	 * @param width
	 *            Window-width
	 * @param height
	 *            Window-height
	 */
	public ScoreMenu(final JFrame frame, Game g) {
		this.frame = frame;
		this.winnerName = g.getWinnerName();
		this.loserName = g.getLoserName();
		this.bombcount = g.getBombcount();
		buttonBackToMain = new JButton("Zurück zum Hauptmenü");
		buttonBackToMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenu m = new MainMenu(frame);
				buttonBackToMain.setVisible(false);
				title.setVisible(false);
			}
		});
		frame.getContentPane().add(BorderLayout.CENTER, this.title);
		buttonBackToMain.setPreferredSize(new Dimension(100, 80));
		frame.getContentPane().add(BorderLayout.SOUTH, buttonBackToMain);
	}

	static class TitlePanel extends JPanel {
		private String titleScreenImage = "../images/db_menu_titlescreen.png";
		ImageIcon img = new ImageIcon(this.getClass().getResource(
				titleScreenImage));

		public void paintComponent(Graphics g) {
			g.drawImage(img.getImage(), 0, 0, this);
			g.drawString("Winner:" + winnerName, 50, 150);
			g.drawString("Loser:" + loserName, 50, 180);
			g.drawString(bombcount + " bombs used", 50, 210);
		}

	}

}
