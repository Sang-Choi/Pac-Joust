import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author chois3, julianda, and jinm
 * 
 *         This class instantiates everything, and all methods run through this
 *         class.
 *
 *
 */

public class GameComponent extends JComponent {

	private JLabel scoreboard;
	private JFrame frame;
	private Player player;
	private Level level;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> monsters = new ArrayList<Enemy>();
	private ArrayList<Egg> eggs = new ArrayList<Egg>();
	private int currentLevel = 1;
	private int numOfLevels = 10;
	private int numTicks = 0;
	private int score = 0;
	private boolean reset = false;

	public GameComponent(JLabel scoreboard, String playerName, JFrame frame) {
		PlayMusic.playMusic("pacman_beginning.wav");
		this.frame = frame;
		this.player = new Player(0, 0, this, playerName);
		this.scoreboard = scoreboard;
		this.loadLevel();
	}

	/**
	 * This method sets the direction and speed of the player based off the arrow
	 * keys pressed
	 * 
	 * @param l
	 * @param r
	 * @param u
	 * @param d
	 */
	public void movePlayer(boolean l, boolean r, boolean u, boolean d) {
		this.player.setSpeed(l, r, u, d);
	}

	/**
	 * This method constantly refreshes the frame to update player movements and
	 * redraw the frame.
	 */
	public void updateState() {
		this.numTicks++;
		this.player.update();

		ArrayList<Entity> allEntities = new ArrayList<Entity>();
		allEntities.addAll(bullets);
		allEntities.addAll(monsters);
		allEntities.addAll(eggs);

		for (Entity entity : allEntities) {
			entity.update();
		}
		handleCollision();

		this.repaint();
	}

	/*
	 * Constructs all levels from text files based on the given number of levels.
	 */
	public void loadLevel() {
		this.monsters.clear();
		this.bullets.clear();
		this.eggs.clear();
		this.level = new Level(this.currentLevel, this);
		this.monsters.addAll(this.level.getMonsters());
		this.player.setPosition(this.level.playerX(), this.level.playerY());
	}

	/**
	 * When the 'u' or 'd' character is pressed, the level changes.
	 * 
	 * @param levelUp
	 */
	public void changeLevel(boolean levelUp) {
		if (levelUp && this.currentLevel < this.numOfLevels) {
			this.currentLevel++;
		} else if (!levelUp && this.currentLevel > 1) {
			this.currentLevel--;
		}
		loadLevel();
	}

	/**
	 * Everything is drawn. The scoreboard text is updated, the level is advanced if
	 * all enemies and eggs are cleared, and the game is reset to level 1
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.level.drawOn(g2);
		this.player.drawOn(g2);

		ArrayList<Entity> allEntities = new ArrayList<Entity>();
		allEntities.addAll(bullets);
		allEntities.addAll(monsters);
		allEntities.addAll(eggs);

		for (Entity entity : allEntities) {
			entity.drawOn(g2);
		}
		scoreboard.setText("Level " + this.currentLevel + "  " + this.player.getName() + " Lives: "
				+ this.player.getLives() + " Score: " + score);
		if (monsters.isEmpty() && eggs.isEmpty() && this.currentLevel < this.numOfLevels) {
			this.currentLevel++;
			this.loadLevel();
		}
		if (this.reset) {
			PlayMusic.playMusic("pacman_beginning.wav");
			this.resetGame();
		}
	}

	/**
	 * Resets the entire game back to level one.
	 */
	public void resetGame() {
		this.reset = false;
		this.score = 0;
		this.player.setLives(3);
		this.currentLevel = 1;
		this.loadLevel();
	}

	/**
	 * Checks all objects for collisions, and calls their appropriate collision
	 * methods in the event of collision.
	 */
	public void handleCollision() {
		ArrayList<Entity> allEntities = new ArrayList<Entity>();
		allEntities.addAll(bullets);
		allEntities.addAll(monsters);
		allEntities.addAll(eggs);

		for (LevelTile t : this.level.getTiles()) {
			if (t.getBox().intersects(player.getBox())) {
				player.collideWithTile(t.getX(), t.getY(), t.getHeight(), t.getWidth());
			}
			for (Enemy monster : monsters) {
				if (t.getBox().intersects(monster.getBox())) {
					monster.collideWithTile(t.getX(), t.getY(), t.getHeight(), t.getWidth());
				}
			}

			for (Egg egg : eggs) {
				if (t.getBox().intersects(egg.getBox())) {
					egg.collideWithTile(t.getX(), t.getY(), t.getHeight(), t.getWidth());
				}
			}

			for (Bullet bullet : bullets) {
				if (t.getBox().intersects(bullet.getBox())) {
					bullet.collideWithTile(t.getX(), t.getY(), t.getHeight(), t.getWidth());
				}
			}
		}
		for (Enemy monster : monsters) {
			if (monster.getBox().intersects(player.getBox())) {
				joust(monster, player);
			}
		}

		for (Bullet bullet : bullets) {
			if (bullet.getBox().intersects(player.getBox())) {
				if (player.loseLife()) {
					this.reset = true;
				}
			}
		}

		for (Egg egg : eggs) {
			if (egg.getBox().intersects(player.getBox())) {
				egg.collect();
			}
		}

		ArrayList<Entity> toRemove = new ArrayList<Entity>();

		for (Entity entity : allEntities) {
			if (entity.shouldRemove()) {
				toRemove.add(entity);
			}
		}

		for (Entity entity : toRemove) {
			this.bullets.remove(entity);
			this.monsters.remove(entity);
			this.eggs.remove(entity);
		}

	}

	/**
	 * Compares the midpoint of both the player and the enemy, killing whichever is
	 * lower.
	 * 
	 * @param monster
	 * @param player
	 */
	public void joust(Enemy monster, Player player) {
		if (player.getY() + (player.getHeight() / 2) < monster.getY() + (monster.getHeight() / 2)) {
			PlayMusic.playMusic("pacman_eatghost.wav");
			monster.death();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				
			}

		} else if (player.getY() + (player.getHeight() / 2) > monster.getY() + (monster.getHeight() / 2)) {
			if (player.loseLife()) {
				this.reset = true;
			}
		}
	}

	/**
	 * Returns the current tick.
	 * 
	 * @return
	 */
	public int getTick() {
		return this.numTicks;
	}

	/**
	 * Returns the player.
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Sets the player back to the default position
	 */
	public void resetPlayer() {
		this.player.setPosition(this.level.playerX(), this.level.playerY());
	}

	/**
	 * Stores a newly created egg in the appropriate ArrayList
	 * 
	 * @param egg
	 */
	public void addEgg(Egg egg) {
		this.eggs.add(egg);
	}

	/**
	 * Stores a newly created enemy in the appropriate ArrayList
	 * 
	 * @param monster
	 */
	public void addEnemy(Enemy monster) {
		this.monsters.add(monster);
	}

	/**
	 * Adds a bullet given parameters from the monster.
	 * 
	 * @param dx
	 * @param dy
	 * @param xPos
	 * @param yPos
	 */
	public void addBullet(int dx, int dy, int xPos, int yPos) {
		bullets.add(new Bullet(dx, dy, xPos, yPos, this));
	}

	/**
	 * Cheat to give player 999 lives
	 */
	public void superhero() {
		player.setLives(999);
	}

	/**
	 * Adds a point value to the total score
	 * 
	 * @param score
	 */
	public void addScore(int score) {
		this.score += score;
	}

	/**
	 * Creates a new win dialog, showing a win screen and giving you your final
	 * score, then exiting upon the button click.
	 */
	public void win() {
		JDialog win = new JDialog(this.frame, "You win!", null);

		JLabel winLabel = new JLabel("You have beaten Memelord Betrayus with a score of " + this.score + "!");
		win.getContentPane().add(winLabel, BorderLayout.NORTH);

		JLabel winImage = new JLabel(new ImageIcon("end.gif"));
		win.getContentPane().add(winImage, BorderLayout.CENTER);

		JButton close = new JButton("Exit Game");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		win.getContentPane().add(close, BorderLayout.SOUTH);
		win.setSize(500, 500);
		setVisible(true);
		win.setVisible(true);
	}
}
