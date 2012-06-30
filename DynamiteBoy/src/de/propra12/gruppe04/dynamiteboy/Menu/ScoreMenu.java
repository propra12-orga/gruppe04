package de.propra12.gruppe04.dynamiteboy.Menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	private static String[] score = new String[5];
	private static String[] names = new String[5];

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
		private String highscoreName;

		public void paintComponent(Graphics g) {
			Font font = new Font("Ubuntu", Font.BOLD, 12);
			g.setFont(font);
			g.drawImage(img.getImage(), 0, 0, this);
			g.drawString("Time played: " + gameMinutes + ":" + gameSeconds, 50,
					120);
			if (playerCount == 1) {
				loadScoreFromXML();
				if (winnerName == "Player 1") {
					g.drawString("You won!", 50, 140);
					int playerScore = this.calculateScore(gameMinutes,
							gameSeconds, player1BombCount);
					g.drawString("Your Score:" + playerScore, 350, 100);
					int playerPosition = newHighscorePosition(playerScore);
					if (playerPosition < 5) {
						String highscoreName = JOptionPane
								.showInputDialog("Enter your Name");
						if (highscoreName != null) {
							names[playerPosition] = highscoreName;
						} else {
							highscoreName = "default";
							names[playerPosition] = highscoreName;
						}
					}
					// Output Highscore
					g.drawString("Highscores:", 350, 120);
					g.drawString(score[0] + ": " + names[0], 350, 140);
					g.drawString(score[1] + ": " + names[1], 350, 160);
					g.drawString(score[2] + ": " + names[2], 350, 180);
					g.drawString(score[3] + ": " + names[3], 350, 200);
					g.drawString(score[4] + ": " + names[4], 350, 220);
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

		private int calculateScore(int gameMinutes, int gameSeconds,
				int player1BombCount) {
			// TODO Auto-generated method stub
			return 0;
		}
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
		return score;
	}

	/**
	 * returns the new high-score position, if there is one (from 0-4) if method
	 * returns 5 -> no new high score
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

	/**
	 * saves new score data
	 */
	public void saveScoreToXML() {

	}

	/**
	 * loads score data
	 */
	public static void loadScoreFromXML() {
		try {
			File mapData = new File(
					"src/de/propra12/gruppe04/dynamiteboy/Menu/scores.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(mapData);
			NodeList scores = doc.getElementsByTagName("score");
			for (int i = 0; i < scores.getLength(); i++) {
				Node node = scores.item(i);
				Element element = (Element) node;
				names[i] = element.getAttribute("name");
				score[i] = element.getTextContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}