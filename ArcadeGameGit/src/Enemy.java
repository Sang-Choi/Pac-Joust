import java.awt.Graphics2D;

/**
 * Abstract class that is used by each monster and egg.
 * 
 * @author julianda, chois3 and jinm
 *
 */
public abstract class Enemy extends Entity {

	protected int scoreVal;
	protected Player target;
	protected boolean tracksX;
	protected boolean tracksY;

	public Enemy(int xPos, int yPos, GameComponent component, Player target) {
		super(xPos, yPos, component);
		this.target = target;
	}

	/**
	 * Updates the enemy's position, tracks the player if applicable, and applies
	 * gravity.
	 */
	public void update(boolean tracksX, boolean tracksY) {
		checkBounds();
		this.prevX = this.xPos;
		this.prevY = this.yPos;
		if (this.tracksX) {
			int xToTarget = this.target.getX() - this.xPos;
			if (xToTarget < -10) {
				this.dx = -Math.abs(this.dx);
			} else if (xToTarget > 10) {
				this.dx = Math.abs(this.dx);
			}
		}
		if (this.tracksY) {
			int yToTarget = this.target.getY() - this.yPos;
			if (yToTarget < 8) {
				this.dy = -Math.abs(this.dy);
			} else if (yToTarget > 8) {
				this.dy = Math.abs(this.dy);
			}
		}
		this.xPos += this.dx;
		this.yPos += this.dy;
	}

	public abstract void shoot();

	/**
	 * Drops an egg, removes itself, and grants some points.
	 */
	public void death() {
		this.component.addEgg(new Egg(this.xPos + this.width / 2, this.yPos, this.component));
		this.markToRemove();
		this.component.addScore(this.scoreVal);
	}

	/**
	 * Draws itself in the direction it is moving
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		if (this.dx < 0) {
			g2.drawImage(this.sprite, xPos + width, yPos, -width, height, component);
		} else {
			g2.drawImage(this.sprite, xPos, yPos, width, height, component);
		}
	}

	/**
	 * Handles collision with a tile, reversing direction when bumping into a side.
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
	 * Handles colliding with the edge of the frame.
	 */
	@Override
	public void checkBounds() {
		if (this.xPos < 0) {
			this.xPos = 0;
			this.dx = -this.dx;
		} else if (this.xPos + this.width > this.component.getWidth()) {
			this.xPos = this.component.getWidth() - this.width;
			this.dx = -this.dx;
		} else if (this.yPos < 0) {
			this.yPos = 0;
			this.dy = -this.dy;
		} else if (this.yPos + this.height > this.component.getHeight()) {
			this.yPos = this.component.getHeight() - this.height;
			this.dy = -this.dy;
		}
	}
}
