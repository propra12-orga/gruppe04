import javax.swing.JFrame;

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
