import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

/**
 * The bullet class, shot by monsters.
 * 
 * @author julianda, chois3, and jinm
 *
 */

public class Bullet extends Entity {

	public Bullet(int dx, int dy, int xPos, int yPos, GameComponent component) {
		super(xPos, yPos, component);
		this.dx = dx;
		this.dy = dy;
		this.height = 15;
		this.width = 15;
	}

	/**
	 * Draws the bullet.
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fill(new RoundRectangle2D.Double(xPos, yPos, this.width, this.height, 15, 15));
	}

	/**
	 * Updates the bullet's position, checking to see if it has left the window.
	 */
	public void update() {
		this.prevX = this.xPos;
		this.xPos += this.dx;
		this.yPos += this.dy;
		if (this.xPos < 0 || this.xPos > this.component.getWidth()) {
			this.markToRemove();
		}

	}

	/**
	 * Handles when the bullet collides with a floor tile.
	 */
	@Override
	public void collideWithTile(int x, int y, int height, int width) {
		this.markToRemove();
	}

}
