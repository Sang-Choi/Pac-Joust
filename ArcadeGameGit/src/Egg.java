import javax.swing.ImageIcon;

/**
 * The egg class drops upon enemy death. Player contact picks it up and adds
 * points to the total. If not picked up within 200 ticks, spawns a new Monster.
 * 
 * @author julianda, chois3, and jinm
 *
 */
public class Egg extends Enemy {

	private int spawnTick;

	public Egg(int xPos, int yPos, GameComponent component) {

		super(xPos, yPos, component, null);
		this.scoreVal = 500;
		this.spawnTick = this.component.getTick();
		this.xPos = xPos;
		this.yPos = yPos;
		this.component = component;
		this.width = 30;
		this.height = 30;
		this.sprite = new ImageIcon("eyesEgg.png").getImage();
	}

	/**
	 * Collides with tiles.
	 */
	@Override
	public void collideWithTile(int x, int y, int height, int width) {
		if (prevY + this.height <= y) {
			this.yPos = y - this.height;
		} else if (prevY >= y + height) {
			this.yPos = y + height;
		} else if (prevX < x) {
			this.xPos = x - this.width;
			this.dx = -this.dx;
		} else {
			this.xPos = x + width;
			this.dx = -this.dx;
		}
	}

	/**
	 * Updates the position.
	 */
	@Override
	public void update() {
		this.prevX = this.xPos;
		this.prevY = this.yPos;
		this.xPos += this.dx;
		this.yPos += this.dy;
		applyGravity();
		newMonster();
	}

	/**
	 * If 200 ticks have passed since the egg spawned, creates a new monster and
	 * removes itself.
	 */
	public void newMonster() {
		if (component.getTick() - spawnTick > 200) {
			this.shoot();
			this.markToRemove();
		}
	}

	/**
	 * If touched within 200 ticks, adds points and removes itself.
	 */
	public void collect() {
		if (this.component.getTick() - this.spawnTick > 30) {
			this.component.addScore(this.scoreVal);
			this.markToRemove();
		}
	}

	/**
	 * "Shoots" by spawning a new, unique Monster3.
	 */
	@Override
	public void shoot() {
		component.addEnemy(new Monster3(this.xPos, this.yPos, this.component, this.component.getPlayer()));
	}

}
