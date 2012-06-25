package de.propra12.gruppe04.dynamiteboy.Main;

import javax.swing.JFrame;

import de.propra12.gruppe04.dynamiteboy.Menu.MainMenu;

/**
 * 
 * The Main class is used for creating an Instance of DynamiteBoy <br>
 * So basically it starts the fun!
 * 
 */
public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(643, 540);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainMenu m = new MainMenu(frame);
		frame.setVisible(true);
	}
}