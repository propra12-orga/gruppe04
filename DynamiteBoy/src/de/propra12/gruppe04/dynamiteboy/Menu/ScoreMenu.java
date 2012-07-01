package de.propra12.gruppe04.dynamiteboy.Menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.propra12.gruppe04.dynamiteboy.Game.C;
import de.propra12.gruppe04.dynamiteboy.Game.Game;
import de.propra12.gruppe04.dynamiteboy.Game.NetworkGame;

/**
 * 
 * ScoreMenu is created when a game ends and statistics are displayed.
 * 
 */
public class ScoreMenu extends JPanel {
	private static JPanel highScoreInput;
	private JButton buttonBackToMain;
	private static JTextField inputPlayerName;
	private static JButton buttonSubmitPlayerName;
	private TitlePanel title = new TitlePanel();
	private static String winnerName;
	private static String loserName;
	private JFrame frame;
	protected String playerName;
	public static int playerScore;
	public static int newHighScorePosition;
	private static int player1BombCount;
	private static int player2BombCount;
	private static int gameMinutes;
	private static int gameSeconds;
	private static int playerCount;
	private static String[] score = new String[5];
	private static String[] names = new String[5];
	private static boolean enteredName;

	/**
	 * Constructor Sets up ScoreMenu with a Button to go back to the main Menu,
	 * and HighScore handling
	 * 
	 * @param frame
	 *            frame to display the Menu on
	 * 
	 * @param g
	 *            game object to get parameters e.g. winner-name or looser-name
	 * 
	 */
	public ScoreMenu(final JFrame frame, final Game g) {
		if (g.getMap().getMapType().equalsIgnoreCase("singleplayer")) {
			enteredName = false;
		}
		this.frame = frame;
		g.getMap().loadMapFromXML(g.getMapname() + ".xml");
		this.score = g.getMap().getScore();
		this.names = g.getMap().getNames();
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
		inputPlayerName = new JTextField(
				"Neuer Highscore! Gib deinen Namen hier ein.");
		buttonSubmitPlayerName = new JButton("Speichern");
		buttonSubmitPlayerName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!enteredName) {
					playerName = inputPlayerName.getText();
					names[newHighScorePosition] = playerName;
					score[newHighScorePosition] = "" + playerScore;
					g.getMap().setNames(names);
					g.getMap().setNames(score);
					highScoreInput.setVisible(false);
					title.updateTitle();
					enteredName = true;
				}

			}
		});
		buttonBackToMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// load old map data first, because we only want to change
				// scores
				g.getMap().loadMapFromXML(g.getMapname() + ".xml");
				// set scores
				g.getMap().setScore(score);
				g.getMap().setNames(names);
				// save
				g.getMap().saveMapToXML();
				// back to menu
				MainMenu m = new MainMenu(frame);
				buttonBackToMain.setVisible(false);
				title.setVisible(false);
			}
		});
		frame.getContentPane().add(BorderLayout.CENTER, this.title);
		buttonBackToMain.setPreferredSize(new Dimension(100, 80));
		inputPlayerName.setPreferredSize(new Dimension(60, 40));
		buttonSubmitPlayerName.setPreferredSize(new Dimension(60, 40));
		highScoreInput = new JPanel(new GridLayout(0, 2));
		highScoreInput.add(inputPlayerName);
		highScoreInput.add(buttonSubmitPlayerName);
		highScoreInput.setVisible(false);
		frame.getContentPane().add(BorderLayout.NORTH, highScoreInput);
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
		private String highscoreName;

		public void paint(Graphics g) {
			Font font = new Font("Ubuntu", Font.BOLD, 12);
			g.setFont(font);
			g.drawImage(img.getImage(), 0, 0, this);
			g.drawString("Time played: " + gameMinutes + ":" + gameSeconds, 50,
					120);
			if (playerCount == 1) {
				if (winnerName == "Player 1") {
					g.drawString("You won!", 50, 140);
					playerScore = this.calculateScore(gameMinutes, gameSeconds,
							player1BombCount);

					newHighScorePosition = newHighscorePosition(playerScore);
					if (newHighScorePosition < 5 && !enteredName) {
						highScoreInput.setVisible(true);
					}
					// Output Highscores
					g.drawString("Highscores:", 350, 85);
					g.drawString("Your Score:" + playerScore, 350, 100);
					g.drawString("#1: " + score[0] + ": " + names[0], 350, 115);
					g.drawString("#2: " + score[1] + ": " + names[1], 350, 130);
					g.drawString("#3: " + score[2] + ": " + names[2], 350, 145);
					g.drawString("#4: " + score[3] + ": " + names[3], 350, 160);
					g.drawString("#5: " + score[4] + ": " + names[4], 350, 175);
				} else {
					g.drawString("You lost!", 50, 140);
				}
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

		void updateTitle() {
			repaint();
		}

		/**
		 * This method calculates the score based on game Duration and number of
		 * used bombs
		 * 
		 * @param gameMinutes
		 * @param gameSeconds
		 * @param bombs
		 * @return
		 */
		public int calculateScore(int gameMinutes, int gameSeconds, int bombs) {
			int score = 1000 - ((gameMinutes * 60 + gameSeconds) * 2 + (bombs * 20));
			if (score < 0) {
				score = 0;
			}
			return score;
		}

		/**
		 * returns the new high-score position, if there is one (from 0-4) if
		 * method returns 5 -> no new high score
		 * 
		 * @param playerScore
		 * @return
		 */
		public static int newHighscorePosition(int playerScore) {
			for (int i = 0; i < 5; i++) {
				int compareScore = Integer.parseInt(score[i]);
				if (compareScore < playerScore) {
					return i;
				}
			}
			return 5;
		}

	}

}