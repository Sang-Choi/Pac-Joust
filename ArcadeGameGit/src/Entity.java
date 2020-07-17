import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;

/**
 * 
 * @author chois3 & julianda This is the entity abstract class. Here, all of our
 *         variables and methods that are universal for the three entities
 *         (monster, player & item) are held.
 */

public abstract class Entity {

	public static final int GRAV_FORCE = 1;
	public static final int TERMINAL_VEL = 5;

	protected int xPos;
	protected int yPos;
	protected int prevX;
	protected int prevY;
	protected int width;
	protected int height;
	protected Image sprite;
	protected GameComponent component;
	protected int dx;
	protected int dy;
	protected boolean shouldRemove;

	public Entity(int xPos, int yPos, GameComponent component) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.component = component;
	}

	public abstract void drawOn(Graphics2D g);

	public abstract void update();

	public abstract void collideWithTile(int x, int y, int height, int width);

	/**
	 * Applies the acceleration of gravity to objects.
	 */
	public void applyGravity() {
		this.dy += GRAV_FORCE;
		if (Math.abs(this.dy) > TERMINAL_VEL) {
			if (this.dy < 0) { // negative vel
				this.dy = -TERMINAL_VEL - 5;
			} else {
				this.dy = TERMINAL_VEL;
			}
		}
	}

	/**
	 * Returns the x-position.
	 * 
	 * @return
	 */
	public int getX() {
		return this.xPos;
	}

	/**
	 * Returns the y-postion.
	 * 
	 * @return
	 */
	public int getY() {
		return this.yPos;
	}

	/**
	 * Returns the object's width.
	 * 
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Returns the object's height.
	 * 
	 * @return
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Returns the bounding box of the object.
	 * 
	 * @return
	 */
	public Rectangle2D.Double getBox() {
		return new Rectangle2D.Double(this.xPos, this.yPos, getWidth(), getHeight());
	}

	/**
	 * Handles collision with the sides of the component.
	 */
	public void checkBounds() {
		if (this.xPos < 0) {
			this.xPos = 0;
		} else if (this.xPos + this.width > this.component.getWidth()) {
			this.xPos = this.component.getWidth() - this.width;
		}
		if (this.yPos < 0) {
			this.yPos = 0;
		} else if (this.yPos + this.height > this.component.getHeight()) {
			this.yPos = this.component.getHeight() - this.height;
		}
	}

	/**
	 * Marks this entity to be removed.
	 */
	public void markToRemove() {
		this.shouldRemove = true;
	}

	/**
	 * Returns whether the object has been marked to remove.
	 * 
	 * @return
	 */
	public boolean shouldRemove() {
		return this.shouldRemove;
	}
}
