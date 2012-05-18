package de.propra12.gruppe04.dynamiteboy.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.propra12.gruppe04.dynamiteboy.Game.Game;

public class MainMenu extends Menu {

	private int frameWidth;
	private int frameHeight;
	private JButton buttonStart;

	/**
	 * @param width
	 *            Window-width
	 * @param height
	 *            Window-height
	 */
	public MainMenu(int width, int height) {
		// TODO change mainmenu into JPanel instead of JFrame
		this.frameWidth = width;
		this.frameHeight = height;
		JFrame frame = new JFrame();

		buttonStart = new JButton("Spiel starten");
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonStart.setText("Spiel wird gestartet...");

				add(new Game());
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setSize(665, 520);
				setLocationRelativeTo(null);
				setTitle("DynamiteBoy");
				setResizable(false);
				setVisible(true);

				// Start Game here
			}

		});
		frame.getContentPane().add(buttonStart);
		frame.setSize(this.frameWidth, this.frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public int getWidth() {
		return this.frameWidth;
	}

	public int getHeigt() {
		return this.frameHeight;
	}

	public void setUpGui() {

	}
}
