
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 * Player is the user-controlled entity. The player when instantiated, has a
 * position changed by the arrow keys, and is able to kill enemies when its
 * midpoint is above their midpoint.
 * 
 * @author chois3, julianda, and jinm
 * 
 *
 */
public class Player extends Entity {

	private int lives;
	private boolean invuln;
	private int invulnTick;
	private String name;

	public Player(int xPos, int yPos, GameComponent component, String name) {
		super(xPos, yPos, component);
		this.dx = 0;
		this.dy = 0;
		this.height = 50;
		this.width = 50;
		this.lives = 3;
		this.name = name;
	}

	/**
	 * Draws the character, with its sprite facing its direction of motion.
	 * 
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		if (invuln) {
			this.sprite = new ImageIcon("InvulnPacman.gif").getImage();
		} else {
			this.sprite = new ImageIcon("pacman.gif").getImage();
		}
		if (this.dx < 0) {
			g2.drawImage(this.sprite, xPos + width, yPos, -width, height, component);
		} else {
			g2.drawImage(this.sprite, xPos, yPos, width, height, component);
		}
	}

	/**
	 * Updates position, ending invulnerability if it has been 100 ticks. Saves
	 * previous position for collision, and calls checkBounds and applies gravity.
	 */
	public void update() {
		if (this.component.getTick() > this.invulnTick + 100) {
			this.invuln = false;
		}
		prevX = this.xPos;
		prevY = this.yPos;
		xPos += this.dx;
		yPos += this.dy;
		checkBounds();
		applyGravity();
	}

	/**
	 * Handles collision with a floor tile.
	 */
	@Override
	public void collideWithTile(int x, int y, int height, int width) {
		if (prevY + this.height <= y) {
			this.yPos = y - this.height;
		} else if (prevY >= y + height) {
			this.yPos = y + height;
		} else if (prevX < x) {
			this.xPos = x - this.width;
		} else {
			this.xPos = x + width;
		}
	}

	/**
	 * This method considers the cases if left & right or up & down are pressed at
	 * the same time. If both are pressed at the same time, they cancel each other
	 * out. Otherwise, they move in the direction of the arrow buttons pressed.
	 * 
	 * @param l
	 * @param r
	 * @param u
	 * @param d
	 */
	public void setSpeed(boolean l, boolean r, boolean u, boolean d) {
		if ((!l && !r) || (l && r)) {
			this.dx = 0;
		} else if (l) {
			this.dx = -3;
		} else if (r) {
			this.dx = 3;
		}
		if ((!u && !d) || (u && d)) {
			this.dy = 0;
		} else if (u) {
			this.dy = -10;
			applyGravity();
		} else if (d) {
			this.dy = 5;
		}
	}

	/**
	 * Causes the player to lose a life, and returns true if all three have been
	 * lost.
	 * 
	 * @return
	 */
	public boolean loseLife() {
		if (!invuln) {
			this.lives--;
			PlayMusic.playMusic("pacman_death.wav");
			this.component.resetPlayer();
			this.invuln();
			if (this.lives == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the player's position to the given x and y values.
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}

	/**
	 * This function sets a small period of invulnerability after respawning.
	 */
	public void invuln() {
		this.invulnTick = this.component.getTick();
		this.invuln = true;
	}

	/**
	 * This method returns how many lives the player has left.
	 * 
	 * @return
	 */
	public int getLives() {
		return this.lives;
	}

	/**
	 * Returns the player's name.
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the player's lives the the number given.
	 * 
	 * @param lives
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}
}
