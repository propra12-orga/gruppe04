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

import de.propra12.gruppe04.dynamiteboy.Game.C;
import de.propra12.gruppe04.dynamiteboy.Game.Game;
import de.propra12.gruppe04.dynamiteboy.Game.NetworkGame;

/**
 * 
 * ScoreMenu is created when a game ends and statistics are displayed.
 * 
 */
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
	 * Constructor Sets up ScoreMenu with a Button to go back to the main Menu
	 * 
	 * @param frame
	 *            frame to display the Menu on
	 * 
	 * @param g
	 *            game object to get parameters e.g. winner-name or looser-name
	 * 
	 */
	public ScoreMenu(final JFrame frame, Game g) {
		this.frame = frame;
		frame.setTitle("DynamiteBoy - Und der Gewinner ist...");
		this.winnerName = g.getWinnerName();
		this.loserName = g.getLoserName();
		this.gameMinutes = (int) g.getCurrentGameTime() / 60;
		this.gameSeconds = (int) g.getCurrentGameTime() % 60;
		this.playerCount = g.getNumberOfPlayers();
		this.player1BombCount = C.BOMBS - g.getPlayer(0).getBombCount();
		if (playerCount > 1) {
			this.player2BombCount = C.BOMBS - g.getPlayer(1).getBombCount();
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

	/**
	 * Constructor Sets up ScoreMenu with a Button to go back to the main Menu
	 * 
	 * @param frame
	 *            frame to display the Menu on
	 * 
	 * @param g
	 *            NetworkGame object to get parameters e.g. winner-name or
	 *            looser-name
	 * 
	 */
	public ScoreMenu(final JFrame frame, NetworkGame g) {
		this.frame = frame;
		frame.setTitle("DynamiteBoy - Und der Gewinner ist...");
		this.winnerName = g.getWinnerName();
		this.loserName = g.getLoserName();
		this.gameMinutes = (int) g.getCurrentGameTime() / 60;
		this.gameSeconds = (int) g.getCurrentGameTime() % 60;
		this.playerCount = 2;
		this.player1BombCount = C.BOMBS - g.getPlayer(0).getBombCount();
		this.player2BombCount = C.BOMBS - g.getPlayer(1).getBombCount();
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

	/**
	 * Class to paint the title image and display game statistics
	 * 
	 */
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
			if (playerCount == 1) {
				g.drawString("You won!", 50, 140);
			}
			if (playerCount > 1) {
				g.drawString("Winner:" + winnerName, 50, 140);
				g.drawString("Loser:" + loserName, 50, 160);
			}
			g.drawString("Player 1 used " + player1BombCount + " bombs", 50,
					180);
			if (playerCount > 1) {
				g.drawString("Player 2 used " + player2BombCount + " bombs",
						50, 200);
			}
		}

	}

}
