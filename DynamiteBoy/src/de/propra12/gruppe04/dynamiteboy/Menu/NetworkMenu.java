package de.propra12.gruppe04.dynamiteboy.Menu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import de.propra12.gruppe04.dynamiteboy.Game.C;
import de.propra12.gruppe04.dynamiteboy.Game.NetworkGame;

public class NetworkMenu {
	private JFrame frame;
	private JRadioButton beClient;
	private JRadioButton beServer;
	private ButtonGroup buttonGroup;
	private JLabel chooseLabel;
	private JPanel choosePanel = new JPanel();
	private JLabel ipLabel;
	private JPanel ipPanel = new JPanel();
	private JButton buttonConfirm;
	private JPanel confirmPanel = new JPanel();
	private JButton buttonConnect;
	private JLabel confirmLabel;
	private String ipAdressToConnect;

	// IP Address Verification
	private static final String IPADDRESS_REGEX = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private Pattern pattern = Pattern.compile(IPADDRESS_REGEX);
	private Matcher matcher;

	public NetworkMenu(final JFrame frame) {
		this.frame = frame;
		frame.setTitle("DynamiteBoy - Netzwerkmenü");

		// Stuff referring to the choosePanel @ NORTH
		beClient = new JRadioButton("Client");
		beServer = new JRadioButton("Server");
		chooseLabel = new JLabel("What are you?");
		buttonGroup = new ButtonGroup();
		buttonGroup.add(beClient);
		buttonGroup.add(beServer);
		beClient.setSelected(true);
		buttonConfirm = new JButton("Auswahl bestätigen");
		choosePanel.add(chooseLabel);
		choosePanel.add(beClient);
		choosePanel.add(beServer);
		choosePanel.add(buttonConfirm);

		/**
		 * If beClient is selected display input dialog for ServerSocket to
		 * connect to. If beServer is selected start GameServer.
		 */
		buttonConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (beClient.isSelected()) {
					ipAdressToConnect = JOptionPane.showInputDialog(frame,
							"Bitte IP Adresse eingeben" + "\n"
									+ "Zum Beispiel \"192.168.0.1\"",
							"127.0.0.1");
					if (ipAdressToConnect != null) {
						if (!validate(ipAdressToConnect)) {
							JOptionPane.showMessageDialog(frame,
									"IP Adresse nicht korrekt");
						} else if (validate(ipAdressToConnect)) {
							setConnectButtonActive();
						}
					}

				} else if (beServer.isSelected()) {
					setConnectButtonActive();
				}
			}

		});

		// Stuff referring to the ipPanel @ CENTER
		ipPanel.setLayout(new BoxLayout(ipPanel, BoxLayout.Y_AXIS));
		ipPanel.setBorder(BorderFactory.createBevelBorder(0));
		JLabel steps1 = new JLabel(
				"Bitte wählen sie ihren Verbindungstyp und bestätigen sie die Auswahl!");
		ipPanel.add(steps1);
		JLabel steps2 = new JLabel("Dann erst können sie sich Verbinden");
		ipPanel.add(steps2);
		JLabel warningLabel = new JLabel(
				"BEACHTEN SIE DAS DER SERVER ZUERST STARTEN MUSS!");
		Icon icon = UIManager.getIcon("OptionPane.warningIcon");
		warningLabel.setIcon(icon);
		ipPanel.add(warningLabel);
		JLabel blanklabel = new JLabel(" ");
		ipPanel.add(blanklabel);
		getInterfaces(); // Display IP address

		// Stuff referring to the connectPanel @ SOUTH
		buttonConnect = new JButton("Verbinden");
		confirmPanel.add(buttonConnect);

		// Add everything to the frame and set it visible
		frame.add(BorderLayout.NORTH, choosePanel);
		frame.add(BorderLayout.CENTER, ipPanel);
		frame.add(BorderLayout.SOUTH, confirmPanel);
		choosePanel.setVisible(true);

	} // Close constructor

	private void setConnectButtonActive() {
		buttonConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				buttonConnect.setText("Spiel wird gestartet...");

				loadGame(ipAdressToConnect);
			}

		});
	}

	private void loadGame(String ip) {
		int type = C.CLIENT;
		if (beServer.isSelected()) {
			type = C.SERVER;
		}
		choosePanel.setVisible(false);
		ipPanel.setVisible(false);
		confirmPanel.setVisible(false);
		NetworkGame netG = new NetworkGame(frame, ip, type, "Maze.xml");
		frame.getContentPane().add(netG);
		netG.setVisible(true);
	}

	/**
	 * Validate ip address with regular expression
	 * 
	 * @param ip
	 *            ip address for validation
	 * @return true if ip is valid
	 */
	public boolean validate(final String ip) {
		matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	/**
	 * Gets all ip-addresses of the current system and prints them out
	 * 
	 */
	private void getInterfaces() {
		try {
			Enumeration e = NetworkInterface.getNetworkInterfaces();

			while (e.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) e.nextElement();
				System.out.println("Net interface: " + ni.getName());
				Enumeration e2 = ni.getInetAddresses();

				while (e2.hasMoreElements()) {
					InetAddress ip = (InetAddress) e2.nextElement();
					System.out.println("IP address: " + ip.toString());
					String ipString = ip.toString();
					ipLabel = new JLabel("Net interface: " + ni.getName()
							+ " IP: " + ipString);
					ipPanel.add(ipLabel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // close getInterfaces()

}
