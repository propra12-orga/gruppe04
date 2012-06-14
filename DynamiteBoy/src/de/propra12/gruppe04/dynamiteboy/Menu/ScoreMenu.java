package de.propra12.gruppe04.dynamiteboy.Menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
	private static int player1BombCount;
	private static int player2BombCount;
	private static int gameMinutes;
	private static int gameSeconds;
	private static int playerCount;

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
		frame.setSize(643, 510);
		this.winnerName = g.getWinnerName();
		this.loserName = g.getLoserName();
		this.gameMinutes = (int) g.getCurrentGameTime() / 60;
		this.gameSeconds = (int) g.getCurrentGameTime() % 60;
		this.playerCount = g.getNumberOfPlayers();
		this.player1BombCount = g.getPlayer(0).BOMBS
				- g.getPlayer(0).getBombCount();
		if (playerCount > 1) {
			this.player2BombCount = g.getPlayer(1).BOMBS
					- g.getPlayer(0).getBombCount();
		}
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
			Font font = new Font("Ubuntu", Font.BOLD, 12);
			g.setFont(font);
			g.drawImage(img.getImage(), 0, 0, this);
			g.drawString("Time played: " + gameMinutes + ":" + gameSeconds, 50,
					120);
			g.drawString("Winner:" + winnerName, 50, 140);
			g.drawString("Loser:" + loserName, 50, 160);
			g.drawString("Player 1 used " + player1BombCount + " bombs", 50,
					180);
			if (playerCount > 1) {
				g.drawString("Player 2 used " + player2BombCount + " bombs",
						50, 200);
			}
		}

	}

}
