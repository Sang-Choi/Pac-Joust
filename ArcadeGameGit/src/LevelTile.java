import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

/**
 * This is the class for a floor tile.
 * 
 * @author julianda, chois3, and jinm
 *
 */
public class LevelTile extends Entity {

	public LevelTile(int x, int y, int h, int w, GameComponent component) {
		super(x, y, component);
		this.width = w;
		this.height = h;
	}

	/**
	 * Draws the floor tile.
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(4));
		g2.draw(new RoundRectangle2D.Double(this.xPos, this.yPos, this.width, this.height, 20, 20));
	}

	@Override
	public void collideWithTile(int x, int y, int height, int width) {
		// does nothing
	}

	@Override
	public void update() {
		// does nothing
	}

}
