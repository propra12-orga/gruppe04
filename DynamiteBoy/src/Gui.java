import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Gui extends Graphics {
	private int frameWidth;
	private int frameHeight;
	private JButton buttonStart;

	/**
	 * @param width
	 *            Window-width
	 * @param height
	 *            Window-height
	 */
	public Gui(int width, int height) {
		this.frameWidth = width;
		this.frameHeight = height;
		JFrame frame = new JFrame();
		buttonStart = new JButton("Spiel starten");
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonStart.setText("Spiel wird gestartet...");
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
