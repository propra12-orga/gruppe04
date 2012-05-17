package de.propra12.gruppe04.dynamiteboy.Main;

import javax.swing.JFrame;

import de.propra12.gruppe04.dynamiteboy.Game.Game;
import de.propra12.gruppe04.dynamiteboy.Menu.MainMenu;

public class Main extends JFrame {

	public Main() {

		add(new MainMenu(640, 480));
		add(new Game());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(665, 520);
		setLocationRelativeTo(null);
		setTitle("DynamiteBoy");
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}
}
