import javax.swing.ImageIcon;

/**
 * This is the variation of enemy that chases the player in any direction.
 * The sprite is Blinky from Pac-Man.
 * 
 * @author julianda, chois3, and jinm
 *
 */
public class Monster3 extends Enemy {

	public Monster3(int xPos, int yPos, GameComponent component, Player player) {
		super(xPos, yPos, component, player);
		this.dx = 1;
		this.dy = 4;
		this.width = 25;
		this.height = 25;
		this.scoreVal = 20;
		this.tracksX = true;
		this.tracksY = true;
		this.sprite = new ImageIcon("blinky.gif").getImage();
	}

	/**
	 * Updates position
	 */
	@Override
	public void update() {
		super.update(this.tracksX, this.tracksY);
	}

	/**
	 * Unused shoot
	 */
	@Override
	public void shoot() {
		// Unused
	}
}