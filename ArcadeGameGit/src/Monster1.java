
import javax.swing.ImageIcon;

/**
 * This monster does not fire bullets, and tracks the player's x position. The
 * sprite is Pinky from Pac-Man.
 * 
 * @author julianda, chois3, and jinm
 *
 */
public class Monster1 extends Enemy {

	public Monster1(int xPos, int yPos, GameComponent component, Player player) {
		super(xPos, yPos, component, player);
		this.dx = 2;
		this.dy = 0;
		this.height = 55;
		this.width = 55;
		this.scoreVal = 100;
		this.tracksX = true;
		this.tracksY = false;
		this.sprite = new ImageIcon("pinky.gif").getImage();
	}

	@Override
	public void update() {
		super.update(this.tracksX, this.tracksY);
		applyGravity();
	}

	/**
	 * Unused shoot method.
	 */
	@Override
	public void shoot() {
		// does nothing
	}

}
