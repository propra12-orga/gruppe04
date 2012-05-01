import javax.swing.JFrame;

public class Gui {
	private int frameWidth;
	private int frameHeight;

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
