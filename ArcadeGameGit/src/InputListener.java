import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * @author julianda, chois3, and jinm This method takes in the user's keyboard
 *         inputs and prompts action from the program based off which key was
 *         pressed.
 *
 */
public class InputListener implements KeyListener {

	private GameComponent gameComponent;
	private boolean l = false;
	private boolean r = false;
	private boolean u = false;
	private boolean d = false;

	public InputListener(GameComponent gameComponent) {
		this.gameComponent = gameComponent;
	}

	/**
	 * Marks true whichever directions are being pressed, then moves the player
	 * accordingly.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			l = true;
			break;

		case KeyEvent.VK_RIGHT:
			r = true;
			break;

		case KeyEvent.VK_UP:
			u = true;
			break;

		case KeyEvent.VK_DOWN:
			d = true;
			break;
		}
		gameComponent.movePlayer(l, r, u, d);
		if (keyCode == KeyEvent.VK_U) {
			gameComponent.changeLevel(true);
		} else if (keyCode == KeyEvent.VK_D) {
			gameComponent.changeLevel(false);
		} else if (keyCode == KeyEvent.VK_1) {
			gameComponent.superhero();

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			l = false;
			break;

		case KeyEvent.VK_RIGHT:
			r = false;
			break;

		case KeyEvent.VK_UP:
			u = false;
			break;

		case KeyEvent.VK_DOWN:
			d = false;
			break;
		}
		gameComponent.movePlayer(l, r, u, d);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
