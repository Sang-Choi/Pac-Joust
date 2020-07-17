import javax.swing.ImageIcon;

/**
 * This is the variation of enemy fires bullets down and stays at a given y position while traversing the screen.
 * The sprite is Inky from Pac-Man.
 * 
 * @author julianda, chois3, and jinm
 *
 */
public class Monster4 extends Enemy {

	public Monster4(int xPos, int yPos, GameComponent component, Player player) {
		super(xPos, yPos, component, player);
		this.dx = 5;
		this.dy = 0;
		this.width = 25;
		this.height = 25;
		this.scoreVal = 20;
		this.sprite = new ImageIcon("inky.gif").getImage();
	}

	/**
	 * Updates position and fires a bullet every 200 ticks.
	 */
	@Override
	public void update() {
		super.update(this.tracksX, this.tracksY);
		if(this.component.getTick() % 100 == 0) {
			if(Math.random() > 0.5) {
				this.shoot();
			}
		}
	}

	/**
	 * Fires a bullet, adding it to the component.
	 */
	@Override
	public void shoot() {
		this.component.addBullet(0, 10, this.xPos, this.yPos + this.height / 2);
	}
}