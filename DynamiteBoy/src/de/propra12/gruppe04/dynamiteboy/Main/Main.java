package de.propra12.gruppe04.dynamiteboy.Main;
import javax.swing.JFrame;

import de.propra12.gruppe04.dynamiteboy.Game.Game;

public class Main extends JFrame {

	public Main() {
		add(new Game());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocationRelativeTo(null);
		setTitle("DynamiteBoy");
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}
}
