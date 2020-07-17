import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The main class for your arcade game.
 * 
 * You can design your game any way you like, but make the game start
 * by running main here.
 * 
 * Also don't forget to write javadocs for your classes and functions!
 * 
 * @author Buffalo
 *
 */

/**
 * 
 * @author chois3, julianda, and jinm
 * 
 *         This is the class where the frame is drawn and the listener for human
 *         keyboard movements is detected. We have also implemented a timer so
 *         that the game is updated every "tick", refreshing the entire frame
 *         each tick so that player movements are very smooth.
 *
 */
public class Main {

	public static final int TICK_DELAY = 10;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		String name = JOptionPane.showInputDialog("Please enter your initials:");
		/**
		 * add a button to start and another to exit
		 */
		JFrame startMenu = new JFrame();
		JPanel menu = new JPanel();
		JLabel background = new JLabel();
		JButton starter = new JButton();
		JButton exit = new JButton();

		configureButton(starter, 300, "newgame.png", startMenu);
		configureButton(exit, 500, "exitgame.png", startMenu);

		/**
		 * set start screen's background jin
		 */
		ImageIcon backgroundPic = new ImageIcon("pac-joust.png");
		background.setIcon(backgroundPic);
		menu.setBounds(0, 0, backgroundPic.getIconWidth(), backgroundPic.getIconHeight());

		menu.setOpaque(false);
		menu.add(background);

		startMenu.setSize(1017, 817);
		startMenu.setTitle("Pac-Joust!");
		startMenu.setBackground(Color.BLACK);
		startMenu.add(menu);
		startMenu.setVisible(true);

		class StartListener implements ActionListener {

			private Font pacFont;
			private String name;

			public StartListener(String name) {
				this.name = name;
			}

			public void actionPerformed(ActionEvent e) {

				if (e.getSource().equals(starter)) {

					startMenu.setVisible(false);
					JFrame frame = new JFrame();

					frame.setSize(1017, 817);
					frame.setTitle("Pac-Joust!");

					/**
					 * creates the font used for the scoreboard from a pac-man font file
					 */
					try {
						this.pacFont = Font.createFont(Font.TRUETYPE_FONT, new File("pacmanFont.ttf"));
						this.pacFont = this.pacFont.deriveFont((float) 24.0);
					} catch (IOException e1) {
						System.out.println("Error!");
					} catch (FontFormatException e2) {
						System.out.println("Error!");
					}

					/**
					 * Creates the scoreboard
					 */
					JLabel scoreboard = new JLabel();
					scoreboard.setFont(this.pacFont);
					scoreboard.setForeground(Color.WHITE);
					scoreboard.setOpaque(true);
					scoreboard.setBackground(Color.BLACK);

					frame.add(scoreboard, BorderLayout.NORTH);

					GameComponent component = new GameComponent(scoreboard, this.name, frame);
					frame.add(component, BorderLayout.CENTER);
					InputListener listener = new InputListener(component);

					Timer timer = new Timer(TICK_DELAY, new GameAdvanceListener(component));
					timer.start();

					frame.addKeyListener(listener);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);

				} else if (e.getSource().equals(exit)) {
					System.exit(0);
				}
			}
		}
		starter.addActionListener(new StartListener(name));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

	}

	/**
	 * Customizes the buttons on the start menu with a custom look.
	 * 
	 * @param button
	 * @param yPos
	 * @param fileName
	 * @param frame
	 */
	public void configureButton(JButton button, int yPos, String fileName, JFrame frame) {
		button.setLocation(300, yPos);
		button.setSize(370, 250);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		ImageIcon buttonImage = new ImageIcon(fileName);
		Image changeSize = buttonImage.getImage().getScaledInstance(button.getWidth(), button.getHeight(),
				buttonImage.getImage().SCALE_DEFAULT);

		buttonImage = new ImageIcon(changeSize);
		button.setIcon(buttonImage);
		button.setPressedIcon(buttonImage);
		frame.add(button);
	}

}
