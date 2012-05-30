package de.propra12.gruppe04.dynamiteboy.Main;

import javax.swing.JFrame;

import de.propra12.gruppe04.dynamiteboy.Menu.MainMenu;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(643, 510);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		MainMenu m = new MainMenu(frame);
	}
}