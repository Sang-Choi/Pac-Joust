import javax.swing.ImageIcon;

/**
 * This is the variation of enemy that fires bullets and doesn't track.
 * The sprite is Clyde from Pac-Man.
 * 
 * @author julianda, chois3, and jinm
 *
 */
public class Monster2 extends Enemy {

	public Monster2(int xPos, int yPos, GameComponent component, Player player) {
		super(xPos, yPos, component, player);
		this.dx = 3;
		this.dy = 0;
		this.width = 70;
		this.height = 70;
		this.scoreVal = 200;
		this.sprite = new ImageIcon("clyde.gif").getImage();
	}

	/**
	 * Updates position and fires a bullet every 200 ticks.
	 */
	@Override
	public void update() {
		super.update(this.tracksX, this.tracksY);
		if (this.component.getTick() % 200 == 0) {
			shoot();
		}
		applyGravity();
	}

	/**
	 * Fires a bullet in the direction it is moving, adding it to the component.
	 */
	@Override
	public void shoot() {
		int direction = this.dx / Math.abs(this.dx);
		this.component.addBullet(direction * 10, 0, this.xPos, this.yPos + this.height / 2);
	}
}
