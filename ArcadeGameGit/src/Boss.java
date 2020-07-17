import javax.swing.ImageIcon;

/**
 * This is the boss enemy. It spawns Monster3 periodically and traverses back
 * and forth across the screen.
 * 
 * @author julianda, chois3, and jinm
 *
 */
public class Boss extends Enemy {

	public Boss(int xPos, int yPos, GameComponent component, Player player) {
		super(xPos, yPos, component, player);
		this.dx = 2;
		this.dy = 0;
		this.width = 100;
		this.height = 200;
		this.scoreVal = 5000;
		this.sprite = new ImageIcon("betrayus.gif").getImage();
	}

	/**
	 * Updates position and spawns a small enemy every 375 ticks.
	 */
	@Override
	public void update() {
		this.dy = (int) (3 * Math.sin(this.component.getTick() / 20));
		super.update(tracksX, tracksY);
		if (this.component.getTick() % 375 == 0) {
			this.shoot();
		}
	}

	/**
	 * The boss's death signifies the end of the game.
	 */
	@Override
	public void death() {
		this.markToRemove();
		this.component.addScore(this.scoreVal);
		this.component.win();
	}

	/**
	 * "Shoots" an annoying Monster3 at the player.
	 */
	@Override
	public void shoot() {
		component.addEnemy(new Monster3(this.xPos, this.yPos, this.component, this.component.getPlayer()));
	}
}